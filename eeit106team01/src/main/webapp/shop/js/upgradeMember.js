function a_buyVIP() {
    $.ajax({
        url: "/product/1630",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(Data) {
            let id = Data.id;
            let name = Data.name;
            let price = Data.price;
            IntoCart(id,price,name)
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    });
}

function IntoCart(id,price,name){

}