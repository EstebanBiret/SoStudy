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

    // Fermer le modal et réinitialiser le formulaire de commentaire
    document.getElementById('commentForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        const form = this;
        const content = form.querySelector('textarea[name="commentContent"]').value;
        // L'action est toujours de la forme /post/comment/{id}, donc on récupère l'ID
        const parentId = form.action.split('/').pop();
        
        // Si l'ID est égal à l'ID du post actuel, c'est un commentaire normal
        const currentPostId = window.location.pathname.split('/')[2];
        
        const isReply = parentId !== currentPostId;

        fetch(form.action, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({ commentContent: content })
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // Mettre à jour le compteur de commentaires
                const commentsStats = document.querySelector('.post-stats-item:has(.post-stats-icon[src$="comments.svg"])');
                if (commentsStats) {
                    const count = commentsStats.querySelector('.post-stats-count');
                    count.textContent = parseInt(count.textContent) + 1;
                }

                // Ajouter le nouveau commentaire
                const newComment = document.createElement('div');
                newComment.className = 'comment';
                if (isReply) {
                    // Ajouter la classe reply pour les réponses
                    newComment.classList.add('reply');
                }

                // Ajouter l'attribut data-comment-id
                newComment.setAttribute('data-comment-id', data.commentId);
                newComment.innerHTML = `
                    <div class="comment-content${isReply ? ' level-1' : ''}">
                        <div class="comment-header">
                            <img src="${data.user.personImagePath}" alt="Avatar" class="comment-avatar">
                            <div class="comment-user-info post-user-info">
                                <h3><a href="/user/${data.user.pseudo}">${data.user.pseudo}</a></h3>
                                <p>${isReply ? 'a répondu aujourd\'hui' : 'a commenté aujourd\'hui'}</p>
                            </div>
                        </div>
                        <p>${content}</p>
                        <div class="comment-replies">
                            <div class="toggle-replies-btn">
                                <button class="toggle-replies-btn"></button>
                            </div>
                            <div class="replies-container" style="display: none;"></div>
                        </div>
                    </div>
                `;

                // Ajouter le commentaire au bon endroit
                if (isReply) {
                    // Trouver le commentaire parent
                    const parentComment = document.querySelector(`[data-comment-id="${parentId}"]`);
                    if (parentComment) {
                        // Trouver le conteneur de réponses du parent
                        const repliesContainer = parentComment.querySelector('.comment-content .comment-replies');
                        if (repliesContainer) {
                            // Afficher le conteneur et ajouter le commentaire
                            repliesContainer.style.display = 'block';
                            // Créer le commentaire avec la structure simple
                            const newComment = document.createElement('div');
                            newComment.className = 'comment';
                            if (isReply) {
                                newComment.classList.add('reply');
                            }
                            newComment.setAttribute('data-comment-id', data.commentId);
                            newComment.innerHTML = `
                                <div class="comment-content${isReply ? ' level-1' : ''}">
                                    <div class="comment-header">
                                        <img src="${data.user.personImagePath}" alt="Avatar" class="comment-avatar">
                                        <div class="comment-user-info post-user-info">
                                            <h3><a href="/user/${data.user.pseudo}">${data.user.pseudo}</a></h3>
                                            <p>${isReply ? 'a répondu aujourd\'hui' : 'a commenté aujourd\'hui'}</p>
                                        </div>
                                    </div>
                                    <p>${content}</p>
                                </div>
                            `;
                            repliesContainer.appendChild(newComment);
                        } else {
                            // Créer un nouveau commentaire avec le conteneur intégré
                            const newCommentWithContainer = document.createElement('div');
                            newCommentWithContainer.className = 'comment';
                            if (isReply) {
                                newCommentWithContainer.classList.add('reply');
                            }
                            newCommentWithContainer.setAttribute('data-comment-id', data.commentId);
                            newCommentWithContainer.innerHTML = `
                                <div class="comment-content${isReply ? ' level-1' : ''}">
                                    <div class="comment-header">
                                        <img src="${data.user.personImagePath}" alt="Avatar" class="comment-avatar">
                                        <div class="comment-user-info post-user-info">
                                            <h3><a href="/user/${data.user.pseudo}">${data.user.pseudo}</a></h3>
                                            <p>${isReply ? 'a répondu aujourd\'hui' : 'a commenté aujourd\'hui'}</p>
                                        </div>
                                        <div class="reply-button">
                                            <button class="reply-btn" data-comment-id="${data.commentId}">Répondre</button>
                                        </div>
                                    </div>
                                    <p>${content}</p>
                                    <div class="comment-replies">
                                        <div class="toggle-replies-btn">
                                            <button class="toggle-replies-btn"></button>
                                        </div>
                                        <div class="replies-container" style="display: none;"></div>
                                    </div>
                                </div>
                            `;
                            // Ajouter le nouveau commentaire avec son conteneur intégré dans le parent
                            parentComment.appendChild(newCommentWithContainer);
                        }
                    } 
                } else {
                    // Ajouter le commentaire à la fin de la liste
                    const commentsContainer = document.getElementById('post-comments');
                    if (commentsContainer) {
                        // Supprimer le message "Pas encore de commentaires" si présent
                        const noCommentsMessage = commentsContainer.querySelector('.no-comments');
                        if (noCommentsMessage) {
                            noCommentsMessage.remove();
                        }
                        // Ajouter le commentaire en premier
                        commentsContainer.insertBefore(newComment, commentsContainer.secondChild);
                    }
                }

                // Fermer le modal et réinitialiser le formulaire
                closeCommentModal();
                resetCommentForm();

                // Ajouter les événements aux nouveaux éléments
                const replyBtn = newComment.querySelector('.reply-btn');
                if (replyBtn) {
                    replyBtn.addEventListener('click', function() {
                        const modal = document.getElementById('commentModal');
                        const commentId = this.getAttribute('data-comment-id');
                        updateCommentForm(null, commentId);
                        modal.style.display = 'flex';
                    });
                }

                const toggleBtn = newComment.querySelector('.toggle-replies-btn');
                if (toggleBtn) {
                    toggleBtn.addEventListener('click', function() {
                        const container = this.closest('.comment').querySelector('.replies-container');
                        const isHidden = container.style.display === 'none';
                        container.style.display = isHidden ? 'block' : 'none';
                        
                        const currentText = this.textContent;
                        const newText = isHidden ? currentText.replace('Afficher', 'Masquer') : currentText.replace('Masquer', 'Afficher');
                        this.textContent = newText;
                    });
                }
            }
        })
        .catch(error => {
            console.error('Erreur lors de l\'ajout du commentaire:', error);
            alert('Une erreur est survenue lors de l\'ajout du commentaire.');
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