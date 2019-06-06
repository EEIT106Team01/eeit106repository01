package net.ddns.eeitdemo.eeit106team01.member.controller.formbacking;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterForm {
	
	@NotNull(message="請輸入使用者帳號")
	@Size(min = 3, max=50, message = "帳號長度至多50")
	private String username;
	
	@NotNull(message="請輸入密碼")
	@Size(min = 6, max = 255, message="密碼至少6碼")
	private String password;
	
	@NotNull(message = "請輸入信箱")
	@Size(min = 1,max =255,message="請輸入正確信箱格式")
	@Email(message = "請輸入正確信箱格式")
	private String email;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
