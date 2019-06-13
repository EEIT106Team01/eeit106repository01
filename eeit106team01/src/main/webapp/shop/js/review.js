//TAGS
let productId = parseInt(location.href.substr((location.href.indexOf(`?`) + 1), location.href.length));

// Document Ready
$(function() {
    let memberId = 0;
    if (member != null) {
        memberId = member.id;
    }
    findReview(productId, memberId);
});

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

//findReview
function findReview(ProductId, memberId) {
    $.ajax({
        type: "GET",
        url: "/shop/findReviewById/?idType=product&id=" + ProductId,
        success: function(response) {
            response.forEach(element => {
                let reviewId = element.id;
                let comment = element.comment;
                if (element.memberId.id == memberId) {
                    $(`#table-2 tbody`).append(
                        `<tr>
                        <td>` + element.memberId.id + ` </td>
                        <td><a href="javascript:;" name="` + element.comment + `" onclick="showAjaxModal(this` + `,` + +reviewId + `,` + element.memberId.id + `,` + element.rating + `)">` + generateRatingStar(element.rating) + `&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;` + element.rating + `分</a></td>
                        <td><a href="javascript:;" name="` + element.comment + `" onclick="showAjaxModal(this` + `,` + +reviewId + `,` + element.memberId.id + `,` + element.rating + `)">` + element.comment + `</a></td>
                        <td>` + getLocaleTime(element.updatedTime) + `</td>
                        </tr>`
                    );
                } else {
                    $(`#table-2 tbody`).append(
                        `<tr>
                        <td>` + element.memberId.id + ` </td>
                        <td>` + generateRatingStar(element.rating) + `&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;` + element.rating + `分</td>
                        <td>` + element.comment + `</td>
                        <td>` + getLocaleTime(element.updatedTime) + `</td>
                        </tr>`
                    );
                }
            });
        }
    });
}

function showAjaxModal(obj, reviewId, memberId, rating) {
    let comment = obj.name;
    $('#modal-7 .modal-body tbody').empty();
    $('#modal-7 .modal-footer span').empty();
    $('#modal-7').modal('show', { backdrop: 'static' });
    $(`#show-memberId`).text(member.name + ` 正在修改評論...`);
    $('#modal-7 .modal-body tbody').append(
        `<tr>
        <th>用戶</th>
        </tr>
        <tr>
        <td>` + member.name + `</td>
        </tr>
        <tr>
        <th>評分</th>
        </tr>
        <tr>
        <td><input id="edit-rating" type="number" class="form-control-sm" min="0" max="5" step="0.5" value="` + rating + `"></input></td>
        </tr>
        <tr>
        <th>評論</th>
        </tr>
        <tr>
        <td><textarea type="text" style="width:400px;height:100px;" placeholder="` + comment + `" value="` + comment + `"></textarea></td>
        </tr> `
    );
    $('#modal-7 .modal-footer').prepend(`<span><button type="button" class="btn btn-primary btn-lg" id="model-update">修改</button></span>`);
    updateReview(reviewId);
}

//updateReview
function updateReview(reviewId) {
    $(`#model-update`).click(function() {
        let json = new Object();
        json.id = reviewId;
        json.rating = $(`#edit-rating`).val();
        json.comment = $(`textarea`).val();
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