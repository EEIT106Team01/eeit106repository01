var head = document.getElementsByTagName('head')[0];

let navCssArr = ["/assets/js/jquery-ui/css/no-theme/jquery-ui-1.10.3.custom.min.css"
    , "/assets/css/font-icons/entypo/css/entypo.css"
    , "//fonts.googleapis.com/css?family=Noto+Sans:400,700,400italic"
    , "/assets/css/bootstrap.css"
    , "/assets/css/neon-core.css"
    , "/assets/css/neon-theme.css"
    , "/assets/css/neon-forms.css"
    , "/assets/css/custom.css"
    , "/navbar/css/navbar.css"];

for (let i = 0; i < navCssArr.length; i++) {
    let navCss = document.createElement('link');
    navCss.rel = 'stylesheet';
    navCss.type = 'text/css';
    navCss.href = navCssArr[i];
    head.appendChild(navCss);
}
//include css End