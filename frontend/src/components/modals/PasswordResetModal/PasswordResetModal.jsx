import { useState } from 'react';
import {
  sendPasswordResetCode,
  verifyPasswordResetCode,
  setNewPassword,
} from '../../../api/authApi';
import { MailIcon, LockIcon, EyeOpenIcon, EyeClosedIcon } from '../shared/icons';
import { useCountdown, formatCountdown } from '../../../hooks/useCountdown';
import logoImage from '../../../assets/icons/logo-modals.svg';
import authStyles from '../shared/auth.module.scss';
import Modal from '../../ui/Modal/Modal';


const CODE_LENGTH = 6;
const CODE_VALIDITY_MS = 30 * 60 * 1000;
const MAX_CODE_ATTEMPTS = 5;
const RESEND_INTERVALS_SECONDS = [60, 300, 600, 900, 3600];


const STEP_SUBTITLES = {
  1: 'Восстановите свой пароль',
  2: 'Код отправлен на почту',
  3: 'Введите новый пароль',
};


function PasswordResetForm({ onClose, onSwitchToLogin }) {
  const [step, setStep] = useState(1);
  const [email, setEmail] = useState('');
  const [code, setCode] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);
  const [isConfirmVisible, setIsConfirmVisible] = useState(false);
  const [errorText, setErrorText] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [codeSentAt, setCodeSentAt] = useState(null);
  const [failedAttempts, setFailedAttempts] = useState(0);
  const [resendCount, setResendCount] = useState(0);
  const { secondsLeft, start, stop } = useCountdown();


  function handleEmailChange(event) { setEmail(event.target.value); setErrorText(''); }
  function handleCodeChange(event) {
    setCode(event.target.value.replace(/\D/g, '').slice(0, CODE_LENGTH));
    setErrorText('');
  }
  function handlePasswordChange(event) { setPassword(event.target.value); setErrorText(''); }
  function handleConfirmChange(event) { setConfirmPassword(event.target.value); setErrorText(''); }


  function handleTogglePasswordVisibility() {
    setIsPasswordVisible(function flip(prev) { return !prev; });
  }


  function handleToggleConfirmVisibility() {
    setIsConfirmVisible(function flip(prev) { return !prev; });
  }


  function isCodeExpired() {
    return Date.now() - codeSentAt > CODE_VALIDITY_MS;
  }


  function resetToFirstStep(message) {
    setStep(1);
    setCode('');
    setCodeSentAt(null);
    setFailedAttempts(0);
    setResendCount(0);
    stop();
    setErrorText(message);
  }


  async function handleEmailSubmit(event) {
    event.preventDefault();
    setErrorText('');


    const trimmedEmail = email.trim();
    if (!trimmedEmail) {
      setErrorText('Заполните все поля');
      return;
    }


    setIsSubmitting(true);
    try {
      await sendPasswordResetCode({ email: trimmedEmail });
      setCodeSentAt(Date.now());
      setResendCount(0);
      setFailedAttempts(0);
      setCode('');
      setStep(2);
      start(RESEND_INTERVALS_SECONDS[0]);
    } catch (error) {
      setErrorText(error.backendMessage || 'Не удалось отправить код. Попробуйте позже');
    } finally {
      setIsSubmitting(false);
    }
  }


  async function handleCodeSubmit(event) {
    event.preventDefault();
    setErrorText('');


    if (code.length < CODE_LENGTH) {
      setErrorText(`Введите ${CODE_LENGTH} цифр кода`);
      return;
    }
    if (isCodeExpired()) {
      resetToFirstStep('Время действия кода истекло. Запросите код заново');
      return;
    }


    setIsSubmitting(true);
    try {
      await verifyPasswordResetCode({ email: email.trim(), code });
      setStep(3);
    } catch (error) {
      const nextAttempts = failedAttempts + 1;
      setFailedAttempts(nextAttempts);


      if (nextAttempts >= MAX_CODE_ATTEMPTS) {
        resetToFirstStep('Превышено число попыток ввода кода. Запросите код заново');
      } else {
        setErrorText(error.backendMessage || 'Неверный код подтверждения');
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
      await sendPasswordResetCode({ email: email.trim() });


      const nextCount = resendCount + 1;
      setResendCount(nextCount);
      const interval = RESEND_INTERVALS_SECONDS[
        Math.min(nextCount, RESEND_INTERVALS_SECONDS.length - 1)
      ];


      setCodeSentAt(Date.now());
      setCode('');
      setErrorText('');
      start(interval);
    } catch (error) {
      setErrorText(error.backendMessage || 'Не удалось отправить код повторно');
    } finally {
      setIsSubmitting(false);
    }
  }


  async function handleNewPasswordSubmit(event) {
    event.preventDefault();
    setErrorText('');


    if (password.length < 8) {
      setErrorText('Длина пароля должна быть не менее 8 символов');
      return;
    }
    if (password !== confirmPassword) {
      setErrorText('Пароли не совпадают');
      return;
    }


    setIsSubmitting(true);
    try {
      await setNewPassword({ email: email.trim(), code, password });
      onClose();
      onSwitchToLogin();
    } catch (error) {
      setErrorText(error.backendMessage || 'Не удалось сохранить пароль. Попробуйте позже');
    } finally {
      setIsSubmitting(false);
    }
  }


  return (
    <>
      <div className={authStyles.logoWrapper}>
        <img src={logoImage} alt="Логотип Kira" className={authStyles.logoImage} />
      </div>


      <h2 className={authStyles.title}>Восстановление пароля</h2>
      <p className={authStyles.subtitle}>{STEP_SUBTITLES[step]}</p>


      {step === 1 && (
        <form className={authStyles.form} onSubmit={handleEmailSubmit}>
          <label className={authStyles.inputField}>
            <span className={authStyles.inputIcon}><MailIcon /></span>
            <input
              type="text"
              placeholder="Email"
              className={authStyles.input}
              value={email}
              onChange={handleEmailChange}
            />
          </label>


          {errorText && <p className={authStyles.errorText}>{errorText}</p>}


          <button
            type="submit"
            className={`${authStyles.submitButton} ${authStyles.gapMedium}`}
            disabled={isSubmitting}
          >
            Отправить код на почту
          </button>
        </form>
      )}


      {step === 2 && (
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


          {errorText && (
            <p className={`${authStyles.errorText} ${authStyles.errorCentered}`}>
              {errorText}
            </p>
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
            Проверить
          </button>
        </form>
      )}


      {step === 3 && (
        <form className={authStyles.form} onSubmit={handleNewPasswordSubmit}>
          <label className={authStyles.inputField}>
            <span className={authStyles.inputIcon}><LockIcon /></span>
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
            <span className={authStyles.inputIcon}><LockIcon /></span>
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


          {errorText && <p className={authStyles.errorText}>{errorText}</p>}


          <button
            type="submit"
            className={`${authStyles.submitButton} ${authStyles.gapMedium}`}
            disabled={isSubmitting}
          >
            Сохранить
          </button>
        </form>
      )}
    </>
  );
}


export default function PasswordResetModal({ isOpen, onClose, onSwitchToLogin }) {
  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      {isOpen && <PasswordResetForm onClose={onClose} onSwitchToLogin={onSwitchToLogin} />}
    </Modal>
  );
}