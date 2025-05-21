
let isFirstMessage = true;

let currentSubscription = null;

let hasConnected = false;

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws'
});

let currentChannelId = null;

stompClient.onConnect = (frame) => {
    setConnected(true);
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
}

function subscribeToChannel(channelId) {
    if (currentSubscription) {
        currentSubscription.unsubscribe();
    }

    currentSubscription = stompClient.subscribe(`/topic/messages/${channelId}`, (message) => {
        const msg = JSON.parse(message.body);
        showMessage(msg.sender, msg.content, msg.dateMessage);
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

function showMessage(sender, message, date) {
    const userId = $("#current-user-id").val();
    const chatMessages = document.getElementById("chat-messages");

    const messageElement = document.createElement("div");

    let profileImageSrc = ""
    let firstMessageText = ""

    if (isFirstMessage) {
        const dateMessage = date.slice(0, 10);
        firstMessageText = `<div class="debut-conv">
                                <p class="debut-conv-text">
                                    <span class="trait"></span>
                                    Début de la conversation : 20 Mai 2025
                                    <span class="trait"></span>
                                </p>
                            </div>`
        chatMessages.innerHTML = firstMessageText;
        isFirstMessage = false;
    }

    if (parseInt(userId) !== sender.idUser) {
        messageElement.className = "message-row friend";
        profileImageSrc = `
                    <a href="/user/${sender.pseudo}" class="prof-pic-container">
                        <img src="${sender.personImagePath}" alt="Photo de profil" class="profile-pic">
                    </a>
                    <div class="full-message">
                        <div class="message-pseudo">
                            <strong>${sender.pseudo}</strong>
                        </div>
                `;

    } else {
        messageElement.className = "message-row me";
        profileImageSrc = `
                    <div class="full-message">
                `;
    }



    const last = $("#lastMessage-"+currentChannelId).get(0);
    last.innerHTML = message

    messageElement.innerHTML =
        profileImageSrc +`
        
            <div class="bubble">
                <div class="message-text-body">${message}</div>
                <span class="message-date">
                    ${date}
                </span>
            </div>
            
        </div>
    `;

    chatMessages.appendChild(messageElement);
    chatMessages.scrollTop = chatMessages.scrollHeight;

    // Add long press behavior
    const bubble = messageElement.querySelector('.bubble');

    const dateSpan = bubble.querySelector('.message-date');

    let pressTimer = null;

    bubble.addEventListener('mousedown', () => {
        pressTimer = setTimeout(() => {
            bubble.classList.add('show-date');
        }, 500);
    });

    bubble.addEventListener('mouseup', () => {
        clearTimeout(pressTimer);
        bubble.classList.remove('show-date');
    });

    bubble.addEventListener('mouseleave', () => {
        clearTimeout(pressTimer);
        bubble.classList.remove('show-date');
    });

    bubble.addEventListener('touchstart', () => {
        pressTimer = setTimeout(() => {
            bubble.classList.add('show-date');
        }, 500);
    });

    bubble.addEventListener('touchend', () => {
        clearTimeout(pressTimer);
        bubble.classList.remove('show-date');
    });
}

function changeChannel(channelId) {
    currentChannelId = channelId;

    isFirstMessage = true;

    $("#chat-messages").html("");
    $.ajax({
        url: "/api/messages/channel/" + channelId,
        method: "GET",
        success: function (messages) {
            messages.forEach(function (msg) {
                showMessage(msg.sender, msg.content, msg.dateMessage);
            });
        },
        error: function () {
            console.error("Erreur lors du chargement des messages");
        }
    });
    if (stompClient.connected) {
        subscribeToChannel(channelId);
    } else {
        stompClient.activate({}, function () {
            subscribeToChannel(channelId);
        });
    }
}

$(function () {
    $("#form-message").on('submit', (e) => {
        e.preventDefault();
        sendMessage(currentChannelId);
    });
    $("#disconnect").click(() => disconnect());
    $(".user-card").click(function() {

        const channelName = $(this).find(".user-name").text();
        $(".conv-name").text(channelName);

        $(".user-card").removeClass("active");
        $(this).addClass("active");

        if (!hasConnected) {
            connect();
            hasConnected = true;
        }
        const channelId = $(this).data("channel-id");
        const isGroup = $(this).data("is-group");
        const channel = $(this).data("channel");

        const settingsIcon = document.querySelector('.conv-settings');

        if (isGroup) {
            settingsIcon.style.display = 'flex';
        }
        else {
            settingsIcon.style.display = 'none';
        }

        if (channelId) {
            changeChannel(channelId);
        }});
});

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

function formatDateToFrench(dateStr) {
    const [day, month, year] = dateStr.split("-");
    const date = new Date(`${year}-${month}-${day}`);

    return date.toLocaleDateString('fr-FR', {
        day: '2-digit',
        month: 'long',
        year: 'numeric'
    });
}


