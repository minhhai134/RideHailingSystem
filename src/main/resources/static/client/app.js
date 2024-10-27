const hostWithPort = 'localhost:8081'

var username ="";


function loadUser() {
    username = sessionStorage.getItem("userId");
    
    if (!username) {
        // If user is not logged in, redirect to login page
        window.location.href = "login.html";
    } else {
        // Display the username

        document.getElementById("welcomeMessage").innerHTML = "Client: " + username;
    }
    console.log(username);

}

// Khởi tạo stompClient
const stompClient = new StompJs.Client({    
    brokerURL: `ws://localhost:8081/ws`
    //ws://${hostWithPort}/ws
});

//------------------------STOMP CLIENT Config-----------------------------------------------------

stompClient.onConnect = (frame) => {
    setConnected(true); // -> Just for modifying Connect and Disconnect Button

    console.log('Connected: ' + frame);

    // // Callback when receive messages from topic '/user/queue/messages'
    // stompClient.subscribe('/user/queue/messages', (greeting) => {
    //     console.log("got msg", JSON.parse(greeting.body));
    //     appendNewMessage(JSON.parse(greeting.body).content);
    // });
    
    // Callback when receive messages from topic ...
    stompClient.subscribe('/topic/'+ username, (greeting) => {
        var noti = JSON.parse(greeting.body);
        console.log("got msg", JSON.parse(greeting.body));
        appendNewMessage(JSON.parse(greeting.body).content);
    });

};



stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};


//------------------------------------------------------------------------------------------------


// Config Connect and Disconnect Button
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}


function appendNewMessage(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendMessage());
});

