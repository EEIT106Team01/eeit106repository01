$(document).ready(function() {
    $("#productsInfo").on("click", (function() {
        $("#div_right2").empty();
        $("#div_right").empty().append(
            `<table class="table table-bordered table-striped datatable" id="table-2">
                <thead>
                    <tr>
                        <th>Img</th>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Brnad</th>
                        <th>Type</th>
                        <th>Price</th>
                        <th>Stock</th>
                        <th>TotalSold</th>
                        <th>UpdateTime</th>
                        <th>CreateTime</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>`)
        getAllProducts()
    }))

    $("#productInsert").on("click", (function() {
        $("#div_right2").empty();
        $("#div_right").empty().append(
            `<div class="modal fade" id="modal-7">
                <div class="modal-dialog">
                    <div class="modal-content">
                        
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Dynamic Content</h4>
                        </div>
                        
                        <div class="modal-body">
                            
                        </div>
                        
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-info">Save changes</button>
                        </div>
                    </div>
                </div>
            </div>` +

            `<div class="panel-body">

                <form role="form" id="form1" class="validate">
                    <div class="form-group">
                        <label class="control-label">產品名稱</label>

                        <input type="text" class="form-control" name="name" id="name" data-validate="required" data-message-required="This is custom message for required field." placeholder="產品名稱" />
                    </div>

                    <div class="form-group">
                        <label class="control-label">品牌</label>

                        <input type="text" class="form-control" name="brand" id="brand" data-validate="required" placeholder="品牌" />
                    </div>

                    <div class="form-group">
                        <label class="control-label">價錢</label>

                        <input type="text" class="form-control" name="price" id="price" data-validate="required" placeholder="價錢" />
                    </div>

                    <div class="form-group">
                        <label class="control-label">類型</label>

                        <input type="text" class="form-control" name="type" id="type" data-validate="required" placeholder="類型" />
                    </div>

                    <div class="form-group">
                        <label class="control-label">庫存</label>

                        <input type="text" class="form-control" name="stock" id="stock" data-validate="required" placeholder="庫存" />
                    </div>

                    <div class="form-group">
                        <label class="control-label">產品圖片</label>

                        <input type="text" class="form-control" name="imageLink" id="imageLink" data-validate="required" placeholder="產品圖片" />
                    </div>

                    <div class="form-group">
                        <label class="control-label">詳細資訊</label>

                        <input type="text" class="form-control" name="information" id="information" data-validate="required" placeholder="詳細資訊" />
                    </div>

                    <div class="form-group">
                        <label class="control-label">詳細圖片</label>

                        <input type="text" class="form-control" name="informationImageLink" id="informationImageLink" data-validate="required" placeholder="詳細圖片" />
                    </div>

                    <div class="form-group">
                        <button type="button" class="btn btn-success">新增</button>
                        <button type="reset" class="btn">Reset</button>
                    </div>
                </form>
            </div>`
        )
    }));

    $("#productUpdate").on("click", (function() {
        $("#div_right2").empty();
        $("#div_right").empty().append(
            `<div class="panel-body" id="div_searchID">
                    <form role="form" id="form1" class="validate">
                        <div class="form-group">
                            <label class="control-label">產品ID</label>

                            <input type="text" class="form-control" name="id" id="id" data-validate="required" data-message-required="This is custom message for required field." placeholder="產品ID" />
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-success" id="btn_searchID">查詢</button>
                            <button type="reset" class="btn">Reset</button>
                        </div>
                    </form>
                </div>`
        )
        $("#btn_searchID").on("click", (function() {
            $("#div_right2").empty().append(
                `<div class="panel-body">
    
                    <form role="form" id="form1" class="validate">
                        <div class="form-group">
                            <label class="control-label">產品名稱</label>
    
                            <input type="text" class="form-control" name="name" id="name" data-validate="required" data-message-required="This is custom message for required field." placeholder="產品名稱" />
                        </div>
    
                        <div class="form-group">
                            <label class="control-label">品牌</label>
    
                            <input type="text" class="form-control" name="brand" id="brand" data-validate="required" placeholder="品牌" />
                        </div>
    
                        <div class="form-group">
                            <label class="control-label">價錢</label>
    
                            <input type="text" class="form-control" name="price" id="price" data-validate="required" placeholder="價錢" />
                        </div>
    
                        <div class="form-group">
                            <label class="control-label">類型</label>
    
                            <input type="text" class="form-control" name="type" id="type" data-validate="required" placeholder="類型" />
                        </div>
    
                        <div class="form-group">
                            <label class="control-label">庫存</label>
    
                            <input type="text" class="form-control" name="stock" id="stock" data-validate="required" placeholder="庫存" />
                        </div>
    
                        <div class="form-group">
                            <label class="control-label">產品圖片</label>
    
                            <input type="text" class="form-control" name="imageLink" id="imageLink" data-validate="required" placeholder="產品圖片" />
                        </div>
    
                        <div class="form-group">
                            <label class="control-label">詳細資訊</label>
    
                            <input type="text" class="form-control" name="information" id="information" data-validate="required" placeholder="詳細資訊" />
                        </div>
    
                        <div class="form-group">
                            <label class="control-label">詳細圖片</label>
    
                            <input type="text" class="form-control" name="informationImageLink" id="informationImageLink" data-validate="required" placeholder="詳細圖片" />
                        </div>
    
                        <div class="form-group">
                            <button type="button" class="btn btn-success" id="btn_update">修改</button>
                            <button type="reset" class="btn">Reset</button>
                        </div>
                    </form>
                </div>`
            )
            let id = $("#id").val();
            getProduct(id)
        }))
    }))


})

function getProduct(id) {
    $.ajax({
        url: "/product/" + id,
        method: "GET",
        dataType: "json",
        success: function(Data) {
            $("#name").val(Data.name);
            $("#brand").val(Data.brand);
            $("#price").val(Data.price);
            $("#type").val(Data.type);
            $("#stock").val(Data.stock);
            $("#ImageLink").val(Data.imageLink);
            $("#Information").val(Data.information);
            $("#InformationImageLink").val(Data.informationImageLink);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    })
}

//Get Locale Time
function getLocaleTime(date) {
    let dateObject = new Date(Date.parse(date));
    return dateObject.toLocaleString(`zh-TW`);
}

function showAjaxModal(id) {
    jQuery('#modal-7').modal('show', { backdrop: 'static' });
    jQuery.ajax({
        url: "/product/" + id,
        success: function(response) {
            jQuery('#modal-7 .modal-body').append('<img src="' + response.imageLink[0] + '"/>');
        }
    });
}

function getAllProducts() {

    $.ajax({
        url: "/products",
        method: "GET",
        // dataType: "json",
        // cache: false,
        success: function(data) {

            $.each(data, function(i, data) {
                let UpdatedTime = new Date(Date.parse(data.updatedTime))
                let CreateTime = new Date(Date.parse(data.createTime))
                    // console.log(data.updateTime)
                var body = "<tr>";
                body += "<td>" +
                    `<a href="javascript:;" onclick="showAjaxModal(` + data.id + `);" class="btn btn-default">
                        <i class="fa fa-image"></i>
                    </a></td>`;
                body += "<td>" + data.id + "</td>";
                body += "<td>" + data.name + "</td>";
                body += "<td>" + data.brand + "</td>";
                body += "<td>" + data.type + "</td>";
                body += "<td>" + data.price + "</td>";
                body += "<td>" + data.stock + "</td>";
                body += "<td>" + data.totalSold + "</td>";
                body += "<td>" + getLocaleTime(UpdatedTime) + "</td>";
                body += "<td>" + getLocaleTime(CreateTime) + "</td>";
                body += "</tr>";
                $(".datatable tbody").append(body);
            });
            /*DataTables instantiation.*/
            $("#table-2").DataTable();

        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })

}

function insertProduct() {
    let name = $("#name");
    let brand = $("#brand");
    let price = $("#price");
    let stock = $("#stock");
    let type = $("#type");
    let ImageLink = $("#ImageLink");
    let Information = $("#Information");
    let InformationImageLink = $("#InformationImageLink");
    // var Input = { name: name, brand: brand, price: price, stock: stock, type: type, imageLink:, information:, informationImageLink: }
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