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
//include css End