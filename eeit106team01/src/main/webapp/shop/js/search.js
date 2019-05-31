$(document).ready(function () {
    getAllType()
    $("#starDay").datepicker()
    $("#endDay").datepicker()

})

function ByType(type){
    var type = $(type).text()
    var brand = $(brand).text()
    getProductsByType(type)
    getAllBrand(type)
    $("#searchPrice").on('click',(function () {
        console.log(brand)
        if(brand == "")
            getProductsByPriceByType(type);
    }))   
    console.log(type)
}

function ByBrand(brand){
    var brand = $(brand).text()
    getProductsByBrand(brand)
    $("#searchPrice").on('click',(function () {
        getProductsByPriceByBrand(brand)
    })) 
    console.log(brand)  
} 

//顯示此次搜尋的產品數量
function getProductQuantity(data){
    var productQuantity = data.length;
    $("#productQuantity").text("總共有"+productQuantity+"件商品")  
}

function getProductsByType(type){
    $.ajax({
        url: "http://localhost:8080/search/type?type="+type,
        method: "GET",
        dataType: "json",
        cache:false,
        success: function (typeData) {
            console.log("getProductsByType() star")
            console.log("getProductQuantity() star") 
            getProductQuantity(typeData)
            console.log("getProductQuantity() end")

            var productsName =[];
            var productsPrice =[];
            var productsImg =[];
            var productTotalSold = []

            var y=0;
            $.each(typeData,function(){
                productsName.push(typeData[y].name)
                productsPrice.push(typeData[y].price)
                productsImg.push(typeData[y].imageLink[0])
                productTotalSold.push(typeData[y].totalSold)
                y++;
            })
            console.log("取資料塞入array")

            var pageSize = typeData.length; 
            var onePageProducts = 12;
            var total = Math.ceil(pageSize/onePageProducts);
            var remain = pageSize%onePageProducts;

            console.log("計算頁數 開始")
            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
               totalPages:total,
               visiblePages:pageSize>5?5:pageSize,
               hideOnlyOnePage: true,
               startPage: 1,
               version:'1.1',
               first:"首頁",
               prev:"上一頁",
               next:"下一頁",
               last:"頁尾",
               onPageClick: function (event, page) {
                console.log("twbsPagination -onclick")
                //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                   var products=[];
                        if(page==1){
                            console.log("page 1 pageSize < onePageProducts star")
                            if(pageSize < onePageProducts){
                                for(var i=0;i<pageSize;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )
                                }
                                console.log("page 1 pageSize < onePageProducts end")
                            }else{
                                console.log("page 1 i<=onePageProducts-1 star")
                                for(var i=0;i<=onePageProducts-1;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
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
                        }else if(page==total){       
                                for(var i=(page-1)*onePageProducts;i<=(page-1)*onePageProducts+remain-1;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )
                                }
                                console.log("page end push end")
                                var result = products.join("") 
                                console.log("append")
                                $(".Products").empty().append(result)
                                console.log("getProductsByType() end")
                        }else{
                            for(var i=(page-1)*onePageProducts;i<=page*onePageProducts-1;i++){
                                products.push(
                                    '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                    '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
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
        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

// 側邊menu用↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
function getAllBrand(type){
    $.ajax({
        url: "http://localhost:8080/search/data?dataName=brand&type="+type,
        method: "GET",
        dataType: "json",
        cache:false,
        success: function (brandData) {   
            var i=0;    
            var productBrandArray =[];
            $.each(brandData,function(){
                productBrandArray.push('<li><a href="" class="brandLabelA" onclick="ByBrand(this);return false">'+brandData[i].data+'</a></li>')
                    i++
                })   

            $("#brandForm").empty().append(productBrandArray.join(""))
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
        cache:false,
        success: function (typesData) {   
            var i=0;    
            var productTypeArray =[];
            $.each(typesData,function(){
                    productTypeArray.push('<li><a href="" onclick="ByType(this);return false">'+typesData[i].data+'</a></li>')
                    i++
                })   
            $("#typeForm").empty().append(productTypeArray.join(""))
        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
// 側邊menu用↑↑↑↑↑↑↑↑↑↑

function getProductsByPrice(){
    // let NameBrandType = brand;
    var queryString = 1080;
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl ="http://localhost:8080/search/price?byNameBrandType=name&queryString="+queryString+"&minPrice="+minPrice+"&maxPrice="+maxPrice;
    if(minPrice==0 || minPrice==null || isNaN(minPrice) || typeof(minPrice) == "undefined"){
        produstUrl ="http://localhost:8080/search/price?byNameBrandType=name&queryString="+queryString+"&maxPrice="+maxPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache:false,
        success: function (priceData) {
            console.log("getProductsByPrice() star") 
            console.log("getProductQuantity() star") 
            getProductQuantity(priceData)
            console.log("getProductQuantity() end") 
            var productsName =[];
            var productsPrice =[];
            var productsImg =[];
            var productTotalSold = []

            var y=0;
            $.each(priceData,function(){
                productsName.push(priceData[y].name)
                productsPrice.push(priceData[y].price)
                productsImg.push(priceData[y].imageLink[0])
                productTotalSold.push(priceData[y].totalSold)
                y++;
            }) 

            var pageSize = priceData.length; 
            var onePageProducts = 12;
            var total = Math.ceil(pageSize/onePageProducts);
            var remain = pageSize%onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
               totalPages:total,
               visiblePages:pageSize>5?5:pageSize,
               hideOnlyOnePage: true,
               startPage: 1,
               version:'1.1',
               first:"首頁",
               prev:"上一頁",
               next:"下一頁",
               last:"頁尾",
               onPageClick: function (event, page) {
                console.log("twbsPagination")
                //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                   var products=[];
                        if(page==1){
                            if(pageSize < onePageProducts){
                                for(var i=0;i<pageSize;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )
                                }
                            }else{
                                for(var i=0;i<=onePageProducts-1;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )                                  
                                }
                            }
                            var result = products.join("") 
                            console.log("append")
                            $(".Products").empty().append(result)
                            console.log("getProductsByPrice() end") 
                        }else if(page==total){       
                                for(var i=(page-1)*onePageProducts;i<=(page-1)*onePageProducts+remain-1;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )
                                }
                                var result = products.join("") 
                                console.log("append")
                                $(".Products").empty().append(result)
                                console.log("getProductsByPrice() end") 
                        }else{
                            for(var i=(page-1)*onePageProducts;i<=page*onePageProducts-1;i++){
                                products.push(
                                    '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                    '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
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

        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

function getProductsByPriceByType(type){
    var queryString = type;
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl ="http://localhost:8080/search/price?byNameBrandType=type&queryString="+queryString+"&minPrice="+minPrice+"&maxPrice="+maxPrice;
    if(minPrice==0 || minPrice==null || isNaN(minPrice) || typeof(minPrice) == "undefined"){
        produstUrl ="http://localhost:8080/search/price?byNameBrandType=type&queryString="+queryString+"&maxPrice="+maxPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache:false,
        success: function (priceData) {
            console.log("getProductsByPriceByType() star") 
            console.log("getProductQuantity() star") 
            getProductQuantity(priceData)
            console.log("getProductQuantity() end") 
            var productsName =[];
            var productsPrice =[];
            var productsImg =[];
            var productTotalSold = []

            var y=0;
            $.each(priceData,function(){
                productsName.push(priceData[y].name)
                productsPrice.push(priceData[y].price)
                productsImg.push(priceData[y].imageLink[0])
                productTotalSold.push(priceData[y].totalSold)
                y++;
            }) 

            var pageSize = priceData.length; 
            var onePageProducts = 12;
            var total = Math.ceil(pageSize/onePageProducts);
            var remain = pageSize%onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
               totalPages:total,
               visiblePages:pageSize>5?5:pageSize,
               hideOnlyOnePage: true,
               startPage: 1,
               version:'1.1',
               first:"首頁",
               prev:"上一頁",
               next:"下一頁",
               last:"頁尾",
               onPageClick: function (event, page) {
                console.log("twbsPagination")
                //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                   var products=[];
                        if(page==1){
                            if(pageSize < onePageProducts){
                                for(var i=0;i<pageSize;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )
                                }
                            }else{
                                for(var i=0;i<=onePageProducts-1;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )                                  
                                }
                            }
                            var result = products.join("") 
                            console.log("append")
                            $(".Products").empty().append(result)
                            console.log("getProductsByPriceByType() end") 
                        }else if(page==total){       
                                for(var i=(page-1)*onePageProducts;i<=(page-1)*onePageProducts+remain-1;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )
                                }
                                var result = products.join("") 
                                console.log("append")
                                $(".Products").empty().append(result)
                                console.log("getProductsByPriceByType() end") 
                        }else{
                            for(var i=(page-1)*onePageProducts;i<=page*onePageProducts-1;i++){
                                products.push(
                                    '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                    '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
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
        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

function getProductsByPriceByBrand(brand){
    var queryString = brand;
    console.log("getProductsByPriceByBrand"+queryString)
    var minPrice = $("#minPrice").val()
    var maxPrice = $("#maxPrice").val()
    var produstUrl ="http://localhost:8080/search/price?byNameBrandType=brand&queryString="+queryString+"&minPrice="+minPrice+"&maxPrice="+maxPrice;
    if(minPrice==0 || minPrice==null || isNaN(minPrice) || typeof(minPrice) == "undefined"){
        produstUrl ="http://localhost:8080/search/price?byNameBrandType=brand&queryString="+queryString+"&maxPrice="+maxPrice;
    }
    $.ajax({
        url: produstUrl,
        method: "GET",
        dataType: "json",
        cache:false,
        success: function (priceData) {
            console.log("getProductsByPriceByBrand() star") 
            console.log("getProductQuantity() star") 
            getProductQuantity(priceData)
            console.log("getProductQuantity() end") 
            var productsName =[];
            var productsPrice =[];
            var productsImg =[];
            var productTotalSold = []

            var y=0;
            $.each(priceData,function(){
                productsName.push(priceData[y].name)
                productsPrice.push(priceData[y].price)
                productsImg.push(priceData[y].imageLink[0])
                productTotalSold.push(priceData[y].totalSold)
                y++;
            }) 

            var pageSize = priceData.length; 
            var onePageProducts = 12;
            var total = Math.ceil(pageSize/onePageProducts);
            var remain = pageSize%onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
               totalPages:total,
               visiblePages:pageSize>5?5:pageSize,
               hideOnlyOnePage: true,
               startPage: 1,
               version:'1.1',
               first:"首頁",
               prev:"上一頁",
               next:"下一頁",
               last:"頁尾",
               onPageClick: function (event, page) {
                console.log("twbsPagination")
                //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                   var products=[];
                        if(page==1){
                            if(pageSize < onePageProducts){
                                for(var i=0;i<pageSize;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )
                                }
                            }else{
                                for(var i=0;i<=onePageProducts-1;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )                                  
                                }
                            }
                            var result = products.join("") 
                            console.log("append")
                            $(".Products").empty().append(result)
                            console.log("getProductsByPriceByBrand() end") 
                        }else if(page==total){       
                                for(var i=(page-1)*onePageProducts;i<=(page-1)*onePageProducts+remain-1;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )
                                }
                                var result = products.join("") 
                                console.log("append")
                                $(".Products").empty().append(result)
                                console.log("getProductsByPriceByBrand() end") 
                        }else{
                            for(var i=(page-1)*onePageProducts;i<=page*onePageProducts-1;i++){
                                products.push(
                                    '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                    '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
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

        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

function getProductsByBrand(brand){
    
    $.ajax({
        url: "http://localhost:8080/search/brand?brand="+brand,
        method: "GET",
        dataType: "json",
        cache:false,
        success: function (brandData) {
            console.log("getProductsByBrand() star")
            console.log("getProductQuantity() star") 
            getProductQuantity(brandData)
            console.log("getProductQuantity() end")
            
            var productsName =[];
            var productsPrice =[];
            var productsImg =[];
            var productTotalSold = []

            var y=0;
            $.each(brandData,function(){
                productsName.push(brandData[y].name)
                productsPrice.push(brandData[y].price)
                productsImg.push(brandData[y].imageLink[0])
                productTotalSold.push(brandData[y].totalSold)
                y++;
            })

            var pageSize = brandData.length; 
            var onePageProducts = 12;
            var total = Math.ceil(pageSize/onePageProducts);
            var remain = pageSize%onePageProducts;

            $('#PageShowUL').empty().removeData("twbs-pagination").unbind('page').twbsPagination({
               totalPages:total,
               visiblePages:pageSize>5?5:pageSize,
               hideOnlyOnePage: true,
               startPage: 1,
               version:'1.1',
               first:"首頁",
               prev:"上一頁",
               next:"下一頁",
               last:"頁尾",
               onPageClick: function (event, page) {
                console.log("twbsPagination")
                //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數

                   var products=[];
                        if(page==1){
                            if(pageSize < onePageProducts){
                                for(var i=0;i<pageSize;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )                               
                                }
                                console.log("page 1 i<pageSize push end")
                            }else{
                                for(var i=0;i<=onePageProducts-1;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )
                                }
                                console.log("page 1 i<=onePageProducts-1 push end")
                            }
                            var result = products.join("") 
                            console.log("append")
                            $(".Products").empty().append(result)
                            console.log("getProductsByBrand() end")
                        }else if(page==total){       
                                for(var i=(page-1)*onePageProducts;i<=(page-1)*onePageProducts+remain-1;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )
                                }
                                console.log("page end push end")
                                var result = products.join("") 
                                console.log("append")
                                $(".Products").empty().append(result)
                                console.log("getProductsByBrand() end")
                        }else{
                            for(var i=(page-1)*onePageProducts;i<=page*onePageProducts-1;i++){
                                products.push(
                                    '<div class="productsDiv border"><a href=""><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                    '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
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
        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}