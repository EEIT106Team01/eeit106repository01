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
                        <label class="control-label">產品圖片
                            <button type="button" class="btn btn-primary btn-icon" id="btn_addImgCol">
                            增加欄位
                            <i class="fa fa-plus"></i></button>
                        </label>
                        <div id="div_insertImg">
                            <div id="div_imageLink0">
                                <input type="text" class="form-control" name="imageLink0" id="imageLink0" data-validate="required" placeholder="順序" />
                                <input type="text" class="form-control" name="imageLinkContent0" id="imageLinkContent0" data-validate="required" placeholder="圖片" />
                            </div><br>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label">詳細資訊
                            <button type="button" class="btn btn-primary btn-icon" id="btn_addInfoCol">
                            增加欄位
                            <i class="fa fa-plus"></i></button>
                        
                        </label>
                        <div id="div_insertInfo">
                            <div id="div_info0">
                                <input type="text" class="form-control" name="information0" id="information0" data-validate="required" placeholder="資訊" />
                                <input type="text" class="form-control" name="informationContent0" id="informationContent0" data-validate="required" placeholder="資訊內容" />
                            </div><br>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label">詳細圖片
                            <button type="button" class="btn btn-primary btn-icon" id="btn_addInfoImgCol">
                            增加欄位
                            <i class="fa fa-plus"></i></button>
                        </label>
                        <div id="div_insertInfoImg">
                            <div id="div_informationImageLink0">
                                <input type="text" class="form-control" name="informationImageLink0" id="informationImageLink0" data-validate="required" placeholder="順序" />
                                <input type="text" class="form-control" name="informationImageLinkContent0" id="informationImageLinkContent0" data-validate="required" placeholder="詳細圖片" />
                            </div><br>
                        </div>
                        
                    </div>

                    <div class="form-group">
                        <button type="button" class="btn btn-success">新增</button>
                        <button type="reset" class="btn">Reset</button>
                    </div>
                </form>
            </div>`
        )
        let k = 1;
        $("#btn_addImgCol").on("click",function(){
            k++;
            $("#div_insertImg").append(
                `<div id="div_imageLink`+k+`">
                    <input type="text" class="form-control" name="imageLink`+k+`" id="imageLink`+k+`" data-validate="required" placeholder="順序" />
                    <input type="text" class="form-control" name="imageLinkContent`+k+`" id="imageLinkContent`+k+`" data-validate="required" placeholder="圖片" />
                    </div><br>`
            )
        })
        let j = 1;
        $("#btn_addInfoCol").on("click",function(){
            j++;
            $("#div_insertInfo").append(
                `<div id="div_imageLink`+k+`">
                    <input type="text" class="form-control" name="information`+k+`" id="information`+k+`" data-validate="required" placeholder="資訊" />
                    <input type="text" class="form-control" name="informationContent`+k+`" id="informationContent`+k+`" data-validate="required" placeholder="資訊內容" />
                    </div><br>`
            )
        })
        let c = 1;
        $("#btn_addInfoImgCol").on("click",function(){
            c++;
            $("#div_insertInfoImg").append(
                `<div id="div_imageLink`+c+`">
                    <input type="text" class="form-control" name="informationImageLink`+c+`" id="informationImageLink`+c+`" data-validate="required" placeholder="順序" />
                    <input type="text" class="form-control" name="informationImageLinkContent`+c+`" id="informationImageLinkContent`+c+`" data-validate="required" placeholder="圖片" />
                    </div><br>`
            )
        })
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
                            <div id="div_imageLinkAll"></div>
                           
                        </div>
    
                        <div class="form-group">
                            <label class="control-label">詳細資訊</label>
                            <div id="div_infoAll""></div>
                           
                        </div>

                        <div class="form-group">
                            <label class="control-label">詳細圖片</label>
                            <div id="div_informationImageLinkALl"></div>
                            
                        </div>

                        <hr>
                        <div class="form-group">
                            <button type="button" class="btn btn-success" id="btn_update">修改</button>
                            <button type="reset" class="btn">Reset</button>
                        </div>
                    </form>
                </div>`
            )
            let id = $("#id").val();
            getProduct(id);
             
            $("#btn_update").on("click",(function(){
                
                let nameVal = $("#name").val();
                let brandVal = $("#brand").val();
                let typeVal = $("#type").val();
                let priceVal = $("#price").val();
                let stockVal = $("#stock").val();

                let imgLength = $("#div_imageLinkAll input").length;
                console.log(  $($("#div_imageLinkAll input").eq(1)).val())
                let imageLinkVal = new Object();
                for(let i =0;i<imgLength;i+=2){
                    imageLinkVal.$($("#div_imageLinkAll input").eq(i)).val()=$($("#div_imageLinkAll input").eq(i+1)).val()
                }

                let infoLength = $("#div_infoAll input").length;
                let informationVal = new Object();
                for(let i =0;i<infoLength;i+=2){
                    infoArray.$($("#div_infoAll input").eq(i)).val()=$($("#div_infoAll input").eq(i+1)).val()
                }

                let infoImgLength = $("#div_informationImageLinkALl input").length;
                let informationImageLinkVal = new Object();
                for(let i =0;i<infoImgLength;i+=2){
                    infoImgArray.$($("#div_informationImageLinkALl input").eq(i)).val()=$($("#div_informationImageLinkALl input").eq(i+1)).val()
                }
                updateProduct(nameVal,brandVal,typeVal,priceVal,stockVal,imageLinkVal,informationVal,informationImageLinkVal)
            })) 
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

            let imgArray =[];
            let k = 0;
            $.each(Data.imageLink,function(key,val) {
                imgArray.push(
                    `<div id="div_imageLink`+k+`">
                    <input type="text" class="form-control" name="imageLink`+k+`" id="imageLink`+k+`" data-validate="required" placeholder="第幾張" />
                    <input type="text" class="form-control" name="imageLinkContent`+k+`" id="imageLinkContent`+k+`" data-validate="required" placeholder="圖片" />
                    </div><br>`
                )
                k++;
            })
            $("#div_imageLinkAll").append(imgArray.join(""));
            let q=0;
            $.each(Data.imageLink,function(key,val) {
                let id_img = "imageLink"+q
                let id2_img = "imageLinkContent"+q
                $("#"+id_img).val(key)
                $("#"+id2_img).val(val)
                q++
            })

            let infoArray =[];
            let i =0;
            $.each(Data.information,function(key,val) {
                infoArray.push(
                    `<div id="div_info`+i+`">
                    <input type="text" class="form-control" name="information`+i+`" id="information`+i+`" data-validate="required" placeholder="資訊" />
                    <input type="text" class="form-control" name="informationContent`+i+`" id="informationContent`+i+`" data-validate="required" placeholder="資訊內容" />
                    </div><br>`
                )
                i++;
            })
            $("#div_infoAll").append(infoArray.join(""));
            let y=0;
            $.each(Data.information,function(key,val) {
                let id_info = "information"+y
                let id2_info = "informationContent"+y
                $("#"+id_info).val(key)
                $("#"+id2_info).val(val)
                y++
            })
            
            let infoImgArray =[];
            let o = 0;
            $.each(Data.informationImageLink,function(key,val) {
                infoImgArray.push(
                    `<div id="div_informationImageLink`+o+`">
                    <input type="text" class="form-control" name="informationImageLink`+o+`" id="informationImageLink`+o+`" data-validate="required" placeholder="順序" />
                    <input type="text" class="form-control" name="informationImageLinkContent`+o+`" id="informationImageLinkContent`+o+`" data-validate="required" placeholder="詳細圖片" />
                    </div><br>`
                )
                o++;
            })
            $("#div_informationImageLinkALl").append(infoImgArray.join(""));
            let p=0;
            $.each(Data.informationImageLink,function(key,val) {
                let id_infoImg = "informationImageLink"+p
                let id2_infoImg = "informationImageLinkContent"+p
                $("#"+id_infoImg).val(key)
                $("#"+id2_infoImg).val(val)
                p++
            })

            console.log($("#div_imageLinkAll input").length)
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

function updateProduct(nameVal,brandVal,typeVal,priceVal,stockVal,imageLinkVal,informationVal,informationImageLinkVal){

    let bean = {name:nameVal,brand:brandVal,type:typeVal,price:priceVal,stock:stockVal,imageLink:imageLinkVal,information:informationVal,informationImageLink:informationImageLinkVal}

    $.ajax({
        url: "/product/update",
        method: "PUT",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(bean),
        success: function() {
            alert("產品更新成功")
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("產品更新失敗")
        }
    })
}