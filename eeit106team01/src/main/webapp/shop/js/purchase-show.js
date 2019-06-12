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
                if (Date.parse(element.createTime) > Date.parse(GetDateStr(-1))) {
                    if (element.id < 10) {
                        id = `0` + element.id + `&nbsp;&nbsp;&nbsp;<span class="label label-sm label-success">New Purchase</span>`
                    } else {
                        id = element.id + `&nbsp;&nbsp;&nbsp;<span class="label label-sm label-success">New Purchase</span>`
                    }
                } else {
                    if (element.id < 10) {
                        id = `0` + element.id;
                    } else {
                        id = element.id;
                    }
                }
                let totalPrice = element.productTotalPrice + element.deliverPrice;
                let refundStatus;
                $(`.datatable tbody`).append(
                    `<tr>
                    <td>
                        <a href="javascript:;" onclick="showAjaxModal(` + element.id + `,` + memberId + `)" class="btn btn-info btn-lg btn-icon icon-left">
                            <i class="entypo-info"></i> 訂單明細／退貨申請
                        </a>
                    </td>
                    <td>` + id + ` </td>
                    <td>` + time + `</td>
                    <td>$` + totalPrice + `</td>
                    </tr>`
                );
            });
        }
    });
}

function showAjaxModal(id, memberId) {
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
                let refundStatus;

                //find Refund
                $.ajax({
                    type: "GET",
                    url: "/shop/findRefundListById/?idType=purchaseList&id=" + purchaseListId,
                    success: function(response) {
                        refundStatus = `退貨中`;
                        $('#modal-7 .modal-body tbody').append(
                            `<tr>
                            <td id="` + purchaseListId + `"><a href="/shop/product.html?` + productId + `" target="_blank">` + productName.substr(0, 8) + `...</a>` + `</td>
                            <td style="font-size:15px">` + productSN + `</td>
                            <td><a title="` + response[0].refundId.comment + `">` + refundStatus + `</a></td>
                            <td>
                                <a href="javascript:;" onclick="refundEdit()" class="btn btn-danger btn-md btn-icon icon-left disabled">
                                    <i class="entypo-help-circled"></i> 退貨申請
                                </a>
                            </td>
                            </tr > `
                        );
                        $(`#show - productList`).text(`訂單編號` + id);
                    },
                    error: function() {
                        refundStatus = `-`;
                        $('#modal-7 .modal-body tbody').append(
                            `<tr>
                            <td id="` + purchaseListId + `"><a href="/shop/product.html?` + productId + `" target="_blank">` + productName.substr(0, 8) + `...</a>` + `</td>
                            <td style="font-size:15px">` + productSN + `</td>
                            <td>` + refundStatus + `</td>
                            <td>
                                <a href="javascript:;" id="` + productSN + `" onclick="refundEdit(this,` + purchaseListId + `)" class="btn btn-danger btn-md btn-icon icon-left">
                                    <i class="entypo-help-circled"></i> 退貨申請
                                </a>
                            </td>
                            </tr > `
                        );
                        $(`#show - productList`).text(`訂單編號` + id);
                    }
                });
            });
        }
    });
}

//newRefund
function newRefund(purchaseListIds, comment) {
    let purchaseListId = String(purchaseListIds);
    let json = new Object();
    json.purchaseListIds = purchaseListId;
    json.comment = $(`.edit-refund textarea`).val();
    let data = JSON.stringify(json);
    $.ajax({
        type: "POST",
        url: "/shop/newRefund",
        data: data,
        contentType: `application/json`,
        success: function(response) {
            location.href = location.href;
        }
    });
}

//create Refund edit
function refundEdit(refundEditBtn, purchaseListId) {
    $('#modal-7 .modal-body tbody .edit-refund').empty();
    $('#modal-7 .modal-footer span').text(``);
    $('#modal-7 .modal-body tbody').append(`
    <tr class="edit-refund"><td colspan="4"><strong>序號: ` + $(refundEditBtn).attr(`id`) + `</strong></td></tr>
    <tr class="edit-refund"><td colspan="4"><textarea cols="55" rows="5" placeholder="退貨申請說明..."></textarea ></td></tr>`)
    $(`#modal-7 .modal-footer`).prepend(`<span><button type="button" onclick="newRefund(` + purchaseListId + `)" class="btn btn-primary btn-lg">送出申請</button></span>`);
}