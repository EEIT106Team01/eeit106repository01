$(document).ready(function () {
    getProducts()
    getAllType()
    getProductsByUpdateTime()
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
            $("#productsQuantity").empty().append("<span class='productsQuantityTitle'>全站共有<h2>"+pageSize+"件商品</h2></span><hr>")

            var products =[]
            var top = 10;
            for (var i = 0; i < top; i++) {
                products.push(
                    '<div class="col-md-2  productDiv"><a href=""><div><img src=' + productsImg[i] + ' class="productImg"><img src="img/hotSale.png" class="hotSale"></div>' +
                    '<span class="name">' + productsName[i].substr( 0, 25 )  + '...</span></a><span class="price">$' + productsPrice[i] + '</span></span></div>'
                )
            }
            var result = products.join("")
            $("#productsTop10").empty().append(result)
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
                    productTypeArray.push('<li class="li"><a href="http://localhost:8080/shop/search.html?type='+typesData[i].data+'">'+typesData[i].data+'</a></li>')
                    i++
                })   
            $("#classification").empty().append(productTypeArray.join(""))

            var y =0;
            productTypeArray2=[];
            $.each(typesData,function(){
                productTypeArray2.push("<option>"+typesData[y].data+"</option>")
                y++
            })   
            $("#searchType").empty().append("<option>全站搜尋</option>"+productTypeArray2.join(""))
        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

function getProductsByUpdateTime(){

    var endDay = GetDateStr(0)
    var startDay = GetDateStr(-30)

    console.log("endDay====="+endDay)
    console.log("startDay====="+startDay)
    $.ajax({
        url: "http://localhost:8080/search/updatedTime?startDay="+startDay+"&endDay="+endDay,
        method: "GET",
        dataType: "json",
        cache:false,
        success: function (dayData) {   
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productsId = [];
            var y = 0;
            $.each(dayData, function () {
                productsName.push(dayData[y].name)
                productsPrice.push(dayData[y].price)
                productsImg.push(dayData[y].imageLink[0])
                productsId.push(dayData[y].id)
                y++;
            })

            var products =[]
            var top = 10;
            for (var i = 0; i < top; i++) {
                products.push(
                    '<div class="col-md-2  productDiv"><a href="http://localhost:8080/shop/product.html/?'+productsId[i]+'"><div><img src=' + productsImg[i] + ' class="productImg"><img src="img/newSale.png" class="newSale"></div>' +
                    '<span class="name">' + productsName[i].substr( 0, 25 ) + '...</span></a><span class="price">$' + productsPrice[i] + '</span></span></div>'
                )
            }
            var result = products.join("")
            $("#newUpdateProduct").empty().append(result)

        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//計算時間
function GetDateStr(AddDayCount) {   
    var dd = new Date();  
    dd.setDate(dd.getDate()+AddDayCount);
    var y = dd.getFullYear();   
    var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);
    var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();
    return y+"-"+m+"-"+d;   
 }  

 function search(productType,productName){
    $.ajax({
        url: "http://localhost:8080/search/TypeName?productType="+productType+"&productName="+productName,
        method: "GET",
        dataType: "json",
        cache:false,
        success: function (searchData) {   
           
        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
 }