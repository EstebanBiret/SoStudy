
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

    if (idUser == parsedData.creator.idUser) isCreator = true;

    const users = parsedData.users || [];

    const modalBody = document.querySelector(".p-modal");
    modalBody.innerHTML = "Membres du groupe : <br><br>";

    if (users.length === 0) {
        modalBody.innerHTML += "<em>Aucun membre trouvé</em>";
    }

    users.forEach(user => {

        const profileImage = user.pathProfilePicture || "/images/default-profile.png";

        const profilePseudo = user.pseudo || `Utilisateur ${user.idUser}`;

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


            deleteButton.onclick = function() {
                deletedId.push(user.idUser);
                line.remove();
            };
            line.appendChild(deleteButton);
        }


        line.classList.add("modal-user");

        const channelName = document.getElementById("channelName");
        if (channelName) {
            channelName.value = parsedData.channelName;
        }
        modalBody.appendChild(line);
    });

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
            location.reload(); // Recharge la page
        })
        .catch(error => {
            console.error("Erreur :", error);
            alert("Échec de la mise à jour du canal.");
        });
});