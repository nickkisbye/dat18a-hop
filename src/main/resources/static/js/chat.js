'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
var room = null

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

var bearerToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiB0b2tlbiIsInVzciI6IlJhc211cyIsImlzcyI6InNwcmluZ2Jvb3Qtand0dG9rZW4iLCJpYXQiOiIyMDIwLTEyLTMwIDE3OjM5OjMyIiwicm9sIjoiQWRtaW5pc3RyYXRvciJ9.ZdtHDyevz_mr08Hnqu1T3cWLq96hIa23yAPACDXH3WlrP0jZ640NvYgz2g0IAxDIGd-1VYM29s4l0C30VEXwvA";
var roomId = 1;

function connect(event) {
    username = document.querySelector('#name').value.trim();
    room = document.querySelector('#room').value.trim();
    if (username && room) {

        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
        var socket = new SockJS('/ws');

        stompClient = Stomp.over(socket);
        stompClient.connect({
            Bearer: "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiB0b2tlbiIsInVzciI6IlJhc211cyIsImlzcyI6InNwcmluZ2Jvb3Qtand0dG9rZW4iLCJpYXQiOiIyMDIwLTEyLTIyIDEzOjIyOjI1Iiwicm9sIjoiQWRtaW5pc3RyYXRvciJ9.2Y147jHd3JC66opuyCxB9KIpLdq5GOWjagDP6IeA5jjB--td59Xft8KP683nxztCQmThioudHKnnpRuDGnhOIA",
            room: 1
        }, onConnected, onError);

    }
    event.preventDefault();
}

function onConnected() {
    stompClient.subscribe('/topic/public', onMessageReceived, {Bearer: bearerToken, room: roomId});
    stompClient.subscribe('/topic/' + room, onMessageReceived, {Bearer: bearerToken, room: roomId});

    // Tell your username to the server
    stompClient.send("/app/chat.addUser", {
            Bearer: "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiB0b2tlbiIsInVzciI6IlJhc211cyIsImlzcyI6InNwcmluZ2Jvb3Qtand0dG9rZW4iLCJpYXQiOiIyMDIwLTEyLTIyIDEzOjIyOjI1Iiwicm9sIjoiQWRtaW5pc3RyYXRvciJ9.2Y147jHd3JC66opuyCxB9KIpLdq5GOWjagDP6IeA5jjB--td59Xft8KP683nxztCQmThioudHKnnpRuDGnhOIA",
            room: 1
        },
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage/" + room, {
            Bearer: bearerToken,
            room: roomId
        }, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if (message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)
