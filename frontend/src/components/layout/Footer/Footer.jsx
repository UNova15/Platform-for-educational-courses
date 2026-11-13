import styles from './Footer.module.scss';
import logoFooterSvg from '../../../assets/icons/logo-footer.svg';

function Footer() {
  return (
    <footer className={styles.footer} id="contacts">
      <div className={styles.footerInner}>
        
        <a href="/" className={styles.logo}>
          <img src={logoFooterSvg} alt="Kira — образовательная платформа" className={styles.logoImg} />
        </a>

        <nav className={styles.nav}>
          <a href="#courses" className={styles.navLink}>Курсы</a>
          <a href="#reviews" className={styles.navLink}>Отзывы</a>
          <a href="#contacts" className={styles.navLink}>Контакты</a>
        </nav>

        <div className={styles.socials}>
          <a href="#!" className={styles.socialCircle} aria-label="YouTube">
            <svg width="20" height="14" viewBox="0 0 20 14" fill="none">
              <rect x="1" y="1" width="18" height="12" rx="3" stroke="currentColor" strokeWidth="1.8"/>
              <path d="M8 4.5L13 7L8 9.5V4.5Z" fill="currentColor"/>
            </svg>
          </a>
          <a href="#!" className={styles.socialCircle} aria-label="Rutube">
            <span className={styles.rutubeIcon}>R</span>
          </a>
          <a href="#!" className={styles.socialCircle} aria-label="Telegram">
            <svg width="18" height="16" viewBox="0 0 18 16" fill="none">
              <path d="M17 1L1 7.5L6.5 9.5M17 1L14.5 15L6.5 9.5M17 1L6.5 9.5M6.5 9.5V14L9.5 11.5" stroke="currentColor" strokeWidth="1.6" strokeLinecap="round" strokeLinejoin="round"/>
            </svg>
          </a>
          <a href="#!" className={styles.socialCircle} aria-label="Rutube">
            <span className={styles.rutubeIcon}>R</span>
          </a>
          <a href="#!" className={styles.socialCircle} aria-label="YouTube">
            <svg width="20" height="14" viewBox="0 0 20 14" fill="none">
              <rect x="1" y="1" width="18" height="12" rx="3" stroke="currentColor" strokeWidth="1.8"/>
              <path d="M8 4.5L13 7L8 9.5V4.5Z" fill="currentColor"/>
            </svg>
          </a>
        </div>
        <a href="mailto:kkrecetov5@gmail.com" className={styles.email}>
          kkrecetov5@gmail.com
        </a>

      </div>
    </footer>
  );
}

export default Footer;