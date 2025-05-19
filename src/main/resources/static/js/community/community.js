let currentCommunityId = null;

// delete modal
function openDeleteModal(button) {
    const communityId = button.getAttribute("data-community-id");
    const communityName = button.getAttribute("data-community-name");
    
    currentCommunityId = communityId;
    
    const modal = document.getElementById("deleteModal");
    const deleteCommunityName = document.getElementById("deleteCommunityName");
    
    deleteCommunityName.textContent = communityName;
    modal.style.display = "flex";
}

function closeDeleteModal() {
    document.getElementById("deleteModal").style.display = "none";
}

// create modal
function openCreateModal() {
    document.getElementById("createModal").style.display = "flex";
}

function closeCreateModal() {
    document.getElementById("createModal").style.display = "none";
}

// close or open modal when clicking outside
window.addEventListener('click', function(event) {
    const deleteModalOverlay = document.getElementById('deleteModal');
    if (event.target === deleteModalOverlay) {
        closeDeleteModal();
    }
    const createModalOverlay = document.getElementById('createModal');
    if (event.target === createModalOverlay) {
        closeCreateModal();
    }
});

// confirm delete
function confirmDelete() {
    fetch(`/community/delete/${currentCommunityId}`, {
        method: "POST"
    })
    .then(res => {
        if (res.ok) {
            // remove community card from DOM
            const communityCard = document.querySelector(`.community-card[data-community-id-card="${currentCommunityId}"]`);
            if (communityCard) {
                communityCard.remove();
            }
            // close modal
            closeDeleteModal();
        } else {
            alert("Erreur lors de la suppression de la communauté");
        }
    });
}

// confirm create
function confirmCreate() {
    const communityName = document.getElementById("communityName").value;
    const communityDescription = document.getElementById("communityDescription").value;
    const communityImage = document.getElementById("communityImage").files[0];
    
    const formData = new FormData();
    formData.append("communityName", communityName);
    formData.append("communityDescription", communityDescription);
    formData.append("communityImage", communityImage);
    
    fetch("/community/new", {
        method: "POST",
        body: formData
    })
    .then(res => {
        if (res.ok) {
            // close modal
            closeCreateModal();
        } else {
            alert("Erreur lors de la création de la communauté");
        }
    });
}

// toggle community membership
function toggleCommunityMembership(button) {
    const communityId = button.getAttribute("data-community-id");
    const isMember = button.getAttribute("data-is-member") === "true";
    const url = `/community/${isMember ? 'leave' : 'join'}/${communityId}`;
    
    fetch(url, {
        method: "POST"
    })
    .then(res => {
        if (res.ok) {
            //update button
            const newButtonHtml = `
                <a href="" class="btn" id="${isMember ? 'join' : 'leave'}"
                   data-community-id="${communityId}"
                   data-is-member="${!isMember}"
                   onclick="event.preventDefault(); toggleCommunityMembership(this)">
                    ${isMember ? 'Rejoindre' : 'Quitter'}
                </a>
                ${!isMember ? `
                    <a href="/community/${communityId}" class="btn" id="voir">
                        Voir la communauté
                    </a>
                ` : ''}
            `;
            button.outerHTML = newButtonHtml;

            //update member count
            const memberCount = document.querySelector(`span[data-community-id="${communityId}"]`);
            if (memberCount) {
                memberCount.textContent = parseInt(memberCount.textContent) + (isMember ? -1 : 1);
            }

            //remove voir button if user is not a member
            if (isMember) {
                const voirButton = document.querySelector(`.community-actions[data-community-id="${communityId}"]`).querySelector('.btn#voir');
                voirButton.remove();
            }

        } else {
            alert("Erreur lors de la mise à jour de l'appartenance à la communauté");
        }
    });
}