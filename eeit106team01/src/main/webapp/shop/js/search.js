$(document).ready(function () {
    $("#searchPrice").click(function () {
        getProductsByPrice();
    }),
    $("#ByPriceAsc").click(function () {
        $('#Products').isotope({
            sortBy: 'price'
         });
    }),
    // $("#ByPriceDesc").click(function () {
    //     $Products.isotope({ sortBy: 'number' })
    // }),
    // $("#ByTime").click(function () {
    //     $Products.isotope({ sortBy: 'number' })
    // }),
    // $("#totleSelled").click(function () {
    //     $Products.isotope({ sortBy: 'number' })
    // }),
    getAllType(),
    getAllBrand(),
    $("#starDay").datepicker(),
    $("#endDay").datepicker(),

    getProductsByType();

    $("#Products").isotope({filter: '.productsDiv'})
    $("#Products").isotope({ 
        masonry: {
            columnWidth: 200
        }
    })
})

function getProductsByType(){

    $.ajax({
        url: "http://localhost:8080/search/type?type=行車紀錄器",
        method: "GET",
        dataType: "json",
        success: function (typeData) {

            getProductQuantity(typeData)
             
            var productsName =[];
            var productsPrice =[];
            var productsImg =[];

            var y=0;
            $.each(typeData,function(){
                productsName.push(typeData[y].name)
                productsPrice.push(typeData[y].price)
                productsImg.push(typeData[y].imageLink[0])
                y++;
            })
            var k = 0;
            var products=[];
            $.each(typeData,function(){
                products.push(
                    '<div class="productsDiv"><a><div><img src='+productsImg[k]+' width="300"></div>'+
                    '<span>'+productsName[k]+'</span><p class="price"><em>'+productsPrice[k]+'</em><p></span></a>'+
                    '<button type="button" class="btn btn-orange"><i class="entypo-note"></i></button>'+
                    '</div>'
                )
                k++;
            }) 
            $("#Products").html("");
            $("#Products").append(products.join(""))
        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

//顯示此次搜尋的產品數量
function getProductQuantity(data){
    var productQuantity = data.length;
    $("#productQuantity").text("總共有"+productQuantity+"件商品")  
}

// 側邊menu用↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
function getAllBrand(){
    $.ajax({
        url: "http://localhost:8080/search/data?dataName=brand&type=行車紀錄器",
        method: "GET",
        dataType: "json",
        success: function (brandData) {   
            var i=0;    
            var productBrandArray =[];
            $.each(brandData,function(){
                productBrandArray.push('<li><a href="#">'+brandData[i].data+'</a></li>')
                    i++
                })   
            $("#brandForm").append(productBrandArray.join(""))
        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

function getAllType(){
    
    $.ajax({
        url: "http://localhost:8080/search/data?dataName=type",
        method: "GET",
        dataType: "json",
        success: function (typesData) {   
            var i=0;    
            var productTypeArray =[];
            $.each(typesData,function(){
                    productTypeArray.push('<li><a href="#">'+typesData[i].data+'</a></li>')
                    i++
                })   
            $("#typeForm").append(productTypeArray.join(""))
        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
// 側邊menu用↑↑↑↑↑↑↑↑↑↑


function getProductsByUpdateTime(){
     
    $.ajax({
        url: "http://localhost:8080/search/type",
        method: "GET",
        dataType: "json",
        success: function (typeData) {   

        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

function getProductsByPrice(){
    // let NameBrandType = brand;
    var queryString = 1080;
    var price = $("#priceForm").serialize();
    $.ajax({
        url: "http://localhost:8080/search/price?byNameBrandType=name&queryString="+queryString+"&"+price,
        method: "GET",
        dataType: "json",
        success: function (priceData) {

            getProductQuantity(priceData)

            var productsName =[];
            var productsPrice =[];
            var productsImg =[];

            var y=0;
            $.each(priceData,function(){
                productsName.push(priceData[y].name)
                productsPrice.push(priceData[y].price)
                productsImg.push(priceData[y].imageLink[0])
                y++;
            })
            var k = 0;
            var products=[];
            $.each(priceData,function(){
                products.push(
                    '<li class="col-md-auto"><a><div><img src='+productsImg[k]+' width="300"></div>'+
                    '<span><span>'+productsName[k]+'</span><em>$'+productsPrice[k]+'</em><span></a>'+
                    '<button type="button" class="btn btn-gold"> <i class="entypo-heart"></i> </button>'+
                    '</li>'
                )
                k++;
            })
            $("#ProductsUl").html("");
            $("#ProductsUl").append(products.join(""))

        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
