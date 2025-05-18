
let currentSubscription = null;

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
        $("#conversation").show();
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
        showMessage(msg.sender.pseudo, msg.content, msg.dateMessage);
    });
}

function sendMessage(channelId) {
    const content = $("#message_sender").val();
    const userId = $("#current-user-id").val();
    console.log("Sending message:", content);
    if (!content || !channelId) return;

    stompClient.publish({
        destination: `/app/channels/${channelId}/send`,
        body: JSON.stringify({ content: content , userId: userId })
    });
    $("#message_sender").val("");
}

function showMessage(senderPseudo, message, date) {
    const chatMessages = document.getElementById("chat-messages");

    const messageElement = document.createElement("div");
    messageElement.className = "message-row me";

    messageElement.innerHTML = `
        <div class="bubble">
            <strong>${senderPseudo}</strong> <small>${date}</small><br/>
            ${message}
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
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $(".user-card").click(function() {
        const channelId = $(this).data("channel-id");
        console.log("Channel ID clicked:", channelId);
        if (channelId) {
            changeChannel(channelId);

            const channelName = $(this).find(".user-name").text();
            $(".conv-name").text(channelName);

            // Optionnel: ajouter une classe active à la sélection visuelle
            $(".user-card").removeClass("active");
            $(this).addClass("active");

        }});
});