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
document.getElementById("createCommunityForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const form = event.target;
    const formData = new FormData(form);

    fetch("/community/new", {
        method: "POST",
        body: formData
    })
    .then(res => res.json())
    .then(community => {
        if (community) {
            form.reset();
            closeCreateModal();

            // update community list
            const communityList = document.querySelector('.community-list');
            if (communityList) {
                // Créer la nouvelle carte de communauté
                const newCard = document.createElement('div');
                newCard.className = 'community-card';
                newCard.innerHTML = `
                    <div class="community-image">
                        <img src="${community.communityImagePath}" alt="Image de la communauté">
                    </div>
                    <div class="community-info">
                        <h2>${community.communityName}</h2>
                        <p>${community.communityDescription}</p>
                        <div class="community-meta">
                            <span class="creation-info">
                                <span class="label">Créée par</span>
                                <a href="/user/${community.userCreator.pseudo}" class="creator-link">
                                    ${community.userCreator.pseudo}
                                </a>
                                <span class="label">le</span>
                                <span class="date">${community.communityCreationDate}</span>
                            </span>
                        </div>
                        <div class="community-stats">
                            <div class="stat">
                                <span>0</span>
                                <span>membre(s)</span>
                            </div>
                            <div class="stat">
                                <span>0</span>
                                <span>post(s)</span>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="community-actions">
                        <a href="" class="btn" id="edit" 
                           data-user-id="${user.idUser}"
                           onclick="event.preventDefault(); openEditModal(this)">
                            Modifier
                        </a>
                        <a href="" class="btn" id="delete" 
                           data-user-id="${user.idUser}"
                           data-community-id="${community.communityId}"
                           data-community-name="${community.communityName}"
                           onclick="event.preventDefault(); openDeleteModal(this)">
                            Supprimer
                        </a>
                    </div>
                `;

                // Ajouter la nouvelle carte au début de la liste
                communityList.insertBefore(newCard, communityList.firstChild);
            }

        } else {
            alert("Erreur lors de la création de la communauté");
        }
    })
    .catch(error => {
        console.error("Erreur:", error);
        alert("Une erreur est survenue lors de la création de la communauté");
    });
});


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