document.addEventListener("DOMContentLoaded", function () {
    const followBtn = document.getElementById("followBtn");

    if (!followBtn) return;

    followBtn.addEventListener("click", async () => {
        const pseudo = followBtn.getAttribute("data-pseudo");
        const isFollowing = followBtn.getAttribute("data-following") === "true";
        const url = isFollowing ? `/user/unfollow/${pseudo}` : `/user/follow/${pseudo}`;

        try {
            const response = await fetch(url, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
            });

            if (response.ok) {
                const newState = !isFollowing;

                // MAJ attribut data
                followBtn.setAttribute("data-following", newState);

                // MAJ du texte
                followBtn.textContent = newState ? "Ne plus suivre" : "Suivre";

                // MAJ de la classe
                followBtn.classList.toggle("unfollow", newState);
            } else {
                alert("Erreur lors de la mise à jour.");
            }
        } catch (error) {
            console.error("Erreur réseau :", error);
            alert("Erreur de connexion.");
        }

        const userPostsCount = document.getElementById('user-followers-count');
        // Update the followers count
        if (userPostsCount) {
            const currentCount = parseInt(userPostsCount.textContent, 10);
            userPostsCount.textContent = isFollowing ? currentCount - 1 : currentCount + 1;
        }
    });
});

function toggleDropdown() {
    const menu = document.getElementById("dropdownMenu");
    menu.style.display = (menu.style.display === "flex") ? "none" : "flex";
}

document.getElementById('openFollowersModal').addEventListener('click', function() {
    document.getElementById('followersModal').style.display = 'flex';
});

function closeFollowersModal() {
    document.getElementById('followersModal').style.display = 'none';
}

document.getElementById('openFollowModal').addEventListener('click', function() {
    document.getElementById('followModal').style.display = 'flex';
});

function closeFollowModal() {
    document.getElementById('followModal').style.display = 'none';
}

// GESTION DE LA MODAL DE SUPPRESSION
const deleteBtn = document.getElementById("deleteProfileBtn");
const deleteModal = document.getElementById("deleteModal");

if (deleteBtn && deleteModal) {
    deleteBtn.addEventListener("click", function () {
        deleteModal.style.display = "flex";
    });
}

// Fonction appelée par le bouton "Supprimer" dans la modal
function confirmDelete() {
    const form = document.createElement("form");
    form.method = "POST";
    form.action = "/user/delete"; // Peut être remplacé par `th:action` côté Thymeleaf

    document.body.appendChild(form);
    form.submit();
}

// Fonction appelée par le bouton "Annuler"
function closeDeleteModal() {
    deleteModal.style.display = "none";
}

// Close modal when clicking outside
window.addEventListener('click', (event) => {
    ["followersModal", "followModal", "deleteModal"].forEach(modalId => {
        const modal = document.getElementById(modalId);
        if (event.target === modal) {
            if (modalId === "followersModal") closeFollowersModal();
            else if (modalId === "followModal") closeFollowModal();
            else if (modalId === "deleteModal") closeDeleteModal();
        }
    });
});
