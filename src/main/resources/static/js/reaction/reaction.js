function submitReaction(button) {
    const postId = button.getAttribute("data-post-id");
    const reactionType = button.getAttribute("data-reaction-type");
    
    console.log('Post ID:', postId);
    console.log('Reaction Type:', reactionType);

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
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        // Update all reaction buttons for this post
        document.querySelectorAll(`[data-post-id="${postId}"]`).forEach(button => {
            const countElement = button.querySelector('span');
            console.log('Count Element:', countElement);

            const currentCount = parseInt(countElement.textContent) || 0;
            const buttonReactionType = button.getAttribute('data-reaction-type');

            if (buttonReactionType === reactionType) {
                // If this is the clicked reaction
                if (data.removed) {
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
                if (!data.removed) {
                    // If a different reaction was added, remove active class from other buttons
                    button.classList.remove('active');
                    // Reset the count to its original value (since we're not changing this reaction)
                    const originalCount = parseInt(button.dataset.originalCount) || 0;
                    countElement.textContent = originalCount;
                }
            }
        });
    })
    .catch(error => {
        console.error('Error:', error);
    });
}