package net.ddns.eeitdemo.eeit106team01.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberHtmlMappingController {
	
	@GetMapping("/member/login")
	public String loginForm() {
		return "redirect:/member/member.html?template=loginForm";
	}
	
	@GetMapping("/member/register")
	public String registerForm() {
		return "redirect:/member/member.html?template=registerForm";
	}
	
	@GetMapping("/member/forget")
	public String forgetForm() {
		return "redirect:/member/member.html?template=forgetForm";
	}
}
