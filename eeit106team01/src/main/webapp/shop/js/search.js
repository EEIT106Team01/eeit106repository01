$(document).ready(function () {


    $("#searchPrice").click(function () {
        getProductsByPrice();
    }),

    getAllType(),
    getAllBrand(),
    $("#starDay").datepicker(),
    $("#endDay").datepicker(),

    getProductsByType();
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
            var productTotalSold = []

            var y=0;
            $.each(typeData,function(){
                productsName.push(typeData[y].name)
                productsPrice.push(typeData[y].price)
                productsImg.push(typeData[y].imageLink[0])
                productTotalSold.push(typeData[y].totalSold)
                y++;
            })

            var pageSize = typeData.length; 
            var onePageProducts = 12;
            var total = Math.ceil(pageSize/onePageProducts);
            var remain = pageSize%onePageProducts;

            $('#PageShowUL').twbsPagination({
               totalPages:total,
               visiblePages:pageSize>5?5:pageSize,
               hideOnlyOnePage: true,
               startPage: 1,
               version:'1.1',
               first:"首頁",
               prev:"上一頁",
               next:"一頁",
               last:"頁尾",
               onPageClick: function (event, page) {

                //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                   var products=[];
                        if(page==1){
                            if(pageSize < onePageProducts){
                                for(var i=0;i<pageSize;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href="#"><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )
                                }
                            }
                            for(var i=0;i<=onePageProducts-1;i++){
                                products.push(
                                    '<div class="productsDiv border"><a href="#"><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                    '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                    '</div>'
                                )
                            }
                            var result = products.join("") 
                            $(".Products").html("");
                            $(".Products").append(result)
                        }else if(page==total){       
                                for(var i=(page-1)*onePageProducts;i<=(page-1)*onePageProducts+remain-1;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href="#"><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )
                                }
                                var result = products.join("") 
                                $(".Products").html("");
                                $(".Products").append(result)
                        }else{
                            for(var i=(page-1)*onePageProducts;i<=page*onePageProducts-1;i++){
                                products.push(
                                    '<div class="productsDiv border"><a href="#"><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                    '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                    '</div>'
                                )
                            }
                            var result = products.join("") 
                            $(".Products").html("");
                            $(".Products").append(result)
                        }
                    }
                });		   
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
            console.log(pageSize)
            console.log(total)
            console.log(remain)

            $('#PageShowUL').twbsPagination({
               totalPages:total,
               visiblePages:pageSize>5?5:pageSize,
               hideOnlyOnePage: true,
               startPage: 1,
               version:'1.1',
               first:"首頁",
               prev:"上一頁",
               next:"一頁",
               last:"頁尾",
               onPageClick: function (event, page) {

                //    $('#PageCon').text('第' + page+'頁'); 顯示目前頁數


                   var products=[];
                        if(page==1){
                            if(pageSize < onePageProducts){
                                for(var i=0;i<pageSize;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href="#"><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )
                                }
                                console.log("page==1")
                            }
                            for(var i=0;i<=onePageProducts-1;i++){
                                products.push(
                                    '<div class="productsDiv border"><a href="#"><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                    '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                    '</div>'
                                )
                                
                            }
                            console.log("for(var i=0;i<=onePageProducts-1;i++)")
                            var result = products.join("") 
                            $(".Products").html("");
                            console.log("html");
                            $(".Products").append(result)
                            console.log("append");
                        }else if(page==total){       
                                for(var i=(page-1)*onePageProducts;i<=(page-1)*onePageProducts+remain-1;i++){
                                    products.push(
                                        '<div class="productsDiv border"><a href="#"><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                        '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                        '</div>'
                                    )
                                }
                                console.log("page==total")
                                var result = products.join("") 
                                $(".Products").html("");
                                console.log("html");
                                $(".Products").append(result)
                                console.log("append");
                        }else{
                            for(var i=(page-1)*onePageProducts;i<=page*onePageProducts-1;i++){
                                products.push(
                                    '<div class="productsDiv border"><a href="#"><div><img src='+productsImg[i]+' class="productImg"></div>'+
                                    '<span class="name">'+productsName[i]+'</span></a><hr><p class="sold"><span class="price">$'+productsPrice[i]+'</span>/ 已售出'+productTotalSold[i]+'件</p></span>'+
                                    '</div>'
                                )
                                
                            }
                            console.log("else")
                            var result = products.join("") 
                            $(".Products").html("");
                            console.log("html");
                            $(".Products").append(result)
                            console.log("append");
                        }
                    }
                });		   

        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}
