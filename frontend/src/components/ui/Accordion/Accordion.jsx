import { useState } from 'react';
import styles from './Accordion.module.scss';

function Accordion({ title, children }) {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div className={styles.accordion}>
      <button 
        type="button"
        className={styles.header} 
        onClick={() => setIsOpen(!isOpen)}
      >
        <span>{title}</span>
        <span className={`${styles.arrow} ${isOpen ? styles.arrowOpen : ''}`}>
          ⌄
        </span>
      </button>
      {isOpen && (
        <div className={styles.body}>
          {children}
        </div>
      )}
    </div>
  );
}

export default Accordion;