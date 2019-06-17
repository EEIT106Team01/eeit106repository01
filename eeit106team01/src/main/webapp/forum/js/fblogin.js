document.addEventListener("DOMContentLoaded", function () {
    window.fbAsyncInit = function () {
        FB.init({
            appId: '308899833353314',
            cookie: true,
            xfbml: true,
            version: '3.3'
        });

        FB.AppEvents.logPageView();

    };

    (function (d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) { return; }
        js = d.createElement(s); js.id = id;
        js.src = "https://connect.facebook.net/zh_TW/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));

    $("#logoutFB").click(function () {
        FB.logout(function (response) {
            sessionStorage.setItem("fblogoutMsg", JSON.stringify(response));
        });
    });
    console.log(JSON.parse(sessionStorage.getItem("fblogoutMsg")));
});

// FB.getLoginStatus(function (response) {
//     statusChangeCallback(response);
// });


function checkLoginState() {
    FB.getLoginStatus(function (response) {
        statusChangeCallback(response);
    });
}


function statusChangeCallback(response) {
    console.log(response);
    FB.api('/me', {
        "fields": "id,name,email"
    }, function (response) {
        console.log(response);
        let mb = {
            address: null,
            birth: null,
            email: response.email,
            id: response.id,
            image: null,
            level: "normal",
            levelTime: 1560495614325,
            memberCreateTime: 1560409214325,
            name: response.name,
            password: null,
            phone: null
        };
        Cookies.set("MemberBean", mb);
        location.href = "/";
    });
}