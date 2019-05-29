const url = 'http://localhost:8080/shop/findReviewById';
var data = { idType: 'review', id: 1 };
$.ajax({
    type: 'GET',
    url: url,
    data: data,
    success: function (result) {
        console.log(result[0]);
        console.log(result[0].image.binaryStream);
        $('body').append(result[0].image.binaryStream);
        var img = document.createElement('img');
        img.src = 'data:image/jpeg;base64,' + btoa('result[0].image.binaryStream');
        document.body.appendChild(img);
    }
});

function hexToBase64(str) {
    return btoa(String.fromCharCode.apply(null, str.replace(/\r|\n/g, "").replace(/([\da-fA-F]{2}) ?/g, "0x$1 ").replace(/ +$/, "").split(" ")));
}