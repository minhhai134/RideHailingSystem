const hostWithPort = 'localhost:8081'

var userId ="";
let fareId, riderId;
var myname = "";
var long_ = "13.361389"; var lat_ = "";

function loadUser() {
    userId = sessionStorage.getItem("userId");
    myname =  sessionStorage.getItem("name");
    if (!userId) {
        // If user is not logged in, redirect to login page
        window.location.href = "login.html";
    } else {
        // Display the userId

        document.getElementById("welcomeMessage").innerHTML = "Driver: " + userId;
    }
    console.log(userId);

// Tạo location:
    switch(myname) {
        case "driver1":
         lat_ = 39.115555;
          break;
        case "driver2":
            lat_ = 39.115556;
          break;
        case "driver3":
            lat_ = 39.115557;
            break;
        case "driver4":
            lat_ = 39.115558;
            break;
      }

      updateLocation("online");
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
    stompClient.subscribe('/topic/'+userId, (message) => {
        var noti = JSON.parse(message.body);
        console.log("got msg", JSON.parse(message.body));
        // appendNewMessage(JSON.parse(greeting.body).content);
        const data = JSON.parse(message.body);
                    if (data && data.fareId && data.riderId) {
                        fareId = data.fareId;
                        riderId = data.riderId;
                        document.getElementById("rideActions").style.display = "block";
                    }

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



$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendMessage());
});

//-----------------------------------------------------------------------------------------------------
// Update location:
async function updateLocation(status) {
    const locationData = {
        id: userId,
        lat_: lat_,
        long_: long_,
        status: status
    };
    try {
        await fetch('http://localhost:8081/api/driver/update-location', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(locationData)
        });
        console.log('Location updated');
    } catch (error) {
        console.error('Error updating location:', error);
    }
}


// Response Ride Matching:
        async function respondToRide(accept) {
            const response = {
                fareId: fareId,
                riderId: riderId,
                driverId: driverId,
                accept: accept ? "accept" : "reject"
            };
            try {
                await fetch('http://localhost:8081/api/driver/response-ride-matching', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(response)
                });
                console.log(`Ride ${accept ? "accepted" : "rejected"}`);
                document.getElementById("rideActions").style.display = "none";
            } catch (error) {
                console.error('Error responding to ride:', error);
            }
        }
