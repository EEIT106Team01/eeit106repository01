package net.ddns.eeitdemo.eeit106team01.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.member.controller.formbacking.RegisterForm;
import net.ddns.eeitdemo.eeit106team01.member.model.service.MemberService;

@RestController()
public class DemoRestfulController {

	@Autowired
	private MemberService<?> memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping(path= {"/register"},produces= {"application/json"})
	public ResponseEntity<?> registerDemo(@Valid RegisterForm bean,BindingResult bindingResult){
		Map<String,String> result = new HashMap<String,String>();
		if(!bindingResult.hasErrors()) {
//			result.put("url","register-success.html");
			result.put("message","success");
			memberService.demoRegister(bean.getEmail(), passwordEncoder.encode(bean.getPassword()), bean.getUsername());
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
		}
//		result.put("message","fail");
//		result.put("url","register-fail.html");
		return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
	}
}
