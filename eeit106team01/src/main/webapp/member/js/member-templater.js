$(document).ready(function(){
	memberPage.selection = memberPage.loginForm;
	$('#mycontainer').html(memberPage.mainframe);
})

function changeTitle(obj){
	let temp = obj.parentNode.parentNode;
	$(temp).find("li").each(function(){
		$(this).removeClass("myform-title-assigned");
	});
	$(obj.parentNode).addClass("myform-title-assigned");
	let selector = $(obj.parentNode).attr("id");
	if(selector == "register"){
		$('#main-form').html(memberPage.registerForm);
	}else if(selector == "login"){
		$('#main-form').html(memberPage.loginForm);
	}else if(selector == "forget"){
		$('#main-form').html(memberPage.forgetForm);
	}
}