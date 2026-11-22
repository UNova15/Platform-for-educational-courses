import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Modal from '../../ui/Modal/Modal';
import {
  registerUser,
  confirmRegistrationCode,
  resendRegistrationCode,
} from '../../../api/authApi';
import { UserIcon, MailIcon, LockIcon, EyeOpenIcon, EyeClosedIcon } from '../shared/icons';
import { useCountdown, formatCountdown } from '../../../hooks/useCountdown';
import logoImage from '../../../assets/icons/logo-modals.svg';
import authStyles from '../shared/auth.module.scss';

const EMAIL_REGEXP = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
const CODE_LENGTH = 6;
const CODE_VALIDITY_MS = 30 * 60 * 1000;
const MAX_CODE_ATTEMPTS = 5;
const RESEND_INTERVALS_SECONDS = [60, 300, 600, 900, 3600];

function RegistrationForm({ onClose, onSwitchToLogin }) {
  const [screen, setScreen] = useState('form'); // 'form' | 'code'
  const [login, setLogin] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);
  const [isConfirmVisible, setIsConfirmVisible] = useState(false);
  const [code, setCode] = useState('');
  const [formError, setFormError] = useState('');
  const [codeError, setCodeError] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [codeSentAt, setCodeSentAt] = useState(null);
  const [failedAttempts, setFailedAttempts] = useState(0);
  const [resendCount, setResendCount] = useState(0);
  const { secondsLeft, start, stop } = useCountdown();
  const navigate = useNavigate();

  function handleLoginChange(event) {
    setLogin(event.target.value);
    setFormError('');
  }
  function handleEmailChange(event) {
    setEmail(event.target.value);
    setFormError('');
  }
  function handlePasswordChange(event) {
    setPassword(event.target.value);
    setFormError('');
  }
  function handleConfirmChange(event) {
    setConfirmPassword(event.target.value);
    setFormError('');
  }

  function handleTogglePasswordVisibility() {
    setIsPasswordVisible(function flip(prev) {
      return !prev;
    });
  }

  function handleToggleConfirmVisibility() {
    setIsConfirmVisible(function flip(prev) {
      return !prev;
    });
  }

  function isCodeExpired() {
    return Date.now() - codeSentAt > CODE_VALIDITY_MS;
  }

  function resetToForm(message) {
    setScreen('form');
    setCode('');
    setCodeSentAt(null);
    setFailedAttempts(0);
    setResendCount(0);
    stop();
    setFormError(message);
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setFormError('');

    const trimmedLogin = login.trim();
    const trimmedEmail = email.trim();

    if (!trimmedLogin || !trimmedEmail || !password || !confirmPassword) {
      setFormError('Заполните все поля');
      return;
    }
    if (!EMAIL_REGEXP.test(trimmedEmail)) {
      setFormError('Неправильный адрес электронной почты');
      return;
    }
    if (password.length < 8) {
      setFormError('Длина пароля должна быть не менее 8 символов');
      return;
    }
    if (password !== confirmPassword) {
      setFormError('Пароли не совпадают');
      return;
    }

    setIsSubmitting(true);
    try {
      await registerUser({ login: trimmedLogin, email: trimmedEmail, password });
      setCodeSentAt(Date.now());
      setResendCount(0);
      setFailedAttempts(0);
      setCode('');
      setScreen('code');
      start(RESEND_INTERVALS_SECONDS[0]);
    } catch (error) {
      setFormError(error.backendMessage || 'Ошибка регистрации. Попробуйте позже');
    } finally {
      setIsSubmitting(false);
    }
  }

  function handleCodeChange(event) {
    const digits = event.target.value.replace(/\D/g, '').slice(0, CODE_LENGTH);
    setCode(digits);
    setCodeError('');
  }

  async function handleCodeSubmit(event) {
    event.preventDefault();
    setCodeError('');

    if (code.length < CODE_LENGTH) {
      setCodeError(`Введите ${CODE_LENGTH} цифр кода`);
      return;
    }
    if (isCodeExpired()) {
      resetToForm('Время действия кода истекло. Начните регистрацию заново');
      return;
    }

    setIsSubmitting(true);
    try {
      const data = await confirmRegistrationCode({ email: email.trim(), code });
      const token = data ? data.accessToken || data.token : null;

      if (token) {
        localStorage.setItem('accessToken', token);
      }
      onClose();
      if (token) {
        navigate('/');
      } else {
        onSwitchToLogin();
      }
    } catch (error) {
      const nextAttempts = failedAttempts + 1;
      setFailedAttempts(nextAttempts);

      if (nextAttempts >= MAX_CODE_ATTEMPTS) {
        resetToForm('Превышено число попыток ввода кода. Начните регистрацию заново');
      } else {
        setCodeError(error.backendMessage || 'Неверный код подтверждения');
      }
    } finally {
      setIsSubmitting(false);
    }
  }

  async function handleResendCode() {
    if (secondsLeft > 0 || isSubmitting) {
      return;
    }

    setIsSubmitting(true);
    try {
      await resendRegistrationCode({ email: email.trim() });
      const nextCount = resendCount + 1;
      setResendCount(nextCount);
      const interval =
        RESEND_INTERVALS_SECONDS[Math.min(nextCount, RESEND_INTERVALS_SECONDS.length - 1)];

      setCodeSentAt(Date.now());
      setCode('');
      setCodeError('');
      start(interval);
    } catch (error) {
      setCodeError(error.backendMessage || 'Не удалось отправить код повторно');
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <>
      <div className={authStyles.logoWrapper}>
        <img src={logoImage} alt="Логотип Kira" className={authStyles.logoImage} />
      </div>

      <h2 className={authStyles.title}>Добро пожаловать!</h2>
      <p className={authStyles.subtitle}>
        {screen === 'form' ? 'Создайте свой аккаунт' : `Код отправлен на ${email}`}
      </p>

      {screen === 'form' ? (
        <form className={authStyles.form} onSubmit={handleSubmit}>
          <label className={authStyles.inputField}>
            <span className={authStyles.inputIcon}>
              <UserIcon />
            </span>
            <input
              type="text"
              placeholder="Логин"
              className={authStyles.input}
              value={login}
              onChange={handleLoginChange}
            />
          </label>

          <label className={authStyles.inputField}>
            <span className={authStyles.inputIcon}>
              <MailIcon />
            </span>
            <input
              type="text"
              placeholder="Почта"
              className={authStyles.input}
              value={email}
              onChange={handleEmailChange}
            />
          </label>

          <label className={authStyles.inputField}>
            <span className={authStyles.inputIcon}>
              <LockIcon />
            </span>
            <input
              type={isPasswordVisible ? 'text' : 'password'}
              placeholder="Пароль"
              className={authStyles.input}
              value={password}
              onChange={handlePasswordChange}
            />
            <button
              type="button"
              className={authStyles.eyeButton}
              onClick={handleTogglePasswordVisibility}
              aria-label="Показать или скрыть пароль"
            >
              {isPasswordVisible ? <EyeOpenIcon /> : <EyeClosedIcon />}
            </button>
          </label>

          <label className={authStyles.inputField}>
            <span className={authStyles.inputIcon}>
              <LockIcon />
            </span>
            <input
              type={isConfirmVisible ? 'text' : 'password'}
              placeholder="Подтвердите пароль"
              className={authStyles.input}
              value={confirmPassword}
              onChange={handleConfirmChange}
            />
            <button
              type="button"
              className={authStyles.eyeButton}
              onClick={handleToggleConfirmVisibility}
              aria-label="Показать или скрыть пароль"
            >
              {isConfirmVisible ? <EyeOpenIcon /> : <EyeClosedIcon />}
            </button>
          </label>

          {formError && <p className={authStyles.errorText}>{formError}</p>}

          <button
            type="submit"
            className={`${authStyles.submitButton} ${authStyles.gapMedium}`}
            disabled={isSubmitting}
          >
            Зарегистрироваться
          </button>
        </form>
      ) : (
        <form className={authStyles.form} onSubmit={handleCodeSubmit}>
          <label className={authStyles.inputField}>
            <input
              type="text"
              inputMode="numeric"
              placeholder="Введите код"
              className={`${authStyles.input} ${authStyles.codeCentered}`}
              value={code}
              onChange={handleCodeChange}
            />
          </label>

          {codeError && (
            <p className={`${authStyles.errorText} ${authStyles.errorCentered}`}>{codeError}</p>
          )}

          <button
            type="button"
            className={`${authStyles.submitButton} ${authStyles.gapMedium}`}
            onClick={handleResendCode}
            disabled={secondsLeft > 0 || isSubmitting}
          >
            {secondsLeft > 0
              ? `Отправить код повторно ${formatCountdown(secondsLeft)}`
              : 'Отправить код повторно'}
          </button>

          <button
            type="submit"
            className={`${authStyles.submitButton} ${authStyles.gapSmall}`}
            disabled={isSubmitting}
          >
            Подтвердить
          </button>
        </form>
      )}

      {screen === 'form' && (
        <p className={authStyles.switchText}>
          Уже есть аккаунт?{' '}
          <button type="button" className={authStyles.switchLink} onClick={onSwitchToLogin}>
            Войти
          </button>
        </p>
      )}
    </>
  );
}

export default function RegistrationModal({ isOpen, onClose, onSwitchToLogin }) {
  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      {isOpen && <RegistrationForm onClose={onClose} onSwitchToLogin={onSwitchToLogin} />}
    </Modal>
  );
}
