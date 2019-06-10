$(document).ready(function() {
    getProductsByType(takeTypeUrlValue());
    getAllType();
    getAllBrand(takeTypeUrlValue());
    if (typeof(takeTypeUrlValue()) != "undefined") {
        console.log("getType star")
        ByType();
    }
    userSearch();

    //搜尋
    $("#search").on("click", (function() {
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
            window.location.href = "http://localhost:8080/shop/search.html?productName=" + productName + "&productType=" + productType;
        }
    }))

    //breadcrumb
    var typeUrl = takeTypeUrlValue();
    var typedecodeURI = decodeURIComponent(typeUrl)
    if (typeof(typeUrl) == "undefined") {
        $("#typeBreadcrumb").append(
            '<a>搜尋結果</a>'
        )
    } else {
        $("#typeBreadcrumb").append(
            '<a href="http://localhost:8080/shop/search.html?type=' + typeUrl + '">' + typedecodeURI + '</a>'
        )
    }
    //datepicker format
    $("#startDay").datepicker({ format: 'yyyy-mm-dd' });
    $("#endDay").datepicker({ format: 'yyyy-mm-dd' });

})

//關鍵字新增
function insertKeyWord() {
    var KW = $("#searchName").val();
    var keyWordInput = { keyword: KW };
    $.ajax({
        url: "http://localhost:8080/keyWord/insert",
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

//搜尋
function userSearch() {
    var type = takeSearchUrlValue2()
    var name = takeSearchUrlValue()
    if (type != null) {
        if (type.match("All") != null) {
            search("productType=&productName=" + name)
            console.log("使用者搜尋 type=ALL")
                //sort close
            $("#sortByType").hide();
            $("#sortByBrand").hide();
            $("#sortByNameByType").hide();
            $("#sortByBrandByMixpriceMaxprice").hide();
            $("#sortByTypeByMixpriceMaxprice").hide();
            $("#sortByNameByMixpriceMaxprice").hide();
            $("#sortByNameByTypeByMixpriceMaxprice").hide();
            //sort open
            $("#sortByName").show();
            //search price Btn close
            $("#searchPriceByBrandBtnDiv").hide();
            $("#searchPriceByNameByTypeBtnDiv").hide();
            $("#searchPriceBytypeBtnDiv").hide();
            //search price Btn open
            $("#searchPriceByNameBtnDiv").show();
            //search time Btn close
            $("#searchTimeByBrandBtnDiv").hide();
            $("#searchTimeByTypeBtnDiv").hide();
            $("#searchTimeByNameByTypeBtnDiv").hide();
            //search time Btn open
            $("#searchTimeByNameBtnDiv").show();

            //search time
            $("#searchTimeByNameBtn").on('click', (function() {
                getProductsByUpdateTimeByName(name)
            }))

            //sort功能
            $("#sortPriceDescByName").on('click', (function() {
                var name = takeSearchUrlValue()
                sortPriceDescByname(name)
            }))
            $("#sortPriceAscByName").on('click', (function() {
                var name = takeSearchUrlValue()
                sortPriceAscByname(name)
            }))
            $("#sortSoldAscByName").on('click', (function() {
                var name = takeSearchUrlValue()
                sortSoldAscByname(name)
            }))
            $("#sortSoldDescByName").on('click', (function() {
                    var name = takeSearchUrlValue()
                    sortSoldDescByname(name)
                }))
                //價錢搜索後sort功能
            $("#searchPriceByNameBtn").on('click', (function() {
                //other sort close
                $("#sortByName").hide();
                //sort open
                $("#sortByNameByMixpriceMaxprice").show();

                getProductsByPriceByName(name)
            }))

            $("#sortSoldDescByNameByMixpriceMaxprice").on('click', (function() {
                getSortDescBySoldByName(name)
            }))
            $("#sortSoldAscByNameByMixpriceMaxprice").on('click', (function() {
                getSortAscBySoldByName(name)
            }))
            $("#sortPriceAscByNameByMixpriceMaxprice").on('click', (function() {
                getSortAscByPriceByName(name)
            }))
            $("#sortPriceDescByNameByMixpriceMaxprice").on('click', (function() {
                getSortDescByPriceByName(name)
            }))
        } else {
            console.log("使用者搜尋 type=else")
            var searchResult = takeSearchUrlValue3()
            search(searchResult)
                //sort close
            $("#sortByType").hide();
            $("#sortByBrand").hide();
            $("#sortByName").hide();
            $("#sortByBrandByMixpriceMaxprice").hide();
            $("#sortByTypeByMixpriceMaxprice").hide();
            $("#sortByNameByMixpriceMaxprice").hide();
            $("#sortByNameByTypeByMixpriceMaxprice").hide();
            //sort open
            $("#sortByNameByType").show();
            //search price Btn close
            $("#searchPriceByBrandBtnDiv").hide();
            $("#searchPriceBytypeBtnDiv").hide();
            $("#searchPriceByNameBtnDiv").hide();
            //search price Btn open
            $("#searchPriceByNameByTypeBtnDiv").show();
            //search time Btn close
            $("#searchTimeByBrandBtnDiv").hide();
            $("#searchTimeByTypeBtnDiv").hide();
            $("#searchTimeByNameBtnDiv").hide();
            //search time Btn open
            $("#searchTimeByNameByTypeBtnDiv").show();
            //search time
            $("#searchTimeByNameByTypeBtn").on('click', (function() {
                var name = takeSearchUrlValue()
                var type = takeSearchUrlValue2()
                console.log("type=" + type + "name=" + name)
                getProductsByUpdateTimeByNameByType(type, name)
            }))

            //sort功能
            $("#sortSoldDescByNameByType").on('click', (function() {
                getSortDescBySoldByNameByType(searchResult)
            }))
            $("#sortSoldAscByNameByType").on('click', (function() {
                getSortAscBySoldByNameByType(searchResult)
            }))
            $("#sortPriceAscByNameByType").on('click', (function() {
                getSortAscByPriceByNameByType(searchResult)
            }))
            $("#sortPriceDescByNameByType").on('click', (function() {
                getSortDescByPriceByNameByType(searchResult)
            }))

            $("#searchPriceByNameByTypeBtn").on('click', (function() {
                //sort close
                $("#sortByNameByType").hide();
                //sort open
                $("#sortByNameByTypeByMixpriceMaxprice").show();
                getProductsByPriceByNameByType(name, type);
            }))

            $("#sortSoldDescByNameByTypeByMixpriceMaxprice").on('click', (function() {
                getSortDescBySoldByNameByType(name, type)
            }))
            $("#sortSoldAscByNameByTypeByMixpriceMaxprice").on('click', (function() {
                getSortAscBySoldByNameByType(name, type)
            }))
            $("#sortPriceAscByNameByTypeByMixpriceMaxprice").on('click', (function() {
                getSortAscByPriceByNameByType(name, type)
            }))
            $("#sortPriceDescByNameByTypeByMixpriceMaxprice").on('click', (function() {
                getSortDescByPriceByNameByType(name, type)
            }))
        }
    } else
        console.log("使用者沒搜尋")
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
    var temp2 = temp[0].split("?productName=");
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

    var type = takeTypeUrlValue();
    getProductsByType(type);
    getAllBrand(type);

    //sort close
    $("#sortByBrand").hide();
    $("#sortByName").hide();
    $("#sortByNameByType").hide();
    $("#sortByBrandByMixpriceMaxprice").hide();
    $("#sortByTypeByMixpriceMaxprice").hide();
    $("#sortByNameByMixpriceMaxprice").hide();
    $("#sortByNameByTypeByMixpriceMaxprice").hide();
    //sort open
    $("#sortByType").show();
    //search price Btn close
    $("#searchPriceByBrandBtnDiv").hide();
    $("#searchPriceByNameBtnDiv").hide();
    $("#searchPriceByNameByTypeBtnDiv").hide();
    //search price Btn open
    $("#searchPriceBytypeBtnDiv").show();
    //search time Btn close
    $("#searchTimeByBrandBtnDiv").hide();
    $("#searchTimeByNameBtnDiv").hide();
    $("#searchTimeByNameByTypeBtnDiv").hide();
    //search time Btn open
    $("#searchTimeByTypeBtnDiv").show();

    //search time
    $("#searchTimeByTypeBtn").on('click', (function() {
        var type = takeTypeUrlValue();
        getProductsByUpdateTimeByType(type)
    }))

    //sort功能
    $("#sortSoldDescByType").on('click', (function() {
        sortSoldDescBytype(type)
    }))
    $("#sortSoldAscByType").on('click', (function() {
        sortSoldAscBytype(type)
    }))
    $("#sortPriceAscByType").on('click', (function() {
        sortPriceAscBytype(type)
    }))
    $("#sortPriceDescByType").on('click', (function() {
            sortPriceDescBytype(type)
        }))
        //價錢搜索+
    $("#searchPriceBytypeBtnDiv").on('click', (function() {
            //other sort close
            $("#sortByType").hide();
            //sort open
            $("#sortByTypeByMixpriceMaxprice").show();

            //驗證
            $("#priceForm").validate({
                rules: {
                    minPrice: {
                        digits: true,
                        min: 0
                    },
                    maxPrice: {
                        digits: true,
                        min: 1
                    }
                },
                messages: {
                    minPrice: {
                        digits: "必須為整數",
                        min: "最低於0"
                    },
                    maxPrice: {
                        digits: "必須為整數",
                        min: "最低於1"
                    }
                },
                errorPlacement: function(error, element) {
                    error.appendTo(element.parent().next("label"));
                }
            })
            if ($("#priceForm").valid()) {
                getProductsByPriceByType(type)
            }

        }))
        //sort price
    $("#sortSoldDescByTypeByMixpriceMaxprice").on('click', (function() {
        geSortDescBySoldByType(type)
    }))
    $("#sortSoldAscByTypeByMixpriceMaxprice").on('click', (function() {
        getSortAscBySoldByType(type)
    }))
    $("#sortPriceAscByTypeByMixpriceMaxprice").on('click', (function() {
        getSortAscByPriceByType(type)
    }))
    $("#sortPriceDescByTypeByMixpriceMaxprice").on('click', (function() {
        getSortDescByPriceByType(type)
    }))
}

function ByBrand(brand) {
    //sort close
    $("#sortByType").hide();
    $("#sortByName").hide();
    $("#sortByNameByType").hide();
    $("#sortByBrandByMixpriceMaxprice").hide();
    $("#sortByTypeByMixpriceMaxprice").hide();
    $("#sortByNameByMixpriceMaxprice").hide();
    $("#sortByNameByTypeByMixpriceMaxprice").hide();
    //sort open
    $("#sortByBrand").show();

    //search price Btn close
    $("#searchPriceBytypeBtnDiv").hide();
    $("#searchPriceByNameBtnDiv").hide();
    $("#searchPriceByNameByTypeBtnDiv").hide();
    //search price Btn open
    $("#searchPriceByBrandBtnDiv").show();
    //search time Btn close
    $("#searchTimeByTypeBtnDiv").hide();
    $("#searchTimeByNameBtnDiv").hide();
    $("#searchTimeByNameByTypeBtnDiv").hide();
    //search time Btn open
    $("#searchTimeByBrandBtnDiv").show();

    var type = takeTypeUrlValue();
    var brand = $(brand).text();
    getProductsByBrand(brand, type);

    //sort brand
    $("#sortSoldDescByBrand").on('click', (function() {
        var type = takeTypeUrlValue();
        sortSoldDescBybrand(brand, type)
    }))
    $("#sortSoldAscByBrand").on('click', (function() {
        var type = takeTypeUrlValue();
        sortSoldAscBybrand(brand, type)
    }))
    $("#sortPriceAscByBrand").on('click', (function() {
        var type = takeTypeUrlValue();
        sortPriceAscBybrand(brand, type)
    }))
    $("#sortPriceDescByBrand").on('click', (function() {
        var type = takeTypeUrlValue();
        sortPriceDescBybrand(brand, type)
    }))
    $("#sortDefultByBrand").on('click', (function() {
            getProductsByBrand(brand)
        }))
        //updateTime
    $("#searchTimeByBrandBtn").on('click', (function() {
            var type = takeTypeUrlValue();

            //驗證
            $("#searchTimeForm").validate({
                rules: {
                    startDay: {
                        required: true,
                        dateISO: true
                    },
                    endDay: {
                        required: true,
                        dateISO: true
                    }
                },
                messages: {
                    startDay: {
                        required: "必須輸入",
                        dateISO: "格式為yyyy-MM-dd"
                    },
                    endDay: {
                        required: "必須輸入",
                        dateISO: "格式為yyyy-MM-dd"
                    }
                },
                errorPlacement: function(error, element) {
                    error.appendTo(element.parent().next("label"));
                }
            })
            if ($("#searchTimeForm").valid()) {
                getProductsByUpdateTimeByBrand(type, brand)
            }


        }))
        //搜價錢btn by brand
    $("#searchPriceByBrandBtn").on('click', (function() {
        var type = takeTypeUrlValue();
        $("#sortByBrand").hide();
        $("#sortByBrandByMixpriceMaxprice").show();

        //驗證
        $("#priceForm").validate({
            rules: {
                minPrice: {
                    digits: true,
                    min: 0
                },
                maxPrice: {
                    digits: true,
                    min: 1
                }
            },
            messages: {
                minPrice: {
                    digits: "必須為整數",
                    min: "最低於0"
                },
                maxPrice: {
                    digits: "必須為整數",
                    min: "最低於1"
                }
            },
            errorPlacement: function(error, element) {
                error.appendTo(element.parent().next("label"));
            }
        })
        if ($("#priceForm").valid()) {
            getProductsByPriceByBrand(brand, type);
        }

    }))

    //sort brand price between mix max
    $("#sortSoldDescByBrandByMixpriceMaxprice").on('click', (function() {
        var type = takeTypeUrlValue();
        getSortDescBySoldBybrand(brand, type)
    }))
    $("#sortSoldAscByBrandByMixpriceMaxprice").on('click', (function() {
        var type = takeTypeUrlValue();
        getSortAscBySoldBybrand(brand, type)
    }))
    $("#sortPriceAscByBrandByMixpriceMaxprice").on('click', (function() {
        var type = takeTypeUrlValue();
        getSortAscByPriceByBrandBybrand(brand, type)
    }))
    $("#sortPriceDescByBrandByMixpriceMaxprice").on('click', (function() {
        var type = takeTypeUrlValue();
        getSortDescByPriceBybrand(brand, type)
    }))
}
//取得該種類全部品牌
function getAllBrand(type) {
    $.ajax({
        url: "http://localhost:8080/search/data?dataName=brand&type=" + type,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(brandData) {
            var i = 0;
            var productBrandArray = [];
            $.each(brandData, function() {
                productBrandArray.push('<li><a href="" class="brandLabelA" onclick="ByBrand(this);return false">' + brandData[i].data + '</a></li>')
                i++
            })

            $("#brandForm").empty().append(productBrandArray.join(""))
        },
        error: function(jqXHR, textStatus, errorThrown) {
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
        success: function(typesData) {
            var i = 0;
            var productTypeArray = [];
            $.each(typesData, function() {
                productTypeArray.push('<li><a href="http://localhost:8080/shop/search.html?type=' + typesData[i].data + '" onclick="ByType(this)">' + typesData[i].data + '</a></li>')
                i++
            })
            $("#typeForm").empty().append(productTypeArray.join(""))

            var y = 0;
            productTypeArray2 = [];
            $.each(typesData, function() {
                productTypeArray2.push("<option>" + typesData[y].data + "</option>")
                y++
            })
            $("#searchType").empty().append("<option>All</option>" + productTypeArray2.join(""))

        },
        error: function(jqXHR, textStatus, errorThrown) {
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
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&maxPrice=" + maxPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            console.log("getProductsByPrice() star")

            getProductQuantity(priceData)

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;
            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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

                        $(".Products").empty().append(result)
                        console.log("getProductsByPrice() end")
                    }
                }
            });

        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        console.log("只輸入最高價格")
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&maxPrice=" + maxPrice;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        console.log("只輸入最低價格")
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&minPrice=" + minPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;
            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
        success: function(typeData) {


            getProductQuantity(typeData)


            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(typeData, function() {
                productsName.push(typeData[y].name)
                productsPrice.push(typeData[y].price)
                productsImg.push(typeData[y].imageLink[0])
                productTotalSold.push(typeData[y].totalSold)
                productsId.push(typeData[y].id);
                y++;
            })


            var pageSize = typeData.length;
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
                onPageClick: function(event, page) {
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
                            // $(".Products").html("");

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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        console.log("只輸入最高價格")
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + queryString + "&maxPrice=" + maxPrice + "&type=" + type;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        console.log("只輸入最低價格")
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + queryString + "&minPrice=" + minPrice + "&type=" + type;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            console.log("getProductsByPriceByBrand() star")

            getProductQuantity(priceData)

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];
            var y = 0;
            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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

                        $(".Products").empty().append(result)
                        console.log("getProductsByPriceByBrand() end")
                    }
                }
            });

        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
        success: function(brandData) {
            console.log("getProductsByBrand() star")

            getProductQuantity(brandData)


            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;
            $.each(brandData, function() {
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
                onPageClick: function(event, page) {
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

                        var result = products.join("")

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

                        var result = products.join("")

                        $(".Products").empty().append(result)
                        console.log("getProductsByBrand() end")
                    }
                }
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
        success: function(searchData) {

            getProductQuantity(searchData)


            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;
            $.each(searchData, function() {
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
                onPageClick: function(event, page) {
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

                        var result = products.join("")

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

                        var result = products.join("")

                        $(".Products").empty().append(result)
                        console.log("getProductsByBrand() end")
                    }
                }
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }

        }
    })
}
//依Name搜尋and價錢區間搜尋
function getProductsByPriceByName(name) {
    var queryString = name;
    console.log("queryString=" + queryString)
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&maxPrice=" + maxPrice;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&minPrice=" + minPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;
            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//依Name&Type搜尋and價錢區間搜尋
function getProductsByPriceByNameByType(name, type) {
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + name + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&type=" + type;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + name + "&maxPrice=" + maxPrice + "&type=" + type;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&minPrice=" + minPrice + "&type=" + type;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
        success: function(sortData) {

            getProductQuantity(sortData)

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
        success: function(sortData) {


            getProductQuantity(sortData)


            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function() {
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
                onPageClick: function(event, page) {
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
                            // $(".Products").html("");

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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
        success: function(sortData) {


            getProductQuantity(sortData)


            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function() {
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
                onPageClick: function(event, page) {
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
                            // $(".Products").html("");

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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
        success: function(sortData) {
            console.log("sortPriceAscBybrand() star")

            getProductQuantity(sortData)


            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function() {
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
                onPageClick: function(event, page) {
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
                            // $(".Products").html("");

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
                        console.log("sortPriceAscBybrand() end")
                    }
                }
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
        success: function(sortData) {


            getProductQuantity(sortData)


            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function() {
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
                onPageClick: function(event, page) {
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
                            // $(".Products").html("");

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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
        success: function(sortData) {

            getProductQuantity(sortData)

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function() {
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
                onPageClick: function(event, page) {
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
                            // $(".Products").html("");

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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
        success: function(sortData) {

            getProductQuantity(sortData)

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function() {
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
                onPageClick: function(event, page) {
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
                            // $(".Products").html("");

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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
        success: function(sortData) {


            getProductQuantity(sortData)


            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function() {
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
                onPageClick: function(event, page) {
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
                            // $(".Products").html("");
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
        success: function(sortData) {
            getProductQuantity(sortData)

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function() {
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
                onPageClick: function(event, page) {
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
                            // $(".Products").html("");

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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
        success: function(sortData) {
            getProductQuantity(sortData)

            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function() {
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
                onPageClick: function(event, page) {
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
                            // $(".Products").html("");
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
        success: function(sortData) {
            getProductQuantity(sortData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function() {
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
                onPageClick: function(event, page) {
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
                            // $(".Products").html("");
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
        success: function(sortData) {
            getProductQuantity(sortData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = [];
            var productsId = [];

            var y = 0;
            $.each(sortData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&maxPrice=" + maxPrice;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&minPrice=" + minPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => b.price - a.price)
            console.log("priceDate==========" + priceData)
            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&maxPrice=" + maxPrice;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&minPrice=" + minPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => a.price - b.price)

            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&maxPrice=" + maxPrice;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&minPrice=" + minPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => b.totalSold - a.totalSold)

            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
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
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&maxPrice=" + maxPrice;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=type&queryString=" + queryString + "&minPrice=" + minPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => a.totalSold - b.totalSold)

            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//商品排序(brand/價錢區間Sold低到高)
function getSortAscBySoldBybrand(brand, type) {
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + brand + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&type=" + type;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + brand + "&maxPrice=" + maxPrice + "&type=" + type;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + brand + "&minPrice=" + minPrice + "&type=" + type;
    }
    console.log(produstUrl)
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => a.totalSold - b.totalSold)

            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//商品排序(brand/價錢區間Sold高到低)
function getSortDescBySoldBybrand(brand, type) {
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + brand + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&type=" + type;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + brand + "&maxPrice=" + maxPrice + "&type=" + type;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + brand + "&minPrice=" + minPrice + "&type=" + type;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => b.totalSold - a.totalSold)

            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//商品排序(brand/價錢區間price低到高)
function getSortAscByPriceByBrandBybrand(brand, type) {
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + brand + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&type=" + type;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + brand + "&maxPrice=" + maxPrice + "&type=" + type;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + brand + "&minPrice=" + minPrice + "&type=" + type;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => a.price - b.price)

            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//商品排序(brand/價錢區間price高到低)
function getSortDescByPriceBybrand(brand, type) {
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + brand + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&type=" + type;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + brand + "&maxPrice=" + maxPrice + "&type=" + type;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=brand&queryString=" + brand + "&minPrice=" + minPrice + "&type=" + type;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => b.price - a.price)

            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//商品排序(name/價錢區間price高到低)
function getSortDescByPriceByName(name) {
    var queryString = name;
    console.log("queryString=" + queryString)
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&maxPrice=" + maxPrice;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&minPrice=" + minPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => b.price - a.price)
            console.log("priceDate==========" + priceData)
            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//商品排序(name/價錢區間price低到高)
function getSortAscByPriceByName(name) {
    var queryString = name;
    console.log("queryString=" + queryString)
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&maxPrice=" + maxPrice;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&minPrice=" + minPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => a.price - b.price)
            console.log("priceDate==========" + priceData)
            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//商品排序(name/價錢區間sold高到低)
function getSortDescBySoldByName(name) {
    var queryString = name;
    console.log("queryString=" + queryString)
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&maxPrice=" + maxPrice;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&minPrice=" + minPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => b.totalSold - a.totalSold)
            console.log("priceDate==========" + priceData)
            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//商品排序(name/價錢區間sold低到高)
function getSortAscBySoldByName(name) {
    var queryString = name;
    console.log("queryString=" + queryString)
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&maxPrice=" + maxPrice;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + queryString + "&minPrice=" + minPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => a.totalSold - b.totalSold)
            console.log("priceDate==========" + priceData)
            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//商品排序(name&type/pirce高到低)
function getSortDescByPriceByNameByType(searchResult) {
    var searchUrl = "http://localhost:8080/search/TypeName?" + searchResult;
    $.ajax({
        url: searchUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(searchData) {
            getProductQuantity(searchData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;
            $.each(searchData, function() {
                productsName.push(searchData[y].name)
                productsPrice.push(searchData[y].price)
                productsImg.push(searchData[y].imageLink[0])
                productTotalSold.push(searchData[y].totalSold)
                productsId.push(searchData[y].id)
                y++;
            })
            searchData.sort((a, b) => b.price - a.price)

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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//商品排序(name&type/pirce低到高)
function getSortAscByPriceByNameByType(searchResult) {
    var searchUrl = "http://localhost:8080/search/TypeName?" + searchResult;
    $.ajax({
        url: searchUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(searchData) {

            getProductQuantity(searchData)


            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;
            $.each(searchData, function() {
                productsName.push(searchData[y].name)
                productsPrice.push(searchData[y].price)
                productsImg.push(searchData[y].imageLink[0])
                productTotalSold.push(searchData[y].totalSold)
                productsId.push(searchData[y].id)
                y++;
            })
            searchData.sort((a, b) => a.price - b.price)

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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//商品排序(name&type/sold低到高)
function getSortAscBySoldByNameByType(searchResult) {
    var searchUrl = "http://localhost:8080/search/TypeName?" + searchResult;
    $.ajax({
        url: searchUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(searchData) {

            getProductQuantity(searchData)


            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;
            $.each(searchData, function() {
                productsName.push(searchData[y].name)
                productsPrice.push(searchData[y].price)
                productsImg.push(searchData[y].imageLink[0])
                productTotalSold.push(searchData[y].totalSold)
                productsId.push(searchData[y].id)
                y++;
            })
            searchData.sort((a, b) => a.totalSold - b.totalSold)

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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//商品排序(name&type/sold高到低)
function getSortDescBySoldByNameByType(searchResult) {
    var searchUrl = "http://localhost:8080/search/TypeName?" + searchResult;
    $.ajax({
        url: searchUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(searchData) {

            getProductQuantity(searchData)


            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;
            $.each(searchData, function() {
                productsName.push(searchData[y].name)
                productsPrice.push(searchData[y].price)
                productsImg.push(searchData[y].imageLink[0])
                productTotalSold.push(searchData[y].totalSold)
                productsId.push(searchData[y].id)
                y++;
            })
            searchData.sort((a, b) => b.totalSold - a.totalSold)

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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//商品排序(name&type/價錢區間price低到高)
function getSortAscByPriceByNameByType(name, type) {
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + name + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&type=" + type;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + name + "&maxPrice=" + maxPrice + "&type=" + type;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + name + "&minPrice=" + minPrice + "&type=" + type;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => a.price - b.price)

            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//商品排序(name&type/價錢區間price高到低)
function getSortDescByPriceByNameByType(name, type) {
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + name + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&type=" + type;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + name + "&maxPrice=" + maxPrice + "&type=" + type;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + name + "&minPrice=" + minPrice + "&type=" + type;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => b.price - a.price)

            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//商品排序(name&type/價錢區間sold高到低)
function getSortDescBySoldByNameByType(name, type) {
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + name + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&type=" + type;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + name + "&maxPrice=" + maxPrice + "&type=" + type;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + name + "&minPrice=" + minPrice + "&type=" + type;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => b.totalSold - a.totalSold)

            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//商品排序(name&type/價錢區間sold低到高)
function getSortAscBySoldByNameByType(name, type) {
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + name + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&type=" + type;
    if (minPrice == 0 || minPrice == null || isNaN(minPrice) || typeof(minPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + name + "&maxPrice=" + maxPrice + "&type=" + type;
    } else if (maxPrice == 0 || maxPrice == null || isNaN(maxPrice) || typeof(maxPrice) == "undefined") {
        produstUrl = "http://localhost:8080/search/price?byNameBrandType=name&queryString=" + name + "&minPrice=" + minPrice + "&type=" + type;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(priceData) {
            getProductQuantity(priceData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            priceData.sort((a, b) => a.totalSold - b.totalSold)

            $.each(priceData, function() {
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}


//類型updatetTime查詢
function getProductsByUpdateTimeByType(type) {
    var startDay = $("#startDay").val()
    var endDay = $("#endDay").val()

    $.ajax({
        url: "http://localhost:8080/search/updatedTime?dataName=type&queryString=" + type + "&startDay=" + startDay + "&endDay=" + endDay,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(timeData) {
            getProductQuantity(timeData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            $.each(timeData, function() {
                productsName.push(timeData[y].name)
                productsPrice.push(timeData[y].price)
                productsImg.push(timeData[y].imageLink[0])
                productTotalSold.push(timeData[y].totalSold)
                productsId.push(timeData[y].id)
                y++;
            })

            var pageSize = timeData.length;
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//品牌updatetTime查詢
function getProductsByUpdateTimeByBrand(type, brand) {
    var startDay = $("#startDay").val()
    var endDay = $("#endDay").val()

    console.log(startDay + endDay)
    $.ajax({
        url: "http://localhost:8080/search/updatedTime?dataName=brand&queryString=" + brand + "&startDay=" + startDay + "&endDay=" + endDay + "&brandType=" + type,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(timeData) {
            getProductQuantity(timeData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            $.each(timeData, function() {
                productsName.push(timeData[y].name)
                productsPrice.push(timeData[y].price)
                productsImg.push(timeData[y].imageLink[0])
                productTotalSold.push(timeData[y].totalSold)
                productsId.push(timeData[y].id)
                y++;
            })

            var pageSize = timeData.length;
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//Name updatetTime查詢
function getProductsByUpdateTimeByName(name) {
    var startDay = $("#startDay").val()
    var endDay = $("#endDay").val()

    $.ajax({
        url: "http://localhost:8080/search/updatedTime?dataName=name&queryString=" + name + "&startDay=" + startDay + "&endDay=" + endDay,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(timeData) {
            getProductQuantity(timeData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            $.each(timeData, function() {
                productsName.push(timeData[y].name)
                productsPrice.push(timeData[y].price)
                productsImg.push(timeData[y].imageLink[0])
                productTotalSold.push(timeData[y].totalSold)
                productsId.push(timeData[y].id)
                y++;
            })

            var pageSize = timeData.length;
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}
//Name Type updatetTime查詢
function getProductsByUpdateTimeByNameByType(type, name) {
    var startDay = $("#startDay").val()
    var endDay = $("#endDay").val()
    console.log("star =" + startDay + " end=" + endDay)
    $.ajax({
        url: "http://localhost:8080/search/updatedTime?dataName=name&queryString=" + name + "&startDay=" + startDay + "&endDay=" + endDay + "&brandType=" + type,
        method: "GET",
        dataType: "json",
        cache: false,
        success: function(timeData) {
            getProductQuantity(timeData)
            var productsName = [];
            var productsPrice = [];
            var productsImg = [];
            var productTotalSold = []
            var productsId = [];
            var y = 0;

            $.each(timeData, function() {
                productsName.push(timeData[y].name)
                productsPrice.push(timeData[y].price)
                productsImg.push(timeData[y].imageLink[0])
                productTotalSold.push(timeData[y].totalSold)
                productsId.push(timeData[y].id)
                y++;
            })

            var pageSize = timeData.length;
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
                onPageClick: function(event, page) {
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
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
            if (jqXHR.status == 404 || errorThrown == 'Not Found') {
                console.log('There was a 404 error.');
                $(".main-content").empty().append(
                    '<div class="page-error-404">' +
                    '<div class="error-symbol">' +
                    '<i class="entypo-attention"></i>' +
                    '</div>' +
                    '<div class="error-text">' +
                    '<h2>404</h2>' +
                    '<p>沒有符合條件的商品!</p>' +
                    '</div>'
                )
            }
        }
    })
}