import styles from './Modal.module.scss'; 

function Modal({ isOpen, onClose, children }) {
  if (!isOpen) return null;
  function handleContentClick(e) {
    e.stopPropagation();
  }

  return (
    <div className={styles.overlay} onClick={onClose}>
      <div className={styles.modalContent} onClick={handleContentClick}>
        <button
          className={styles.closeBtn}
          onClick={onClose}
          aria-label="Закрыть"
        >
          ✕
        </button>
        {children}
      </div>
    </div>
  );
}

export default Modal;