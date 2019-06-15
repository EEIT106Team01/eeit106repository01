var memberPage = new Object();

memberPage.mainframe = function(){
	return `
<div class='mylogo'>
	<img src='image/FaDaTtsai.png'>
</div>
<div class='myform'>
	<div class='myform-title'>
		<ul id='myform-title-ul' class='myform-title-ul'>
			<li id="login" class='myform-title-ul-li myform-title-assigned'><a href="javascript:;" onclick="changeTitle(this)">會員登入</a></li>
			<li id="register" class='myform-title-ul-li'><a href="javascript:;" onclick="changeTitle(this)">會員註冊</a></li>
			<li id="forget" class='myform-title-ul-li'><a href="javascript:;" onclick="changeTitle(this)">忘記密碼</a></li>
		</ul>
	</div>
	<div class='myform-main' id="main-form">
		${memberPage.selection}
	</div>
</div>
`;
}

memberPage.loginForm =`
<form action='/login' method='post'>
    <div>
        <h3>會員登入</h3></div>
    <div class='myform-block'>
        <div>
            <p>帳號:</p>
        </div>
        <div>
            <input class='myform-main-input' type='text' name='username' id='username' autocomplete="off" maxlength="12" placeholder="username">
        </div>
    </div>
    <div class='myform-block'>
        <div>
            <p>密碼:</p>
        </div>
        <div>
            <input class='myform-main-input' type="password" name='password' id='password' autocomplete="off" maxlength="25" placeholder="password">
        </div>
    </div>
    <div class='myform-block'>
        <div>
            <button class='myform-main-button' type='submit'>登入</button>
        </div>
    </div>
    <div class='myform-block'>
        <a href="#" class="btn fb"><i class="fa fa-facebook fa-fw"></i> Login with Facebook </a>
    </div>
    <div class='myform-block'>
        <a href="#" class="btn google"><i class="fa fa-google fa-fw"></i> Login with Google+</a>
    </div>
</form>
`;

memberPage.registerForm=`
<form action='javascript:;' onsubmit='register(this)'>
    <div>
        <h3>會員註冊</h3>
    </div>
    <div class='myform-block'>
        <div class='myform-tag'>
            <div class="myform-tag-left">
                <p>帳號:</p>
            </div>
            <div class="myform-tag-right">
                <p>請輸入大小寫英文數字8~12</p>
        	</div>
        </div>
            <input class='myform-main-input' type='text' autocomplete="off" maxlength="12" name='username' id='username' placeholder="username">
        </div>
    </div>
    <div class='myform-block'>
        <div class='myform-tag'>
        	<div class="myform-tag-left">
                <p>密碼:</p>
            </div>
            <div class="myform-tag-right">
                <p>請輸入大小寫英文數字10~25</p>
        	</div>
        </div>
        <div>
            <input class='myform-main-input' autocomplete="off" maxlength="25" type="password" name='password' id='password' placeholder="password">
        </div>
    </div>
    <div class='myform-block'>
        <div class='myform-tag'>
        	<div class="myform-tag-left">
            	<p>確認密碼:</p>
            </div>
            <div class="myform-tag-right">
                <p>請再次輸入密碼</p>
        	</div>
        </div>
        <div>
            <input class='myform-main-input' autocomplete="off" maxlength="25" type="password" name='password' placeholder="password">
        </div>
    </div>
    <div class='myform-block'>
        <div class='myform-tag'>
        	<div class="myform-tag-left">
            	<p>信箱:</p>
            </div>
            <div class="myform-tag-right">
                <p>請輸入信箱</p>
        	</div>
        </div>
        <div>
            <input class='myform-main-input' autocomplete="off" type="email" name='email' id='email' placeholder="email">
        </div>
    </div>
    <div class='myform-block'>
        <div>
            <button class='myform-main-button' type='submit'>註冊</button>
        </div>
    </div>
</form>
`;

memberPage.forgetForm=`
<form action='javascript:;' onsubmit='forget(this)'>
    <div>
        <h3>忘記密碼</h3></div>
    <div class='myform-block'>
        <div>
            <p>信箱:</p>
        </div>
        <div>
            <input class='myform-main-input' type="email" name='email' id='email' placeholder="email">
        </div>
    </div>
    <div class='myform-block'>
        <div>
            <button class='myform-main-button' type='submit'>送出</button>
        </div>
    </div>
</form>
`;