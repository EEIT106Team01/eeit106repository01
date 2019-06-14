function a_buyVIP() {
    $.ajax({
        url: `/search/TypeName?productType=會員&productName=VIP會員`,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(Data) {
            let id = Data[0].id;
            let name = Data[0].name;
            let price = Data[0].price;
            let image = Data[0].imageLink[0].replace(/\"/, ``).replace(/\"/, ``);
            IntoCart(id, price, name, image);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    });
}

function IntoCart(productId, price, name, image) {
    let id = parseInt(productId);
    let productName = name;
    let productPrice = parseInt(price);
    let productImage = image;
    let productQuantity = parseInt(1);
    let productTotalQuantity = parseInt(1);

    let productInfoJson = new Object();
    productInfoJson.id = id;
    productInfoJson.name = productName;
    productInfoJson.price = productPrice;
    productInfoJson.quantity = productQuantity;
    productInfoJson.image = productImage;
    productInfoJson.totalQuantity = productTotalQuantity;
    let productData = JSON.stringify(productInfoJson);

    let cartLocalStorage = localStorage;

    if (null != cartLocalStorage.getItem(productId)) {
        cartLocalStorage.removeItem(cartLocalStorage.getItem(productId));
        cartLocalStorage.setItem(productId, productData);
    } else {
        cartLocalStorage.setItem(productId, productData);
    }

    var result = cartLocalStorage.getItem(productId);
    location.href = location.href;

}

$(function() {
    addToCart();
});