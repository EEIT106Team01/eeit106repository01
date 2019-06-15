
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
	let data = {username:obj['username'].value,password:obj['password'].value,email:obj['email'].value};
	initAjax("post","demoAjaxGG",data,function(text){console.log(text);});
}

function login(obj){
	let data = {username:obj['username'].value,password:obj['password'].value};
	initAjax("post","demoAjaxGG",data,function(text){console.log(text);});
}


