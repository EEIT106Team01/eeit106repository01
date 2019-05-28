$(document).ready(function () {
    $("#searchPrice").click(function () {
        getProductsByPrice();
    });
    getAllType();
    // getAllBrand();
    $("#starDay").datepicker();
    $("#endDay").datepicker();
})


function getProductsByPrice(){
    var price = $("#priceForm").serialize();
    $.ajax({
        url: "http://localhost:8080/search/price?" + price,
        method: "GET",
        dataType: "json",
        success: function (priceData) {

        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

function getAllBrand(){
    
    $.ajax({
        url: "http://localhost:8080/search/brand",
        method: "GET",
        dataType: "json",
        success: function (brandData) {   
            var i=0;    
            var productBrandArray =[];
            $.each(brandData,function(){
                productBrandArray.push('<li><a href="#">'+brandData[i].brand+'</a></li>')
                    i++
                })   
            $("#typeForm").append(productBrandArray.join(""))
        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

function getAllType(){
    
    $.ajax({
        url: "http://localhost:8080/search/type",
        method: "GET",
        dataType: "json",
        success: function (typeData) {   
            var i=0;    
            var productTypeArray =[];
            $.each(typeData,function(){
                    productTypeArray.push('<li><a href="#">'+typeData[i].type+'</a></li>')
                    i++
                })   
            $("#typeForm").append(productTypeArray.join(""))
        },error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}

function getUpdateTime(){
    
}