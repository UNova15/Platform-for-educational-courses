import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Modal from '../../ui/Modal/Modal';
import { loginUser } from '../../../api/authApi';
import { MailIcon, LockIcon, EyeOpenIcon, EyeClosedIcon } from '../shared/icons';
import logoImage from '../../../assets/icons/logo-modals.svg';
import authStyles from '../shared/auth.module.scss';

const EMAIL_REGEXP = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

function LoginModal({ isOpen, onClose, onSwitchToRegistration, onSwitchToPasswordReset }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const navigate = useNavigate();

  function handleEmailChange(event) {
    setEmail(event.target.value);
    setErrorMessage('');
  }

  function handlePasswordChange(event) {
    setPassword(event.target.value);
    setErrorMessage('');
  }

  function handleTogglePasswordVisibility() {
    setIsPasswordVisible(function flipVisibility(prevValue) {
      return !prevValue;
    });
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setErrorMessage('');

    const trimmedEmail = email.trim();

    if (!trimmedEmail || !password) {
      setErrorMessage('Заполните все поля');
      return;
    }

    if (!EMAIL_REGEXP.test(trimmedEmail)) {
      setErrorMessage('Неправильный адрес электронной почты');
      return;
    }

    setIsSubmitting(true);

    try {
      const data = await loginUser({ email: trimmedEmail, password });
      const token = data.accessToken || data.token;

      if (!token) {
        setErrorMessage('Неверная почта или пароль');
        return;
      }

      localStorage.setItem('accessToken', token);
      onClose();
      navigate('/profile');
    } catch {
      setErrorMessage('Неверная почта или пароль');
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <div className={authStyles.logoWrapper}>
        <img src={logoImage} alt="Логотип Kira" className={authStyles.logoImage} />
      </div>

      <h2 className={authStyles.title}>Добро пожаловать!</h2>
      <p className={authStyles.subtitle}>Войдите в свой аккаунт</p>

      <form className={authStyles.form} onSubmit={handleSubmit}>
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

        {errorMessage && <p className={authStyles.errorText}>{errorMessage}</p>}

        <div className={authStyles.forgotWrapper}>
          <button type="button" className={authStyles.forgotLink} onClick={onSwitchToPasswordReset}>
            Забыли пароль?
          </button>
        </div>

        <button type="submit" className={authStyles.submitButton} disabled={isSubmitting}>
          Войти
        </button>
      </form>

      <p className={authStyles.switchText}>
        Нет аккаунта?{' '}
        <button type="button" className={authStyles.switchLink} onClick={onSwitchToRegistration}>
          Зарегистрироваться
        </button>
      </p>
    </Modal>
  );
}

export default LoginModal;
