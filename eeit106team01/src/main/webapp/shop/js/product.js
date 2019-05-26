$(document).ready(function () {
    $("#getNameButton").click(function () {
        getProductsByName();
    }),
    $("[data-toggle=popover]")
        .popover({html:true}),

    getProduct();
});

function classification(){
    
}

//產品種類搜尋
function getProductsByType(){
    var type = 行車紀錄器;
    $.ajax({
        url: "http://localhost:8080/products/type?type=" + type,
        method: "GET",
        dataType: "json",
        success: function (products) {

        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}   

// 產品名搜尋
function getProductsByName() {
    var name = $("#getNameForm").serialize();
    $.ajax({
        url: "http://localhost:8080/products/name?" + name,
        method: "GET",
        dataType: "json",
        success: function (products) {
            $("#searchResult").html("");
            var i = 0;
            var imgLink = [];
            var productsName =[];
            var productsPrice =[];
            var productsImg =[];
            $.each(products, function () {

                $.each(products[i].imageLink, function (key, val) {
                    imgLink.push(val)
                },

                $.each(recommendData,function(){
                    var y=0;
                    productsName.push(products[y].name)
                    productsPrice.push(products[y].price)
                    productsImg.push(products[y].imageLink[0])
                    i++;
                }),

                $("#searchResult").append(

                ),
            i++)
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

// 單件商品
function getProduct() {
    var id = 50;
    
    $.ajax({
        url: "http://localhost:8080/product/" + id,
        method: "GET",
        dataType: "json",
        success: function (productData) {

            //產品圖片
            productImgDiv(productData);
            //產品資訊
            searchResult(productData)
            //詳細規格
            productInformation(productData)
            //庫存數量.購買/購物車按鈕    
            buy(productData);

                $("#recommendTitle").text("你可能會喜歡")
                var name = productData.name;
                function getRecommendProducts(){
                    var i = 0;                
                    var recommendName =[];
                    var recommendPrice =[];
                    var recommendImg =[];
                    var recommendAll=[];
                    var recommendC1=[];
                    var recommendC2=[];
                    $.ajax({
                        url: "http://localhost:8080/products/recommend?name=" + name,
                        method: "GET",
                        dataType: "json",
                        success: function (recommendData) {

                            $.each(recommendData,function(){
                                recommendName.push(recommendData[i].name)
                                recommendPrice.push(recommendData[i].price)
                                recommendImg.push(recommendData[i].imageLink[0])
                                i++;
                            })   
                                for(let k=0;k<3;k++){
                                    recommendC1.push( '<div class="col-md-4"><div class="card mb-2"><img class="card-img-top" src='+recommendImg[k]+' alt="Card image cap"><div class="card-body"><h4 class="card-title">$'+recommendPrice[k]+'</h4><p class="card-text">'+recommendName[k]+'</p></div></div></div>')
                                }
                                for(let q=3;q<6;q++){
                                    recommendC2.push( '<div class="col-md-4"><div class="card mb-2"><li><a><img class="card-img-top" src='+recommendImg[q]+' alt="Card image cap"><div class="card-body"><h4 class="card-title">$'+recommendPrice[q]+'</h4><p class="card-text">'+recommendName[q]+'</p></div></a></li></div></div>')
                                }
                            //推薦商品輪播
                            $("#recommendResult").append(
                                // <!--Carousel Wrapper-->
                                '<div id="multi-item-example" class="carousel slide carousel-multi-item" data-ride="carousel">'+
                                                            
                                //Controls
                                    '<div class="controls-top">'+
                                    '<a class="btn-floating" href="#multi-item" data-slide="prev"><i class="fas fa-chevron-left"></i></a>'+
                                    '<a class="btn-floating" href="#multi-item" data-slide="next"><i class="fas fa-chevron-right"></i></a>'+
                                    '</div>'+
               
                                //Indicators
                                    '<ol class="carousel-indicators">'+
                                    '<li data-target="#multi-item" data-slide-to="0" class="active"></li>'+
                                    '<li data-target="#multi-item" data-slide-to="1"></li>'+
                                    '</ol>'+
    
                                // Slides
                                    '<div class="carousel-inner" role="listbox">'+
                                
                                //First slide
                                        '<div class="carousel-item active">'+recommendC1.join("")+'</div>'+
                                ///.First slide
                                //Second slide
                                        '<div class="carousel-item">'+recommendC2.join("")+'</div>'+
                                    '</div>'+
                                '</div>'
                            )
                        },error: function (jqXHR, textStatus, errorThrown) {
                            console.log(textStatus)
                        }
                    })
                }
                getRecommendProducts();

                $("#reviewTitle").text("顧客評價")
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}


function searchResult(productData){
    var productImgArray = [];
    productImgArray.push('<img src='+productData.imageLink[0]+' width="300">')
    $("#searchResult").html("");   
    $("#productLeftInfo").append(productImgArray.join(""))
    $("#productRightInfo").append(
    "<div id='productDiv'>"+'<hr>'+
        "<table>" +
            "<th colspan='2'>" + productData.name + "</th>" +
            "<tr>" +
                "<td style='color:#AAAAAA'>" + "價錢 :" + "</td>" +
                "<td  style='color:#FF0000;font-size:30px;'>$" + productData.price + "</td>" +
            "</tr>" +
            "<tr>" +
                "<td style='color:#AAAAAA'>" + "品牌/系列 :" + "</td>" +
                "<td>" + productData.brand + "</td>" +
            "</tr>" +
                "<tr>" +
                    "<td style='color:#AAAAAA'>" + "配送 :" + "</td>" +
                    "<td>快速宅配到貨 / 超商取貨</td>" +                             
                "</tr>" +
                "<tr>" +
                    "<td style='color:#AAAAAA'>" + "付款 :" + "</td>" +
                    "<td>貨到付款 / 超商付款取貨 / 信用卡</td>" +                             
                "</tr>" +
        "</table>"+
    "</div>")
}

function productInformation(productData){
    var informationArray = [];
    $.each(productData.information, function (key, val) {
        informationArray.push("<tr><td>"+ key +"</td><td>" + val +"</td></tr>");
    }) 
    $("#productInformation").html("");
    $("#productInformation").append(      
        "<table id='informationTable' class='table table-striped'>" +
            "<thead>"+
                "<th colspan='2'>" + "詳細規格" + "</th>" +
            "</thead>" +
                "<tbody>"+
                informationArray.join('')+
                "</tbody>"+
        "</table>"
    )
}

function productImgDiv(productData){
    var imgArray = [];
    $.each(productData.informationImageLink, function (key, val) {
        imgArray.push('<img src='+ val+' width="600">')
    })
    $("#productImgDiv").html("");
    $("#productImgDiv").append(imgArray.join(""))
}

function buy(productData){
    var stock = productData.stock;
    var txt = "";
    if(stock!=0){
        for(let i =1;i<=stock;i++){
            txt += "<option value='" + i + "'>"+i+"</option>";
        }
    }else{
        txt += "<option value='0'>0</option>";
    }

    //下訂
    $("#buy").html("")
    $("#buy").append(
        '<p style="color:#FF0000">$'+productData.price+'</p>'+
        '<p>數量 <select id="quantity">'+txt+'<select> (庫存'+stock+'件)</p>'+
        '<button type="button" class="btn btn-warning" id="buyNow">立即購買</button> '+
        '<button type="button" class="btn btn-outline-warning" id="shoppingCartButton">加入購物車</button>')
}