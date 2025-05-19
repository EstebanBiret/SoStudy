
let currentSubscription = null;

let hasConnected = false;

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws'
});

let currentChannelId = null;

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    if (currentChannelId) {
        subscribeToChannel(currentChannelId);
    }
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").css("display", "flex");

    } else {
        $("#conversation").hide();
    }
    $("#chat-messages").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    if (currentSubscription) {
        currentSubscription.unsubscribe();
        currentSubscription = null;
    }
    setConnected(false);
    console.log("Disconnected");
}

function subscribeToChannel(channelId) {
    console.log("Subscribing to channel:", channelId);
    if (currentSubscription) {
        currentSubscription.unsubscribe();
    }

    currentSubscription = stompClient.subscribe(`/topic/messages/${channelId}`, (message) => {
        const msg = JSON.parse(message.body);
        console.log("Received message:", msg);
        showMessage(msg.sender.pseudo, msg.userId, msg.content, msg.dateMessage);
    });
}

function sendMessage(channelId) {
    const content = $("#message_sender").val();
    const userId = $("#current-user-id").val();
    if (!content || !channelId) return;

    stompClient.publish({
        destination: `/app/channels/${channelId}/send`,
        body: JSON.stringify({ content: content , userId: userId })
    });
    $("#message_sender").val("");
}

function showMessage(senderPseudo, id, message, date) {
    const userId = $("#current-user-id").val();
    const chatMessages = document.getElementById("chat-messages");

    const messageElement = document.createElement("div");

    if (userId != id) {
        messageElement.className = "message-row friend";
    } else {
        messageElement.className = "message-row me";
    }


    const last = $("#lastMessage-"+currentChannelId).get(0);

    console.log("last", last);
    last.innerHTML = message

    messageElement.innerHTML = `
        <div class="bubble">
            <div class="message-pseudo" ><strong>${senderPseudo}</strong> <small>${date}</small><br/></div>
            <div class="message-text-body">${message}</div>
        </div>
    `;

    chatMessages.appendChild(messageElement);
    chatMessages.scrollTop = chatMessages.scrollHeight;
}

function changeChannel(channelId) {
    currentChannelId = channelId;

    if (stompClient.connected) {
        subscribeToChannel(channelId);
        $("#chat-messages").html(""); // vide les anciens messages affichés
        // Tu peux ici aussi faire un appel AJAX pour charger l’historique si besoin
    }
}

$(function () {
    $("#form-message").on('submit', (e) => {
        e.preventDefault();
        sendMessage(currentChannelId);
    });
    $("#disconnect").click(() => disconnect());
    $(".user-card").click(function() {
        if (!hasConnected) {
            connect();
            hasConnected = true;
        }
        const channelId = $(this).data("channel-id");

        if (channelId) {
            changeChannel(channelId);

            const channelName = $(this).find(".user-name").text();
            $(".conv-name").text(channelName);

            // Optionnel: ajouter une classe active à la sélection visuelle
            $(".user-card").removeClass("active");
            $(this).addClass("active");

        }});
});

// changer l'icone quand on survole l'image pour envoyer
let sendIcon = document.querySelector('.send-icon');
let sendIconHover = document.querySelector('.send-icon-hover');
sendIcon.addEventListener('mouseover', function() {
    sendIcon.style.display = 'none';
    sendIconHover.style.display = 'block';
});
sendIconHover.addEventListener('mouseout', function() {
    sendIcon.style.display = 'block';
    sendIconHover.style.display = 'none';
});

