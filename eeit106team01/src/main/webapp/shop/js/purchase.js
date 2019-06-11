//RETURN VALUES
let shoppingCart = findAllFromLocalStorage();
let currentTime = getCurrentTime();

//TAGS
let timeTitle = $(`#currentTime`).text();
let productsTotalPrice = $(`#productsTotalPrice`).text();
let deliveryPrice = $(`#deliveryPrice`)
    .text()
    .substr(
        $(`#deliveryPrice`)
        .text()
        .indexOf("$") + 1,
        $(`#deliveryPrice`).text().length
    );
let memberDiscount = generateMemberDiscount();

//Document Ready
$(function() {
    //Time
    $(`#currentTime`).text(getLocaleTime(currentTime));
    //Purchase ID
    getNewPurchaseId();
    //Products
    $(`#productsFromCart`).append(generateProductHtml());
    //Price
    $(`#productsTotalPrice`).append(generateProductsTotalPrice());
    if (memberDiscount) {
        $(`#totalPrice`).append(
            generateProductsTotalPrice() + parseInt(deliveryPrice) - 100
        );
    } else {
        $(`#totalPrice`).append(
            generateProductsTotalPrice() + parseInt(deliveryPrice)
        );
        $(`#memberDiscount`).append(`無折扣`);
    }
    newPurchase();
    //Member
});

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
    return shoppingCart;
}

function generatePurchaseId(purchaseId) {
    $(`#purchaseId`).append(purchaseId);
}

//Generate Product html
function generateProductHtml() {
    let products = getProductFromCart();
    let productDivArray = [];
    products.forEach(product => {
        let productDiv =
            `<tr>` +
            `<td class="text-center" id="` +
            product.id +
            `">` +
            (products.indexOf(product) + 1) +
            `</td>` +
            `<td>` +
            product.name +
            `</td>` +
            `<td>` +
            `<div class="input-spinner">
            <button type="button" class="btn btn-primary " data-step="-1">-</button>
            <input class="form-control size-1" value="` + product.quantity + `" data-min="1" data-max="` + product.totalQuantity + `">
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
    if (1 == 2) {
        return true;
    } else {
        return false;
    }
}

$(`#receiver-check`).click(function(e) {
    if ($(this).prop("checked") == true) {
        $(`#receiverName`).attr(`value`, $(`#memberName`).text());
        $(`#receiverAddress`).attr(`value`, $(`#memberAddress`).text());
        $(`#receiverPhone`).attr(`value`, $(`#memberPhone`).text());
        $(`#receiverMail`).attr(`value`, $(`#memberMail`).text());
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
        let productTotalPrice = $(`#productsTotalPrice`).text().replace(/\$/, ``);
        let deliverStatus = `unsent`;
        let deliverType = `address`;
        let deliverPrice = 60;
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
        createJson.deliverPrice = deliverPrice;
        createJson.receiverInformation = receiverInformationJson;
        createJson.memberId = 1;
        let data = JSON.stringify(createJson);

        let cartLocalStorage = localStorage;
        cartLocalStorage.clear();

        $.ajax({
            type: "POST",
            url: url,
            data: data,
            contentType: `application/json`,
            success: function(response) {
                let id = String(response.id);
                console.log(id);
                $.ajax({
                    type: "POST",
                    url: "/shop/processEcpay",
                    data: id,
                    contentType: `application/json`,
                    success: function(response) {
                        $(`body`).html(response);
                    }
                });
            }
        });
    })
}