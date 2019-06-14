$(function() {
    verifyLogin();
    memberInfo(member);
    findPurchaseByMemberId(member);
});

//generate member info 
function memberInfo(member) {
    let name = member.name;
    document.title = name + ` - ` + `訂單查詢`;
    $(`#memberId`).prepend(name + ` `);
}

//Generate Purchase By Member Id
function findPurchaseByMemberId(member) {
    let memberId = parseInt(member.id);
    $.ajax({
        type: "GET",
        url: `/shop/findPurchaseById?idType=member&id=` + memberId,
        success: function(response) {
            response.forEach(element => {
                let time = getLocaleTime(element.createTime);
                let daysAgo = getLocaleTime(GetDateStr(-1));
                let id;
                if (Date.parse(element.createTime) < Date.parse(GetDateStr(-1))) {
                    if (element.id < 10) {
                        id = `0` + element.id + `&nbsp;&nbsp;&nbsp;<span class="label label-sm label-success">新訂單</span>`
                    } else {
                        id = element.id + `&nbsp;&nbsp;&nbsp;<span class="label label-sm label-success">新訂單</span>`
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
                    <a href="javascript:;" onclick="showAjaxModalReview(` + element.id + `,` + memberId + `)" class="btn btn-info btn-lg btn-icon icon-left">
                        <i class="entypo-comment"></i> 評論商品
                    </a>
                        <a href="javascript:;" onclick="showAjaxModalRefund(` + element.id + `,` + memberId + `)" class="btn btn-danger btn-lg btn-icon icon-left">
                            <i class="entypo-back"></i> 退貨申請
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

function showAjaxModalReview(purchaseId, memberId) {
    $('#modal-8 .modal-body tbody').text(``);
    $('#modal-8 .modal-footer span').text(``);
    $('#modal-8').modal('show', { backdrop: 'static' });
    $.ajax({
        url: `/shop/findPurchaseListById/?idType=purchase&id=` + purchaseId,
        success: function(response) {
            response.forEach(element => {
                let productId = element.productId.id;
                let purchaseListId = element.id;
                let productName = element.productId.name;
                let productPrice = element.price;
                let productSN = element.serialNumber;
                let reviewStatus;

                //find review
                $.ajax({
                    type: "GET",
                    url: "/shop/findReviewById/?idType=purchaseList&id=" + purchaseListId,
                    success: function(response) {
                        reviewStatus = response[0].rating;
                        $('#modal-8 .modal-body tbody').append(
                            `<tr>
                <td id="` + purchaseListId + `"><a href="/shop/product.html?` + productId + `" target="_blank">` + productName.substr(0, 8) + `...</a>` + `</td>
                <td style="font-size:15px">` + productSN + `</td>
                <td><a name="` + response[0].comment + `" class="` + productSN + `" onclick="editReview(this,` + reviewStatus + `,` + response[0].id + `)" title="分數: ` + reviewStatus + ` 評論: ` + response[0].comment + `">` + generateRatingStarUtil(reviewStatus) + `</a></td>
                <td>
                    <a href="javascript:;" onclick="reviewEdit()" class="btn btn-info btn-lg btn-icon icon-left disabled">
                        <i class="entypo-comment"></i> 評分
                    </a>
                </td>
                </tr > `
                        );
                        $(`#show-productList2`).text(`訂單編號 ` + purchaseId);
                    },
                    error: function() {
                        reviewStatus = `-`;
                        $('#modal-8 .modal-body tbody').append(
                            `<tr>
                <td id="` + purchaseListId + `"><a href="/shop/product.html?` + productId + `" target="_blank">` + productName.substr(0, 8) + `...</a>` + `</td>
                <td style="font-size:15px">` + productSN + `</td>
                <td>` + reviewStatus + `</td>
                <td>
                    <a href="javascript:;" id="` + productSN + `" onclick="reviewEdit(this,` + purchaseListId + `,` + productId + `)" class="btn btn-info btn-lg btn-icon icon-left">
                        <i class="entypo-comment"></i> 評論
                    </a>
                </td>
                </tr > `
                        );
                        $(`#show-productList2`).text(`訂單編號 ` + purchaseId);
                    }
                });

            });
        }
    });

}

function showAjaxModalRefund(purchaseId, memberId) {
    $('#modal-7 .modal-body tbody').text(``);
    $('#modal-7 .modal-footer span').text(``);
    $('#modal-7').modal('show', { backdrop: 'static' });
    $.ajax({
        url: `/shop/findPurchaseListById/?idType=purchase&id=` + purchaseId,
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
                                <a href="javascript:;" onclick="refundEdit()" class="btn btn-danger btn-lg btn-icon icon-left disabled">
                                    <i class="entypo-back"></i> 退貨
                                </a>
                            </td>
                            </tr > `
                        );
                        $(`#show-productList`).text(`訂單編號 ` + purchaseId);
                    },
                    error: function() {
                        refundStatus = `-`;
                        $('#modal-7 .modal-body tbody').append(
                            `<tr>
                            <td id="` + purchaseListId + `"><a href="/shop/product.html?` + productId + `" target="_blank">` + productName.substr(0, 8) + `...</a>` + `</td>
                            <td style="font-size:15px">` + productSN + `</td>
                            <td>` + refundStatus + `</td>
                            <td>
                                <a href="javascript:;" id="` + productSN + `" onclick="refundEdit(this,` + purchaseListId + `)" class="btn btn-danger btn-lg btn-icon icon-left">
                                    <i class="entypo-back"></i> 退貨
                                </a>
                            </td>
                            </tr > `
                        );
                        $(`#show-productList`).text(`訂單編號 ` + purchaseId);
                    }
                });
            });
        }
    });
}

//newRefund
function newRefund(purchaseListIds) {
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


//create review edit
function reviewEdit(reviewEditBtn, purchaseListId, productId) {
    $('#modal-8 .modal-body tbody .edit-review').empty();
    $('#modal-8 .modal-footer span').text(``);
    $('#modal-8 .modal-body tbody').append(`
    <tr class="edit-review"><td colspan="4"><strong>序號: ` + $(reviewEditBtn).attr(`id`) + `</strong></td></tr>
    <tr class="edit-review"><td colspan="4"><input id="new-rating" type="number" class="form-control-sm" min="0" max="5" step="0.5" placeholder="評分"></input></td></tr>
    <tr class="edit-review"><td colspan="4"><textarea cols="55" rows="5" placeholder="評論內容..."></textarea ></td></tr>`);
    $('#modal-8 .modal-footer').prepend(`<span><button type="button" onclick="newReview(` + purchaseListId + `,` + productId + `)" class="btn btn-primary btn-lg">送出評論</button></span>`);
}

//newReview
function newReview(purchaseListIds, productId) {
    $.ajax({
        type: "GET",
        url: "/product/" + productId,
        contentType: `application/json`,
        success: function(response) {
            let json = new Object();
            let purchaseListId = new Object();
            purchaseListId.id = purchaseListIds;
            json.memberId = member;
            json.purchaseListId = purchaseListId;
            json.productId = response;
            json.rating = $(`#new-rating`).val();
            json.comment = $(`.edit-review textarea`).val();
            let data = JSON.stringify([json]);
            console.log(data)
            $.ajax({
                type: "POST",
                url: "/shop/newReviews",
                data: data,
                contentType: `application/json`,
                success: function(response) {
                    location.href = location.href;
                }
            });
        }
    });
}

//updateReview
function editReview(obj, reviewStatus, reviewId) {
    $('#modal-8 .modal-body tbody .edit-review').empty();
    $('#modal-8 .modal-footer span').empty();
    $('#modal-8 .modal-body tbody').append(
        `<tr class="edit-review">
    <th colspan="4">序號: &nbsp;&nbsp;&nbsp;<strong>` + $(obj).attr(`class`) + `</strong></th>
    </tr>
    <tr class="edit-review">
        <th colspan="4">評分</th>
    </tr>
    <tr class="edit-review">
    <td colspan="4"><input id="edit-rating" type="number" class="form-control-sm" min="0" max="5" step="0.5" value="` + reviewStatus + `"></input></td>
    </tr>
    <tr class="edit-review">
    <th colspan="4">評論</th>
    </tr>
    <tr class="edit-review">
    <td colspan="4"><textarea type="text" style="width:400px;height:100px;" placeholder="` + $(obj).attr(`name`) + `" value="` + $(obj).attr(`name`) + `"></textarea></td>
    </tr>`
    );
    $('#modal-8 .modal-footer').prepend(`<span><button type="button" class="btn btn-primary btn-lg" id="model-update">修改</button></span>`);
    updateReview(reviewId);
}

function updateReview(reviewId) {
    $(`#model-update`).click(function() {
        let json = new Object();
        json.id = reviewId;
        json.rating = $(`#edit-rating`).val();
        if ($(`textarea`).val() != "") {
            json.comment = $(`textarea`).val();
        } else {
            json.comment = $(`textarea`).attr(`value`);
        }
        let data = JSON.stringify(json);
        $.ajax({
            type: "PUT",
            url: "/shop/updateReview",
            data: data,
            contentType: `application/json`,
            success: function(response) {
                location.href = location.href;
            }
        });
    });
}