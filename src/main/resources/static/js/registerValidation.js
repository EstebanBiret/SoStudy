let emailValid = false;
let pseudoValid = false;

function debounce(func, wait = 300) {
    let timeout;
    return (...args) => {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), wait);
    };
}

document.addEventListener('DOMContentLoaded', () => {
    const emailInput = document.getElementById('email');
    const pseudoInput = document.getElementById('pseudo');
    const form = document.getElementById('registerForm');

    emailInput.addEventListener('blur', debounce(() => {
        const email = emailInput.value.trim();
        if (email.length > 2) {
            fetch(`/auth/check-email?email=${encodeURIComponent(email)}`)
                .then(response => response.json())
                .then(isAvailable => {
                    const emailCheck = document.getElementById('emailCheck');
                    if (!isAvailable) {
                        emailCheck.textContent = 'Cet email est déjà utilisé.';
                        emailCheck.style.color = 'red';
                        emailValid = false;
                    } else {
                        emailCheck.textContent = '';
                        emailValid = true;
                    }
                });
        }
    }));

    pseudoInput.addEventListener('blur', debounce(() => {
        const pseudo = pseudoInput.value.trim();
        if (pseudo.length >=1) {
            fetch(`/auth/check-pseudo?pseudo=${encodeURIComponent(pseudo)}`)
                .then(response => response.json())
                .then(isAvailable => {
                    const pseudoCheck = document.getElementById('pseudoCheck');
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
        if (!emailValid || !pseudoValid) {
            e.preventDefault();
            alert("L'email ou le pseudo est déjà utilisé. Veuillez les corriger avant de valider.");
        }
    });
});
