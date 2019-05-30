$("#search").click(function () {
    $("#image").empty();
    $("#content thead tr").empty();
    $("#content tbody tr").empty();
    const url = 'http://localhost:8080/shop/findReviewById';
    var data = { idType: $('#idType').val(), id: $('#id').val() };
    $.ajax({
        type: 'GET',
        url: url,
        data: data,
        success: function (result) {
            var textHead = [];
            var text = [];
            var img = [];
            $.each(result[0], function (key, val) {
                if (key.match(/^(image)$/) && !val.match(/^(AA==)$/)) {
                    img.push("<img src='data:image/png;base64," + val + "' class='img-thumbnail'>");
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
            $(text.join("")).appendTo("#content tbody tr");

        }
    }).done(function () {
        console.log("success");
    }).fail(function (result) {
        console.log(result);
        var errorsHead = [];
        var errors = [];
        $.each(result, function (key, val) {
            if (key.match(/^(statusText|responseText)$/)) {
                errorsHead.push("<th class='" + key + "'>" + key + "</th>");
                errors.push("<td>" + val + "</td>");
            } else if (key.match(/^(status)$/)) {
                errorsHead.push("<th scope='col' class='" + key + "'>" + key + "</th>");
                errors.push("<td scope='row'>" + val + "</td>");
            }
        });
        $(errorsHead.join("")).appendTo("#content thead tr");
        $(errors.join("")).appendTo("#content tbody tr");
    }).always(function () {
        console.log("complete");
    });
});


