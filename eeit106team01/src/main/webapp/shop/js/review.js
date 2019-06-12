//TAGS
let currentFilter = $(`#currentFilter`).text();
let commentFilterCurrentStatus = $(`.comment-filters a[class='current']`).text();
let reviewCount = $(`#reviewCount`).text();
let productId = parseInt(location.href.substr((location.href.indexOf(`?`) + 1), location.href.length));

// Document Ready
$(function() {
    generateCurrentFilterText();
    findReviewsByProductId(`product`, productId);
});

//Generate Current Filter Text
function generateCurrentFilterText() {
    $(`#currentFilter`).prepend(commentFilterCurrentStatus);
}

//Generate Reviews
function findReviewsByProductId(idType, id) {
    const resource = `shop/findReviewById`;
    let url = urlDomain + resource + `/?idType=product&` + `id=` + id;
    let productPageUrl = urlDomain + getOneProduct;
    $.ajax({
        type: "GET",
        url: url,
        success: function(response) {
            let reviewsJson = new Object();
            reviewsJson = response;
            generateReviewCounts(reviewsJson.length);
            console.log(reviewsJson);
            reviewsJson.forEach(review => {
                let reviewId = parseInt(review.id);
                let comment = String(review.comment);
                let updateTime = getLocaleTime(review.updatedTime);
                let rating = parseFloat(review.rating);
                let image = review.image;
                let productId = review.productId.id;
                let productName = review.productId.name;
                let productBrand = review.productId.brand;
                let productType = review.productId.type;
                let purchaseListId = review.purchaseListId.purchaseId.id;
                let purchaseListSerialNumber = review.purchaseListId.serialNumber;
                let memberId = review.memberId.id;
                // Get Member information from session
                let currentMemberId = parseInt(1);
                generateReview(productPageUrl, reviewId, comment, updateTime, rating, image, productId, productName, purchaseListId, purchaseListSerialNumber, memberId, currentMemberId);
                findReviewsByMemberId(`member`, currentMemberId);
            });
        }
    });
}

// Generate Review Count
function generateReviewCounts(count) {
    $(`#reviewCount`).text(count);
};

// Generate Rating Star
function generateRatingStar(rating) {
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

// New Review
function generateReview(productPageUrl, reviewId, comment, updateTime, rating, image, productId, productName, purchaseListId, purchaseListSerialNumber, memberId, currentMemberId) {
    if (memberId == currentMemberId) {
        $(`<li> 
            <div class="comment-checkbox">
            <div class="checkbox checkbox-replace"> 
                <input type="checkbox"> 
            </div> 
            </div> 
            <div class="comment-details"> 
            <span style="display:none;"><span id="purchaseListId">` + purchaseListId + `</span><span id="purchaseListSerialNumber">` + purchaseListSerialNumber + `</span><span id="memberId">` + memberId + `</span></span>
            <div class="comment-head"> 
                <a href="memberProfile">MemberId: ` + memberId + `</a> 給予
                <a href="` + productPageUrl + productId + `">` + productName + `</a>` + ` ` + generateRatingStar(rating) + ` ` + `<span id='review-edit-badge'>` + rating + `分` + `</span></div> 
                <p class="comment-text">` + comment + ` </p> 
                <div class="comment-footer"> 
                <div class="comment-time">` + updateTime + `</div> 
                <div class="action-links"> <a href="javascript:;" onclick="jQuery('#modal-edit-comment-` + reviewId + `').modal('show');" class="edit"><i class="entypo-pencil"></i>編輯</a> 
                </div> 
            </div> 
            </div> 
        </li>`).appendTo($(`.comments-list`));
    } else {
        $(`<li> 
        <div class="comment-checkbox">
        <div class="checkbox checkbox-replace"> 
            <input type="checkbox"> 
        </div> 
        </div> 
        <div class="comment-details"> 
        <span style="display:none;"><span id="purchaseListId">` + purchaseListId + `</span><span id="purchaseListSerialNumber">` + purchaseListSerialNumber + `</span><span id="memberId">` + memberId + `</span></span>
        <div class="comment-head"> 
            <a href="memberProfile">MemberId: ` + memberId + `</a> 給予
            <a href="` + productPageUrl + productId + `">` + productName + `</a> </div> 
            <p class="comment-text">` + comment + ` </p> 
            <div class="comment-footer"> 
            <div class="comment-time">` + updateTime + `</div> 
        </div> 
        </div> 
    </li>`).appendTo($(`.comments-list`));
    }


};

// Update Review
function findReviewsByMemberId(idType, id) {
    const resource = `shop/findReviewById`;
    let url = urlDomain + resource + `/?idType=member&` + `id=` + id;
    let productPageUrl = urlDomain + getOneProduct;
    $.ajax({
        type: "GET",
        url: url,
        success: function(response) {
            let reviewsJson = new Object();
            reviewsJson = response;
            // console.log(reviewsJson);
            reviewsJson.forEach(review => {
                let reviewId = parseInt(review.id);
                let comment = String(review.comment);
                let updateTime = getLocaleTime(review.updatedTime);
                let rating = parseFloat(review.rating);
                let image = review.image;
                let productId = review.productId.id;
                let productName = review.productId.name;
                let productBrand = review.productId.brand;
                let productType = review.productId.type;
                let purchaseListId = review.purchaseListId.purchaseId.id;
                let purchaseListSerialNumber = review.purchaseListId.serialNumber;
                let memberId = review.memberId.id;
                generateReviewEdit(productPageUrl, reviewId, comment, updateTime, rating, image, productId, productName, purchaseListId, purchaseListSerialNumber, memberId);
            });
        }
    });
}

// Generate Review Edit
function generateReviewEdit(productPageUrl, reviewId, comment, updateTime, rating, image, productId, productName, purchaseListId, purchaseListSerialNumber, memberId) {
    console.log(purchaseListId);
    $(`<div class="modal fade" id="modal-edit-comment-` + reviewId + `">
        <div class="modal-dialog" style="width: 60%; top:50px;">
            <div class="modal-content">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">編輯評論</h4>
                </div>

                <div class="modal-body">

                    <div class="row">

                        <div class="col-sm-2">
                            <a href="memberIdPicture">
                                <img src="../assets/images/member-1.jpg" class="img-rounded img-responsive" />
                            </a>

                            <br />
                            <span>評論編號: <span id="review-id-edit">` + reviewId + `</span></span>
                            <br />
                            <span>訂單編號: <span id="purchase-id-edit">` + purchaseListId + `</span></span>
                        </div>

                        <div class="col-sm-10">

                            <div class="row">
                                <div class="col-md-12">

                                    <div class="form-group">
                                        <label for="field-1" class="control-label">會員</label>
                                        <input type="text" class="form-control" id="member-id-edit" value="` + memberId + `" disabled="disabled">
                                    </div>

                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-12">

                                    <div class="form-group">
                                        <label for="field-1" class="control-label">評論內容</label>

                                        <textarea class="form-control" id="comment-edit" style="min-height: 120px;">` + comment + `</textarea>
                                    </div>

                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4">

                                    <div class="form-group">
                                        <label for="field-1" class="control-label">上次修改時間</label>

                                        <p>
                                            <strong id="update-time-edit">` + updateTime + `</strong>
                                        </p>
                                    </div>

                                </div>

                                <div class="col-md-4">

                                    <div class="form-group">
                                        <label for="field-1" class="control-label">商品名稱</label>

                                        <p>
                                            <a href="` + productPageUrl + productId + `">
                                                <strong id="product-id-edit">` + productName + `</strong>
                                            </a>
                                        </p>
                                    </div>

                                </div>

                                <div class="col-md-4">

                                    <div class="form-group">
                                        <label for="field-1" class="control-label">商品評分</label>
                                        <div class="input-spinner">
                                            <button type="button" class="btn btn-default" data-step="-0.5">-</button>
                                            <input id="rating-edit" class="form-control size-1" value="` + rating + `" data-min="0" data-max="5">
                                            <button type="button" class="btn btn-default" data-step="0.5">+</button>
                                        </div>
                                    </div>

                                </div>
                            </div>

                        </div>

                    </div>

                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">關閉</button>
                    <button type="button" class="btn btn-info" id="update-edit">修改</button>
                </div>
            </div>
        </div>
        </div>
    `).appendTo($(`#modal-edit`));

}