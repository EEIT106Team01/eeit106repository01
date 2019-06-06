//URL Domain
const urlDomain = `http://localhost:8080/`;

// Controller
const getOneProduct = `shop/product.html?`;

//Current Time
function getCurrentTime() {
    let currentTime = Date.now();
    let date = new Date(currentTime);
    return date;
};

//Get Locale Time
function getLocaleTime(date) {
    let dateObject = new Date(Date.parse(date));
    return dateObject.toLocaleString(`zh-TW`);
}

// Find All from LocalStorage
function findAllFromLocalStorage() {
    let jsonArray = [],
        keys = Object.keys(localStorage),
        i = keys.length;
    while (i--) {
        if (isNaN(keys[i])) {
            console.log(`There are ` + i + ` NaN.`);
        } else {
            let jsonObject = JSON.parse(localStorage.getItem(keys[i]));
            jsonArray.push(jsonObject);
        }
    };
    return jsonArray;
};