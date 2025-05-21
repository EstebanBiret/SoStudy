let currentEventId = null;

// --- Modals management ---
function openModal(modalId) {
    document.getElementById(modalId).style.display = "flex";
}

function closeModal(modalId) {
    document.getElementById(modalId).style.display = "none";
}

// Delete modal
function openDeleteModal(button) {
    currentEventId = button.getAttribute("data-event-id");
    const eventName = button.getAttribute("data-event-name");
    document.getElementById("deleteEventName").textContent = eventName;
    openModal("deleteModal");
}

function closeDeleteModal() {
    closeModal("deleteModal");
}

// Create modal
function openCreateModal() {
    currentEventId = null;
    openModal("createModal");
}

function closeCreateModal() {
    closeModal("createModal");
}

// Edit modal
function openEditModal(button) {
    currentEventId = button.getAttribute("data-event-id");
    const eventName = button.getAttribute("data-event-name");
    const eventDescription = button.getAttribute("data-event-description");
    const eventLocation = button.getAttribute("data-event-location");
    const eventBeginningDate = button.getAttribute("data-event-beginning-date");
    const eventEndDate = button.getAttribute("data-event-end-date");
    document.getElementById("eventName").value = eventName;
    document.getElementById("eventDescription").value = eventDescription;
    document.getElementById("eventLocation").value = eventLocation;
    document.getElementById("eventBeginningDate").value = parseFrenchDateToInputFormat(eventBeginningDate);
    document.getElementById("eventEndDate").value = parseFrenchDateToInputFormat(eventEndDate);
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

function parseFrenchDateToInputFormat(frenchDate) {
    const months = {
        "janvier": "01",
        "février": "02",
        "mars": "03",
        "avril": "04",
        "mai": "05",
        "juin": "06",
        "juillet": "07",
        "août": "08",
        "septembre": "09",
        "octobre": "10",
        "novembre": "11",
        "décembre": "12"
    };

    const parts = frenchDate.toLowerCase().split(" ");
    if (parts.length !== 3) return ""; // format incorrect

    const day = parts[0].padStart(2, '0');
    const month = months[parts[1]];
    const year = parts[2];

    if (!month) return ""; // mois invalide

    return `${year}-${month}-${day}`;
}

// --- Delete event ---
function confirmDelete() {
    if (!currentEventId) return;

    fetch(`/event/delete/${currentEventId}`, { method: "POST" })
    .then(res => {
        if (!res.ok) throw new Error("Erreur lors de la suppression de l'événement");
        const eventCard = document.querySelector(`.event-card[data-event-id-card="${currentEventId}"]`);
        if (!eventCard) return;

        const eventList = eventCard.closest('.event-list');
        eventCard.remove();

        // If last event deleted
        if (eventList && eventList.children.length === 1) { //(1 because of title 'Evénements')
            eventList.remove();

            const noEventContainer = document.createElement('div');
            noEventContainer.className = 'no-following-container';
            noEventContainer.innerHTML = `
                <div class="no-following-content">
                    <h2>Aucun événement</h2>
                    <p>Il n'y a pas encore d'événement. Soyez le premier à en publier un !</p>
                    <a href="#" onclick="event.preventDefault(); openCreateModal()" class="createEventBtn">
                        Publier un événement
                    </a>
                </div>
            `;

            document.querySelector('.container').appendChild(noEventContainer);
        }
        closeDeleteModal();
    })
    .catch(err => alert(err.message));
}

// --- Create event ---
document.getElementById("createEventForm").addEventListener("submit", function(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);

    const startDate = new Date(formData.get("eventBeginningDate"));
    const endDate = new Date(formData.get("eventEndDate"));

    if (endDate < startDate) {
        alert("La date de fin doit être supérieure ou égale à la date de début.");
        return;
    }

    fetch("/event/new", {
        method: "POST",
        body: formData
    })
    .then(res => {
        if (!res.ok) throw new Error("Erreur lors de la publication de l'événement");
        return res.json();
    })
    .then(event => {
        addEventCard(event);
        form.reset();
        closeCreateModal();
    })
    .catch(err => {
        console.error(err);
        alert("Une erreur est survenue lors de la publication de l'événement");
    });
});

// --- Edit event ---
document.getElementById("editEventForm").addEventListener("submit", function(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);

    const startDate = new Date(formData.get("eventBeginningDate"));
    const endDate = new Date(formData.get("eventEndDate"));

    if (endDate < startDate) {
        alert("La date de fin doit être supérieure ou égale à la date de début.");
        return;
    }

    fetch(`/event/edit/${currentEventId}`, {
        method: "POST",
        body: formData
    })
    .then(res => {
        if (!res.ok) throw new Error("Erreur lors de la modification de l'événement");
        return res.json();
    })
    .then(event => {
        const eventCard = document.querySelector(`.event-card[data-event-id-card="${event.eventId}"]`);
        if (!eventCard) return;

        eventCard.querySelector(".event-info h2").textContent = event.eventName;
        eventCard.querySelector(".event-info p").textContent = event.eventDescription;
        eventCard.querySelector(".event-image img").src = event.eventImagePath;
        eventCard.querySelector(".event-location span").textContent = event.eventLocation;
        eventCard.querySelector("#spanEventBeginningDate").textContent = formatDate(event.eventBeginningDate);
        eventCard.querySelector("#spanEventEndDate").textContent = formatDate(event.eventEndDate);

        //edit data-id
        eventCard.querySelector(".event-actions a#edit").setAttribute("data-event-name", event.eventName);
        eventCard.querySelector(".event-actions a#edit").setAttribute("data-event-description", event.eventDescription);
        eventCard.querySelector(".event-actions a#edit").setAttribute("data-event-location", event.eventLocation);
        eventCard.querySelector(".event-actions a#edit").setAttribute("data-event-beginning-date", formatDate(event.eventBeginningDate));
        eventCard.querySelector(".event-actions a#edit").setAttribute("data-event-end-date", formatDate(event.eventEndDate));
        
        eventCard.querySelector(".event-actions a#delete").setAttribute("data-event-name", event.eventName);
        
        closeEditModal();
    })
    .catch(err => {
        console.error(err);
        alert("Une erreur est survenue lors de la modification de l'événement");
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

// Add event card
function addEventCard(event) {
    const newCard = document.createElement('div');
    console.log(event.eventImagePath);
    newCard.className = 'event-card';
    newCard.setAttribute('data-event-id-card', event.eventId);
    newCard.innerHTML = `
        <div class="event-image">
            <img src="${event.eventImagePath}" alt="Image de l'événement">
        </div>
        <div class="event-info">
            <h2>${event.eventName}</h2>
            <p>${event.eventDescription}</p>
            <div class="event-meta">
                <span class="creation-info">
                    <span class="label">Publié par</span>
                    <a href="/user/${event.userCreator.pseudo}" class="creator-link">${event.userCreator.pseudo}</a>
                    <span class="label">le</span>
                    <span class="date">${formatDate(event.eventPublicationDate)}</span>
                </span>
            </div>
            <div class="event-location-time">
                <div class="event-location">
                    <img src="images/logos/location.svg">
                    <span>${event.eventLocation}</span>
                </div>
                <div class="event-time">
                    <img src="images/logos/calendar.svg">
                    <span id="spanEventBeginningDate">${formatDate(event.eventBeginningDate)}</span>
                    <span class="label">↣</span>
                    <span id="spanEventEndDate">${formatDate(event.eventEndDate)}</span>
                </div>
            </div>
            <div class="event-stats">
                <div class="stat">
                    <span data-event-id="${event.eventId}">1</span>
                    <span>participant(s)</span>
                </div>
            </div>
        </div>
        <hr>
        <div class="event-actions" data-event-id="${event.eventId}">
            <a href="#" class="btn" id="edit"
                data-user-id="${event.userCreator.idUser}"
                data-event-id="${event.eventId}"
                data-event-name="${event.eventName}"
                data-event-description="${event.eventDescription}"
                data-event-location="${event.eventLocation}"
                data-event-beginning-date="${event.eventBeginningDate}"
                data-event-end-date="${event.eventEndDate}"
                onclick="event.preventDefault(); openEditModal(this)">
                Modifier
            </a>
            <a href="#" class="btn" id="delete"
                data-user-id="${event.userCreator.idUser}"
                data-event-id="${event.eventId}"
                data-event-name="${event.eventName}"
                onclick="event.preventDefault(); openDeleteModal(this)">
                Supprimer
            </a>
        </div>
    `;

    const eventList = document.querySelector('.event-list');

    if (eventList) {
        const createBtn = eventList.querySelector('.createEventBtn2');
        if (createBtn) {
            eventList.insertBefore(newCard, createBtn.nextSibling);
        } else {
            eventList.insertBefore(newCard, eventList.firstChild);
        }
    } else {
        // Remove "Aucun événement" div
        const noEventContainer = document.querySelector('.no-following-container');
        if (noEventContainer) noEventContainer.remove();

        const newEventList = document.createElement('div');
        newEventList.className = 'event-list';

        // Create event button
        const createButton = document.createElement('a');
        createButton.className = 'createEventBtn2';
        createButton.href = '#';
        createButton.textContent = 'Publier un événement';
        createButton.onclick = function(e) {
            e.preventDefault();
            openCreateModal();
        };

        newEventList.appendChild(createButton);
        newEventList.appendChild(newCard);

        document.querySelector('.container').appendChild(newEventList);
    }
}

// --- Toggle membership ---
function toggleEventMembership(button) {
    const eventId = button.getAttribute("data-event-id");
    const isMember = button.getAttribute("data-is-member") === "true";
    const url = `/event/${isMember ? 'leave' : 'join'}/${eventId}`;

    fetch(url, { method: "POST" })
    .then(res => {
        if (!res.ok) throw new Error("Erreur lors de la mise à jour de l'appartenance à l'événement");

        // Update button
        const newButtonHtml = `
            <a href="#" class="btn" id="${isMember ? 'join' : 'leave'}"
               data-event-id="${eventId}"
               data-is-member="${!isMember}"
               onclick="event.preventDefault(); toggleEventMembership(this)">
                ${isMember ? 'Participer' : 'Ne plus participer'}
            </a>`;
        button.outerHTML = newButtonHtml;

        // Update member count
        const memberCount = document.querySelector(`span[data-event-id="${eventId}"]`);
        if (memberCount) {
            memberCount.textContent = parseInt(memberCount.textContent) + (isMember ? -1 : 1);
        }
    })
    .catch(err => alert(err.message));
}