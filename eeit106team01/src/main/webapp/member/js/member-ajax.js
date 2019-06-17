
function initAjax(){
	if(arguments.length == 3){
		inner(arguments[0],arguments[1],null,arguments[2],null);
	}
	else if(arguments.length == 4){
		inner(arguments[0],arguments[1],arguments[2],arguments[3],null);
	}
	else if(arguments.length == 5){
		inner(arguments[0],arguments[1],arguments[2],arguments[3],arguments[4]);
	}
	function inner(method,url,data,callback,exceptionHandle){
		$.ajax(
				{
					type:method,
					url:url,
					async:true,
					data:data,
					success:function(text){
						callback(text);
					},
					error:function(jqXHR, exception){
						exceptionHandle(jqXHR, exception);
					}
				}
		);	
	}
}


function forget(obj){
	let data = {username:obj['username'].value,email:obj['email'].value};
	initAjax("post","forget",data,
			function(text){
				if(text['result']){
					if(text['result']=='success'){
						window.location.href = "recievepassword.html?username="+obj['username'].value+"&email="+obj['email'].value;
					}
				}
			},
			function(status,text){
				error = status.responseJSON;
				if(error.reason == 'notfound'){
					alert("該用戶不存在或錯誤請求");
					resetForm(memberPage.forgetForm);
				}
			});
	return;
}

function register(obj){
	let errors = null;
	//check username
	let usernameOk = usernameRegexCheck(obj['username'].value);
//	console.log("username:"+usernameOk);
	//check password
	let passwordOk = false;
	if(obj['password'][0].value == obj['password'][1].value){
		passwordOk = passwordRegexCheck(obj['password'][0].value);
	}
//	console.log("password:"+passwordOk);
	//check email
	let emailOk = emailRegexCheck(obj['email'].value);
	console.log("email:"+emailOk);
	//everything is fine then
	if(usernameOk && passwordOk && emailOk){
		let data = {username:obj['username'].value,password:obj['password'][0].value,email:obj['email'].value};
//		initAjax("post","demoAjaxGG",data,function(text){console.log(text);});
		initAjax("post","register",data,
		function(text){
			if(text['result']){
				if(text['result']=='success'){
					window.location.href = "emailconfirm.html?username="+text['username']+"&email="+text['email'];
				}
			}
		},
		function(status,text){
			error = status.responseJSON;
			if(error.reason == 'duplicate'){
				alert("帳號重複");
				resetForm(memberPage.registerForm);
			}
				
		});
		return;
	}
	 alert("輸入格式錯誤");
	 resetForm(memberPage.registerForm);
	 return ;
}

function login(obj){
	let data = {username:obj['username'].value,password:obj['password'].value};
	initAjax("post","login",data,
	function(text){
		if(text['url']){
			window.location.href = text['url'];
		}
	},function(status,text){
		error = status.responseJSON;
		if(error.reason == 'fail login'){
			alert("登入失敗");
			resetForm(memberPage.loginForm);
		}	
	});
	return;
}

function emailRegexCheck(email){
	return /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(email);
}

function passwordRegexCheck(password){
	return /^.*(?=.{9,25})(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*)(-+=]).*$/.test(password);
}

function usernameRegexCheck(username){
	return /^.[A-Za-z0-9]{7,12}$/.test(username);
}

var iconok = "<i class='fa fa-check' style='color:green;'></i>";
var iconnook = "<i class='fa fa-times' style='color:red;'></i>";

function checkUsername(obj){
	let username = obj.value;
	let result = usernameRegexCheck(username);
	if(username.length == 0 ){
		$("#checkusername").html("");
	}else{
		if(result){
			$("#checkusername").html(iconok);
		}else{
			$("#checkusername").html(iconnook);
		}
	}
}

function checkPassword(obj){
	let password = obj.value;
	let result = passwordRegexCheck(password);
	if(password.length==0){
		$('#checkpassword').html("");
	}else{
		if(result){
			$('#checkpassword').html(iconok);
		}else{
			$('#checkpassword').html(iconnook);
		}
	}	
}

function confirmPassword(obj){
	let password = obj.value;
	let result = ($('#password').val() == password);
	if(password.length==0){
		$('#confirmpassword').html("");
	}else{
		if(result){
			$('#confirmpassword').html(iconok);
		}else{
			$('#confirmpassword').html(iconnook);
		}
	}	
}

function checkEmail(obj){
	let email = obj.value;
	let result = emailRegexCheck(email);
	if(email.length==0){
		$('#checkemail').html("");
	}else{
		if(result){
			$('#checkemail').html(iconok);
		}else{
			$('#checkemail').html(iconnook);
		}
	}
}

function resetForm(obj){
	$('#main-form').html(obj);
}