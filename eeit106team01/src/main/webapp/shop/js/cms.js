$(document).ready(function () {

    $("#productsInfo").on("click", function () {
        getAllProducts()
    })

})

function getAllProducts() {

    $.ajax({
        url: "http://localhost:8080/products",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (Data) {
            
            $('#table_products').DataTable( {
                "ajax": "data/objects.txt",
                "columns": [
                    { "data": "id" },
                    { "data": "name" },
                    { "data": "brand" },
                    { "data": "type" },
                    { "data": "price" },
                    { "data": "stock" },
                    { "data": "totalSold" },
                    { "data": "updatedTime" },
                    { "data": "createTime" }
                ]
            } );

            var i = 0;
            data=[];
            $.each(Data, function() {
                data.push()

            //     $("#productsTbody").append(
            //         '<tr class="gradeA odd" role="row">'+
            //             '<td class="sorting_1">'+Data[i].id+'</td>'+
            //             '<td>'+Data[i].name+'</td>'+
            //             '<td>'+Data[i].brand+'</td>'+
            //             '<td class="center">'+Data[i].type+'</td>'+
            //             '<td class="center">'+Data[i].price+'</td>'+
            //             '<td>'+Data[i].stock+'</td>'+
            //             '<td>'+Data[i].totalSold+'</td>'+
            //             '<td>'+Data[i].updatedTime+'</td>'+
            //             '<td>'+Data[i].createTime+'</td>'+
            //         '</tr>'
            //     )
                i++
            })

        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })

}