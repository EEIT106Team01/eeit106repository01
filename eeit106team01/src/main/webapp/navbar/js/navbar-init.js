var head = document.getElementsByTagName('head')[0];

let navCssArr = ["/assets/js/jquery-ui/css/no-theme/jquery-ui-1.10.3.custom.min.css"
    , "/assets/css/font-icons/entypo/css/entypo.css"
    , "//fonts.googleapis.com/css?family=Noto+Sans:400,700,400italic"
    , "/assets/css/bootstrap.css", "/assets/css/neon-core.css"
    , "/assets/css/neon-theme.css", "/assets/css/neon-forms.css"
    , "/assets/css/custom.css", "/navbar/css/notosanstc.css"
    , "/navbar/css/navbar.css"
    , "/navbar/css/VIPcircle.css"
];

for (let i = 0; i < navCssArr.length; i++) {
    let navCss = document.createElement('link');
    navCss.rel = 'stylesheet';
    navCss.type = 'text/css';
    navCss.href = navCssArr[i];
    head.appendChild(navCss);
}
let navJs01 = document.createElement('script');
navJs01.src = '/navbar/js/navbar-initjs.js';

head.appendChild(navJs01);

function init() {
    gapi.load('auth2', function(){
        gapi.auth2.init({
		    client_id: '782156516037-i2qol45grfp7v696ipplrss7pn6kuqe5.apps.googleusercontent.com' //客户端ID
		  })
    });
}

document.addEventListener("DOMContentLoaded", function () {
    // var body = document.getElementsByTagName('body')[0];
    let logoutFBDiv = '<div id="fb-root"></div>';
    let logoutFBScript = '<script async defer crossorigin="anonymous" src="https://connect.facebook.net/zh_TW/sdk.js#xfbml=1&version=v3.3&appId=308899833353314&autoLogAppEvents=1"></script>';

    // body.appendChild(logoutFBDiv);
    // body.appendChild(logoutFBScript);
    $("body").append(logoutFBDiv);
    $("body").append(logoutFBScript);


    // let logoutFBScript1 = '<script src="https://apis.google.com/js/api:client.js"></script>';
    let logoutFBScript2 = '<script src="https://apis.google.com/js/platform.js?onload=init" async defer></script>';
    
    // $("head").append(logoutFBScript1);
    $("head").append(logoutFBScript2);
});

//include css End