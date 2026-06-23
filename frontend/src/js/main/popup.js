function openPopup(id) {
    const popup = document.getElementById(id);
    if (!popup) {
        console.error(`Попап с id "${id}" не найден`);
        return;
    }
    popup.classList.add('is-open');
    document.body.style.overflow = 'hidden';
}

function closePopup(id) {
    const popup = document.getElementById(id);
    if (!popup) {
        console.error(`Попап с id "${id}" не найден`);
        return;
    }
    popup.classList.remove('is-open');

    const anyOpen = document.querySelector('.popup-overlay.is-open');
    if (!anyOpen) {
        document.body.style.overflow = '';
    }
}

function togglePassword(btn, id) {
    const passwordInput = document.getElementById(id);
    if (!passwordInput) {
        console.error(`Инпут с id "${id}" не найден`);
        return;
    }
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        btn.textContent = 'Скрыть';
    } else {
        passwordInput.type = 'password';
        btn.textContent = 'Показать';
    }
}


document.addEventListener('click', (e) => {
    if (e.target.classList.contains('popup-overlay')) {
        closePopup(e.target.id);
    }
});


document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') {
        document.querySelectorAll('.popup-overlay.is-open')
            .forEach(p => closePopup(p.id));
    }
});

window.openPopup = openPopup;
window.closePopup = closePopup;
window.togglePassword = togglePassword;