package net.ddns.eeitdemo.eeit106team01.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.ddns.eeitdemo.eeit106team01.member.model.entity.MemberEntity;
import net.ddns.eeitdemo.eeit106team01.member.model.service.MemberService;

@Controller
public class DemoRestfulController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@PostMapping(path= {"/member/register"},produces= {"application/json"})
	@ResponseBody
	public ResponseEntity<?> registerDemo(@Valid MemberEntity bean,BindingResult bindingResult,HttpServletRequest request){
		Map<String,String> result = new HashMap<String,String>();
		if(!bindingResult.hasErrors()) {
				MemberEntity memberEntity = 
//						memberService.register(bean.getUsername(),bean.getEmail(),passwordEncoder.encode(bean.getPassword()));
						new MemberEntity();
				
			if(memberEntity!=null) {
				result.put("result","success");
				result.put("redirect","");
				StringBuffer baseUrl = domainNameGenerator(request.getRequestURL(), request.getRequestURI(), true);
				
				baseUrl.append("member/activate?code=").append(Base64.encodeBase64URLSafeString(passwordEncoder.encode(bean.getUsername()).getBytes()));
				
				MimeMessage mimeMessage = javaMailSender.createMimeMessage();
				simpleSetMimeMessageHelper(mimeMessage,bean.getEmail(),baseUrl.toString());
				javaMailSender.send(mimeMessage);
				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
			}else {
				result.put("reason","duplicate");
			}
		}
		result.put("result","fail");
		return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
	}
	@GetMapping("/member/activate/**")
	public String successRegister(@PathVariable String id) {
		System.out.println(id);
		return "redirect:/member/member.html";
	}
	
	@GetMapping(path= {"/emailtest"},produces= {"application/json"})
	@ResponseBody
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
					""+
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
	@ResponseBody
	public ResponseEntity<?> demoAjax(String username,String email,String password,HttpServletRequest request){
		Map<String,Object> map= new HashMap<String,Object>();
		String baseUrl = String.format("%s://%s:%d/",request.getScheme(),  request.getServerName(), request.getServerPort());
		map.put("test", "hello");
		map.put("username",username);
		map.put("email",email);
		map.put("password",password);
		map.put("scheme",request.getScheme());
		map.put("serverName",request.getServerName());
		map.put("serverPort",request.getServerPort());
		map.put("requestURI",request.getRequestURI());
		map.put("requestURL",request.getRequestURL());
		map.put("jjjjjj",domainNameGenerator(request.getRequestURL(), request.getRequestURI(), true));
		return ResponseEntity.ok(map);
	}
	
	private void simpleSetMimeMessageHelper(MimeMessage message,String email,String url) {
		MimeMessageHelper mimeMessageHelper =  new MimeMessageHelper(message);
		try {
			mimeMessageHelper.setSubject("行車發大財註冊通知");
			mimeMessageHelper.setTo(email);
			mimeMessageHelper.setText("<body>\r\n" + 
					"	<p>歡迎加入行車發大財</p>\r\n" + 
					"	<p>您收到這封信件是因為您在行車發大財註冊</p>\r\n" + 
					"	<p>請點擊以下網址啟動您的帳戶</p>\r\n" + 
					"	<p>-----------------------------------------------------</p>\r\n" + 
					url+
					"	<p>-----------------------------------------------------</p>\r\n" + 
					"	<p>感謝您的使用，祝您行車發大財</p>\r\n" + 
					"</body>", true);;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	private StringBuffer domainNameGenerator(StringBuffer url,String uri,boolean hasSlash) {
		if(hasSlash)
			return	url.delete(url.lastIndexOf(uri)+1, url.length());
		else
			return	url.delete(url.lastIndexOf(uri), url.length());
	}
}
