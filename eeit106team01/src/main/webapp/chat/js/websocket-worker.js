self.importScripts("/chat/js/sockjs.min.js");
self.importScripts("/chat/js/stomp.js");


var ports = [];
var subscribeNorth = [];
var subscribeMiddle = [];
var subscribeSouth = [];
var subscribeEast = [];

var stompClient = null;

function connect(e) {
    let chatUsername = e.data.chatUsername;
    if (stompClient == null) {
        var socket = new SockJS('/simple');
        stompClient = Stomp.over(socket);
        stompClient.connect({
            'name': chatUsername
        }, function (frame) {
            console.log('Connected:' + frame);
            subsribe();
            console.log(e);
            console.log(ports);
            e.srcElement.postMessage({
                "command": "connected"
            });
        });
    } else {
        console.log(e);
        console.log(ports);
        e.srcElement.postMessage({
            "command": "connected"
        });
    }
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log('Disconnected');
}

function subsribe() {
    stompClient.subscribe('/user/topic/msg', function (response) {
        sendMessageBack("privateMessage", response.body);
    });
    stompClient.subscribe('/user/topic/checkUser', function (response) {
        sendMessageBack("checkUser", response.body);
    });
    stompClient.subscribe('/topic/getOnlineUsers', function (response) {
        sendMessageBack("getOnlineUsers", response.body);
    });
    subscribeRegion("north");
    subscribeRegion("middle");
    subscribeRegion("south");
    subscribeRegion("east");
}

function subscribeRegion(region) {
    stompClient.subscribe('/topic/' + region, function (response) {
        sendMessageBack("regionMessage", response.body, region);
    });
}


function sendMessageBack(command, message, region) {
    let data = {
        "command": command,
        "message": message,
        "region": region
    };
    for (var m = 0, n = ports.length; m < n; m++) {
        ports[m].postMessage(data);
    }
}

onconnect = function (e) {
    if (e.ports && e.ports.length > 0) {
        for (var i = 0, j = e.ports.length; i < j; i++) {
            console.log(e);
            e.ports[i].onmessage = function (e) {
                if (e.data.command == "subscribeRegion") {
                    if (e.data.region == "north") {
                        subscribeNorth.push(e.ports[i]);
                    }
                    if (e.data.region == "middle") {
                        subscribeMiddle.push(e.ports[i]);
                    }
                    if (e.data.region == "south") {
                        subscribeSouth.push(e.ports[i]);
                    }
                    if (e.data.region == "east") {
                        subscribeEast.push(e.ports[i]);
                    }
                } else if (e.data.command == "getAllActiveMsg") {
                    getAllActiveMsg(e);
                } else if (e.data.command == "regionMessage") {
                    sendRegionMessage(e);
                } else if (e.data.command == "privateMessage") {
                    sendPrivateMessage(e);
                } else if (e.data.command == "getOnlineUsers") {
                    getOnlineUsers(e);
                } else if (e.data.command == "checkUser") {
                    checkUser(e);
                } else if (e.data.command == "getRegionActive") {
                    getRegionActive(e);
                } else if (e.data.command == "connect") {
                    connect(e);
                } else if (e.data.command == "closing") {
                    ports.splice(ports.indexOf(this), 1);
                    subscribeNorth.splice(subscribeNorth.indexOf(this), 1);
                    subscribeMiddle.splice(subscribeMiddle.indexOf(this), 1);
                    subscribeSouth.splice(subscribeSouth.indexOf(this), 1);
                    subscribeEast.splice(subscribeEast.indexOf(this), 1);
                    console.log(ports);
                }
            }
            ports.push(e.ports[i]);
        }
    }
}

function getRegionActive(e) {
    let region = e.data.region;
    if (region) {
        let regionSubscribe = stompClient.subscribe('/user/topic/' + region + '/getActive', function (response) {
            e.srcElement.postMessage({
                "command": "getRegionActive",
                "message": response.body,
                "region": region
            });
            regionSubscribe.unsubscribe();
        });
        stompClient.send("/" + region + '/getActive', {});
    }
}

function getAllActiveMsg(e) {
    let activeMsgSubscribe = stompClient.subscribe('/user/topic/getAllActiveMsg', function (response) {
        console.log(e);
        e.srcElement.postMessage({
            "command": "getAllActiveMsg",
            "message": response.body
        });
        activeMsgSubscribe.unsubscribe();
    });
    stompClient.send("/getAllActiveMsg", {});
}

function sendRegionMessage(e) {
    stompClient.send("/" + e.data.region, {}, e.data.message);
}

function sendPrivateMessage(e) {
    stompClient.send("/msg", {}, e.data.message);
}

function getOnlineUsers(e) {
    stompClient.send("/getOnlineUsers", {});
}

function checkUser(e) {
    stompClient.send("/checkUser", {}, e.data.message);
}