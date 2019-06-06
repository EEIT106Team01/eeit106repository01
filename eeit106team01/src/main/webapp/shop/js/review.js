// Image Preview
// 當上傳檔案改變時清除目前預覽圖，並且呼叫previewFiles()
//Insert
$("#upload").change(function() {
    $("#previewDiv").empty(); // 清空當下預覽
    previewFiles(this.files, "insert"); // this即為<input>元素
});
//Update
function updatePreview() {
    $("#updateImage").change(function() {
        $("#image").empty(); // 清空當下預覽
        previewFiles(this.files, "update"); // this即為<input>元素
    });
}
// 使用FileReader讀取檔案，並且回傳Base64編碼後的source
function convertFile(file) {
    return new Promise((resolve, reject) => {
        // 建立FileReader物件
        let reader = new FileReader();
        // 註冊onload事件，取得result則resolve (會是一個Base64字串)
        reader.onload = () => {
            resolve(reader.result);
        };
        // 註冊onerror事件，若發生error則reject
        reader.onerror = () => {
            reject(reader.error);
        };
        // 讀取檔案
        reader.readAsDataURL(file);
    });
}
// 在頁面上新增<img>
//Insert preview
function showPreviewImage(src, fileName) {
    let image = new Image(300); // 設定寬px
    image.id = "preImage";
    image.className = "img-thumbnail";
    image.name = fileName;
    image.src = src; // <img>中src屬性除了接url外也可以直接接Base64字串
    $("#previewDiv").append(image);
}
//Update preview
function showUpdatePreviewImage(src, fileName) {
    let image = new Image(300); // 設定寬px
    image.id = "preUpdateImage";
    image.className = "img-thumbnail";
    image.name = fileName;
    image.src = src; // <img>中src屬性除了接url外也可以直接接Base64字串
    $("#image").append(image);
}
// 預覽圖片，將取得的files一個個取出丟到convertFile()
function previewFiles(files, location) {
    if (files && files.length >= 1) {
        $.map(files, file => {
            convertFile(file)
                .then(data => {
                    console.log(data); // 把編碼後的字串輸出到console
                    if (location == "insert") {
                        showPreviewImage(data, file.name);
                    } else if (location == "update") {
                        showUpdatePreviewImage(data, file.name);
                    }
                })
                .catch(err => console.log(err));
        });
    }
}

// Search
$("#search").click(function() {
    $("#image").empty();
    $("#updateImageButton").empty();
    $("#content thead tr").empty();
    $("#content tbody tr").empty();
    const url = "http://localhost:8080/shop/findReviewById";
    var data = { idType: $("#idType").val(), id: $("#id").val() };
    $.ajax({
            type: "GET",
            url: url,
            data: data,
            success: function(result) {
                var textHead = [];
                var text = [];
                var img = [];
                $.each(result[0], function(key, val) {
                    if (key.match(/^(image)$/) && val != null) {
                        img.push(
                            "<img id='currentImage' src='data:image/jpeg;base64," +
                            val +
                            "'class='img-thumbnail'>"
                        );
                    } else if (key.match(/^(createTime|updatedTime)$/)) {
                        textHead.push("<th class='" + key + "'>" + key + "</th>");
                        text.push("<td>" + new Date(val) + "</td>");
                    } else if (key.match(/^(rating)$/)) {
                        textHead.push("<th class='" + key + "'>" + key + "</th>");
                        text.push(
                            "<td><input type='number' id='updateRating' class='form-control' value='" +
                            val +
                            "'></input></td>"
                        );
                    } else if (key.match(/^(comment)$/)) {
                        textHead.push("<th class='" + key + "'>" + key + "</th>");
                        text.push(
                            "<td><textarea id='updateComment' class='form-control' rows='2' value='" +
                            val +
                            "'>" +
                            val +
                            "</textarea></td>"
                        );
                    } else if (key.match(/^(id)$/)) {
                        textHead.push("<th scope='col' class='" + key + "'>" + key + "</th>");
                        text.push("<td scope='row' id='reviewId'>" + val + "</td>");
                    } else if (key.match(/^(memberId|purchaseListId|productId)$/)) {
                        textHead.push("<th class='" + key + "'>" + key + "</th>");
                        text.push("<td>" + val.id + "</td>");
                    }
                });
                $(img.join("")).appendTo("#image");
                var updateImageInput =
                    "<input type='file' name='' class='form-control-file mt-2 mb-2' id='updateImage' placeholder='' data-target='image' accept='.png,.jpg,.jpeg'>";
                $(updateImageInput).appendTo("#updateImageButton");
                $(textHead.join("")).appendTo("#content thead tr");
                $(text.join("")).appendTo($("#content tbody tr").eq(0));
                var updateButton =
                    "<td colspan='8'><button type='button' name='' id='update' class='btn btn-secondary btn-lg btn-block'>Update</button></td>";
                $(updateButton).appendTo($("#content tbody tr").eq(1));
                updatePreview();
                updateReview();
            }
        })
        .done(function() {
            console.log("success");
        })
        .fail(function(result) {
            console.log(result);
            var errorsHead = [];
            var errors = [];
            $.each(result, function(key, val) {
                if (key.match(/^(statusText|responseText)$/)) {
                    errorsHead.push("<th class='" + key + "'>" + key + "</th>");
                    errors.push("<td>" + val + "</td>");
                } else if (key.match(/^(status)$/)) {
                    errorsHead.push(
                        "<th scope='col' class='" + key + "'>" + key + "</th>"
                    );
                    errors.push("<td scope='row'>" + val + "</td>");
                }
            });
            $(errorsHead.join("")).appendTo("#content thead tr");
            $(errors.join("")).appendTo($("#content tbody tr").eq(0));
        });
});

//Insert
$("#insert").click(function() {
    $("#image").empty();
    $("#content thead tr").empty();
    $("#content tbody tr").empty();
    const url = "http://localhost:8080/shop/newReviews";

    //Parse type
    var rating = parseFloat($("#rating").val());
    var memberId = parseInt($("#memberId").val());
    var purchaseListId = parseInt($("#purchaseListId").val());
    var productId = parseInt($("#productId").val());
    //Image
    var image = $("#preImage").attr("src");
    var imgByte = image.substr(image.indexOf(",") + 1, image.length);

    var json = new Object();
    json.rating = rating;
    json.comment = $("#comment").val();
    json.imageBase64 = imgByte;
    json.memberId = { id: memberId };
    json.purchaseListId = { id: purchaseListId };
    json.productId = { id: productId };
    var data = JSON.stringify([json]);
    // console.log(data);

    $.ajax({
            type: "POST",
            url: url,
            data: data,
            contentType: "application/json; charset=utf-8",
            success: function(result) {
                var textHead = [];
                var text = [];
                var img = [];
                $.each(result[0], function(key, val) {
                    if (key.match(/^(image)$/) && val != null) {
                        img.push(
                            "<img src='data:image/jpeg;base64," +
                            val +
                            "'class='img-thumbnail'>"
                        );
                    } else if (key.match(/^(createTime|updatedTime)$/)) {
                        textHead.push("<th class='" + key + "'>" + key + "</th>");
                        text.push("<td>" + new Date(val) + "</td>");
                    } else if (key.match(/^(rating|comment)$/)) {
                        textHead.push("<th class='" + key + "'>" + key + "</th>");
                        text.push("<td>" + val + "</td>");
                    } else if (key.match(/^(id)$/)) {
                        textHead.push("<th scope='col' class='" + key + "'>" + key + "</th>");
                        text.push("<td scope='row'>" + val + "</td>");
                    } else if (key.match(/^(memberId|purchaseListId|productId)$/)) {
                        textHead.push("<th class='" + key + "'>" + key + "</th>");
                        text.push("<td scope='row'>" + val.id + "</td>");
                    }
                });
                $(img.join("")).appendTo("#image");
                $(textHead.join("")).appendTo("#content thead tr");
                $(text.join("")).appendTo($("#content tbody tr").eq(0));
            }
        })
        .done(function() {
            console.log("success");
        })
        .fail(function(result) {
            console.log(result);
            var errorsHead = [];
            var errors = [];
            $.each(result, function(key, val) {
                if (key.match(/^(statusText|responseText)$/)) {
                    errorsHead.push("<th class='" + key + "'>" + key + "</th>");
                    errors.push("<td>" + val + "</td>");
                } else if (key.match(/^(status)$/)) {
                    errorsHead.push(
                        "<th scope='col' class='" + key + "'>" + key + "</th>"
                    );
                    errors.push("<td scope='row'>" + val + "</td>");
                }
            });
            $(errorsHead.join("")).appendTo("#content thead tr");
            $(errors.join("")).appendTo($("#content tbody tr").eq(0));
        });
});

//Update
function updateReview() {
    $("#update").click(function() {
        const url = "http://localhost:8080/shop/updateReview";

        //Parse type
        var rating = parseFloat($("#updateRating").val());
        var reviewId = parseInt($("#reviewId").text());
        //Image
        var newImageVal = $("#preUpdateImage").attr("src");
        var currentImageVal = $("#currentImage").attr("src");
        if (newImageVal != null) {
            var imgByte = newImageVal.substr(
                newImageVal.indexOf(",") + 1,
                newImageVal.length
            );
        } else if (newImageVal == null && currentImageVal != null) {
            var imgByte = currentImageVal.substr(
                currentImageVal.indexOf(",") + 1,
                currentImageVal.length
            );
        }

        var json = new Object();
        json.id = reviewId;
        json.rating = rating;
        json.comment = $("#updateComment").val();
        if (imgByte != null) {
            json.image = imgByte;
        } else {
            json.image = "null";
        }
        var data = JSON.stringify(json);
        console.log(data);

        $.ajax({
                type: "PUT",
                url: url,
                data: data,
                contentType: "application/json; charset=utf-8",
                success: function(result) {
                    var textHead = [];
                    var text = [];
                    var img = [];
                    $.each(result[0], function(key, val) {
                        if (key.match(/^(image)$/) && val != null) {
                            img.push(
                                "<img src='data:image/jpeg;base64," +
                                val +
                                "'class='img-thumbnail'>"
                            );
                        } else if (key.match(/^(createTime|updatedTime)$/)) {
                            textHead.push("<th class='" + key + "'>" + key + "</th>");
                            text.push("<td>" + new Date(val) + "</td>");
                        } else if (key.match(/^(rating|comment)$/)) {
                            textHead.push("<th class='" + key + "'>" + key + "</th>");
                            text.push("<td>" + val + "</td>");
                        } else if (key.match(/^(id)$/)) {
                            textHead.push(
                                "<th scope='col' class='" + key + "'>" + key + "</th>"
                            );
                            text.push("<td scope='row'>" + val + "</td>");
                        } else if (key.match(/^(memberId|purchaseListId|productId)$/)) {
                            textHead.push("<th class='" + key + "'>" + key + "</th>");
                            text.push("<td scope='row'>" + val.id + "</td>");
                        }
                    });
                    $(img.join("")).appendTo("#image");
                    $(textHead.join("")).appendTo("#content thead tr");
                    $(text.join("")).appendTo($("#content tbody tr").eq(0));
                }
            })
            .done(function() {
                console.log("success");
            })
            .fail(function(result) {
                console.log(result);
                var errorsHead = [];
                var errors = [];
                $.each(result, function(key, val) {
                    if (key.match(/^(statusText|responseText)$/)) {
                        errorsHead.push("<th class='" + key + "'>" + key + "</th>");
                        errors.push("<td>" + val + "</td>");
                    } else if (key.match(/^(status)$/)) {
                        errorsHead.push(
                            "<th scope='col' class='" + key + "'>" + key + "</th>"
                        );
                        errors.push("<td scope='row'>" + val + "</td>");
                    }
                });
                $(errorsHead.join("")).appendTo("#content thead tr");
                $(errors.join("")).appendTo($("#content tbody tr").eq(0));
            });
    });
}

//Review

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
                let purchaseListId = review.purchaseListId.id;
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
                <a href="` + productPageUrl + productId + `">` + productName + `</a> </div> 
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
/*
`<li>
    <div class="comment-checkbox">
		<div class="checkbox checkbox-replace">
			<input type="checkbox">
		</div>
	</div>
		<div class="comment-details">
		    <div class="comment-head">
				<a href="memberProfile">MemberId:` + memberId + `</a> 給予
                <a href="url` + `/?` + `idType="` + data.idType + `"&` + `id="` + data.id `">` + productId + `</a>
			</div>
				<p class="comment-text">` + comment + `
				</p>
			<div class="comment-footer">
				<div class="comment-time">` + updateTime + `</div>
            <div class="action-links">
                <a href="javascript:;" onclick="jQuery('#modal-edit-comment').modal('show');" class="edit"><i class="entypo-pencil"></i>編輯</a>
            </div>
         </div>
    </div>
</li>`
*/

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
                let purchaseListId = review.purchaseListId.id;
                let purchaseListSerialNumber = review.purchaseListId.serialNumber;
                let memberId = review.memberId.id;
                generateReviewEdit(productPageUrl, reviewId, comment, updateTime, rating, image, productId, productName, purchaseListId, purchaseListSerialNumber, memberId);
            });
        }
    });
}

// Generate Review Edit
function generateReviewEdit(productPageUrl, reviewId, comment, updateTime, rating, image, productId, productName, purchaseListId, purchaseListSerialNumber, memberId) {
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
/*
<div class="modal fade" id="modal-edit-comment-2">
<div class="modal-dialog" style="width: 60%; top:50px;">
    <div class="modal-content">

        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">編輯評論</h4>
        </div>

        <div class="modal-body">

            <div class="row">

                <div class="col-sm-2">
                    <a href="#">
                        <img src="../assets/images/member-1.jpg" class="img-rounded img-responsive" />
                    </a>

                    <br />
                    <span>評論編號: <span id="review-id-edit"></span></span>
                    <br />
                    <span>訂單編號: <span id="purchase-id-edit"></span></span>
                </div>

                <div class="col-sm-10">

                    <div class="row">
                        <div class="col-md-12">

                            <div class="form-group">
                                <label for="field-1" class="control-label">會員</label>
                                <input type="text" class="form-control" id="member-id-edit" value="" disabled="disabled">
                            </div>

                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12">

                            <div class="form-group">
                                <label for="field-1" class="control-label">評論內容</label>

                                <textarea class="form-control" id="comment-edit" style="min-height: 120px;"></textarea>
                            </div>

                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-4">

                            <div class="form-group">
                                <label for="field-1" class="control-label">上次修改時間</label>

                                <p>
                                    <strong id="update-time-edit"></strong>
                                </p>
                            </div>

                        </div>

                        <div class="col-md-4">

                            <div class="form-group">
                                <label for="field-1" class="control-label">商品名稱</label>

                                <p>
                                    <a href="#">
                                        <strong id="product-id-edit"></strong>
                                    </a>
                                </p>
                            </div>

                        </div>

                        <div class="col-md-4">

                            <div class="form-group">
                                <label for="field-1" class="control-label">商品評分</label>
                                <div class="input-spinner">
                                    <button type="button" class="btn btn-default" data-step="-0.5">-</button>
                                    <input id="rating-edit" class="form-control size-1" value="" data-min="0" data-max="5">
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
*/