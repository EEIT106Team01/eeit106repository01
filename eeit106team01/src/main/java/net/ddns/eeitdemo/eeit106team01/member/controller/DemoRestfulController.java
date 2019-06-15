package net.ddns.eeitdemo.eeit106team01.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.member.model.entity.MemberEntity;
import net.ddns.eeitdemo.eeit106team01.member.model.service.MemberService;

@RestController
public class DemoRestfulController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@PostMapping(path= {"/register"},produces= {"application/json"})
	public ResponseEntity<?> registerDemo(@Valid MemberEntity bean,BindingResult bindingResult){
		Map<String,String> result = new HashMap<String,String>();
		if(!bindingResult.hasErrors()) {
			result.put("message","success");
			memberService.register( bean.getUsername(),bean.getEmail(),passwordEncoder.encode(bean.getPassword()));
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
		}
		return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
	}
	
	@GetMapping(path= {"/emailtest"},produces= {"application/json"})
	public ResponseEntity<?> emailTest(){
		
		Map<String,String> result = new HashMap<String,String>();
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper =  new MimeMessageHelper(mimeMessage);
		try {
			mimeMessageHelper.setSubject("行車發大財註冊通知");
			mimeMessageHelper.setTo("hamonicnocturne@gmail.com");
			mimeMessageHelper.setText("<body>\r\n" + 
					"	<p>歡迎加入行車發大財</p>\r\n" + 
					"	<p>您收到這封信件是因為您在行車發大財註冊</p>\r\n" + 
					"	<p>請點擊以下網址啟動您的帳戶</p>\r\n" + 
					"	<p>-----------------------------------------------------</p>\r\n" + 
					"	\r\n" + 
					"	<p>-----------------------------------------------------</p>\r\n" + 
					"	<p>感謝您的使用，祝您行車發大財</p>\r\n" + 
					"</body>", true);;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		javaMailSender.send(mimeMessage);
		return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
	}
	
	@PostMapping("/member/demoAjaxGG")
	public ResponseEntity<?> demoAjax(String username,String email,String password){
		Map<String,Object> map= new HashMap<String,Object>();
		map.put("test", "hello");
		map.put("username",username);
		map.put("email",email);
		map.put("password",password);
		return ResponseEntity.ok(map);
	}
}
