let currentCommunityId = null;

// --- Modals management ---
function openModal(modalId) {
    document.getElementById(modalId).style.display = "flex";
}

function closeModal(modalId) {
    document.getElementById(modalId).style.display = "none";
}

// Delete modal
function openDeleteModal(button) {
    currentCommunityId = button.getAttribute("data-community-id");
    const communityName = button.getAttribute("data-community-name");
    document.getElementById("deleteCommunityName").textContent = communityName;
    openModal("deleteModal");
}

function closeDeleteModal() {
    closeModal("deleteModal");
}

// Create modal
function openCreateModal() {
    currentCommunityId = null;
    openModal("createModal");
}

function closeCreateModal() {
    closeModal("createModal");
}

// Edit modal
function openEditModal(button) {
    currentCommunityId = button.getAttribute("data-community-id");
    const communityName = button.getAttribute("data-community-name");
    const communityDescription = button.getAttribute("data-community-description");
    document.getElementById("communityName").value = communityName;
    document.getElementById("communityDescription").value = communityDescription;
    openModal("editModal");
}

function closeEditModal() {
    closeModal("editModal");
}

// Close modal when clicking outside
window.addEventListener('click', (event) => {
    ["deleteModal", "createModal", "editModal"].forEach(modalId => {
        const modal = document.getElementById(modalId);
        if (event.target === modal) closeModal(modalId);
    });
});

// --- Delete community ---
function confirmDelete() {
    if (!currentCommunityId) return;

    fetch(`/community/delete/${currentCommunityId}`, { method: "POST" })
    .then(res => {
        if (!res.ok) throw new Error("Erreur lors de la suppression de la communauté");
        const communityCard = document.querySelector(`.community-card[data-community-id-card="${currentCommunityId}"]`);
        if (!communityCard) return;

        const communityList = communityCard.closest('.community-list');
        communityCard.remove();

        // If last community deleted
        if (communityList && communityList.children.length === 1) { //(1 because of title 'Communautés')
            communityList.remove();

            const noCommunityContainer = document.createElement('div');
            noCommunityContainer.className = 'no-following-container';
            noCommunityContainer.innerHTML = `
                <div class="no-following-content">
                    <h2>Aucune communauté</h2>
                    <p>Il n'y a pas encore de communauté. Soyez le premier à en créer une !</p>
                    <a href="#" onclick="event.preventDefault(); openCreateModal()" class="createCommunityBtn">
                        Créer une communauté
                    </a>
                </div>
            `;

            document.querySelector('.container').appendChild(noCommunityContainer);
        }
        closeDeleteModal();
    })
    .catch(err => alert(err.message));
}

// --- Create community ---
document.getElementById("createCommunityForm").addEventListener("submit", function(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);

    fetch("/community/new", {
        method: "POST",
        body: formData
    })
    .then(res => {
        if (!res.ok) throw new Error("Erreur lors de la création de la communauté");
        return res.json();
    })
    .then(community => {
        addCommunityCard(community);
        form.reset();
        closeCreateModal();
    })
    .catch(err => {
        console.error(err);
        alert("Une erreur est survenue lors de la création de la communauté");
    });
});

// --- Edit community ---
document.getElementById("editCommunityForm").addEventListener("submit", function(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);

    fetch(`/community/edit/${currentCommunityId}`, {
        method: "POST",
        body: formData
    })
    .then(res => {
        if (!res.ok) throw new Error("Erreur lors de la modification de la communauté");
        return res.json();
    })
    .then(community => {
        const communityCard = document.querySelector(`.community-card[data-community-id-card="${community.communityId}"]`);
        if (!communityCard) return;

        communityCard.querySelector(".community-info h2").textContent = community.communityName;
        communityCard.querySelector(".community-info p").textContent = community.communityDescription;
        communityCard.querySelector(".community-image img").src = community.communityImagePath;

        //edit data-id
        communityCard.querySelector(".community-actions a#edit").setAttribute("data-community-name", community.communityName);
        communityCard.querySelector(".community-actions a#edit").setAttribute("data-community-description", community.communityDescription);
        communityCard.querySelector(".community-actions a#delete").setAttribute("data-community-name", community.communityName);
        communityCard.querySelector(".community-actions a#delete").setAttribute("data-community-description", community.communityDescription);
        
        closeEditModal();
    })
    .catch(err => {
        console.error(err);
        alert("Une erreur est survenue lors de la modification de la communauté");
    });
});

// Format date
function formatDate(dateString) {
    const months = [
        "janvier", "février", "mars", "avril", "mai", "juin",
        "juillet", "août", "septembre", "octobre", "novembre", "décembre"
    ];
    const dateParts = dateString.split("-");
    const day = parseInt(dateParts[2]);
    const month = parseInt(dateParts[1]) - 1;
    const year = dateParts[0];
    return `${day} ${months[month]} ${year}`;
}

// Add community card
function addCommunityCard(community) {
    const newCard = document.createElement('div');
    newCard.className = 'community-card';
    newCard.setAttribute('data-community-id-card', community.communityId);
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
                    <a href="/user/${community.userCreator.pseudo}" class="creator-link">${community.userCreator.pseudo}</a>
                    <span class="label">le</span>
                    <span class="date">${formatDate(community.communityCreationDate)}</span>
                </span>
            </div>
            <div class="community-stats">
                <div class="stat">
                    <span data-community-id="${community.communityId}">1</span>
                    <span>membre(s)</span>
                </div>
                <div class="stat">
                    <span>0</span>
                    <span>post(s)</span>
                </div>
            </div>
        </div>
        <hr>
        <div class="community-actions" data-community-id="${community.communityId}">
            <a href="#" class="btn" id="edit"
                data-user-id="${community.userCreator.idUser}"
                data-community-id="${community.communityId}"
                data-community-name="${community.communityName}"
                data-community-description="${community.communityDescription}"
                onclick="event.preventDefault(); openEditModal(this)">
                Modifier
            </a>
            <a href="#" class="btn" id="delete"
                data-user-id="${community.userCreator.idUser}"
                data-community-id="${community.communityId}"
                data-community-name="${community.communityName}"
                onclick="event.preventDefault(); openDeleteModal(this)">
                Supprimer
            </a>
            <a class="btn" id="voir"
                href="/community/${community.communityId}"
                data-user-id="${community.userCreator.idUser}">
                Voir la communauté
            </a>
        </div>
    `;

    const communityList = document.querySelector('.community-list');

    if (communityList) {
        const createBtn = communityList.querySelector('.createCommunityBtn2');
        if (createBtn) {
            communityList.insertBefore(newCard, createBtn.nextSibling);
        } else {
            communityList.insertBefore(newCard, communityList.firstChild);
        }
    } else {
        // Remove "Aucune communauté" div
        const noCommunityContainer = document.querySelector('.no-following-container');
        if (noCommunityContainer) noCommunityContainer.remove();

        const newCommunityList = document.createElement('div');
        newCommunityList.className = 'community-list';

        // Create community button
        const createButton = document.createElement('a');
        createButton.className = 'createCommunityBtn2';
        createButton.href = '#';
        createButton.textContent = 'Créer une communauté';
        createButton.onclick = function(e) {
            e.preventDefault();
            openCreateModal();
        };

        newCommunityList.appendChild(createButton);
        newCommunityList.appendChild(newCard);

        document.querySelector('.container').appendChild(newCommunityList);
    }
}

// --- Toggle membership ---
function toggleCommunityMembership(button) {
    const communityId = button.getAttribute("data-community-id");
    const isMember = button.getAttribute("data-is-member") === "true";
    const url = `/community/${isMember ? 'leave' : 'join'}/${communityId}`;

    fetch(url, { method: "POST" })
    .then(res => {
        if (!res.ok) throw new Error("Erreur lors de la mise à jour de l'appartenance à la communauté");

        // Update button
        const newButtonHtml = `
            <a href="#" class="btn" id="${isMember ? 'join' : 'leave'}"
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

        // Update member count
        const memberCount = document.querySelector(`span[data-community-id="${communityId}"]`);
        if (memberCount) {
            memberCount.textContent = parseInt(memberCount.textContent) + (isMember ? -1 : 1);
        }

        // Remove "voir" button if user leaves the community
        if (isMember) {
            const actions = document.querySelector(`.community-actions[data-community-id="${communityId}"]`);
            if (actions) {
                const voirBtn = actions.querySelector('.btn#voir');
                if (voirBtn) voirBtn.remove();
            }
        }
    })
    .catch(err => alert(err.message));
}