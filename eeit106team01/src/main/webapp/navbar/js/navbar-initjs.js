let checkNavbar = setInterval(function () {
    if (document.getElementById("myNavBarGroup")) {
        clearInterval(checkNavbar);

        let navbarMemberBean = Cookies.get("MemberBean");
        if (navbarMemberBean != undefined) {
            navbarMemberBean = JSON.parse(Cookies.get("MemberBean"));
        }
        console.log(navbarMemberBean);

        if (navbarMemberBean != undefined) {
            let username;
            username = navbarMemberBean.name;
            console.log("username: " + username);
            if (username && (username != undefined)) {
                $("#navbarUsername").text(username);
            }
            $("#login").css({display: "none"});
            // $("#logout").css({display: "block"});
            // $("#userDropdownMenu").css({display: "block"});
            $("#navbarUserImage").attr("src","/navbar/images/pepe.jpg");
        } else {
            // $("#login").css({display: "block"});
            $("#logout").css({display: "none"});
            $("#userDropdownMenu").css({display: "none"});
            $("#navbarUsername").text("請先登入");
        }

        $("#logout").on("click",function(){
            $.ajax({
                url: "http://localhost:8080/forumlogout",
                type: "GET",
                success: function (data, textStatus, jqXHR) {
                    location.reload(true);
                }
            });
        });
    }
}, 100);