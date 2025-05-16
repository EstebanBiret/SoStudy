let currentPostId = null;

function openRepostModal(button) {
    currentPostId = button.getAttribute("data-post-id");
    document.getElementById("repostModal").style.display = "flex";
}

function closeRepostModal() {
    document.getElementById("repostModal").style.display = "none";
    document.getElementById("repostContent").value = "";
}

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
            // Mise à jour dynamique du bouton
            const repostBtn = document.getElementById("repost-btn-" + currentPostId);
            if (repostBtn) {
                repostBtn.outerHTML = `
                    <button type="button" class="repost-btn" id="unrepost-btn-${currentPostId}" data-post-id="${currentPostId}" onclick="submitUnrepost(this)">
                        <img src="/images/logos/unrepost.png" alt="Unrepost" class="repost-icon">
                        Ne plus reposter
                    </button>
                `;
            }
            closeRepostModal();
        } else {
            alert("Erreur lors du repost");
        }
    });
}

function submitUnrepost(button) {
    const postId = button.getAttribute("data-post-id");

    fetch("/repost/delete/" + postId, {
        method: "POST"
    })
    .then(res => {
        if (res.ok) {
            // Mise à jour dynamique du bouton
            const unrepostBtn = document.getElementById("unrepost-btn-" + postId);
            if (unrepostBtn) {
                unrepostBtn.outerHTML = `
                    <button type="button" class="repost-btn" id="repost-btn-${postId}" data-post-id="${postId}" onclick="openRepostModal(this)">
                        <img src="/images/logos/repost.png" alt="Repost" class="repost-icon">
                        Reposter
                    </button>
                `;
            }
        } else {
            alert("Erreur lors du unrepost");
        }
    });
}
