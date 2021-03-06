//Document Ready
$(function() {
    verifyLogin();
    //Time
    $(`#currentTime`).text(getLocaleTime(getCurrentTime()));
    //Purchase ID
    getNewPurchaseId();
    //Products
    appendProduct();
    //delivery price 
    $(`#deliveryPrice`).empty();
    if (($(`#productsFromCart tr`).length == 1 && $($(`#productsFromCart tr:last td`).eq(1)).text().match(/VIP會員/)) || member.level.match(/VIP/)) {
        $(`#deliveryPrice`).append(`$` + 0);
    } else {
        $(`#deliveryPrice`).append(`$` + 60);
    }
    let deliveryPrice = $(`#deliveryPrice`).text().replace(`$`, ``);
    //Price
    appendTotalPrice(deliveryPrice);
    newPurchase();
    quantityBtn();
});

function appendProduct() {
    $(`#productsFromCart`).empty();
    $(`#productsFromCart`).append(generateProductHtml());
    if ($(`#productsFromCart tr`).length == 0) {
        location.href = `/shop/index.html`;
    }
}

function appendTotalPrice(deliveryPrice) {
    $(`#productsTotalPrice`).empty();
    $(`#productsTotalPrice`).append(`$` + generateProductsTotalPrice());
    if (memberDiscount) {
        $(`#totalPrice`).empty();
        $(`#totalPrice`).append(`$`);
        $(`#totalPrice`).append(
            Math.round((generateProductsTotalPrice() + parseInt(deliveryPrice)) * 0.9));
        $(`#memberDiscount`).empty();
        $(`#memberDiscount`).append(`VIP`);
    } else {
        $(`#totalPrice`).empty();
        $(`#totalPrice`).append(`$`);
        $(`#totalPrice`).append(generateProductsTotalPrice() + parseInt(deliveryPrice));
        $(`#memberDiscount`).empty();
        $(`#memberDiscount`).append(`無折扣`);
    }
}

//TAGS
let timeTitle = $(`#currentTime`).text();
let productsTotalPrice = $(`#productsTotalPrice`).text();
let memberDiscount = generateMemberDiscount();

//Member
$(`#memberId`).append(`&nbsp;&nbsp;` + member.id);
$(`#memberName`).append(`&nbsp;&nbsp;` + member.name);
if (member.level.match(/normal/)) {
    $(`#memberShip`).append(`&nbsp;&nbsp;普通會員`);
} else if (member.level.match(/VIP/)) {
    $(`#memberShip`).append(`&nbsp;&nbsp;VIP會員`);
}
$(`#memberAddress`).append(`&nbsp;&nbsp;` + member.address);
$(`#memberMail`).append(`&nbsp;&nbsp;` + member.email);
$(`#memberPhone`).append(`&nbsp;&nbsp;` + member.phone);


//New Purchase ID
function getNewPurchaseId() {
    const resource = `shop/findPurchaseIdCount`;
    $.ajax({
        type: "GET",
        url: urlDomain + resource,
        success: function(response) {
            let currentPurchaseSize = response;
            let newPurchaseId = new String(currentPurchaseSize + 1);
            generatePurchaseId(newPurchaseId);
            //Title
            document.title = $(`#purchaseId`).text();
        }
    });
}

//Product From Cart
function getProductFromCart() {
    return findAllFromLocalStorage();
}

function generatePurchaseId(purchaseId) {
    $(`#purchaseId`).empty();
    $(`#purchaseId`).append(`購物車 No. ` + purchaseId);
}

//Generate Product html
function generateProductHtml() {
    let products = getProductFromCart();
    let productDivArray = [];
    products.forEach(product => {
        let productDiv =
            `<tr>` +
            `<td class="text-center" id="` + product.id + `"><a class="` + product.id + `" href="javaScript:;" onclick="deletePurchaseInCart(this)">` + (products.indexOf(product) + 1) + `</a></td>` +
            `<td>` +
            product.name +
            `</td>` +
            `<td>` +
            `<div class="input-spinner" name="price-` + product.price + `" id="id-` + product.id + `">
            <button type="button" class="btn btn-primary " data-step="-1">-</button>
            <input class="form-control size-2" value="` + product.quantity + `" data-min="1" data-max="` + product.totalQuantity + `">
            <button type="button" class="btn btn-primary " data-step="1">+</button>
            </div>` +
            `</td>` +
            `<td>` +
            `$` +
            product.price +
            `</td>` +
            `</tr>`;
        product.image;
        productDivArray.push(productDiv);
    });
    return productDivArray;
}

//Generate Products Total Price
function generateProductsTotalPrice() {
    let productsTotalPrice = 0;
    let products = getProductFromCart();
    products.forEach(product => {
        productsTotalPrice += product.price * product.quantity;
    });
    return productsTotalPrice;
}

//Generate Member Discount
function generateMemberDiscount() {
    if (member.level.match(/VIP/)) {
        return true;
    } else {
        return false;
    }
}

//Quantity Btn
function quantityBtn() {
    $(`.input-spinner button`).click(function() {
        let plusOrMinus;
        if ($(this).text().match(/\+/)) {
            plusOrMinus = true;
        } else {
            plusOrMinus = false;
        }
        let price = parseInt($(this).parent(`div .input-spinner`).attr(`name`).replace(`price-`, ``));
        let name = $(this).parent(`div .input-spinner`).attr(`name`);
        let indexCount = parseInt($(`.text-center:last`).text()) - 1;
        let otherPrice = 0;
        let id = parseInt($(this).parent(`div .input-spinner`).attr(`id`).replace(`id-`, ``));
        for (let index = 0; index < indexCount; index++) {
            otherPrice += parseInt($($(`div .input-spinner[name!=` + $(this).parent(`div .input-spinner`).attr(`name`) + `]`).eq(index)).attr(`name`).replace(`price-`, ``));
        }
        editTotalPrice(id, plusOrMinus, price, name, otherPrice);
    });
}

//Edit total price
function editTotalPrice(id, boolean, price, name, otherPrice) {
    let cart = localStorage;
    let currentProductPrice = parseInt($(`#productsTotalPrice`).text().replace(`$`, ``));
    let currentTotalPrice = parseInt($(`#totalPrice`).text().replace(`$`, ``));
    let inputValue = parseInt($(`div[name=` + name + `] input`).attr(`value`));
    if (boolean) {
        if (inputValue < parseInt($(`div[name=` + name + `] input`).attr(`data-max`))) {
            getProductFromCart().forEach(element => {
                if (element.id == id) {
                    let newProduct = new Object();
                    newProduct.id = element.id;
                    newProduct.name = element.name;
                    newProduct.price = element.price;
                    newProduct.quantity = inputValue + 1;
                    newProduct.image = element.image;
                    newProduct.totalQuantity = element.totalQuantity;
                    let newP = JSON.stringify(newProduct);
                    cart.removeItem(element.id);
                    cart.setItem(element.id, newP);
                    //Products
                    appendProduct();
                    //delivery price 
                    if (($(`#productsFromCart tr`).length == 1 && $($(`#productsFromCart tr:last td`).eq(1)).text().match(/VIP會員/)) || member.level.match(/VIP/)) {
                        $(`#deliveryPrice`).empty();
                        $(`#deliveryPrice`).append(`$` + 0);
                    } else {
                        $(`#deliveryPrice`).empty();
                        $(`#deliveryPrice`).append(`$` + 60);
                    }
                    let deliveryPrice = $(`#deliveryPrice`).text().replace(`$`, ``);
                    //Price
                    appendTotalPrice(deliveryPrice);
                    newPurchase();
                    quantityBtn();
                }
            });
        }
    } else {
        if (inputValue > parseInt($(`div[name=` + name + `] input`).attr(`data-min`))) {
            getProductFromCart().forEach(element => {
                if (element.id == id) {
                    let newProduct = new Object();
                    newProduct.id = element.id;
                    newProduct.name = element.name;
                    newProduct.price = element.price;
                    newProduct.quantity = inputValue - 1;
                    newProduct.image = element.image;
                    newProduct.totalQuantity = element.totalQuantity;
                    let newP = JSON.stringify(newProduct);
                    cart.removeItem(element.id);
                    cart.setItem(element.id, newP);
                    //Products
                    appendProduct();
                    //delivery price 
                    if (($(`#productsFromCart tr`).length == 1 && $($(`#productsFromCart tr:last td`).eq(1)).text().match(/VIP會員/)) || member.level.match(/VIP/)) {
                        $(`#deliveryPrice`).empty();
                        $(`#deliveryPrice`).append(`$` + 0);
                    } else {
                        $(`#deliveryPrice`).empty();
                        $(`#deliveryPrice`).append(`$` + 60);
                    }
                    let deliveryPrice = $(`#deliveryPrice`).text().replace(`$`, ``);
                    //Price
                    appendTotalPrice(deliveryPrice);
                    newPurchase();
                    quantityBtn();
                }
            });
        }
    }
}

$(`#receiver-check`).click(function(e) {
    if ($(this).prop("checked") == true) {
        $(`#receiverName`).attr(`value`, $(`#memberName`).text().trim());
        $(`#receiverAddress`).attr(`value`, $(`#memberAddress`).text().trim());
        $(`#receiverPhone`).attr(`value`, $(`#memberPhone`).text().trim());
        $(`#receiverMail`).attr(`value`, $(`#memberMail`).text().trim());
    } else {
        $(`#receiverName`).attr(`value`, "");
        $(`#receiverAddress`).attr(`value`, "");
        $(`#receiverPhone`).attr(`value`, "");
        $(`#receiverMail`).attr(`value`, "");
    };
});

//newPurchase
function newPurchase() {
    $(`#check-out`).click(function() {
        let url = `/shop/newPurchase`;
        let productIds = [];
        let payStatus = `unpaid`;
        let productTotalPrice = $(`#totalPrice`).text().replace(/\$/, ``);
        let deliverStatus = `unsent`;
        let deliverType = `address`;
        let deliveryPrice = parseInt($(`#deliveryPrice`).text().replace(`$`, ``));
        getProductFromCart().forEach(product => {
            for (let index = 0; index < product.quantity; index++) {
                productIds.push(product.id);
            }
        });
        let receiverInformationJson = new Object();
        receiverInformationJson.address = $(`#receiverAddress`).attr(`value`);
        receiverInformationJson.receiver = $(`#receiverName`).attr(`value`);

        let createJson = new Object();
        createJson.id = productIds;
        createJson.payStatus = payStatus;
        createJson.productTotalPrice = productTotalPrice;
        createJson.deliverStatus = deliverStatus;
        createJson.deliverType = deliverType;
        createJson.deliverPrice = deliveryPrice;
        createJson.receiverInformation = receiverInformationJson;
        createJson.memberId = member.id;
        let data = JSON.stringify(createJson);
        console.log(data);

        let cartLocalStorage = localStorage;
        cartLocalStorage.clear();

        $.ajax({
            type: "POST",
            url: url,
            data: data,
            contentType: `application/json`,
            success: function(response) {
                let id = String(response.id);
                $.ajax({
                    type: "POST",
                    url: "/shop/processEcpay",
                    data: id,
                    contentType: `application/json`,
                    success: function(response) {
                        $(`body`).html(response);
                    }
                });
            },
            error: function(jqXHr) {
                console.log(jqXHr);
            }
        });
    })
}

function deletePurchaseInCart(data) {
    localStorage.removeItem($(data).attr(`class`));
    //Purchase ID
    getNewPurchaseId();
    //Products
    appendProduct();
    //delivery price 
    $(`#deliveryPrice`).empty();
    if (($(`#productsFromCart tr`).length == 1 && $($(`#productsFromCart tr:last td`).eq(1)).text().match(/VIP會員/)) || member.level.match(/VIP/)) {
        $(`#deliveryPrice`).append(`$` + 0);
    } else {
        $(`#deliveryPrice`).append(`$` + 60);
    }
    let deliveryPrice = $(`#deliveryPrice`).text().replace(`$`, ``);
    //Price
    appendTotalPrice(deliveryPrice);
    newPurchase();
    quantityBtn();
};