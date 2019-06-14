document.addEventListener("DOMContentLoaded", function () {
    $("#videoBean").after(
        '<div id="videoModal" class="modal fade" style="margin-top: 10%" role="dialog">'
        + '<div class="modal-dialog">'
        + '<!-- Modal content-->'
        + '<div class="modal-content">'
        + '<div class="modal-header">'
        + '<button type="button" class="close" data-dismiss="modal">&times;</button>'
        + '<h2 class="modal-title">不支援的格式</h2>'
        + '</div>'
        + '<div class="modal-body">'
        + '<p>僅接受如mp4等影片檔案</p>'
        + '</div>'
        + '<div class="modal-footer">'
        + '<button type="button" class="btn btn-default" data-dismiss="modal">確定</button>'
        // + '<button onclick="deleteContent()" type="button" class="btn btn-danger" data-dismiss="modal">我明白，仍要刪除</button>'
        + '</div>'
        + '</div>'
        + '</div>'
        + '</div>'
    );
    $("#videoBean").on("change", function (e) {
        if (!checkFileMimeType(this.files[0])) {
            $("#videoModal").modal('toggle');
            $(this).val("");
        }
    });
});

function upload() {
    $("#msg").text("");
    var checkFile = $("#videoBean").val();
    var msgInfo = "";
    if (null == checkFile || "" == checkFile) {
        $("#msg").text("檔案不存在，請檢查檔案！");
    } else {
        $("#videoBean").attr('readonly', true);
        var formData = new FormData(document.getElementById("FileuploadForm"));
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: '/videos',
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("error");
                console.log(jqXHR.status);
                videoUploadResult = jqXHR.status;
                flag = false;
                $("#msg").text("連接伺服器錯誤，請重試");
            },
            success: function (data, textStatus, jqXHR) {
                console.log("data = " + data);
                console.log("jqXHR.status = " + jqXHR.status);
                console.log("jqXHR.getResponseHeader('location') = " + jqXHR.getResponseHeader('location'));
                if (jqXHR.status === 201) {
                    console.log("success");
                    videoUploadResult = data;
                } else if (jqXHR.status === 204) {
                    console.log("upload error, http status code = 204");
                    console.log("jqXHR.getResponseHeader('errorMsg') = " + jqXHR.getResponseHeader('location'));
                }
            },
            xhr: function () {
                var xhr = $.ajaxSettings.xhr();
                if (xhr.upload) {
                    //監聽處理事件
                    xhr.upload.addEventListener("progress", progressHandle, false);
                    //監聽完成事件
                    xhr.addEventListener("load", completeHandle, false);
                    //監聽錯誤事件
                    xhr.addEventListener("error", failedHandle, false);
                    //監聽取消事件
                    xhr.addEventListener("abort", canceledHandle, false);
                    //開始顯示進度條
                    showProgress();
                    return xhr;
                }
            }
        }, 'json');
    }
}
var start = 0;
//顯示進度條
function showProgress() {
    start = new Date().getTime();
    $(".progress-body").css("display", "block");
}
//隱藏進度條
function hideProgress() {
    $("#videoBean").val('');
    $('.progress-body .progress-speed').html("0 M/S, 0/0M");
    $('.progress-bar').css("width", "0%");
    $('.progress-body .progress-info').html("請選擇上傳檔案，並點擊上傳檔案按鈕");
    $("#submit").attr('disabled', false);
    $(".progress-body").css("display", "none");
}
//進度條更新
function progressHandle(e) {
    var percent = e.loaded / e.total * 100;
    var time = ((new Date().getTime() - start) / 1000).toFixed(3);
    if (time == 0) {
        time = 1;
    }
    $('.progress-body .progress-speed').html(((e.loaded / 1024) / 1024 / time).toFixed(2) + "M/S, " + ((e.loaded / 1024) / 1024).toFixed(2) + "/" + ((e.total / 1024) / 1024).toFixed(2) + " MB. ");
    $('.progress-bar').css("width", percent.toFixed(2) + "%");
    $('.percentage').html(percent.toFixed(2) + "%");
    if (percent == 100) {
        $('.progress-body .progress-info').html("上傳完成，檔案處理中...");
    } else {
        $('.progress-body .progress-info').html("檔案上傳中...");
    }
};
//上傳完成處理
function completeHandle(e) {
    $('.progress-body .progress-info').html("上傳檔案完成。");
    setTimeout(hideProgress, 2000);
};
//上傳出錯處理
function failedHandle(e) {
    $('.progress-body .progress-info').html("上傳檔案出錯，檔案過大或服務不可用。");
};
//上傳取消處理
function canceledHandle(e) {
    $('.progress-body .progress-info').html("上傳檔案取消。");
};

//檢查上傳檔案mimeType
function checkFileMimeType(file) {
    // let file = this.files[0];
    let fileType = file["type"];
    let validVideoTypes = ["video/mp4"];
    if ($.inArray(fileType, validVideoTypes) < 0) {
        // invalid file type code goes here.
        return false;
    } else {
        return true;
    }
}