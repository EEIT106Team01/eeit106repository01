$(document).ready(function () {
    getProductsByType(takeTypeUrlValue())
    getAllType()
    getAllBrand(takeTypeUrlValue())
    userSearch()

    $("#starDay").datepicker()
    $("#endDay").datepicker()
    $("#searchPriceByBrandBtn").hide();
    $("#searchPriceBytypeBtn").on('click', (function () {
        $("#sortByBrandByMixpriceMaxprice").hide();
        $("#sortByTypeByMixpriceMaxprice").show();
        $("#sortByType").hide();
        var type = takeTypeUrlValue()
        getProductsByPriceByType(type);

    }))

    var type = takeTypeUrlValue();
    $("#sortByBrand").hide();
    $("#sortByType").show();
    $("#sortSoldDescByType").on('click', (function () {
        sortSoldDescBytype(type)
    }))
    $("#sortSoldAscByType").on('click', (function () {
        sortSoldAscBytype(type)
    }))
    $("#sortPriceAscByType").on('click', (function () {
        sortPriceAscBytype(type)
    }))
    $("#sortPriceDescByType").on('click', (function () {
        sortPriceDescBytype(type)
    }))
    $("#sortDefultByType").on('click', (function () {
        getProductsByType(type)
    }))

    $("#sortByBrandByMixpriceMaxprice").hide()
    $("#sortByTypeByMixpriceMaxprice").hide()
})


//搜尋
function userSearch() {
    var type = takeSearchUrlValue2()
    if (type != null) {
        if (type.match("All") != null) {
            search("productType=&" + takeSearchUrlValue())
        }
        else {
            search(takeSearchUrlValue3())
        }
    } else
        console.log("")
}

//顯示此次搜尋的產品數量
function getProductQuantity(data) {
    var productQuantity = data.length;
    $("#productQuantity").text(+productQuantity)
}
//url處理-取type
function takeTypeUrlValue() {
    //URL
    var url = location.href;
    //取得=之後的值
    var temp = url.split("type=");
    return temp[1]
}
//url處理-取productName
function takeSearchUrlValue() {
    //URL
    var url = location.href;
    //取得問號之後的值
    var temp = url.split("&productType=");
    console.log(temp)
    var temp2 = temp[0].split("?");
    console.log(temp2)
    return temp2[1]
}
//url處理-取productType
function takeSearchUrlValue2() {
    //URL
    var url = location.href;
    //取得問號之後的值
    var temp = url.split("&productType=");
    console.log(temp)
    return temp[1]
}
//url處理-取productType&Name
function takeSearchUrlValue3() {
    //URL
    var url = location.href;
    //取得問號之後的值
    var temp = url.split("?");
    return temp[1]
}

// 側邊menu用↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

function ByType(type) {
    $("#searchPriceByBrandBtn").hide();
    $("#searchPriceBytypeBtn").show();

    // var type = $(type).text();
    var type = takeTypeUrlValue();
    getProductsByType(type);
    getAllBrand(type);
    

    $("#searchPriceBytypeBtn").on('click', (function () {
        $("#sortByBrandByMixpriceMaxprice").hide()
        $("#sortByTypeByMixpriceMaxprice").show()
        getProductsByPriceByType(type);

        $("#sortSoldDescByTypeByMixpriceMaxprice").on('click', (function () {
            geSortDescBySoldByType(type)
        }))
        $("#sortSoldAscByTypeByMixpriceMaxprice").on('click', (function () {
            getSortAscBySoldByType(type)
        }))
        $("#sortPriceAscByTypeByMixpriceMaxprice").on('click', (function () {
            getSortAscByPriceByType(type)
        }))
        $("#sortPriceDescByTypeByMixpriceMaxprice").on('click', (function () {
            getSortDescByPriceByType(type)
        }))
    }))


    $("#sortByBrand").hide();
    $("#sortByType").show();
    $("#sortSoldDescByType").on('click', (function () {
        sortSoldDescBytype(type)
    }))
    $("#sortSoldAscByType").on('click', (function () {
        sortSoldAscBytype(type)
    }))
    $("#sortPriceAscByType").on('click', (function () {
        sortPriceAscBytype(type)
    }))
    $("#sortPriceDescByType").on('click', (function () {
        sortPriceDescBytype(type)
    }))
    $("#sortDefultByType").on('click', (function () {
        getProductsByType(type)
    }))
    console.log(type)
}

function ByBrand(brand) {
    $("#searchPriceByBrandBtn").show();
    $("#searchPriceBytypeBtn").hide();
    $("#sortByTypeByMixpriceMaxprice").hide()
    var type = takeTypeUrlValue();
    var brand = $(brand).text();
    getProductsByBrand(brand, type);

    

    $("#searchPriceByBrandBtn").on('click', (function () {
        $("#sortByBrand").hide();
        $("#sortByTypeByMixpriceMaxprice").hide()
        $("#sortByBrandByMixpriceMaxprice").show()
        getProductsByPriceByBrand(brand, type);

        $("#sortSoldDescByBrandByMixpriceMaxprice").on('click', (function () {
            getSortDescBySoldBybrand(type)
        }))
        $("#sortSoldAscByBrandByMixpriceMaxprice").on('click', (function () {
            getSortAscBySoldBybrand(type) 
        }))
        $("#sortPriceAscByBrandByMixpriceMaxprice").on('click', (function () {
            getSortAscByPriceByBrandBybrand(type) 
        }))
        $("#sortPriceDescByBrandByMixpriceMaxprice").on('click', (function () {
            getSortDescByPriceBybrand(type)
        }))
    }))

    $("#sortByBrand").show();
    $("#sortByType").hide();


    $("#sortSoldDescByBrand").on('click', (function () {
        sortSoldDescBybrand(brand, type)
    }))
    $("#sortSoldAscByBrand").on('click', (function () {
        sortSoldAscBybrand(brand, type)
    }))
    $("#sortPriceAscByBrand").on('click', (function () {
        sortPriceAscBybrand(brand, type)
    }))
    $("#sortPriceDescByBrand").on('click', (function () {
        sortPriceDescBybrand(brand, type)
    }))
    $("#sortDefultByBrand").on('click', (function () {
        getProductsByBrand(brand)
    }))
    console.log(brand)
}
//取得該種類全部品牌
function getAllBrand(type) {
    $.ajax({
        url: "http://localhost:8080/search/data?dataName=brand&type=" + type,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (brandData) {
            var i = 0;
            var productBrandArray = [];
            $.each(brandData, function () {
                productBrandArray.push('<li><a href="" class="brandLabelA" onclick="ByBrand(this);return false">' + brandData[i].data + '</a></li>')
                i++
            })

            $("#brandForm").empty().append(productBrandArray.join(""))
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//取得全部種類
function getAllType() {
    $.ajax({
        url: "http://localhost:8080/search/data?dataName=type",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (typesData) {
            var i = 0;
            var productTypeArray = [];
            $.each(typesData, function () {
                productTypeArray.push('<li><a href="http://localhost:8080/shop/search.html?type=' + typesData[i].data + '" onclick="ByType(this)">' + typesData[i].data + '</a></li>')
                i++
            })
            $("#typeForm").empty().append(productTypeArray.join(""))
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
// 側邊menu用↑↑↑↑↑↑↑↑↑↑

//全站產品依產品搜尋(不使用)
function getProductsByPrice() {
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof (minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&maxPrice=" + maxPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (priceData) {
            console.log("getProductsByPrice() star")
            console.log("getProductQuantity() star")
            getProductQuantity(priceData)
            console.log("getProductQuantity() end")
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;
            $.each(priceData, function () {
                productsName.push(priceData[y].name)
                productsPrice.push(priceData[y].price)
                productsImg.push(priceData[y].imageLink[0])
                productTotalSold.push(priceData[y].totalSold)
                productsId.push(priceData[y].id)
                y++;
            })

            var pageSize = priceData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        } else {
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPrice() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPrice() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPrice() end")
                    }
                }
            });

        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//依種類搜尋and價錢區間搜尋
function getProductsByPriceByType(type) {
    var queryString = type;
    console.log("queryString=" + queryString)
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof (minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&maxPrice=" + maxPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;
            $.each(priceData, function () {
                productsName.push(priceData[y].name)
                productsPrice.push(priceData[y].price)
                productsImg.push(priceData[y].imageLink[0])
                productTotalSold.push(priceData[y].totalSold)
                productsId.push(priceData[y].id)
                y++;
            })

            var pageSize = priceData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        } else {
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//依種類搜尋
function getProductsByType(type) {
    $.ajax({
        url: "http://localhost:8080/search/type?type=" + type,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (typeData) {
            console.log("getProductsByType() star")
            console.log("getProductQuantity() star")
            getProductQuantity(typeData)
            console.log("getProductQuantity() end")

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(typeData, function () {
                productsName.push(typeData[y].name)
                productsPrice.push(typeData[y].price)
                productsImg.push(typeData[y].imageLink[0])
                productTotalSold.push(typeData[y].totalSold)
                productsId.push(typeData[y].id);
                y++;
            })
            console.log("取資料塞入array")

            var pageSize = typeData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            console.log("計算頁數 開始")
            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination -onclick")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        console.log("page 1 pageSize < onePageProducts star")
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 pageSize < onePageProducts end")
                        } else {
                            console.log("page 1 i<=onePageProducts-1 star")
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 i<=onePageProducts-1 end")
                        }

                        var result = products.join("")
                        // $(".Products").html("");
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page end push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page else push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//依品牌搜尋and價錢區間搜尋
function getProductsByPriceByBrand(brand, type) {
    var queryString = brand;
    console.log(type)
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + queryString + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&type=" + type;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof (minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + queryString + "&maxPrice=" + maxPrice + "&type=" + type;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (priceData) {
            console.log("getProductsByPriceByBrand() star")
            console.log("getProductQuantity() star")
            getProductQuantity(priceData)
            console.log("getProductQuantity() end")
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];
            var y = 0;
            $.each(priceData, function () {
                productsName.push(priceData[y].name)
                productsPrice.push(priceData[y].price)
                productsImg.push(priceData[y].imageLink[0])
                productTotalSold.push(priceData[y].totalSold)
                productsId.push(priceData[y].id)
                y++;
            })

            var pageSize = priceData.length;
            var onePageProducts = 12;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        } else {
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByBrand() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByBrand() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByBrand() end")
                    }
                }
            });

        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//依品牌搜尋
function getProductsByBrand(brand, type) {

    $.ajax({
        url: "http://localhost:8080/search/brand?brand=" + brand + "&type=" + type,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (brandData) {
            console.log("getProductsByBrand() star")
            console.log("getProductQuantity() star")
            getProductQuantity(brandData)
            console.log("getProductQuantity() end")

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;
            $.each(brandData, function () {
                productsName.push(brandData[y].name)
                productsPrice.push(brandData[y].price)
                productsImg.push(brandData[y].imageLink[0])
                productTotalSold.push(brandData[y].totalSold)
                productsId.push(brandData[y].id)
                y++;
            })

            var pageSize = brandData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數

                    var products = [];
                    if (page == 1) {
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 i<pageSize push end")
                        } else {
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 i<=onePageProducts-1 push end")
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByBrand() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page end push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByBrand() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page else push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByBrand() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//搜尋
function search(searchResult) {
    var searchUrl = "http://localhost:8080/search/TypeName?" + searchResult;
    $.ajax({
        url: searchUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (searchData) {
            console.log("getProductQuantity() star")
            getProductQuantity(searchData)
            console.log("getProductQuantity() end")

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;
            $.each(searchData, function () {
                productsName.push(searchData[y].name)
                productsPrice.push(searchData[y].price)
                productsImg.push(searchData[y].imageLink[0])
                productTotalSold.push(searchData[y].totalSold)
                productsId.push(searchData[y].id)
                y++;
            })

            var pageSize = searchData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數

                    var products = [];
                    if (page == 1) {
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 i<pageSize push end")
                        } else {
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 i<=onePageProducts-1 push end")
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByBrand() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page end push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByBrand() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page else push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByBrand() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

//商品排序(TYPE/price高到低)
function sortPriceDescBytype(type) {

    $.ajax({
        url: "http://localhost:8080/search/sort?dataName=type&queryString=" + type + "&type=price&sort=desc&brandType=",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (sortData) {

            getProductQuantity(sortData)

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function () {
                productsName.push(sortData[y].name)
                productsPrice.push(sortData[y].price)
                productsImg.push(sortData[y].imageLink[0])
                productTotalSold.push(sortData[y].totalSold)
                productsId.push(sortData[y].id);
                y++;
            })

            var pageSize = sortData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數
                    var products = [];
                    if (page == 1) {
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        } else {
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        }
                        var result = products.join("")
                        $(".Products").empty().append(result)
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        $(".Products").empty().append(result)

                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        $(".Products").empty().append(result)
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(TYPE/price低到高)
function sortPriceAscBytype(type) {
    $.ajax({
        url: "http://localhost:8080/search/sort?dataName=type&queryString=" + type + "&type=price&sort=asc&brandType=",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (sortData) {
            console.log("getProductsByType() star")
            console.log("getProductQuantity() star")
            getProductQuantity(sortData)
            console.log("getProductQuantity() end")

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function () {
                productsName.push(sortData[y].name)
                productsPrice.push(sortData[y].price)
                productsImg.push(sortData[y].imageLink[0])
                productTotalSold.push(sortData[y].totalSold)
                productsId.push(sortData[y].id);
                y++;
            })
            console.log("取資料塞入array")

            var pageSize = sortData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            console.log("計算頁數 開始")
            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination -onclick")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        console.log("page 1 pageSize < onePageProducts star")
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 pageSize < onePageProducts end")
                        } else {
                            console.log("page 1 i<=onePageProducts-1 star")
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 i<=onePageProducts-1 end")
                        }

                        var result = products.join("")
                        // $(".Products").html("");
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page end push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page else push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(BRAND/price高到低)
function sortPriceDescBybrand(brand, type) {
    $.ajax({
        url: "http://localhost:8080/search/sort?dataName=brand&queryString=" + brand + "&type=price&sort=desc&brandType=" + type,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (sortData) {
            console.log("getProductsByType() star")
            console.log("getProductQuantity() star")
            getProductQuantity(sortData)
            console.log("getProductQuantity() end")

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function () {
                productsName.push(sortData[y].name)
                productsPrice.push(sortData[y].price)
                productsImg.push(sortData[y].imageLink[0])
                productTotalSold.push(sortData[y].totalSold)
                productsId.push(sortData[y].id);
                y++;
            })
            console.log("取資料塞入array")

            var pageSize = sortData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            console.log("計算頁數 開始")
            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination -onclick")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        console.log("page 1 pageSize < onePageProducts star")
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 pageSize < onePageProducts end")
                        } else {
                            console.log("page 1 i<=onePageProducts-1 star")
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 i<=onePageProducts-1 end")
                        }

                        var result = products.join("")
                        // $(".Products").html("");
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page end push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page else push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(BRAND/price低到高)
function sortPriceAscBybrand(brand, type) {
    $.ajax({
        url: "http://localhost:8080/search/sort?dataName=brand&queryString=" + brand + "&type=price&sort=asc&brandType=" + type,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (sortData) {
            console.log("sortPriceAscBybrand() star")
            console.log("getProductQuantity() star")
            getProductQuantity(sortData)
            console.log("getProductQuantity() end")

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function () {
                productsName.push(sortData[y].name)
                productsPrice.push(sortData[y].price)
                productsImg.push(sortData[y].imageLink[0])
                productTotalSold.push(sortData[y].totalSold)
                productsId.push(sortData[y].id);
                y++;
            })
            console.log("取資料塞入array")

            var pageSize = sortData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            console.log("計算頁數 開始")
            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination -onclick")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        console.log("page 1 pageSize < onePageProducts star")
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 pageSize < onePageProducts end")
                        } else {
                            console.log("page 1 i<=onePageProducts-1 star")
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 i<=onePageProducts-1 end")
                        }

                        var result = products.join("")
                        // $(".Products").html("");
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page end push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page else push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("sortPriceAscBybrand() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(NAME/price高到低)
function sortPriceDescByname(name) {
    $.ajax({
        url: "http://localhost:8080/search/sort?dataName=name&queryString=" + name + "&type=price&sort=desc&brandType=",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (sortData) {
            console.log("getProductsByType() star")
            console.log("getProductQuantity() star")
            getProductQuantity(sortData)
            console.log("getProductQuantity() end")

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function () {
                productsName.push(sortData[y].name)
                productsPrice.push(sortData[y].price)
                productsImg.push(sortData[y].imageLink[0])
                productTotalSold.push(sortData[y].totalSold)
                productsId.push(sortData[y].id);
                y++;
            })
            console.log("取資料塞入array")

            var pageSize = sortData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            console.log("計算頁數 開始")
            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination -onclick")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        console.log("page 1 pageSize < onePageProducts star")
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 pageSize < onePageProducts end")
                        } else {
                            console.log("page 1 i<=onePageProducts-1 star")
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 i<=onePageProducts-1 end")
                        }

                        var result = products.join("")
                        // $(".Products").html("");
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page end push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page else push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(NAME/price低到高)
function sortPriceAscByname(name) {
    $.ajax({
        url: "http://localhost:8080/search/sort?dataName=name&queryString=" + name + "&type=price&sort=asc&brandType=",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (sortData) {
            console.log("getProductsByType() star")
            console.log("getProductQuantity() star")
            getProductQuantity(sortData)
            console.log("getProductQuantity() end")

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function () {
                productsName.push(sortData[y].name)
                productsPrice.push(sortData[y].price)
                productsImg.push(sortData[y].imageLink[0])
                productTotalSold.push(sortData[y].totalSold)
                productsId.push(sortData[y].id);
                y++;
            })
            console.log("取資料塞入array")

            var pageSize = sortData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            console.log("計算頁數 開始")
            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination -onclick")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        console.log("page 1 pageSize < onePageProducts star")
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 pageSize < onePageProducts end")
                        } else {
                            console.log("page 1 i<=onePageProducts-1 star")
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 i<=onePageProducts-1 end")
                        }

                        var result = products.join("")
                        // $(".Products").html("");
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page end push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page else push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(TYPE/totalSold高到低)
function sortSoldDescBytype(type) {
    $.ajax({
        url: "http://localhost:8080/search/sort?dataName=type&queryString=" + type + "&type=totalSold&sort=desc&brandType=",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (sortData) {
            console.log("getProductsByType() star")
            console.log("getProductQuantity() star")
            getProductQuantity(sortData)
            console.log("getProductQuantity() end")

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function () {
                productsName.push(sortData[y].name)
                productsPrice.push(sortData[y].price)
                productsImg.push(sortData[y].imageLink[0])
                productTotalSold.push(sortData[y].totalSold)
                productsId.push(sortData[y].id);
                y++;
            })
            console.log("取資料塞入array")

            var pageSize = sortData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            console.log("計算頁數 開始")
            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination -onclick")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        console.log("page 1 pageSize < onePageProducts star")
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 pageSize < onePageProducts end")
                        } else {
                            console.log("page 1 i<=onePageProducts-1 star")
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 i<=onePageProducts-1 end")
                        }

                        var result = products.join("")
                        // $(".Products").html("");
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page end push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page else push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(TYPE/totalSold低到高)
function sortSoldAscBytype(type) {
    $.ajax({
        url: "http://localhost:8080/search/sort?dataName=type&queryString=" + type + "&type=totalSold&sort=asc&brandType=",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (sortData) {
            console.log("getProductsByType() star")
            console.log("getProductQuantity() star")
            getProductQuantity(sortData)
            console.log("getProductQuantity() end")

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function () {
                productsName.push(sortData[y].name)
                productsPrice.push(sortData[y].price)
                productsImg.push(sortData[y].imageLink[0])
                productTotalSold.push(sortData[y].totalSold)
                productsId.push(sortData[y].id);
                y++;
            })
            console.log("取資料塞入array")

            var pageSize = sortData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            console.log("計算頁數 開始")
            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination -onclick")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        console.log("page 1 pageSize < onePageProducts star")
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 pageSize < onePageProducts end")
                        } else {
                            console.log("page 1 i<=onePageProducts-1 star")
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 i<=onePageProducts-1 end")
                        }

                        var result = products.join("")
                        // $(".Products").html("");
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page end push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page else push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(BRAND/totalSold高到低)
function sortSoldDescBybrand(brand, type) {
    $.ajax({
        url: "http://localhost:8080/search/sort?dataName=brand&queryString=" + brand + "&type=totalSold&sort=desc&brandType=" + type,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (sortData) {
            console.log("getProductsByType() star")
            console.log("getProductQuantity() star")
            getProductQuantity(sortData)
            console.log("getProductQuantity() end")

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function () {
                productsName.push(sortData[y].name)
                productsPrice.push(sortData[y].price)
                productsImg.push(sortData[y].imageLink[0])
                productTotalSold.push(sortData[y].totalSold)
                productsId.push(sortData[y].id);
                y++;
            })
            console.log("取資料塞入array")

            var pageSize = sortData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            console.log("計算頁數 開始")
            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination -onclick")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        console.log("page 1 pageSize < onePageProducts star")
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 pageSize < onePageProducts end")
                        } else {
                            console.log("page 1 i<=onePageProducts-1 star")
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 i<=onePageProducts-1 end")
                        }

                        var result = products.join("")
                        // $(".Products").html("");
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page end push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page else push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(BRAND/totalSold低到高)
function sortSoldAscBybrand(brand, type) {
    $.ajax({
        url: "http://localhost:8080/search/sort?dataName=brand&queryString=" + brand + "&type=totalSold&sort=asc&brandType=" + type,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (sortData) {
            console.log("getProductsByType() star")
            console.log("getProductQuantity() star")
            getProductQuantity(sortData)
            console.log("getProductQuantity() end")

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function () {
                productsName.push(sortData[y].name)
                productsPrice.push(sortData[y].price)
                productsImg.push(sortData[y].imageLink[0])
                productTotalSold.push(sortData[y].totalSold)
                productsId.push(sortData[y].id);
                y++;
            })
            console.log("取資料塞入array")

            var pageSize = sortData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            console.log("計算頁數 開始")
            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination -onclick")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        console.log("page 1 pageSize < onePageProducts star")
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 pageSize < onePageProducts end")
                        } else {
                            console.log("page 1 i<=onePageProducts-1 star")
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 i<=onePageProducts-1 end")
                        }

                        var result = products.join("")
                        // $(".Products").html("");
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page end push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page else push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(NAME/totalSold高到低)
function sortSoldDescByname(name) {
    $.ajax({
        url: "http://localhost:8080/search/sort?dataName=name&queryString=" + name + "&type=totalSold&sort=desc&brandType=",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (sortData) {
            console.log("getProductsByType() star")
            console.log("getProductQuantity() star")
            getProductQuantity(sortData)
            console.log("getProductQuantity() end")

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function () {
                productsName.push(sortData[y].name)
                productsPrice.push(sortData[y].price)
                productsImg.push(sortData[y].imageLink[0])
                productTotalSold.push(sortData[y].totalSold)
                productsId.push(sortData[y].id);
                y++;
            })
            console.log("取資料塞入array")

            var pageSize = sortData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            console.log("計算頁數 開始")
            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination -onclick")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        console.log("page 1 pageSize < onePageProducts star")
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 pageSize < onePageProducts end")
                        } else {
                            console.log("page 1 i<=onePageProducts-1 star")
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 i<=onePageProducts-1 end")
                        }

                        var result = products.join("")
                        // $(".Products").html("");
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page end push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page else push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(NAME/totalSold高到低)
function sortSoldAscByname(name) {
    $.ajax({
        url: "http://localhost:8080/search/sort?dataName=name&queryString=" + name + "&type=totalSold&sort=asc&brandType=",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (sortData) {
            console.log("getProductsByType() star")
            console.log("getProductQuantity() star")
            getProductQuantity(sortData)
            console.log("getProductQuantity() end")

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function () {
                productsName.push(sortData[y].name)
                productsPrice.push(sortData[y].price)
                productsImg.push(sortData[y].imageLink[0])
                productTotalSold.push(sortData[y].totalSold)
                productsId.push(sortData[y].id);
                y++;
            })
            console.log("取資料塞入array")

            var pageSize = sortData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            console.log("計算頁數 開始")
            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination -onclick")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        console.log("page 1 pageSize < onePageProducts star")
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 pageSize < onePageProducts end")
                        } else {
                            console.log("page 1 i<=onePageProducts-1 star")
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                            console.log("page 1 i<=onePageProducts-1 end")
                        }

                        var result = products.join("")
                        // $(".Products").html("");
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page end push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        console.log("page else push end")
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

//以下用前端排序

//商品排序(TYPE/價錢區間price高到低)
function getSortDescByPriceByType(type) {
    var queryString = type;
    console.log("queryString=" + queryString)
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof (minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&maxPrice=" + maxPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => b.price - a.price)

            $.each(priceData, function () {
                productsName.push(priceData[y].name)
                productsPrice.push(priceData[y].price)
                productsImg.push(priceData[y].imageLink[0])
                productTotalSold.push(priceData[y].totalSold)
                productsId.push(priceData[y].id)
                y++;
            })

            var pageSize = priceData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        } else {
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(TYPE/價錢區間price低到高)
function getSortAscByPriceByType(type) {
    var queryString = type;
    console.log("queryString=" + queryString)
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof (minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&maxPrice=" + maxPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => a.price - b.price)

            $.each(priceData, function () {
                productsName.push(priceData[y].name)
                productsPrice.push(priceData[y].price)
                productsImg.push(priceData[y].imageLink[0])
                productTotalSold.push(priceData[y].totalSold)
                productsId.push(priceData[y].id)
                y++;
            })

            var pageSize = priceData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        } else {
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(TYPE/價錢區間Sold高到低)
function geSortDescBySoldByType(type) {
    var queryString = type;
    console.log("queryString=" + queryString)
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof (minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&maxPrice=" + maxPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => b.total - a.total)

            $.each(priceData, function () {
                productsName.push(priceData[y].name)
                productsPrice.push(priceData[y].price)
                productsImg.push(priceData[y].imageLink[0])
                productTotalSold.push(priceData[y].totalSold)
                productsId.push(priceData[y].id)
                y++;
            })

            var pageSize = priceData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        } else {
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(TYPE/價錢區間Sold低到高)
function getSortAscBySoldByType(type) {
    var queryString = type;
    console.log("queryString=" + queryString)
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof (minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&maxPrice=" + maxPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => a.total - b.total)

            $.each(priceData, function () {
                productsName.push(priceData[y].name)
                productsPrice.push(priceData[y].price)
                productsImg.push(priceData[y].imageLink[0])
                productTotalSold.push(priceData[y].totalSold)
                productsId.push(priceData[y].id)
                y++;
            })

            var pageSize = priceData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        } else {
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(brand/價錢區間Sold低到高)
function getSortAscBySoldBybrand(type) {
    var queryString = type;
    console.log("queryString=" + queryString)
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + queryString + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&type=" + type;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof (minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + queryString + "&maxPrice=" + maxPrice + "&type=" + type;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => a.total - b.total)

            $.each(priceData, function () {
                productsName.push(priceData[y].name)
                productsPrice.push(priceData[y].price)
                productsImg.push(priceData[y].imageLink[0])
                productTotalSold.push(priceData[y].totalSold)
                productsId.push(priceData[y].id)
                y++;
            })

            var pageSize = priceData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        } else {
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(brand/價錢區間Sold高到低)
function getSortDescBySoldBybrand(type) {
    var queryString = type;
    console.log("queryString=" + queryString)
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + queryString + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&type=" + type;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof (minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + queryString + "&maxPrice=" + maxPrice + "&type=" + type;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => b.total - a.total)

            $.each(priceData, function () {
                productsName.push(priceData[y].name)
                productsPrice.push(priceData[y].price)
                productsImg.push(priceData[y].imageLink[0])
                productTotalSold.push(priceData[y].totalSold)
                productsId.push(priceData[y].id)
                y++;
            })

            var pageSize = priceData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        } else {
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(brand/價錢區間price低到高)
function getSortAscByPriceByBrandBybrand(type) {
    var queryString = type;
    console.log("queryString=" + queryString)
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + queryString + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&type=" + type;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof (minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + queryString + "&maxPrice=" + maxPrice + "&type=" + type;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => a.price - b.price)

            $.each(priceData, function () {
                productsName.push(priceData[y].name)
                productsPrice.push(priceData[y].price)
                productsImg.push(priceData[y].imageLink[0])
                productTotalSold.push(priceData[y].totalSold)
                productsId.push(priceData[y].id)
                y++;
            })

            var pageSize = priceData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        } else {
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
//商品排序(brand/價錢區間price高到低)
function getSortDescByPriceBybrand(type) {
    var queryString = type;
    console.log("queryString=" + queryString)
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + queryString + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&type=" + type;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof (minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + queryString + "&maxPrice=" + maxPrice + "&type=" + type;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => b.price - a.price)

            $.each(priceData, function () {
                productsName.push(priceData[y].name)
                productsPrice.push(priceData[y].price)
                productsImg.push(priceData[y].imageLink[0])
                productTotalSold.push(priceData[y].totalSold)
                productsId.push(priceData[y].id)
                y++;
            })

            var pageSize = priceData.length;
            var onePageProducts = 40;
            var total = Math.ceil(pageSize / onePageProducts);
            var remain = pageSize % onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
                totalPages: total,
                visiblePages: pageSize > 5 ? 5 : pageSize,
                hideOnlyOnePage: true,
                startPage: 1,
                version: '1.1',
                first: "首頁",
                prev: "上一頁",
                next: "下一頁",
                last: "頁尾",
                onPageClick: function (event, page) {
                    console.log("twbsPagination")
                    //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                    var products = [];
                    if (page == 1) {
                        if (pageSize < onePageProducts) {
                            for (var i = 0; i < pageSize; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        } else {
                            for (var i = 0; i <= onePageProducts - 1; i++) {
                                products.push(
                                    '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                    '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                    '</div>'
                                )
                            }
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else if (page == total) {
                        for (var i = (page - 1) * onePageProducts; i <= (page - 1) * onePageProducts + remain - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    } else {
                        for (var i = (page - 1) * onePageProducts; i <= page * onePageProducts - 1; i++) {
                            products.push(
                                '<div class="productsDiv border"><a href="http://localhost:8080/shop/product.html?' + productsId[i] + '"><div><img src=' + productsImg[i] + ' class="productImg"></div>' +
                                '<span class="name">' + productsName[i] + '</span></a><hr><p class="sold"><span class="price">$' + productsPrice[i] + '</span>/ 已售出' + productTotalSold[i] + '件</p></span>' +
                                '</div>'
                            )
                        }
                        var result = products.join("")
                        console.log("append")
                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByType() end")
                    }
                }
            });
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}