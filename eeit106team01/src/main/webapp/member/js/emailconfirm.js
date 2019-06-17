$(document).ready(function(){
	getUsername();
});

var timer = 5;

function getUsername(){
	let urlParams = new URLSearchParams(window.location.search);
	
	let usernames = urlParams.getAll('username');
	let emails = urlParams.getAll('email');
	
	let username = null;
	let email = null;
	
	if(usernames[0]){
		username = usernames[0];
	}else{
		username = "訪客";
	}
	
	if(emails[0]){
		email = emails[0];
	}
	
	$("#text").text(username);
	
	$("#remail").text(email);
	
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