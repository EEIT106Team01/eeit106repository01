
function initAjax(){
	if(arguments.length == 3){
		inner(arguments[0],arguments[1],null,arguments[2]);
	}
	else if(arguments.length == 4){
		inner(arguments[0],arguments[1],arguments[2],arguments[3]);
	}
	function inner(method,url,data,callback){
		$.ajax(
				{
					type:method,
					url:url,
					async:true,
					data:data,
					success:function(text){
						callback(text);
					}
				}
		);	
	}
}

function forget(obj){
	let data = {email:obj['email'].value};
	initAjax("post","demoAjaxGG",data,function(text){console.log(text);});
}

function register(obj){
	
	//check username
	let usernameOk = usernameRegexCheck(obj['username'].value);
	console.log("username:"+usernameOk);
	//check password
	let passwordOk = false;
	if(obj['password'][0].value == obj['password'][1].value){
		passwordOk = passwordRegexCheck(obj['password'][0].value);
	}
	console.log("password:"+passwordOk);
	//check email
	let emailOk = emailRegexCheck(obj['email'].value);
	console.log("email:"+emailOk);
	//everything is fine then
	if(usernameOk && passwordOk && emailOk){
		let data = {username:obj['username'].value,password:obj['password'][0].value,email:obj['email'].value};
//		initAjax("post","demoAjaxGG",data,function(text){console.log(text);});
		initAjax("post","register",data,function(text){console.log(text);});
		return;
	}
	return console.log("fail");
}

function login(obj){
	let data = {username:obj['username'].value,password:obj['password'].value};
	initAjax("post","demoAjaxGG",data,function(text){console.log(text);});
}

function emailRegexCheck(email){
	return true;
}

function passwordRegexCheck(password){
	return /^.*(?=.{9,25})(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*)(-+=]).*$/.test(password);
}

function usernameRegexCheck(username){
	return /^.[A-Za-z0-9]{7,12}$/.test(username);
}


