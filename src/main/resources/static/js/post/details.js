// Fonction pour réinitialiser le formulaire
function resetCommentForm() {
    const form = document.getElementById('commentForm');
    const title = document.getElementById('modalTitle');
    
    form.action = '';
    title.textContent = 'Ajouter un commentaire';
    document.querySelector('textarea[name="commentContent"]').value = '';
}

// Fonction pour fermer le modal de commentaire
function closeCommentModal() {
    const modal = document.getElementById('commentModal');
    modal.style.display = 'none';
    resetCommentForm();
}

// Fonction pour réinitialiser le formulaire
document.addEventListener('DOMContentLoaded', function() {
    // Gérer l'affichage/masquage des réponses
    document.querySelectorAll('.toggle-replies-btn button').forEach(button => {
        button.addEventListener('click', function() {
            const container = this.closest('.comment').querySelector('.replies-container');
            const isHidden = container.style.display === 'none';
            
            container.style.display = isHidden ? 'block' : 'none';
            
            // Mettre à jour le texte du bouton
            const currentText = this.textContent;
            const newText = isHidden ? currentText.replace('Afficher', 'Masquer') : currentText.replace('Masquer', 'Afficher');
            this.textContent = newText;
        });
    });

    // Gérer l'ouverture du modal de commentaire
    document.querySelectorAll('.comment-btn').forEach(button => {
        button.addEventListener('click', function() {
            const modal = document.getElementById('commentModal');
            const postId = this.getAttribute('data-post-id');
            updateCommentForm(postId, null);
            modal.style.display = 'flex';
        });
    });

    // Gérer la réponse aux commentaires
    document.querySelectorAll('.reply-btn').forEach(button => {
        button.addEventListener('click', function() {
            const modal = document.getElementById('commentModal');
            const commentId = this.getAttribute('data-comment-id');
            updateCommentForm(null, commentId);
            modal.style.display = 'flex';
        });
    });

    // Fermer le modal quand on clique en dehors
    window.addEventListener('click', function(event) {
        const modal = document.getElementById('commentModal');
        if (event.target === modal) {
            modal.style.display = 'none';
            resetCommentForm();
        }
    });

    // Fonction pour mettre à jour le formulaire de commentaire
    function updateCommentForm(postId, commentId) {
        const form = document.getElementById('commentForm');
        const title = document.getElementById('modalTitle');
        
        if (postId) {
            form.action = `/post/comment/${postId}`;
            title.textContent = 'Ajouter un commentaire';
        } else if (commentId) {
            form.action = `/post/comment/${commentId}`;
            title.textContent = 'Répondre au commentaire';
        }
    }
});