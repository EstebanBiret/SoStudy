
const idUser = document.getElementById("current-user-id").value;

let deletedId = [];


let isCreator = false;

function openModal() {
    if (!currentChannelId) return;

    // Récupère la chaîne JSON déjà sérialisée côté serveur
    const channelJsonString = CHANNEL_DATA_MAP[currentChannelId];
    if (!channelJsonString) {
        console.warn("Pas de JSON pour le channel", currentChannelId);
        return;
    }

    let parsedData;
    try {
        parsedData = JSON.parse(channelJsonString);
    } catch (e) {
        console.error("Échec du parsing JSON du channel :", e);
        return;
    }
    const modalBody = document.querySelector(".modal-body");
    modalBody.innerHTML= `<div class="channel-name">
            <p class="p-modal p-group">Nom de la conversation :</p>
        </div>
        <div class="share-link">
            <input type="text" name="channelName" id="channelName" placeholder="Nom du groupe" value="${ parsedData.channelName}" readonly>
        </div>`

    if (idUser == parsedData.creator.idUser) {
        isCreator = true;
        const modal = document.querySelector(".modal");
        modal.classList.add("full-height");

        const channelName = document.getElementById("channelName");
        channelName.removeAttribute("readonly");
    }

    const users = parsedData.users || [];

    modalBody.innerHTML+= `<div class="group-container">
                                <p class="p-modal p-group">Membres du groupe : </p>
                                <div class="user-group-list"></div>
                            </div>`;

    const groupList = document.querySelector(".user-group-list");

    if (users.length === 0) {
        modalBody.innerHTML += "<em>Aucun membre trouvé</em>";
    }

    users.forEach(user => {

        const profileImage = user.pathProfilePicture || "/images/default-profile.png";

        const profilePseudo = user.pseudo || `Utilisateur ${user.idUser}`;

        if (user.idUser == 1) {
            return;
        }

        const line = document.createElement("div");
        line.innerHTML = `
                    <div class="prof-pic-pseudo-modal">
                        <a href="/user/${user.pseudo}" class="prof-pic-container">
                            <img src="${profileImage}" alt="Photo de profil" class="profile-pic">
                        
                        <p class="modal-pseudo">${profilePseudo}</p>
                        </a>
                    </div>`



        if(isCreator && user.idUser !== parsedData.creator.idUser) {
            const deleteButton = document.createElement("button");
            deleteButton.innerHTML ="<img src='/images/logos/delete-white.svg' alt='Supprimer' class='delete-img'>";
            deleteButton.className = "delete-button";

            deleteButton.addEventListener("click", function() {
                deletedId.push(user.idUser);
                line.remove();
            })
            line.appendChild(deleteButton);

        }


        line.classList.add("modal-user");

        groupList.appendChild(line);
    });

    let CreatorForm = "";
    if (isCreator) {
        CreatorForm = `    
                <div class="channel-image">
                    <p class="p-modal p-group">Image de la conversation :</p>
                    <input type="file" id="channel-image-input" accept="image/*" style="display: none">
                        <label for="channel-image-input" class="channel-image-label">
                            <img src="/images/logos/upload-black.svg" alt="Upload" class="upload-icon">
                            <img src="/images/logos/upload-purple.svg" alt="Upload" class="upload-icon-hover">
                            <span class="upload-text">Télécharger une image</span>
                        </label>
        
                </div>
                <div class="buttons">
                    <button type="button" class="btn cancel" onclick="closeModal()">Annuler</button>
                    <button type="submit" class="btn confirm" id="confirmUpdate">Valider</button>
                </div>`
        const wrapper = document.createElement("div");
        wrapper.innerHTML = CreatorForm;
        modalBody.appendChild(wrapper);

        let label = document.querySelector('.channel-image-label');
        let uploadIcon = document.querySelector('.upload-icon');
        let uploadIconHover = document.querySelector('.upload-icon-hover');
        label.addEventListener('mouseover', function() {
            uploadIcon.style.display = 'none';
            uploadIconHover.style.display = 'block';
        });
        label.addEventListener('mouseout', function() {
            uploadIcon.style.display = 'block';
            uploadIconHover.style.display = 'none';
        });

        document.getElementById("confirmUpdate").addEventListener("click", async function () {
            const channelName = document.getElementById("channelName").value;
            const imageInput = document.getElementById("channel-image-input");
            const imageFile = imageInput.files[0];

            // Exclure l'utilisateur supprimé (id = 1)
            const validUsers = users.filter(user => user.idUser != 1);

            const allUsersSelected = validUsers.every(user => deletedId.includes(user.idUser));

            console.log("deletedId", deletedId);
            console.log("validUsers", validUsers);
            console.log("allUsersSelected", allUsersSelected);

            if (allUsersSelected) {
                let confirmDelete = confirm("Êtes-vous sûr de vouloir supprimer tous les utilisateurs ? Cela supprimera également le groupe.");
                if (confirmDelete){
                    fetch("/channels/delete", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify({
                            channelId: currentChannelId
                        })
                    })
                    .then(response => {
                        if (!response.ok) throw new Error("Erreur lors de la suppression du groupe");
                        return response.json();
                    })
                    .then(data => {
                        location.reload();
                    })
                    .catch(error => {
                        console.error("Erreur :", error);
                        alert("Échec de la suppression du groupe.");
                    });
                    return;
                }
            }

            const formData = new FormData();
            formData.append("channelId", currentChannelId);
            formData.append("channelName", channelName);
            formData.append("deletedUserIds", JSON.stringify(deletedId));

            if (imageFile) {
                formData.append("channelImage", imageFile);
            }

            fetch("/channels/update", {
                method: "POST",
                body: formData
            })
                .then(response => {
                    if (!response.ok) throw new Error("Erreur lors de la mise à jour");
                    return response.json();
                })
                .then(data => {
                    location.reload();
                })
                .catch(error => {
                    console.error("Erreur :", error);
                    alert("Échec de la mise à jour du canal.");
                });
        });
    }
    else {
        CreatorForm = `
        <div class="buttons ">
            <button type="button" class="btn cancel leave-group" onclick="leaveGroup()">Quitter le groupe</button>
        </div>
        `
        const wrapper = document.createElement("div");
        wrapper.innerHTML = CreatorForm;
        modalBody.appendChild(wrapper);
    }



    document.getElementById("myModal").style.display = "flex";
}

function closeModal() {
    document.getElementById("myModal").style.display = "none";
}




window.onclick = function(event) {
    const modal = document.getElementById("myModal");
    if (event.target === modal) {
        modal.style.display = "none";
    }
}


function leaveGroup(){
    let confirmLeave = confirm("Êtes-vous sûr de vouloir quitter le groupe ?");

    if (confirmLeave) {
        fetch("/channels/leave", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                channelId: currentChannelId
            })
        })
        .then(response => {
            if (!response.ok) throw new Error("Erreur lors de la sortie du groupe");
            return response.json();
        })
        .then(data => {
            location.reload();
        })
        .catch(error => {
            console.error("Erreur :", error);
            alert("Échec de la sortie du groupe.");
        });
    }
    else {
        closeModal();
    }
}


