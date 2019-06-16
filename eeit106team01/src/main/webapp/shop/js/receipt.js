$(function() {
    verifyLogin();
    getReceipt(getEcpaySn());
    getReceiptItem(getEcpaySn().substr(getEcpaySn().indexOf(`ZZ`) + 2, getEcpaySn().length));
});

// Get ecpay SN
function getEcpaySn() {
    let ecpaySn = location.href.substr(location.href.indexOf(`?`) + 1, location.href.length);
    return ecpaySn;
};

//member
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

function getReceipt(ecpaySn) {
    let url = urlDomain + `shop/receipt/` + ecpaySn;
    $.ajax({
        type: "GET",
        url: url,
        success: function(response) {
            document.title = document.title + ecpaySn;
            $(`#receiptId`).append(ecpaySn);
            $(`#createTime`).append(getLocaleTime(response.createTime));
            $(`#productsTotalPrice`).append(response.productTotalPrice);
            $(`#deliveryPrice`).append(response.deliverPrice);
            $(`#totalPrice`).append(response.productTotalPrice + response.deliverPrice);
            if ((response.payStatus).match(`paid`)) {
                $(`#payStatus`).append(`已付款`);
            }
            let receiverInformation = response.receiverInformation;
            $(`#receiverName`).append(`&nbsp;&nbsp;` + receiverInformation.receiver);
            $(`#receiverAddress`).append(`&nbsp;&nbsp;` + receiverInformation.address);
            $(`#receiverPhone`).append(`&nbsp;&nbsp;` +  member.phone);
            $(`#receiverMail`).append(`&nbsp;&nbsp;` + member.email);
        }
    }).fail(function(response) {
        console.log(response);
    });
};

function getReceiptItem(purchaseId) {
    let url = urlDomain + `shop/findPurchaseListById/?idType=purchase&id=` + purchaseId;
    $.ajax({
        type: "GET",
        url: url,
        success: function(response) {
            let productDivArray = [];
            let num = 0;
            response.forEach(element => {
                let product = element.productId;
                let tr = $(`#receiptItem tr`).eq(response.indexOf(element) - 1);
                let td = $(tr).children(`td`).eq(1).text();
                let count = parseInt($(tr).children(`td`).eq(2).text());
                if (isNaN(count)) {
                    count = 0;
                }
                let productId = element.id;
                let productName = product.name;
                let productPrice = element.price;
                let productCount = 1;
                if (td == productName) {
                    productCount += count;
                } else {
                    num++;
                }
                let productDiv =
                    `<tr>` +
                    `<td class="text-center" id="` +
                    productId +
                    `">` +
                    num +
                    `</td>` +
                    `<td>` +
                    productName +
                    `</td>` +
                    `<td>` +
                    productCount +
                    `</td>` +
                    `<td>` +
                    `$` +
                    productPrice +
                    `</td>` +
                    `</tr>`;
                productDivArray.push(productDiv);

                $(productDiv).appendTo(`#receiptItem`);
                if (td == productName) {
                    $(tr).empty();
                    count += 1;
                }
            });
        }
    });
}