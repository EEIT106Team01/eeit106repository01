$(document).ready(function(){
	getUsername();
});

var timer = 3;

function getUsername(){
	timeout();
}

function timeout(){
	if(timer!=0){
		setTimeout(function(){ timeout(); },1000);
	}else if(timer == 0){
		window.location.href="/";
	}
	timer = timer-1;
	if(timer>=0)
		$("#second").text(timer);
}