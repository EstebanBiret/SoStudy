let currentPostId = null;

function openRepostModal(button) {
    currentPostId = button.getAttribute("data-post-id");
    document.getElementById("repostModal").style.display = "flex";
}

function closeRepostModal() {
    document.getElementById("repostModal").style.display = "none";
    document.getElementById("repostContent").value = "";
}

// Fermer le modal quand on clique en dehors
window.addEventListener('click', function(event) {
    const modal = document.getElementById('repostModal');
    if (event.target === modal) {
        closeRepostModal();
    }
});

function submitRepost(event) {
    event.preventDefault();
    const content = document.getElementById("repostContent").value;

    fetch("/repost/" + currentPostId, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({ content: content })
    })
    .then(res => {
        if (res.ok) {
            // Mise à jour dynamique du bouton / des boutons
            document.querySelectorAll(`.repost-btn[data-post-id="${currentPostId}"]`).forEach(btn => {
                btn.outerHTML = `
                    <button type="button" class="repost-btn" id="unrepost-btn-${currentPostId}" data-post-id="${currentPostId}" onclick="submitUnrepost(this)">
                        <img src="/images/logos/unrepost.png" alt="Unrepost" class="repost-icon">
                        Ne plus reposter
                    </button>`;
            });

            //maj dynamique du compteur de reposts du/des posts
            // On trouve tous les boutons de repost avec l'ID du post actuel
            const repostBtns = document.querySelectorAll(`.repost-btn[data-post-id="${currentPostId}"]`);
            repostBtns.forEach(btn => {
                // On remonte au post-card parent
                const postCard = btn.closest('.post-card');
                if (postCard) {
                    // On trouve le compteur de reposts dans le post-card
                    const repostStatsItem = postCard.querySelector('.post-stats-item:has(.post-stats-icon[src$="repost.png"])');
                    if (repostStatsItem) {
                        const repostCount = repostStatsItem.querySelector('.post-stats-count');
                        if (repostCount) {
                            repostCount.textContent = parseInt(repostCount.textContent) + 1;
                        }
                    }
                }
            });

            closeRepostModal();
        } else {
            alert("Erreur lors du repost");
        }
    });
}

function submitUnrepost(button) {
    const postId = button.getAttribute("data-post-id");
    const repostId = button.getAttribute('data-repost-id');

    fetch("/repost/delete/" + postId, {
        method: "POST"
    })
    .then(res => {
        if (res.ok) {

            // Mettre à jour tous les boutons correspondant à ce postId
            document.querySelectorAll(`.repost-btn[data-post-id="${postId}"]`).forEach(btn => {
                btn.outerHTML = `
                    <button type="button" class="repost-btn" id="repost-btn-${postId}" data-post-id="${postId}" onclick="openRepostModal(this)">
                        <img src="/images/logos/repost.png" alt="Repost" class="repost-icon">
                        Reposter
                    </button>`;
            });

            // Supprimer visuellement le repost de l’utilisateur (si c'est le profil de l'utilisateur connecté)
            if (repostId) {
                const repostElement = document.getElementById('repost-wrapper-' + repostId);
                if (repostElement) {
                    repostElement.remove();
                }
            }

            // Mettre à jour le compteur de reposts
            const repostBtns = document.querySelectorAll(`.repost-btn[data-post-id="${postId}"]`);
            repostBtns.forEach(btn => {
                // On remonte au post-card parent
                const postCard = btn.closest('.post-card');
                if (postCard) {
                    // On trouve le compteur de reposts dans le post-card
                    const repostStatsItem = postCard.querySelector('.post-stats-item:has(.post-stats-icon[src$="repost.png"])');
                    if (repostStatsItem) {
                        const repostCount = repostStatsItem.querySelector('.post-stats-count');
                        if (repostCount) {
                            repostCount.textContent = parseInt(repostCount.textContent) - 1;
                        }
                    }
                }
            });

            //retirer un au nombre de post de l'user connecté
            const userPostsCount = document.getElementById('user-posts-count');
            if (userPostsCount) {
                userPostsCount.textContent = parseInt(userPostsCount.textContent) - 1;
            }

            //si c'était son dernier post, on ajoute la div du no post
            if (parseInt(userPostsCount.textContent) === 0) {
                const noPostsContainer = document.createElement('div');
                noPostsContainer.innerHTML = `<div class="no-posts">
                    <div class="no-following-content">
                        <h2>Vous n'avez pas encore posté !</h2>
                        <p>Vous pouvez créer un post pour partager vos idées ou tout ce qui vous passionne.</p>
                        <a th:href="@{/post/publish}" id="createPostBtn">
                            Créer un post
                        </a>
                    </div>`;
                document.querySelector('.posts-container').appendChild(noPostsContainer);
            }

        } else {
            alert("Erreur lors du unrepost");
        }
    });
}