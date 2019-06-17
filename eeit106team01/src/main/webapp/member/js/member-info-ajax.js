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

function photoUpload(obj){
//	let data = $("#myphoto").attr('src');
	console.log(obj);
	
//	initAjax("post","testupload",data,function(text){console.log(text);});
}