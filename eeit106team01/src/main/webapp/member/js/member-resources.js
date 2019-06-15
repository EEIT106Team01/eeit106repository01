var memberPage = new Object();

memberPage.mainframe = function(){
	return `
<div class='mylogo'>
	<img src='image/FaDaTtsai.png'>
</div>
<div class='myform'>
	<div class='myform-title'>
		<ul class='myform-title-ul'>
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
<form action='javascript:;' onsubmit='login(this)'>
	<div><h3>會員登入</h3></div>
	<div class='myform-block'><div><p>帳號:</p></div>
	<div><input type='text' name='username' id='username' autocomplete="off" maxlength="25"  placeholder="username"></div></div>
	<div class='myform-block'><div><p>密碼:</p></div>
	<div><input type="password" name='password' id='password' autocomplete="off" maxlength="255" placeholder="password"></div></div>
	<div class='myform-block'>
	<div><button type='submit'>登入</button></div></div>
	<div class='myform-block'>
		<a href="#" class="btn fb"><i class="fa fa-facebook fa-fw"></i> Login with Facebook </a>
	</div>
	<div class='myform-block'>
		<a href="#" class="btn google"><i class="fa fa-google fa-fw"></i> Login with Google+</a>
	</div>
</form>
`;

memberPage.registerForm=`
<form>
	<div><h3>會員註冊</h3></div>
	<div class='myform-block'><div><p>帳號:</p></div>
	<div><input type='text' name='username' id='username' placeholder="username"></div></div>
	<div class='myform-block'><div><p>密碼:</p></div>
	<div><input type="password" name='password' id='password' placeholder="password"></div></div>
	<div class='myform-block'><div><p>確認密碼:</p></div>
	<div><input type="password" name='password' placeholder="password"></div></div>
	<div class='myform-block'><div><p>信箱:</p></div>
	<div><input type="email" name='email' id='email' placeholder="email"></div></div>
	<div class='myform-block'>
	<div><button type='submit'>註冊</button></div></div>
</form>
`;

memberPage.forgetForm=`
<form>
	<div><h3>忘記密碼</h3></div>
	<div class='myform-block'><div><p>信箱:</p></div>
	<div><input type="email" name='email' id='email' placeholder="email"></div></div>
	<div class='myform-block'>
	<div><button type='submit'>送出</button></div></div>
</form>
`;