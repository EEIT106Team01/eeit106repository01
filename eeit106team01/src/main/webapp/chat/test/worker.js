var ports = []


onconnect = function (e) {

    if (e.ports && e.ports.length > 0) {
        for (var i = 0, j = e.ports.length; i < j; i++) {
            e.ports[i].onmessage = function (e) {
                for (var m = 0, n = ports.length; m < n; m++) {
                    ports[m].postMessage(e.data);
                }
            }
            ports.push(e.ports[i]);
        }
    }
}