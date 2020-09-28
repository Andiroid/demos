var sendMsgBtn = document.getElementById("sendMsgBtn"),
    msgDisplay = document.getElementById('msgDisplay'),
    msgInput = document.getElementById("msgInput"),
    startWrap = document.getElementById("startWrap"),
    usrNameInput = document.getElementById("usrName"),
    websocket = username = null,
    objDiv = document.getElementById("msgDisplay"),
    connectionState = false;


(establishConnection = (usr) => {
    websocket = new WebSocket(`ws://${location.host}/api/stream?queryId=${usr}`);
    //sendMsgBtn.addEventListener("click", onClickButton);
})(username);


joinChat = () => {
    startWrap.style.display = "none";
    if (usrNameInput.value !== "") {
        document.getElementById('msgDisplay').innerHTML = "";
        establishConnection(usrNameInput.value);
    }
}


websocket.onopen = function (e) {
    toggleConnect();
    //document.getElementById('status').innerHTML = "<span onclick='toggleConnect()' class='connected' alt='connected' title='connected'>•<span>";
    //writeToScreen("<span style='color:green;'>CONNECTED<span>");
    //doSend("CONNECTED ESTABLISHED");
};

websocket.onclose = function (e) {
    if (e.wasClean) {
        //alert(`[close] Connection closed cleanly, code=${e.code} reason=${e.reason}`);
    } else {
        // e.g. server process killed or network down
        // event.code is usually 1006 in this case
        //alert('[close] Connection died');
    }
    //document.getElementById('status').innerHTML = "<span onclick='toggleConnect()' alt='disconnected' title='disconnected' class='disconnected'>•<span>";
    toggleConnect();
};

websocket.onmessage = function (e) {
    writeToScreen("<span>" + e.data + "</span>");
    objDiv.scrollTop = objDiv.scrollHeight;
};

websocket.onerror = function (e) {
    writeToScreen("<span class=error>ERROR:</span> " + e.data);
};

doSend = (message) => {
    //writeToScreen("SENT: " + message);
    websocket.send(message);
}

writeToScreen = (message) => {
    msgDisplay.insertAdjacentHTML("beforeend", `<p>${message}</p>`);
}

toggleConnect = () => {
    if (connectionState) {
        websocket.close();
        document.getElementById('status').innerHTML = "<span onclick='toggleConnect()' alt='disconnected' title='disconnected' class='disconnected'>•<span>";
        connectionState = false;
    } else {
        //establishConnection();
        document.getElementById('status').innerHTML = "<span onclick='toggleConnect()' class='connected' alt='connected' title='connected'>•<span>";
        connectionState = true;
    }
}

function onClickButton() {
    var msg = msgInput.value;
    doSend(msg);
    msgInput.value = "";
    msgInput.focus();
    objDiv.scrollTop = objDiv.scrollHeight;
}

msgInput.addEventListener("keyup", function (e) {
    if (e.keyCode === 13) {
        //event.preventDefault();
        onClickButton();
    }
});







//let url = `ws://${location.host}/api/stream`;
//let socket = new WebSocket(url);
//let ulElement = document.getElementById('msgDisplay');
//socket.onopen = function (e) {
//    //alert("[open] Connection established");
//    //alert("Sending to server");
//    socket.send("My name is John");

//};

//socket.onmessage = function (event) {

//    //alert(`[message] Data received from server: ${event.data}`);
//    ulElement.innerHTML = ulElement.innerHTML += `<li>${event.data}</li>`
//};

//socket.onclose = function (event) {
//    if (event.wasClean) {
//        alert(`[close] Connection closed cleanly, code=${event.code} reason=${event.reason}`);
//    } else {
//        // e.g. server process killed or network down
//        // event.code is usually 1006 in this case
//        alert('[close] Connection died');
//    }
//};

//socket.onerror = function (error) {
//    alert(`[error] ${error.message}`);
//};

//document.getElementById("sendMsgBtn").addEventListener("click", function () {
//    let textElement = document.getElementById("msgInput");
//    let text = textElement.value;
//    console.log('Sending text: ' + text);
//    socket.send(text);
//    textElement.value = '';
//});







//(function () {
//    let webSocket
//    var getWebSocketMessages = function (onMessageReceived) {
//        let url = `ws://${location.host}/api/stream`;
//        console.log('url is: ' + url);

//        webSocket = new WebSocket(url);

//        webSocket.onmessage = onMessageReceived;
//    };

//    let ulElement = document.getElementById('msgDisplay');

//    getWebSocketMessages(function (message) {
//        ulElement.innerHTML = ulElement.innerHTML += `<li>${message.data}</li>`
//        console.log(message);
//    });

//    document.getElementById("sendMsgBtn").addEventListener("click", function () {
//        let textElement = document.getElementById("msgInput");
//        let text = textElement.value;
//        console.log('Sending text: ' + text);
//        webSocket.send(text);
//        textElement.value = '';
//    });
//}());