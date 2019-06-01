importScripts("/chat/js/sockjs.min.js", "/chat/js/stomp.min.js");


var ports = [];
var subscribeNorth = [];
var subscribeMiddle = [];
var subscribeSouth = [];
var subscribeEast = [];

var name = "pikachu";
var stompClient = null;

if (stompClient == null) {
    connect();
}

function connect() {
    var socket = new SockJS('/simple');
    stompClient = Stomp.over(socket);
    stompClient.connect({
        'name': chatUsername
    }, function (frame) {
        console.log('Connected:' + frame);
        subsribe();
    });
    // $('#chat').attr('data-current-user', chatUsername);
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log('Disconnected');
}

function subsribe() {
    neonChat.close();
    stompClient.subscribe('/user/topic/msg', function (response) {
        sendMessageBack("privateMessage", response.body);
        // var msgBean = JSON.parse(response.body);
        // console.log(msgBean);
        // if (Array.isArray(msgBean)) {
        //     showOldPrivateMessage(msgBean);
        // } else {
        //     onReceivePrivateMessage(msgBean.fromUser, msgBean);
        // }
    });
    stompClient.subscribe('/user/topic/checkUser', function (response) {
        sendMessageBack("checkUser", response.body);
        // console.log(eval(response.body));
        // if (eval(response.body)) {
        //     let id = $("#checkUser").val();
        //     if (!$("#" + id).get(0)) {
        //         neonChat.addUser("group-2", id, "online", false, id);
        //         neonChat.refreshUserIds();
        //     }
        //     getOnlineUsers();
        // } else {
        //     $("#checkUser").attr("placeholder", "User does not exist!");
        // }
        // $("#checkUser").val("");
    });
    stompClient.subscribe('/topic/getOnlineUsers', function (response) {
        sendMessageBack("getOnlineUsers", response.body);
        // let data = eval(response.body);
        // let allUsers = neonChat.getAllUserIds();
        // if (Array.isArray(allUsers)) {
        //     $(allUsers).each(function (i) {
        //         if (data.indexOf(allUsers[i]) !== -1) {
        //             neonChat.setStatus(allUsers[i], "online");
        //         } else {
        //             neonChat.setStatus(allUsers[i], "offline");
        //         }
        //     });
        // }
    });
    subscribeRegion("north");
    subscribeRegion("middle");
    subscribeRegion("south");
    subscribeRegion("east");
}

function subscribeRegion(region) {
    stompClient.subscribe('/topic/' + region, function (response) {
        sendMessageBack("regionMessage", response.body, region);
        // var msgBean = JSON.parse(response.body);
        // console.log(msgBean);
        // if (Array.isArray(msgBean)) {
        //     showOldMessage(msgBean);
        // } else {
        //     onReceiveMessage(region, msgBean);
        // }
    });
}


function sendMessageBack(command, message, region) {
    let data = [command, message];
    for (var m = 0, n = ports.length; m < n; m++) {
        ports[m].postMessage(data);
    }
}

function sendMessageToServer(command, message, region) {
    if (stompClient != null) {
        if (command == "privateMessage") {
            stompClient.send("/msg", {}, message);
        } else if (command == "checkUser") {
            stompClient.send("/checkUser", {}, message);
        } else if (command == "getOnlineUsers") {
            stompClient.send("/getOnlineUsers", {});
        } else if (command == "regionMessage") {
            stompClient.send("/" + region, {}, message);
        } else if (command == "getOld") {
            if (!!region) {
                stompClient.send("/" + region, {}, message);
            } else {
                stompClient.send("/msg", {}, message);
            }
        }
    }
}


onconnect = function (e) {
    if (e.ports && e.ports.length > 0) {
        for (var i = 0, j = e.ports.length; i < j; i++) {
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
                    getRegionActive(e);
                }

                for (var m = 0, n = ports.length; m < n; m++) {
                    ports[m].postMessage(e.data);
                }

            }
            ports.push(e.ports[i]);
        }
    }
}

function getRegionActive(e) {
    let region = e.data.region;
    if (region) {
        let regionSubscribe =  stompClient.subscribe('/user/topic/' + region + '/getActive', function (response) {
            var msgBean = JSON.parse(response.body);
            console.log(msgBean);
            if (Array.isArray(msgBean)) {
                showOldMessage(msgBean, true);
            } else {
                onReceiveMessage(region, msgBean);
            }
            regionSubscribe.unsubscribe();
        });
        stompClient.send("/" + region + '/getActive', {});
    }
}