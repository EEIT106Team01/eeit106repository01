$(function() {
    memberInfo(1);
    findPurchaseByMemberId(1);
});

//generate member info 
function memberInfo(id) {
    console.log();
    document.title = `Member ` + id + `-` + `訂單查詢`;
    $(`#memberId`).prepend(`Member ` + id + ` `);
}

//Generate Purchase By Member Id
function findPurchaseByMemberId(id) {
    let memberId = parseInt(id);
    $.ajax({
        type: "GET",
        url: `/shop/findPurchaseById?idType=member&id=` + memberId,
        success: function(response) {
            response.forEach(element => {
                let time = getLocaleTime(element.createTime);
                let daysAgo = getLocaleTime(GetDateStr(-1));
                let id;
                if (time > daysAgo) {
                    id = element.id + `&nbsp;&nbsp;&nbsp;<span class="label label-sm label-success">New Purchase</span>`
                } else {
                    id = element.id;
                }
                let totalPrice = element.productTotalPrice + element.deliverPrice;
                let refundStatus = `-`;

                $(`.datatable tbody`).append(
                    `<tr>
                        <td>` + refundStatus + `</td>
                        <td>` + id + ` </td>
                        <td>` + time + `</td>
                        <td>` + totalPrice + `</td>
                        <td>
                            <a href="javascript:;" onclick="showAjaxModal(` + element.id + `)" class="btn btn-info btn-lg btn-icon icon-left">
                                <i class="entypo-info"></i> 訂單明細
                            </a>
                            <a href="#" class="btn btn-danger btn-lg btn-icon icon-left">
                                <i class="entypo-help-circled"></i> 退貨申請
                            </a>
                        </td>
                    </tr>`
                );
            });
        }
    });
}

function showAjaxModal(id) {
    $('#modal-7 .modal-body tbody').text(``);
    $('#modal-7').modal('show', { backdrop: 'static' });
    $.ajax({
        url: `/shop/findPurchaseListById/?idType=purchase&id=` + id,
        success: function(response) {
            response.forEach(element => {
                let productId = element.productId.id;
                let purchaseListId = element.id;
                let productName = element.productId.name;
                let productPrice = element.price;
                let productSN = element.serialNumber;
                $('#modal-7 .modal-body tbody').append(
                    `<tr>
                        <td id="` + purchaseListId + `"><a href="/shop/product.html?` + productId + `">` + productName.substr(0, 8) + `...</a>` + `</td>
                        <td>` + productPrice + `</td>
                        <td>` + productSN + `</td>
                    </tr>`
                );
            });
        }
    });
}