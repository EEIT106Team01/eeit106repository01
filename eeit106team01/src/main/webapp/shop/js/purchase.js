//RETURN VALUES
let shoppingCart = findAllFromLocalStorage();
let currentTime = getCurrentTime();

//TAGS
let timeTitle = $(`#currentTime`).text();
let productsTotalPrice = $(`#productsTotalPrice`).text();
let deliveryPrice = $(`#deliveryPrice`).text().substr($(`#deliveryPrice`).text().indexOf('$') + 1, $(`#deliveryPrice`).text().length);
let memberDiscount = generateMemberDiscount();

//Document Ready
$(function () {
    //Time
    $(`#currentTime`).text(currentTime);
    //Purchase ID
    getNewPurchaseId();
    //Products
    $(`#productsFromCart`).append(generateProductHtml());
    //Price
    $(`#productsTotalPrice`).append(generateProductsTotalPrice());
    if (memberDiscount) {
        $(`#totalPrice`).append(generateProductsTotalPrice() + parseInt(deliveryPrice) - 100);
    } else {
        $(`#totalPrice`).append(generateProductsTotalPrice() + parseInt(deliveryPrice));
        $(`#memberDiscount`).append(`無折扣`);
    }
});

//New Purchase ID
function getNewPurchaseId() {
    const resource = `shop/findPurchaseIdCount`;
    $.ajax({
        type: "GET",
        url: urlDomain + resource,
        success: function (response) {
            let currentPurchaseSize = response;
            let newPurchaseId = new String(currentPurchaseSize + 1);
            generatePurchaseId(newPurchaseId);
            //Title
            document.title = $(`#purchaseId`).text();
        }
    });
};

//Product From Cart
function getProductFromCart() {
    return shoppingCart;
};

function generatePurchaseId(purchaseId) {
    $(`#purchaseId`).append(purchaseId);
};

//Generate Product html
function generateProductHtml() {
    let products = getProductFromCart();
    let productDivArray = [];
    products.forEach(product => {
        let productDiv = `<tr>` +
                            `<td class="text-center" id="` + product.id + `">` + (products.indexOf(product) + 1) + `</td>` +
                            `<td>` + product.name + `</td>` +
                            `<td>` + product.quantity + `</td>` +
                            `<td class="text-right">`+ `$`+product.price + `</td>` +
                        `</tr>`;
        product.image;
        productDivArray.push(productDiv);
    });
    return productDivArray;
};

//Generate Products Total Price
function generateProductsTotalPrice() {
    let productsTotalPrice = 0;
    let products = getProductFromCart();
    products.forEach(product => {
        productsTotalPrice += product.price;
    });
    return productsTotalPrice;
};

//Generate Member Discount
function generateMemberDiscount() {
    if (1 == 2) {
        return true;
    } else {
        return false;
    }
};