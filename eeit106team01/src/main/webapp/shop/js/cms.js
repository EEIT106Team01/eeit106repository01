$(document).ready(function () {

    getAllProducts()

    //dataTable---------star
    var $table2 = jQuery("#table-2");
    // Initialize DataTable
    $table2.DataTable({
        "sDom": "tip",
        "bStateSave": false,
        "iDisplayLength": 8,
        "aoColumns": [
            { "bSortable": false },
            null,
            null,
            null,
            null
        ],
        "bStateSave": true
    });

    // Highlighted rows
    $table2.find("tbody input[type=checkbox]").each(function (i, el) {
        var $this = $(el),
            $p = $this.closest('tr');

        $(el).on('change', function () {
            var is_checked = $this.is(':checked');

            $p[is_checked ? 'addClass' : 'removeClass']('highlight');
        });
    });

    // Replace Checboxes
    $table2.find(".pagination a").click(function (ev) {
        replaceCheckboxes();
    });

    // Sample Function to add new row
    var giCount = 1;

    function fnClickAddRow() {
        jQuery('#table-2').dataTable().fnAddData(['<div class="checkbox checkbox-replace"><input type="checkbox" /></div>', giCount + ".1", giCount + ".2", giCount + ".3", giCount + ".4"]);
        replaceCheckboxes(); // because there is checkbox, replace it
        giCount++;
    }
    //dataTable---------end
})

function getAllProducts() {

    $.ajax({
        url: "/products",
        method: "GET",
        dataType: "json",
        cache: false,
        success: function (Data) {

            // var i = 0;
            // data = [];
            // $(".datatable thead").append(
            //     '<tr>' +
            //     '<th>ID</th>' +
            //     '<th>Name</th>' +
            //     '<th>Brnad</th>' +
            //     '<th>Type</th>' +
            //     '<th>Price</th>' +
            //     // '<th>Stock</th>' +
            //     // '<th>TotalSold</th>' +
            //     // '<th>UpdateTime</th>' +
            //     // '<th>CreateTime</th>' +
            //     '</tr>'
            // )
            Data.forEach(element => {
                $(".datatable tbody").append(
                    '<tr class="gradeA odd" role="row">' +
                    '<td>' + element.id + '</td>' +
                    '<td>' + element.name + '</td>' +
                    '<td>' + element.brand + '</td>' +
                    '<td>' + element.type + '</td>' +
                    '<td>' + element.price + '</td>' +
                    // '<td>' + Data[i].stock + '</td>' +
                    // '<td>' + Data[i].totalSold + '</td>' +
                    // '<td>' + Data[i].updatedTime + '</td>' +
                    // '<td>' + Data[i].createTime + '</td>' +
                    '</tr>'
                )
            });

        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })

}