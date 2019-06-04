$(document).ready(function () {
    getProduct(takeUrlValue())
});

function takeUrlValue() {
    //URL
    var url = location.href;

    //取得問號之後的值
    var temp = url.split("?");
    return temp[1]
}

// 單件商品
function getProduct(id) {

    $.ajax({
        url: "http://localhost:8080/product/" + id,
        method: "GET",
        dataType: "json",
        success: function (productData) {

            //產品圖片
            $("#productImgDivButton").click(function () {
                productImgDiv(productData);
            });
            //產品資訊
            searchResult(productData)
            //詳細規格
            $("#productInformationButton").click(function () {
                productInformation(productData)
            });
            //庫存數量.購買/購物車按鈕    
            buy(productData);

            $("#reviewResultButton").click(function () {
                review();
            });


            var type = productData.type;
            console.log(type)
            getRecommendProducts(type);

            function review() {
                $("#reviewTitle").text("商品評價")
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

//推薦商品
function getRecommendProducts(type) {
    console.log("getRecommendProducts start")
    $("#recommendTitle").text("你可能會喜歡")
    $.ajax({
        url: "http://localhost:8080/products/recommend?type=" + type,
        method: "GET",
        dataType: "json",
        success: function (recommendData) {
            recommendTop(recommendData);
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
    console.log("getRecommendProducts end")
}

function searchResult(productData) {
    var productImgArray = [];
    productImgArray.push('<img src=' + productData.imageLink[0] + ' width="450">')
    $("#searchResult").html("");
    $("#productLeftInfo").append(productImgArray.join(""))
    $("#productRightInfo").append(
        "<div id='productDiv'>" + '<hr>' +
        "<table>" +
        "<th colspan='2' id='productName'>" + productData.name + "</th>" +
        "<tr>" +
        "<td style='color:#AAAAAA'>" + "價錢 :" + "</td>" +
        "<td  style='color:#FF0000;font-size:30px;' id='productPrice'>$" + productData.price + "</td>" +
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
        "</table>" +
        "</div>")
}

function productInformation(productData) {
    var informationArray = [];
    $.each(productData.information, function (key, val) {
        informationArray.push("<tr><td>" + key + "</td><td>" + val + "</td></tr>");
    })
    $("#productInformation").html("");
    $("#productInformation").append(
        "<table id='informationTable' class='table table-striped'>" +
        "<thead>" +
        "<th colspan='2'>" + "詳細規格" + "</th>" +
        "</thead>" +
        "<tbody>" +
        informationArray.join('') +
        "</tbody>" +
        "</table>"
    )
}

function productImgDiv(productData) {
    var imgArray = [];
    $.each(productData.informationImageLink, function (key, val) {
        imgArray.push('<img src=' + val + ' width="600">')
    })
    $("#productImgDiv").html("");
    $("#productImgDiv").append(imgArray.join(""))
}

function buy(productData) {
    var stock = productData.stock;
    var txt = "";
    if (stock != 0) {
        for (let i = 1; i <= stock; i++) {
            txt += "<option value='" + i + "'>" + i + "</option>";
        }
    } else {
        txt += "<option value='0'>0</option>";
    }

    //下訂
    $("#buy").html("")
    $("#buy").append(
        '<p>數量 <select id="quantity">' + txt + '<select> (庫存' + stock + '件)</p>' +
        '<button type="button" class="btn btn-warning" id="buyNow">立即購買</button> ' +
        '<button type="button" class="btn btn-outline-warning" id="shoppingCartButton">加入購物車</button>')

    addToCart();
}

function recommendTop(recommendData) {
    console.log("recommendTop start")
    var i = 0;
    var recommendName = [];
    var recommendPrice = [];
    var recommendImg = [];
    var recommendAll = [];
    var recommendC1 = [];
    var recommendC2 = [];
    $.each(recommendData, function () {
        recommendName.push(recommendData[i].name)
        recommendPrice.push(recommendData[i].price)
        recommendImg.push(recommendData[i].imageLink[0])
        i++;
    })

    if(recommendData.length>=10){
        for (let k = 0; k < 5; k++) {
            recommendC1.push(
                '<div class="col-md-2"><div class="item-box-blog"><div class="item-box-blog-image"><div class="item-box-blog-date bg-danger"><span class="rec">推薦商品</span></div>' +
                '<figure><img alt="" src=' + recommendImg[k] + '></figure>' +
                '</div><div class="item-box-blog-body">' +
                '<div class="item-box-blog-heading"><a href="#" tabindex="0"><h5>' + recommendName[k].substr(0, 10) + "..." + '</h5></a></div>' +
                '<div class="item-box-blog-text"><p>$' + recommendPrice[k] + '</p></div>' +
                '</div></div></div>'
            )
        }
        for (let q = 5; q < 10; q++) {
            recommendC2.push('<div class="col-md-2"><div class="item-box-blog"><div class="item-box-blog-image"><div class="item-box-blog-date bg-danger"><span class="rec">推薦商品</span></div>' +
                '<figure><img alt="" src=' + recommendImg[q] + '></figure>' +
                '</div><div class="item-box-blog-body">' +
                '<div class="item-box-blog-heading"><a href="#" tabindex="0"><h5>' + recommendName[q].substr(0, 10) + "..." + '</h5></a></div>' +
                '<div class="item-box-blog-text"><p>$' + recommendPrice[q] + '</p></div>' +
                '</div></div></div>'
            )
        }

        $("#item1").append("<div class='col-md-1'></div>" + recommendC1.join("") + "<div class='col-md-1'></div>")
        $("#item2").append("<div class='col-md-1'></div>" + recommendC2.join("") + "<div class='col-md-1'></div>")

    }else if(recommendData.length<=5){
        for (let k = 0; k < recommendData.length; k++) {
            recommendC1.push(
                '<div class="col-md-2"><div class="item-box-blog"><div class="item-box-blog-image"><div class="item-box-blog-date bg-danger"><span class="rec">推薦商品</span></div>' +
                '<figure><img alt="" src=' + recommendImg[k] + '></figure>' +
                '</div><div class="item-box-blog-body">' +
                '<div class="item-box-blog-heading"><a href="#" tabindex="0"><h5>' + recommendName[k].substr(0, 10) + "..." + '</h5></a></div>' +
                '<div class="item-box-blog-text"><p>$' + recommendPrice[k] + '</p></div>' +
                '</div></div></div>'
            )
        }

        $("#item1").append("<div class='col-md-1'></div>" + recommendC1.join("") + "<div class='col-md-1'></div>")

    }else if(5<recommendData.length<10){
        for (let k = 0; k < 5; k++) {
            recommendC1.push(
                '<div class="col-md-2"><div class="item-box-blog"><div class="item-box-blog-image"><div class="item-box-blog-date bg-danger"><span class="rec">推薦商品</span></div>' +
                '<figure><img alt="" src=' + recommendImg[k] + '></figure>' +
                '</div><div class="item-box-blog-body">' +
                '<div class="item-box-blog-heading"><a href="#" tabindex="0"><h5>' + recommendName[k].substr(0, 10) + "..." + '</h5></a></div>' +
                '<div class="item-box-blog-text"><p>$' + recommendPrice[k] + '</p></div>' +
                '</div></div></div>'
            )
        }
        for (let q = 5; q < recommendData.length; q++) {
            recommendC2.push('<div class="col-md-2"><div class="item-box-blog"><div class="item-box-blog-image"><div class="item-box-blog-date bg-danger"><span class="rec">推薦商品</span></div>' +
                '<figure><img alt="" src=' + recommendImg[q] + '></figure>' +
                '</div><div class="item-box-blog-body">' +
                '<div class="item-box-blog-heading"><a href="#" tabindex="0"><h5>' + recommendName[q].substr(0, 10) + "..." + '</h5></a></div>' +
                '<div class="item-box-blog-text"><p>$' + recommendPrice[q] + '</p></div>' +
                '</div></div></div>'
            )
        }
        
        $("#item1").append("<div class='col-md-1'></div>" + recommendC1.join("") + "<div class='col-md-1'></div>")
        $("#item2").append("<div class='col-md-1'></div>" + recommendC2.join("") + "<div class='col-md-1'></div>")
    }
   
    //推薦商品輪播
    $("#item1").append("<div class='col-md-1'></div>" + recommendC1.join("") + "<div class='col-md-1'></div>")
    $("#item2").append("<div class='col-md-1'></div>" + recommendC2.join("") + "<div class='col-md-1'></div>")
    console.log("recommendTop end")
}

function getAllType() {
    var i = 0;
    var productTypeArray = [];
    $.ajax({
        url: "http://localhost:8080/search/type",
        method: "GET",
        dataType: "json",
        success: function (typeData) {

            $.each(typeData, function () {
                productTypeArray.push('<li><span><a href="">' + typeData[i].type + '</a></span></li>')
            })
            $("#Classification").attr("data-content", "<ul>" + productTypeArray.join("") + "</ul>");
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
