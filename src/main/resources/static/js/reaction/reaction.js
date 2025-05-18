function submitReaction(button) {
    const postId = button.getAttribute("data-reactions-post-id");
    const reactionType = button.getAttribute("data-reaction-type");
    const isRemoving = button.classList.contains('active');
    
    console.log('Post ID:', postId);
    console.log('Reaction Type:', reactionType);
    console.log('Is removing:', isRemoving);

    if (!postId || !reactionType) {
        console.error('Post ID ou Reaction Type manquant');
        return;
    }

    const url = `/reaction/update/${postId}/${reactionType}`;
    fetch(url, {
        method: 'POST',
        credentials: 'same-origin'
    })
    .then(response => {
        if (response.ok) {
            // Update all reaction buttons for this post
            // First, find all occurrences of this post in the feed
            const allPostButtons = document.querySelectorAll(`[data-reactions-post-id="${postId}"]`);
            
            // Find the current active reaction (if any) for each occurrence
            const currentActiveButtons = new Set();
            allPostButtons.forEach(button => {
                if (button.classList.contains('active')) {
                    currentActiveButtons.add(button);
                }
            });

            // Update all occurrences of this post
            allPostButtons.forEach(button => {
                const countElement = button.querySelector('span');
                const currentCount = parseInt(countElement.textContent) || 0;
                const buttonReactionType = button.getAttribute('data-reaction-type');

                if (buttonReactionType === reactionType) {
                    // If this is the clicked reaction
                    if (isRemoving) {
                        // If the reaction was removed
                        countElement.textContent = currentCount - 1;
                        button.classList.remove('active');
                    } else {
                        // If the reaction was added
                        countElement.textContent = currentCount + 1;
                        button.classList.add('active');
                    }
                } else {
                    // If this is a different reaction
                    if (!isRemoving) {
                        // If a different reaction was added
                        button.classList.remove('active');
                        // If this was the previous active reaction, decrease its count
                        if (currentActiveButtons.has(button)) {
                            countElement.textContent = currentCount - 1;
                        }
                    }
                }
            });
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert("Erreur lors de la reaction");
    });
}