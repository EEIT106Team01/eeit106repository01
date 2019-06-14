//URL Domain
const urlDomain = `/`;

//Get Member
let memberBean = Cookies.get("MemberBean");
let member = null;
if (memberBean != undefined) {
    member = JSON.parse(Cookies.get("MemberBean"));
}

// Verify Login
function verifyLogin() {
    if (memberBean == undefined) {
        $(`#draggable-events`).hide();
        location.href = "/forum/login.html";
    } else {
        $(`#draggable-events`).show();
        addToCart();
    }
}

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
    return dateObject.toLocaleString(`en-US`);
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

// Generate Rating Star
function generateRatingStarUtil(rating) {
    let ratingFloat = parseFloat(rating);
    if (rating == 0) {
        return `<span class='fa fa-star-o'></span><span class='fa fa-star-o'></span><span class='fa fa-star-o'></span><span class='fa fa-star-o'></span><span class='fa fa-star-o'></span>`
    } else if (rating > 0 && rating < 1) {
        return `<span class='fa fa-star-half-o'></span><span class='fa fa-star-o'></span><span class='fa fa-star-o'></span><span class='fa fa-star-o'></span><span class='fa fa-star-o'></span>`
    } else if (rating == 1) {
        return `<span class='fa fa-star'></span><span class='fa fa-star-o'></span><span class='fa fa-star-o'></span><span class='fa fa-star-o'></span><span class='fa fa-star-o'></span>`
    } else if (rating > 1 && rating < 2) {
        return `<span class='fa fa-star'></span><span class='fa fa-star-half-o'></span><span class='fa fa-star-o'></span><span class='fa fa-star-o'></span><span class='fa fa-star-o'></span>`
    } else if (rating == 2) {
        return `<span class='fa fa-star'></span><span class='fa fa-star'></span><span class='fa fa-star-o'></span><span class='fa fa-star-o'></span><span class='fa fa-star-o'></span>`
    } else if (rating > 2 && rating < 3) {
        return `<span class='fa fa-star'></span><span class='fa fa-star'></span><span class='fa fa-star-half-o'></span><span class='fa fa-star-o'></span><span class='fa fa-star-o'></span>`
    } else if (rating == 3) {
        return `<span class='fa fa-star'></span><span class='fa fa-star'></span><span class='fa fa-star'></span><span class='fa fa-star-o'></span><span class='fa fa-star-o'></span>`
    } else if (rating > 3 && rating < 4) {
        return `<span class='fa fa-star'></span><span class='fa fa-star'></span><span class='fa fa-star'></span><span class='fa fa-star-half-o'></span><span class='fa fa-star-o'></span>`
    } else if (rating == 4) {
        return `<span class='fa fa-star'></span><span class='fa fa-star'></span><span class='fa fa-star'></span><span class='fa fa-star'></span><span class='fa fa-star-o'></span>`
    } else if (rating > 4 && rating < 5) {
        return `<span class='fa fa-star'></span><span class='fa fa-star'></span><span class='fa fa-star'></span><span class='fa fa-star'></span><span class='fa fa-star-half-o'></span>`
    } else if (rating == 5) {
        return `<span class='fa fa-star'></span><span class='fa fa-star'></span><span class='fa fa-star'></span><span class='fa fa-star'></span><span class='fa fa-star'></span>`
    }
}