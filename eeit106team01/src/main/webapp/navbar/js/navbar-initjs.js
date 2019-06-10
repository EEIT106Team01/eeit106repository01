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
            } else {
                console.log("No username???");
            }
            $.ajax({
                url: "/getMemberTempsImages/" + navbarMemberBean.id,
                type: "GET",
                success: function (memberImageData) {
                    if (navbarMemberBean.id == memberImageData.id) {
                        if (memberImageData.image) {
                            console.log("get memberImage ok");
                            $("#navbarUserImage").attr("src", memberImageData.image);
                        } else {
                            console.log("no memberImage in this member");
                            $("#navbarUserImage").attr("src", "/navbar/images/notLogin.jpg");
                        }
                        let imgHeight = document.getElementById('navbarUserImage').clientHeight;
                        $("#navbarUserImage").css({ top: `calc(50% - ${imgHeight / 2}px)` });
                    }
                }
            });
            $("#login").css({ display: "none" });
            $("#navbarRegister").css({ display: "none" });
        } else {
            $("#logout").css({ display: "none" });
            $("#userDropdownMenu").css({ display: "none" });
            $("#navbarUserImage").css({ display: "none" });
            $("#navbarUsername").text("");
            $("#toggleChatBtn").css({ display: "none" });
            $("#notiDrop").css({ display: "none" });
        }

        $("#logout").on("click", function () {
            $.ajax({
                url: "/forumlogout",
                type: "GET",
                success: function (data, textStatus, jqXHR) {
                    location.reload(true);
                }
            });
            Cookies.remove('MemberBean');
            if (worker) {
                worker.port.postMessage({
                    "command": "logout"
                });
            }
        });
        $("#login").on("click", function () {
            sessionStorage.setItem("previousPage", location.pathname + location.search);
        });
    }
}, 50);