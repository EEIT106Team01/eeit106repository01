$(document).ready(function() {
        //verify is admin
        if (memberBean != undefined) {
            $(`#draggable-events`).show();
            addToCart();
            if (member.level == "administrator") {
                location.href = "/shop/CMS.html";
            }
        } else {
            $(`#draggable-events`).hide();
        }

        getProducts()
        getAllType()
        getProductsByUpdateTime()
        getKeyword()
        getTop10Type1()
        getTop10Type2()
        getTop10Type3()
        if (localStorage.hasOwnProperty('type')) {
            var type = localStorage.getItem("type")
            getMightLikeProduct(type)
        }

        $("#search").on("click", (function() {
            if ($("#searchName").val() != null && typeof($("#searchName").val()) != "undefined" && $("#searchName").val().length != 0) {
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
        }))
        addToCart();
    })
    //right-area-全站產品排行and全站產品數量
function getProducts() {

    $.ajax({
        url: "/products",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(productsData) {
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;
            $.each(productsData, function() {
                if (productsData[y].id != 1630) {
                    productsName.push(productsData[y].name);
                    productsPrice.push(productsData[y].price);
                    productsImg.push(productsData[y].imageLink[0]);
                    productTotalSold.push(productsData[y].totalSold);
                    productsId.push(productsData[y].id);
                }
                y++;
            })

            var pageSize = productsData.length;
            $("#productsQuantity").empty().append("<span class='productsQuantityTitle'>全站共有<h2>" + pageSize + "件商品</h2></span><hr>")

            var products = [];
            var top = 10;
            for (var i = 0; i < top; i++) {
                products.push(
                    '<div class="col-md-2  productDiv"><a href="/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"><img src="img/hotSale.png" class="hotSale"></div>' +
                    '<span class="name">' + productsName[i].substr(0, 25) + '...</span></a><span class="price">$' + productsPrice[i] + '</span></span></div>'
                )
            }
            var result = products.join("");
            $("#productsTop10").empty().append(result);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    })
}
//left-area種類列表
function getAllType() {
    $.ajax({
        url: "/search/data?dataName=type",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(typesData) {
            var i = 0;
            var productTypeArray = [];
            $.each(typesData, function() {
                if (typesData[i].data != "會員") {
                    productTypeArray.push('<li class="li"><a href="/shop/search.html?type=' + typesData[i].data + '">' + typesData[i].data + '</a></li>')
                }
                i++
            })
            $("#classification").empty().append(productTypeArray.join(""))

            var y = 0;
            productTypeArray2 = [];
            $.each(typesData, function() {
                if (typesData[y].data != "會員") {
                    productTypeArray2.push("<option>" + typesData[y].data + "</option>")
                }
                y++
            })
            $("#searchType").empty().append("<option>All</option>" + productTypeArray2.join(""))
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    })
}
//新產品推薦
function getProductsByUpdateTime() {

    var endDay = GetDateStr(1);
    var startDay = GetDateStr(-30);
    $.ajax({
        url: "/search/updatedTime?dataName=&queryString=&brandType=&startDay=" + startDay + "&endDay=" + endDay,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(dayData) {
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productsId = [];
            var y = 0;
            $.each(dayData, function() {
                if (dayData[y].id != 1630) {
                    productsName.push(dayData[y].name)
                    productsPrice.push(dayData[y].price)
                    productsImg.push(dayData[y].imageLink[0])
                    productsId.push(dayData[y].id)
                }
                y++;
            })

            var products = []
            var top = 10;
            for (var i = 0; i < top; i++) {
                products.push(
                    '<div class="col-md-2  productDiv"><a href="/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"><img src="img/newSale.png" class="newSale"></div>' +
                    '<span class="name">' + productsName[i].substr(0, 25) + '...</span></a><span class="price">$' + productsPrice[i] + '</span></span></div>'
                )
            }
            var result = products.join("")
            $("#newUpdateProduct").empty().append(result)

        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//計算時間
function GetDateStr(AddDayCount) {
    var dd = new Date();
    dd.setDate(dd.getDate() + AddDayCount);
    var y = dd.getFullYear();
    var m = (dd.getMonth() + 1) < 10 ? "0" + (dd.getMonth() + 1) : (dd.getMonth() + 1);
    var d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();
    return y + "-" + m + "-" + d;
}

//關鍵字新增
function insertKeyWord() {
    var KW = $("#searchName").val();
    var keyWordInput = { keyword: KW }
    $.ajax({
        url: "/keyWord/insert",
        method: "POST",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(keyWordInput),
        success: function() {
            console.log("keyWord input success")
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

//取關鍵字
function getKeyword() {
    $.ajax({
        url: "/keyWords",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(keywordData) {
            var kwArray = [];
            var i = 0;
            $.each(keywordData, function() {
                kwArray.push(
                    '<a href="/shop/search.html?productName=' + keywordData[i].keyword + '&productType=All">' +
                    '<button type="button" class="btn btn-primary">' + keywordData[i].keyword + '</button>' +
                    '<span>&nbsp</span>')
                i++
            })
            if (kwArray.length < 5) {
                kwArray.length = 5;
            }
            var result = kwArray.join("")
            $("#keyWord").empty().append(result)
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

//找出最新預覽同種類商品
function getMightLikeProduct(type) {
    $.ajax({
        url: "/search/sort?dataName=type&queryString=" + type + "&type=totalSold&sort=desc&brandType=",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(sortData) {

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function() {
                if (sortData[y].id != 1630) {
                    productsName.push(sortData[y].name)
                    productsPrice.push(sortData[y].price)
                    productsImg.push(sortData[y].imageLink[0])
                    productsId.push(sortData[y].id);
                }
                y++;
            })

            var products = [];
            var top = 5;
            for (var i = 0; i < top; i++) {
                products.push(
                    '<div class="col-md-2  productDiv"><a href="/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"><img src="img/hotSale.png" class="hotSale"></div>' +
                    '<span class="name">' + productsName[i].substr(0, 25) + '...</span></a><span class="price">$' + productsPrice[i] + '</span></span></div>'
                )
            }
            $("#mightLikeItDiv").empty().append(
                '<div class="col-md-12">' +
                '<div class="classification">' +
                '<hr>' +
                '<span>你可能會喜歡</spanp>' +
                '<a href="/shop/search.html?type=' + type + '">' +
                '<span class="more">更多同種類商品</span>' +
                '</a>' +
                '</div>' +
                '<div id="mightLikeIt">' + products.join("") + '</div>'
            )
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    })
}
//種類TOP10-1
function getTop10Type1() {
    var type = encodeURIComponent("行車紀錄器")
    $.ajax({
        url: "/search/sort?dataName=type&queryString=" + type + "&type=totalSold&sort=desc&brandType=",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(sortData) {

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function() {
                productsName.push(sortData[y].name)
                productsPrice.push(sortData[y].price)
                productsImg.push(sortData[y].imageLink[0])
                productsId.push(sortData[y].id);
                y++;
            })

            var products = [];
            var top = 5;
            for (var i = 0; i < top; i++) {
                products.push(
                    '<div class="col-md-2  productDiv"><a href="/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"><img src="img/hotSale.png" class="hotSale"></div>' +
                    '<span class="name">' + productsName[i].substr(0, 25) + '...</span></a><span class="price">$' + productsPrice[i] + '</span></span></div>'
                )
            }
            $("#top10Product1").empty().append(products.join(""))
            $("#top10Product1A").attr("href", "/shop/search.html?type=" + type)
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    })
}
//種類TOP10-2
function getTop10Type2() {
    var type = encodeURIComponent("安全帽")
    $.ajax({
        url: "/search/sort?dataName=type&queryString=" + type + "&type=totalSold&sort=desc&brandType=",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(sortData) {

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function() {
                productsName.push(sortData[y].name)
                productsPrice.push(sortData[y].price)
                productsImg.push(sortData[y].imageLink[0])
                productsId.push(sortData[y].id);
                y++;
            })

            var products = [];
            var top = 5;
            for (var i = 0; i < top; i++) {
                products.push(
                    '<div class="col-md-2  productDiv"><a href="/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"><img src="img/hotSale.png" class="hotSale"></div>' +
                    '<span class="name">' + productsName[i].substr(0, 25) + '...</span></a><span class="price">$' + productsPrice[i] + '</span></span></div>'
                )
            }
            $("#top10Product2").empty().append(products.join(""))
            $("#top10Product2A").attr("href", "/shop/search.html?type=" + type)
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    })
}
//種類TOP10-3
function getTop10Type3() {
    var type = encodeURIComponent("衛星導航")
    $.ajax({
        url: "/search/sort?dataName=type&queryString=" + type + "&type=totalSold&sort=desc&brandType=",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(sortData) {

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function() {
                productsName.push(sortData[y].name)
                productsPrice.push(sortData[y].price)
                productsImg.push(sortData[y].imageLink[0])
                productsId.push(sortData[y].id);
                y++;
            })

            var products = [];
            var top = 5;
            for (var i = 0; i < top; i++) {
                products.push(
                    '<div class="col-md-2  productDiv"><a href="/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"><img src="img/hotSale.png" class="hotSale"></div>' +
                    '<span class="name">' + productsName[i].substr(0, 25) + '...</span></a><span class="price">$' + productsPrice[i] + '</span></span></div>'
                )
            }
            $("#top10Product3").empty().append(products.join(""))
            $("#top10Product3A").attr("href", "/shop/search.html?type=" + type)
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    })
}