let currentPostIdToDelete = null;

// --- Modals management ---
function openModal(modalId) {
    document.getElementById(modalId).style.display = "flex";
}

function closeModal(modalId) {
    document.getElementById(modalId).style.display = "none";
}

// Delete modal
function openDeletePostModal(button) {
    currentPostIdToDelete = button.getAttribute("data-post-id");
    openModal("deletePostModal");
}

function closeDeletePostModal() {
    closeModal("deletePostModal");
}

// Close modal when clicking outside
window.addEventListener('click', (event) => {
    ["deletePostModal"].forEach(modalId => {
        const modal = document.getElementById(modalId);
        if (event.target === modal) closeModal(modalId);
    });
});

// --- Delete event ---
function confirmDeletePost() {
    if (!currentPostIdToDelete) return;

    fetch(`/post/delete/${currentPostIdToDelete}`, { method: "POST" })
    .then(res => {
        if (!res.ok) throw new Error("Erreur lors de la suppression du post");
        const postCard = document.getElementById(`post-card-${currentPostIdToDelete}`);
        console.log(postCard);
        if (!postCard) return;

        postCard.remove();

        //retirer un au nombre de posts de l'user connecté
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
                    <a href="post/publish" id="createPostBtn">
                        Créer un post
                    </a>
                </div>`;
            document.querySelector('.posts-container').appendChild(noPostsContainer);
        }

        closeDeletePostModal();
    })
    .catch(err => alert(err.message));
}