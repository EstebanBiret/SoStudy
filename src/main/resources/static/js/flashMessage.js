function closeFlashMessage() {
    const alertBox = document.getElementById('errorAlert');
    if (alertBox) {
        alertBox.classList.add('hide');
        // Supprime du DOM après l'animation (0.5s)
        setTimeout(() => {
            alertBox.remove();
        }, 500);
    }
}

window.addEventListener('DOMContentLoaded', () => {
    setTimeout(() => {
        closeFlashMessage();
    }, 5000);
})