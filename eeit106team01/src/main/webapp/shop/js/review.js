// Search
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
                if (key.match(/^(image)$/) && val != null) {
                    img.push("<img src='data:image/jpeg;base64," + val + "' class='img-thumbnail'>");
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

// 當上傳檔案改變時清除目前預覽圖，並且呼叫previewFiles()
$("#upload").change(function () {
    $("#previewDiv").empty() // 清空當下預覽
    previewFiles(this.files) // this即為<input>元素
})
// 使用FileReader讀取檔案，並且回傳Base64編碼後的source
function convertFile(file) {
    return new Promise((resolve, reject) => {
        // 建立FileReader物件
        let reader = new FileReader()
        // 註冊onload事件，取得result則resolve (會是一個Base64字串)
        reader.onload = () => { resolve(reader.result) }
        // 註冊onerror事件，若發生error則reject
        reader.onerror = () => { reject(reader.error) }
        // 讀取檔案
        reader.readAsDataURL(file)
    })
}
// 在頁面上新增<img>
function showPreviewImage(src, fileName) {
    let image = new Image(300) // 設定寬px
    image.id = "preImage";
    image.name = fileName
    image.src = src // <img>中src屬性除了接url外也可以直接接Base64字串
    $("#previewDiv").append(image).append(`<p>File: ${image.name}`)
}
// 預覽圖片，將取得的files一個個取出丟到convertFile()
function previewFiles(files) {
    if (files && files.length >= 1) {
        $.map(files, file => {
            convertFile(file)
                .then(data => {
                    console.log(data) // 把編碼後的字串輸出到console
                    showPreviewImage(data, file.name)
                })
                .catch(err => console.log(err))
        })
    }
}

//Insert 
$("#insert").click(function () {
    $("#image").empty();
    $("#content thead tr").empty();
    $("#content tbody tr").empty();
    const url = 'http://localhost:8080/shop/newReviews';

    //Parse type
    var rating = parseFloat($('#rating').val());
    var memberId = parseInt($('#memberId').val());
    var purchaseListId = parseInt($('#purchaseListId').val());
    var productId = parseInt($('#productId').val());
    //Image
    var image = $('#preImage').attr('src');
    var imgByte = image.substr(image.indexOf(',') + 1, image.length);

    var json = new Object();
    json.rating = rating;
    json.comment = $('#comment').val();
    json.imageBase64 = imgByte;
    json.memberId = { 'id': memberId };
    json.purchaseListId = { 'id': purchaseListId };
    json.productId = { 'id': productId };
    var data = JSON.stringify([json]);
    console.log(data);

    $.ajax({
        type: 'POST',
        url: url,
        data: data,
        contentType: 'application/json; charset=utf-8',
        success: function (result) {
            var textHead = [];
            var text = [];
            var img = [];
            $.each(result[0], function (key, val) {
                if (key.match(/^(image)$/) && val != null) {
                    var reader = new FileReader();
                    reader.readAsDataURL(val); 
                    reader.onloadend = function() {
                        base64data = reader.result;                
                        console.log(base64data);
                    }
                    img.push("<img src='data:image/jpeg;base64," + val + "' class='img-thumbnail'>");
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
