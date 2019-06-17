package net.ddns.eeitdemo.eeit106team01.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberHtmlMappingController {
	
	@GetMapping({"/member/login","/member/register","/member/forget"})
	public String loginForm() {
		return "redirect:/member/member.html";
	}
}
