$(document).ready(function () {
    $("#getNameButton").click(function () {
        getProductsByName();
    })

    getProduct();
    // $("#getIdButton").click(function () {
    //     getProduct();
        
    // })
    // $(".infoImgClass").mouseenter(function(){
    //     imgChange();
    // })
});

// 產品名搜尋
function getProductsByName() {
    var name = $("#getNameForm").serialize();
    $.ajax({
        url: "http://localhost:8080/products/name?" + name,
        method: "GET",
        dataType: "json",
        success: function (data) {
            $("#searchResult").html("");
            var i = 0;
            var informationArray = [];
            var imgLink = []

            $.each(data, function () {

                $.each(data[i].imageLink, function (key, val) {
                    imgLink.push(val)
                },

                $("#searchResult").append(
                    "<div>"+
                        "<table>" +
                            "<th colspan='2'>" + data[i].name + "</th>" +
                                "<tr>" +
                                    "<td>" + "品牌 :" + "</td>" +
                                    "<td>" + data[i].brand + "</td>" +
                                "</tr>" +
                                "<tr>" +
                                    "<td>" + "價錢 :" + "</td>" +
                                    "<td>" + data[i].price + "</td>" +
                                "</tr>" +
                        "</table>"+
                    "</div>"),
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
    var id = 44;
    var i = 0;
    var informationArray = [];
    var imgArray = [];
    var productImgArray = [];
    $.ajax({
        url: "http://localhost:8080/product/" + id,
        method: "GET",
        dataType: "json",
        success: function (productData) {


            $.each(productData.information, function (key, val) {
                informationArray.push("<tr><td>"+ key +"</td><td>" + val +"</td></tr>");
            })+

            $.each(productData.informationImageLink,function(key,val){
                imgArray.push('<div><img src='+val+' alt='+productData.name+' title='+productData.name+' class="infoImgClass" width="70"></div>')
                
            })
                productImgArray.push('<div><img src='+productData.imageLink[0]+' alt='+productData.name+' title='+productData.name+' class="infoImgClass" width="300"></div>')

            $("#searchResult").html("");
            $("#searchResult").append(
                '<div id="productImgDiv">'+
                productImgArray.join("")+
                imgArray.join("")+
                    
                "</div>"+

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
                    
                    
                    
                    $("#buy").html("")
                    $("#buy").append(
                        '<p style="color:#FF0000">$'+productData.price+'</p>'+
                        '<p>數量 <select id="quantity">'+txt+'<select> (庫存'+stock+'件)</p>'+
                        '<button type="button" class="btn btn-warning" id="buyNow">立即購買</button> '+
                        '<button type="button" class="btn btn-outline-warning" id="shoppingCartButton">加入購物車</button>')
                    }


                    
                buy(productData);
                $("#recommendTitle").text("この商品をチェックした人はこんな商品もチェックしています")
                var name = productData.name;
                function getRecommendProducts(){
                    var i = 0;
                    var y = 0;
                    
                    var recommendName =[];
                    var recommendPrice =[];
                    var recommendImg =[];
                    var recommendAll=[];
                    var recommendC1=[];
                    var recommendC2="";
                    $.ajax({
                        url: "http://localhost:8080/products/recommend?name=" + name,
                        method: "GET",
                        dataType: "json",
                        success: function (recommendData) {

                            $.each(recommendData,function(){
                                recommendName.push(recommendData[i].name)
                                recommendPrice.push(recommendData[i].price)
                                recommendImg.push(
                                    '<img src='+recommendData[i].imageLink[0]+
                                    ' alt="'+recommendData[i].name+
                                    ' title="'+recommendData[i].name+
                                    ' width="200">')
                                i++;
                            })

                            $.each(recommendName,function(){
                                recommendAll.push(
                                    '<div id="recommendDiv">'+recommendImg[y]+
                                        '<p>'+recommendName[y]+'</p>'+
                                        '<p style="color:#FF0000">$ '+recommendPrice[y]+'</p>'+
                                    '</div>'
                                )
                                y++;
                            })



                                for(let k=0;k<=4;k++){
                                    recommendC1.push( '<div class="col-md-4"><div class="card mb-2"><img class="card-img-top" src="'+recommendImg[k]+'"'+'alt="Card image cap"><div class="card-body"><h4 class="card-title"></h4><p class="card-text"></p><a class="btn btn-primary">Button</a></div></div></div>')
                                }
                                for(let q=4;k<=7;q++){
                                    recommendC2.push( '<div class="col-md-4"><div class="card mb-2"><img class="card-img-top" src="'+recommendImg[q]+'"'+'alt="Card image cap"><div class="card-body"><h4 class="card-title"></h4><p class="card-text"></p><a class="btn btn-primary">Button</a></div></div></div>')
                                }

                            
                            $("#recommendResult").append(
                                // recommendAll.join("")
                                )
                        // <!--Carousel Wrapper-->
                            '<div id="multi-item-example" class="carousel slide carousel-multi-item" data-ride="carousel">'+
                            
                        //    Controls
                            '<div class="controls-top">'+
                              '<a class="btn-floating" href="#multi-item-example" data-slide="prev"><i class="fas fa-chevron-left"></i></a>'+
                              '<a class="btn-floating" href="#multi-item-example" data-slide="next"><i'+
                                  'class="fas fa-chevron-right"></i></a>'+
                            '</div>'+
                        // /.Controls
                          
                        //    Indicators
                            '<ol class="carousel-indicators">'+
                              '<li data-target="#multi-item-example" data-slide-to="0" class="active"></li>'+
                              '<li data-target="#multi-item-example" data-slide-to="1"></li>'+
                            '</ol>'+
                        //   /.Indicators
                          
                            // Slides
                            '<div class="carousel-inner" role="listbox">'+
                          
                        //   First slide
                              '<div class="carousel-item active">'+recommendC1+
                            '</div>'+
                              
                        //   /.First slide
                          
                            //   Second slide
                              '<div class="carousel-item">'+
                              recommendC2+             
                              '</div>'+
                            //  /.Second slide
                            '</div>'+
                        //   /.Slides
                          
                          '</div>'
                        //   /.Carousel Wrapper
                            
                        },error: function (jqXHR, textStatus, errorThrown) {
                            console.log(textStatus)
                        }
                    })
                }
                getRecommendProducts();

                $("#reviewTitle").text("カスタマーレビュー")
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}




// function imgChange(){
//     var src = $("img").attr("src")
//     $("#InfoImg").attr("src",src)
// }

