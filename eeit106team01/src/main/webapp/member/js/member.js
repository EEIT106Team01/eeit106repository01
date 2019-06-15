
function switchHtmlById(id,text){
	$(id).html("");
	$(id).html(text);
}

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

function changeTitle(obj){
	let temp = obj.parentNode.parentNode;
	$(temp).find("li").each(function(){
		$(this).removeClass("myform-title-assigned");
	});
	$(obj.parentNode).addClass("myform-title-assigned");
	let selector = $(obj.parentNode).attr("id");
	if(selector == "register"){
		initAjax("get","register-form.html",null,function(text){switchHtmlById("#main-form",text);});
	}else if(selector == "login"){
		initAjax("get","login-form.html",null,function(text){switchHtmlById("#main-form",text);});
	}else if(selector == "forget"){
		initAjax("get","forget-form.html",null,function(text){switchHtmlById("#main-form",text);});
	}
}

