$(document).ready(function () {
    getProducts()
    getAllType()

})

function getProducts() {

    $.ajax({
        url: "http://localhost:8080/products",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (productsData) {
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []

            var y = 0;
            $.each(productsData, function () {
                productsName.push(productsData[y].name)
                productsPrice.push(productsData[y].price)
                productsImg.push(productsData[y].imageLink[0])
                productTotalSold.push(productsData[y].totalSold)
                y++;
            })

            var pageSize = productsData.length;
            $("#productsQuantity").empty().append("<p>全站共有"+pageSize+"件商品</p>")

            var products =[]
            var top = 10;
            for (var i = 0; i < top; i++) {
                products.push(
                    '<div class="col-md-2  productDiv"><a href=""><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                    '<span class="name">' + productsName[i] + '</span></a><span class="price">$' + productsPrice[i] + '</span></span></div>'
                )
            }
            var result = products.join("")
            $("#productsTop10Div").empty().append(result)
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

function getAllType(){
    $.ajax({
        url: "http://localhost:8080/search/data?dataName=type",
        method: "GET",
        dataType: "json",
        cache:false,
        success: function (typesData) {   
            var i=0;    
            var productTypeArray =[];
            $.each(typesData,function(){
                    productTypeArray.push('<li><a href="" onclick="ByType(this);return false">'+typesData[i].data+'</a></li>')
                    i++
                })   
            $("#classification").empty().append(productTypeArray.join(""))
        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}