export function showError(container, message) {
    const errorDiv = container.querySelector('.form-error');
    if (!errorDiv) return;

    let p = errorDiv.querySelector('p');
    if (!p) {
        p = document.createElement('p');
        errorDiv.appendChild(p);
    }

    p.textContent = message;
    errorDiv.style.display = 'block';
}

export function hideError(container) {
    const errorDiv = container.querySelector('.form-error');
    if (errorDiv) {
        errorDiv.style.display = 'none';
    }
}