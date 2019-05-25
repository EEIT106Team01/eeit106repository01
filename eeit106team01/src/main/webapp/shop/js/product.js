$(document).ready(function () {
    $("#getNameButton").click(function () {
        getProductsByName();
    })
    $("#getIdButton").click(function () {
        getProduct();
        
    })
    // $(".infoImgClass").mouseenter(function(){
    //     imgChange();
    // })
});

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
                '<div style="float:left;">'+
                productImgArray.join("")+
                imgArray.join("")+
                    
                "</div>"+

                "<div>"+
                    "<table>" +
                        "<th colspan='2'>" + productData.name + "</th>" +
                            "<tr>" +
                                "<td>" + "品牌 :" + "</td>" +
                                "<td>" + productData.brand + "</td>" +
                            "</tr>" +
                            "<tr>" +
                                "<td>" + "價錢 :" + "</td>" +
                                "<td>" + productData.price + "</td>" +
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
                $("#recommendTitle").text("この商品をチェックした人はこんな商品もチェックしています")
                var name = productData.name;
                function getRecommendProducts(){
                    var recommendName =[];
                    var recommendPrice =[];
                    var recommendImg =[];
                    $.ajax({
                        url: "http://localhost:8080/products/recommend?name=" + name,
                        method: "GET",
                        dataType: "json",
                        success: function (recommendData) {
                            $("#recommendResult").append(
                                "<div class='row'>"+
                                    $.each(recommendData,function(){
                                        $("#recommendResult").append(
                                            
                                        )
                                    })+
                                "</div>"
                            )
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