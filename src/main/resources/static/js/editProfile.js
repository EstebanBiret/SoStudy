let pseudoValid = true; // Supposé valide par défaut

function debounce(func, wait = 300) {
    let timeout;
    return (...args) => {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), wait);
    };
}

document.addEventListener('DOMContentLoaded', () => {
    const pseudoInput = document.getElementById('pseudo');
    const pseudoCheck = document.getElementById('pseudoCheck');
    const form = document.getElementById('registerForm');

    const currentPseudo = pseudoInput.value.trim(); // pseudo original de session

    pseudoInput.addEventListener('blur', debounce(() => {
        const pseudo = pseudoInput.value.trim();

        // Si le pseudo n’a pas changé, on ne fait rien
        if (pseudo === currentPseudo) {
            pseudoCheck.textContent = '';
            pseudoValid = true;
            return;
        }

        if (pseudo.length >= 1) {
            fetch(`/auth/check-pseudo?pseudo=${encodeURIComponent(pseudo)}`)
                .then(response => response.json())
                .then(isAvailable => {
                    if (!isAvailable) {
                        pseudoCheck.textContent = 'Ce pseudo est déjà pris.';
                        pseudoCheck.style.color = 'red';
                        pseudoValid = false;
                    } else {
                        pseudoCheck.textContent = '';
                        pseudoValid = true;
                    }
                });
        }
    }));

    form.addEventListener('submit', function (e) {
        if (!pseudoValid) {
            e.preventDefault();
            showModal("Ce pseudo est déjà utilisé. Veuillez en choisir un autre.");

        }
    });
});

function showModal(message) {
    const modal = document.getElementById('customModal');
    const modalMessage = document.getElementById('modalMessage');
    modalMessage.textContent = message;
    modal.style.display = "block";

    const closeBtn = document.getElementById('closeModal');
    closeBtn.onclick = () => modal.style.display = "none";

    window.onclick = (event) => {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    };
}
