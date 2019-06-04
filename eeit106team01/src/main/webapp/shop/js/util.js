//URL Domain
const urlDomain = `http://localhost:8080/`;

//Current Time
function getCurrentTime() {
    let currentTime = Date.now();
    let date = new Date(currentTime);
    return date;
};

// Find All from LocalStorage
function findAllFromLocalStorage() {
    let jsonArray = [],
        keys = Object.keys(localStorage),
        i = keys.length;
    while (i--) {
        let jsonObject = JSON.parse(localStorage.getItem(keys[i]));
        jsonArray.push(jsonObject);
    };
    return jsonArray;
};