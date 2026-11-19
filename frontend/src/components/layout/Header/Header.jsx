import styles from './Header.module.scss';
import logoSvg from '../../../assets/icons/logo.svg';

function Header({ onOpenLogin }) {
  return (
    <header className={styles.header}>
      <div className={styles.headerInner}>
        <a href="/" className={styles.logo}>
          <img src={logoSvg} alt="Kira" className={styles.logoImg} />
        </a>

        <nav className={styles.nav}>
          <a href="#courses" className={styles.navLink}>Курсы</a>
          <a href="#reviews" className={styles.navLink}>Отзывы</a>
          <a href="#contacts" className={styles.navLink}>Контакты</a>
        </nav>

        <button className={styles.loginBtn} onClick={onOpenLogin}>
          Войти
        </button>
      </div>
    </header>
  );
}

export default Header;