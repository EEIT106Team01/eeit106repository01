function a_buyVIP() {
    $.ajax({
        url: `/search/TypeName?productType=會員&productName=VIP會員`,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(Data) {
            let id = Data.id;
            let name = Data.name;
            let price = Data.price;
            IntoCart(id, price, name)
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    });
}

function IntoCart(id, price, name) {
    let productId = parseInt(id);
    let productName = name;
    let productPrice = parseInt(price);
    let productQuantity = parseInt(1);

    let productInfoJson = new Object();
    productInfoJson.id = productId;
    productInfoJson.name = productName;
    productInfoJson.price = productPrice;
    productInfoJson.quantity = productQuantity;
    let productData = JSON.stringify(productInfoJson);

    if (null != cartLocalStorage.getItem(productId)) {
        cartLocalStorage.removeItem(cartLocalStorage.getItem(productId));
        cartLocalStorage.setItem(productId, productData);
    } else {
        cartLocalStorage.setItem(productId, productData);
    }

    var result = cartLocalStorage.getItem(productId);
    location.href = location.href;
}