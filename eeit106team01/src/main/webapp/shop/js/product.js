$(document).ready(function () {
    $("#getNameButton").click(function () {
        getProductsByName();
    })
});

function getProductsByName() {
    var name = $("#getNameForm").serialize();
    $.ajax({
        url: "http://localhost:8080/products/name?" + name,
        method: "GET",
        dataType: "json",
        success: function (data) {
            var items = [];
            $.each(data, function (key, val) {
                items.push("<li id='" + key + "'>" + key +":"+ val + "</li>");
            });
            $("<ul/>", {
                "class": "my-new-list",
                html: items.join("")
            }).appendTo("#searchResult");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus)
        }
    })
}