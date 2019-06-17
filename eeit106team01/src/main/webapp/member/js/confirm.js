$(document).ready(function(){
	getUsername();
});

var timer = 3;

function getUsername(){
	let urlParams = new URLSearchParams(window.location.search);
	let usernames = urlParams.getAll('username');
	let username = null;
	if(usernames[0]){
		username = usernames[0];
	}else{
		username = "訪客";
	}
	$("#text").text(username);
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