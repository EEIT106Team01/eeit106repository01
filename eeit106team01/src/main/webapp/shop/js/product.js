$(document).ready(function() {
    getProduct(takeUrlValue());
    getAllType();
    //搜尋
    $("#search").on("click", function() {
        if ($("#searchName").val() != null || typeof($("#searchName").val()) != "undefined" || $("#searchName").val().length == 0) {
            insertKeyWord();
        }

        //驗證
        $("#searchForm").validate({
            rules: {
                searchName: {
                    required: true
                }
            },
            messages: {
                searchName: {
                    required: "必須輸入"
                }
            },
            errorPlacement: function(error, element) {
                error.appendTo(element.parent().next("label"));
            }
        })
        if ($("#searchForm").valid()) {
            var productType = $("#searchType").val();
            var productName = $("#searchName").val();
            window.location.href = "/shop/search.html?productName=" + productName + "&productType=" + productType;
        }
    });

});

function takeUrlValue() {
    var url = location.href;
    var temp = url.split("?");
    return temp[1];
}

//關鍵字新增
function insertKeyWord() {
    var KW = $("#searchName").val();
    var keyWordInput = { keyword: KW };
    $.ajax({
        url: "/keyWord/insert",
        method: "POST",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(keyWordInput),
        success: function() {
            console.log("keyWord input success");
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    });
}

//搜尋種類列表
function getAllType() {
    $.ajax({
        url: "/search/data?dataName=type",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(typesData) {
            var y = 0;
            productTypeArray2 = [];
            $.each(typesData, function() {
                productTypeArray2.push("<option>" + typesData[y].data + "</option>");
                y++;
            });
            $("#searchType")
                .empty()
                .append("<option>All</option>" + productTypeArray2.join(""));
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    });
}

//搜單件商品
function getProduct(id) {
    $.ajax({
        url: "/product/" + id,
        method: "GET",
        dataType: "json",
        success: function(productData) {
            //產品圖片
            $("#productImgDivButton").click(function() {
                productImgDiv(productData);
            });
            //產品資訊
            searchResult(productData);
            //詳細規格
            $("#productInformationButton").click(function() {
                productInformation(productData);
            });
            //庫存數量.購買/購物車按鈕
            buy(productData);

            $("#reviewResultButton").click(function() {
                review();
            });

            var type = productData.type;
            getRecommendProducts(type);
            //type存入localStorage
            if (localStorage.hasOwnProperty('type')) {
                localStorage.removeItem("type");
            }
            localStorage.setItem("type", type)
                //breadcrumb
            $("#typeBreadcrumb").append(
                '<a href="/shop/search.html?type=' +
                type +
                '">' +
                type +
                "</a>"
            );
            $("#productBreadcrumb").append("<a>" + productData.name + "</a>");

            function review() {
                $("#reviewTitle").text("商品評價");
            }
            //cloudZoom
            CloudZoom.quickStart();
            // Initialize the slider.
            $(function() {
                $("#slider1").Thumbelina({
                    $bwdBut: $("#slider1 .left"),
                    $fwdBut: $("#slider1 .right")
                });
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    });
}
//產品查詢結果
function searchResult(productData) {
    var productImgArray = [];
    productImgArray.push(
        "<img src=" +
        productData.imageLink[0] +
        'style="border-radius:8px; border-width:5px; border-style:outset; border-color: gray;" class="cloudzoom image" alt="Cloud Zoom small image" id="zoom1" data-cloudzoom="zoomSizeMode:`image`,autoInside: 550">'
    );
    $("#searchResult").html("");
    $("#productLeftInfo").append(
        "<div id='surround'>" + productImgArray.join("") + "</div>"
    );
    $("#productRightInfo").append(
        "<div id='productDiv'>" +
        "<hr>" +
        "<table>" +
        "<th colspan='2' id='productName'>" +
        productData.name +
        "</th>" +
        "<tr>" +
        "<td style='color:#AAAAAA'>" +
        "價錢 :" +
        "</td>" +
        "<td  style='color:#FF0000;font-size:30px;' id='productPrice'>$" +
        productData.price +
        "</td>" +
        "</tr>" +
        "<tr>" +
        "<td style='color:#AAAAAA'>" +
        "品牌/系列 :" +
        "</td>" +
        "<td>" +
        productData.brand +
        "</td>" +
        "</tr>" +
        "<tr>" +
        "<td style='color:#AAAAAA'>" +
        "配送 :" +
        "</td>" +
        "<td>快速宅配到貨 / 超商取貨</td>" +
        "</tr>" +
        "<tr>" +
        "<td style='color:#AAAAAA'>" +
        "付款 :" +
        "</td>" +
        "<td>貨到付款 / 超商付款取貨 / 信用卡</td>" +
        "</tr>" +
        "</table>" +
        "</div>"
    );
}
//詳細規格
function productInformation(productData) {
    var informationArray = [];
    $.each(productData.information, function(key, val) {
        informationArray.push("<tr><td>" + key + "</td><td>" + val + "</td></tr>");
    });
    $("#productInformation").html("");
    $("#productInformation").append(
        "<table id='informationTable' class='table table-striped'>" +
        "<thead>" +
        "<th colspan='2'>" +
        "詳細規格" +
        "</th>" +
        "</thead>" +
        "<tbody>" +
        informationArray.join("") +
        "</tbody>" +
        "</table>"
    );
}
//產品圖片
function productImgDiv(productData) {
    var imgArray = [];
    $.each(productData.informationImageLink, function(key, val) {
        imgArray.push(
            '<span class="row"><span class="col-md-2"></span><img class="col-md-8" src=' +
            val +
            ' width="60%"/><span class="col-md-2"></span></span>'
        );
    });
    $("#productImgDiv").html("");
    $("#productImgDiv").append(imgArray.join(""));
}
//庫存數量 下定
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
    $("#buy").html("");
    if (stock <= 0) {
        $("#buy").append(
            '<p>數量 <select id="quantity">' +
            txt +
            "<select> (庫存" +
            stock +
            "件)</p>" +
            '<button type="button" class="btn btn-warning" id="buyNow" disabled="disabled">補貨中</button> ' +
            '<button type="button" class="btn btn-outline-warning" id="shoppingCartButton" disabled="disabled">加入購物車</button>'
        );
    } else {
        $("#buy").append(
            '<p>數量 <select id="quantity">' +
            txt +
            "<select> (庫存" +
            stock +
            "件)</p>" +
            '<button type="button" class="btn btn-warning" id="buyNow">立即購買</button> ' +
            '<button type="button" class="btn btn-outline-warning" id="shoppingCartButton">加入購物車</button>'
        );
    }


    addToCart();
}
//推薦商品
function recommendTop(recommendData) {
    var i = 0;
    var recommendName = [];
    var recommendPrice = [];
    var recommendImg = [];
    var recommendId =[];
    var recommendAll = [];
    var recommendC1 = [];
    var recommendC2 = [];
    $.each(recommendData, function() {
        recommendId.push(recommendData[i].id);
        recommendName.push(recommendData[i].name);
        recommendPrice.push(recommendData[i].price);
        recommendImg.push(recommendData[i].imageLink[0]);
        i++;
    });

    if (recommendData.length >= 10) {
        for (let k = 0; k < 5; k++) {
            recommendC1.push(
                '<div class="col-md-2">' +
                "<div>" +
                "<div>" +
                "<div>" +
                '<span class="rec"><img src="img/recommend-icon.png"></span>' +
                "</div>" +
                '<img class="recIMG" src=' +
                recommendImg[k] +
                "/>" +
                "</div>" +
                "<div>" +
                "<div>" +
                '<a href="/shop/product.html?'+recommendId[k]+'" tabindex="0">' +
                "<h5>" +
                recommendName[k].substr(0, 10) +
                "..." +
                "</h5>" +
                "</a>" +
                "</div>" +
                "<div>" +
                '<p class="recPrice">$' +
                recommendPrice[k] +
                "</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>"
            );
        }
        for (let q = 5; q < 10; q++) {
            recommendC2.push(
                '<div class="col-md-2">' +
                "<div>" +
                "<div>" +
                "<div>" +
                '<span class="rec"><img src="img/recommend-icon.png"></span>' +
                "</div>" +
                '<img class="recIMG" src=' +
                recommendImg[q] +
                "/>" +
                "</div>" +
                "<div>" +
                "<div>" +
                '<a href="/shop/product.html?'+recommendId[q]+'" tabindex="0">' +
                "<h5>" +
                recommendName[q].substr(0, 10) +
                "..." +
                "</h5>" +
                "</a>" +
                "</div>" +
                "<div>" +
                '<p class="recPrice">$' +
                recommendPrice[q] +
                "</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>"
            );
        }

        $("#item1").append(
            "<div class='col-md-1'></div>" +
            recommendC1.join("") +
            "<div class='col-md-1'></div>"
        );
        $("#item2").append(
            "<div class='col-md-1'></div>" +
            recommendC2.join("") +
            "<div class='col-md-1'></div>"
        );

        $("#recCarouselDiv").empty().append(
            '<a class="left carousel-control leftbtn" href="#carousel-reviews" role="button" data-slide="prev">' +
            '<span class="glyphicon glyphicon-chevron-left"></span>' +
            '</a>' +
            '<a class="right carousel-control rightbtn" href="#carousel-reviews" role="button" data-slide="next">' +
            '<span class="glyphicon glyphicon-chevron-right"></span>' +
            '</a>'
        )
    } else if (recommendData.length <= 5) {
        for (let k = 0; k < recommendData.length; k++) {
            recommendC1.push(
                '<div class="col-md-2">' +
                "<div>" +
                "<div>" +
                "<div>" +
                '<span class="rec"><img src="img/recommend-icon.png"></span>' +
                "</div>" +
                '<img class="recIMG" src=' +
                recommendImg[k] +
                "/>" +
                "</div>" +
                "<div>" +
                "<div>" +
                '<a href="#" tabindex="0">' +
                "<h5>" +
                recommendName[k].substr(0, 10) +
                "..." +
                "</h5>" +
                "</a>" +
                "</div>" +
                "<div>" +
                '<p class="recPrice">$' +
                recommendPrice[k] +
                "</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>"
            );
        }

        $("#item1").append(
            "<div class='col-md-1'></div>" +
            recommendC1.join("") +
            "<div class='col-md-1'></div>"
        );
    } else if (5 < recommendData.length < 10) {
        for (let k = 0; k < 5; k++) {
            recommendC1.push(
                '<div class="col-md-2">' +
                "<div>" +
                "<div>" +
                "<div>" +
                '<span class="rec"><img src="img/recommend-icon.png"></span>' +
                "</div>" +
                '<img class="recIMG" src=' +
                recommendImg[k] +
                "/>" +
                "</div>" +
                "<div>" +
                "<div>" +
                '<a href="/shop/product.html?'+recommendId[k]+'" tabindex="0">' +
                "<h5>" +
                recommendName[k].substr(0, 10) +
                "..." +
                "</h5>" +
                "</a>" +
                "</div>" +
                "<div>" +
                '<p class="recPrice">$' +
                recommendPrice[k] +
                "</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>"
            );
        }
        for (let q = 5; q < recommendData.length; q++) {
            recommendC2.push(
                '<div class="col-md-2">' +
                "<div>" +
                "<div>" +
                "<div>" +
                '<span class="rec"><img src="img/recommend-icon.png"></span>' +
                "</div>" +
                '<img class="recIMG" src=' +
                recommendImg[q] +
                "/>" +
                "</div>" +
                "<div>" +
                "<div>" +
                '<a href="/shop/product.html?'+recommendId[q]+'" tabindex="0">' +
                "<h5>" +
                recommendName[q].substr(0, 10) +
                "..." +
                "</h5>" +
                "</a>" +
                "</div>" +
                "<div>" +
                '<p class="recPrice">$' +
                recommendPrice[q] +
                "</p>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>"
            );
        }

        $("#item1").append(
            "<div class='col-md-1'></div>" +
            recommendC1.join("") +
            "<div class='col-md-1'></div>"
        );
        $("#item2").append(
            "<div class='col-md-1'></div>" +
            recommendC2.join("") +
            "<div class='col-md-1'></div>"
        );

        $("#recCarouselDiv").empty().append(
            '<a class="left carousel-control leftbtn" href="#carousel-reviews" role="button" data-slide="prev">' +
            '<span class="glyphicon glyphicon-chevron-left"></span>' +
            '</a>' +
            '<a class="right carousel-control rightbtn" href="#carousel-reviews" role="button" data-slide="next">' +
            '<span class="glyphicon glyphicon-chevron-right"></span>' +
            '</a>'
        )
    }
}
//取得推薦商品
function getRecommendProducts(type) {
    $("#recommendTitle").text("你可能會喜歡");
    $.ajax({
        url: "/products/recommend?type=" + type,
        method: "GET",
        dataType: "json",
        success: function(recommendData) {
            recommendTop(recommendData);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    });
}