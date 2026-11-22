import styles from './Header.module.scss';
import logoSvg from '../../../assets/icons/logo.svg';

function Header({ onLoginClick }) {
  return (
    <header className={styles.header}>
      <div className={styles.headerInner}>
        <a href="/" className={styles.logo}>
          <img src={logoSvg} alt="Kira" className={styles.logoImg} />
        </a>

        <nav className={styles.nav}>
          <a href="#courses-section" className={styles.navLink}>
            Курсы
          </a>
          <a href="#footer" className={styles.navLink}>
            Отзывы
          </a>
          <a href="#footer" className={styles.navLink}>
            Контакты
          </a>
        </nav>

        <button type="button" className={styles.loginBtn} onClick={onLoginClick}>
          Войти
        </button>
      </div>
    </header>
  );
}

export default Header;
