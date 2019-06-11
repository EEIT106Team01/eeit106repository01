//URL Domain
const urlDomain = `/`;

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

// Find All from LocalStorage(shopping Cart)
function findAllFromLocalStorage() {
    let jsonArray = [],
        keys = Object.keys(localStorage),
        i = keys.length;
    while (i--) {
        if (isNaN(keys[i])) {
            // console.log(`There are ` + i + ` NaN.`);
        } else {
            let jsonObject = JSON.parse(localStorage.getItem(keys[i]));
            jsonArray.push(jsonObject);
        }
    };
    return jsonArray;
};

//計算時間
function GetDateStr(AddDayCount) {
    var dd = new Date();
    dd.setDate(dd.getDate() + AddDayCount);
    var y = dd.getFullYear();
    var m = (dd.getMonth() + 1) < 10 ? "0" + (dd.getMonth() + 1) : (dd.getMonth() + 1);
    var d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();
    return y + "-" + m + "-" + d;
}