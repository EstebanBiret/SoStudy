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

// Close modal when clicking outside
window.addEventListener('click', (event) => {
    ["followersModal", "followModal"].forEach(modalId => {
        const modal = document.getElementById(modalId);
        if (event.target === modal) {
            closeFollowModal(modalId);
            closeFollowersModal(modalId);
        }
    });
});
