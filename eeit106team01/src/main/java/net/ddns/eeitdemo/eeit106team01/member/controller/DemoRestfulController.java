package net.ddns.eeitdemo.eeit106team01.member.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	@Autowired
	private ServletContext servletContext;
	
	@PostMapping(path= {"/member/register"},produces= {"application/json"})
	@ResponseBody
	public ResponseEntity<?> register(@Valid MemberEntity bean,BindingResult bindingResult,HttpServletRequest request){
		Map<String,String> result = new HashMap<String,String>();
		if(!bindingResult.hasErrors()) {
			String uuid = memberService.register(bean.getUsername(),bean.getEmail(),passwordEncoder.encode(bean.getPassword()));
			if(uuid!=null) {
				StringBuffer baseUrl = domainNameGenerator(request.getRequestURL(), request.getRequestURI(), true);
				baseUrl.append("member/activate?token=").append(uuid);			
				MimeMessage mimeMessage = javaMailSender.createMimeMessage();
				simpleSetMimeMessageHelper(mimeMessage,bean.getEmail(),"行車發大財註冊通知",registrationComfirmLetterTemplater(baseUrl.toString()));
				javaMailSender.send(mimeMessage);
				result.put("result","success");
				result.put("username",bean.getUsername());
				result.put("email",bean.getEmail());
				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
			}else {
				result.put("reason","duplicate");
			}
		}
		result.put("result","fail");
		return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
	}
	
	@PostMapping(path= {"/member/forget"},produces= {"application/json"})
	@ResponseBody
	public ResponseEntity<?> forget(String username,String email,HttpServletRequest request){
			Map<String,String> result = new HashMap<String,String>();
			String tempPassword = passwordEncoder.encode(UUID.randomUUID().toString()).substring(20,31);
			String encodePassword = passwordEncoder.encode(tempPassword);
			boolean flag = memberService.forgetPassword(username,email,encodePassword);
			if(flag) {
				StringBuffer baseUrl = domainNameGenerator(request.getRequestURL(), request.getRequestURI(), true);		
				MimeMessage mimeMessage = javaMailSender.createMimeMessage();
				simpleSetMimeMessageHelper(mimeMessage,email,"行車發大財密碼更動通知",forgetPasswordComfirmLetterTemplater(baseUrl.toString(),tempPassword));
				javaMailSender.send(mimeMessage);
				result.put("result","success");
				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
			}
			result.put("reason","notfound");
			result.put("result","fail");
		return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
	}
	
	
	@GetMapping(path= {"/member/activate"},produces= {"application/json"})
	public String confirmRegister(@RequestParam("token")String token,RedirectAttributes redirectAttributes) {
		MemberEntity entity = memberService.registerConfirm(token);
		if(entity!=null) {
			redirectAttributes.addAttribute("username", entity.getUsername());
		}
		return "redirect:/member/confirm.html";
	}
	
	@RequestMapping(value = "/member/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
	    System.err.println("EXECUTED");
		modelMap.addAttribute("file", file);
		System.out.println("contenttype: "+file.getContentType());
		System.out.println("filename: "+file.getOriginalFilename());
		System.out.println("name: "+file.getName());
		System.out.println("size: "+file.getSize());
		System.out.println("realpath: "+servletContext.getRealPath(""));
		System.out.println("extension: "+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
		try {
			File ddd = new File(servletContext.getRealPath("")+"member/resources/temp.jpg");
			if(!ddd.exists()) {
				ddd.createNewFile();
			}
//			file.transferTo(ddd);
			System.out.println(ddd.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return file.toString();
	}
	
	private void simpleSetMimeMessageHelper(MimeMessage message,String email,String title,String content) {
		MimeMessageHelper mimeMessageHelper =  new MimeMessageHelper(message);
		try {
			mimeMessageHelper.setSubject(title);
			mimeMessageHelper.setTo(email);
			mimeMessageHelper.setText(content, true);;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	private String forgetPasswordComfirmLetterTemplater(String url,String password) {
		StringBuffer result = new StringBuffer();
						result.append("<body>")
						.append("<p>行車發大財通知</p>")
						.append("<p>因為您曾請求尋回密碼服務</p>")
						.append("<p>以下是您的暫時性密碼</p>")
						.append("<p>-----------------------------------------------------</p>")
						.append(password)
						.append("<p>-----------------------------------------------------</p>")
						.append("<p>請務必更改密碼以維護帳號安全</p>")
						.append("<p>感謝您的使用，祝您行車發大財</p>")
						.append(url)
						;
		return result.toString();
	}
	
	private String registrationComfirmLetterTemplater(String url) {
		StringBuffer result = new StringBuffer();
						result.append("<body>")
						.append("<p>歡迎加入行車發大財</p>")
						.append("<p>您收到這封信件是因為您在行車發大財註冊</p>")
						.append("<p>請24小時內點擊以下網址啟動您的帳戶</p>")
						.append("<p>-----------------------------------------------------</p>")
						.append(url)
						.append("<p>-----------------------------------------------------</p>")
						.append("<p>感謝您的使用，祝您行車發大財</p>")
						;
		return result.toString();
	}
	
	private StringBuffer domainNameGenerator(StringBuffer url,String uri,boolean hasSlash) {
		if(hasSlash)
			return	url.delete(url.lastIndexOf(uri)+1, url.length());
		else
			return	url.delete(url.lastIndexOf(uri), url.length());
	}
	
	private String multipartFileAsStaticResourceWithUuidName(MultipartFile file,String location) {
		String fileExtension =file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String fileUuidName = new StringBuffer().append(UUID.randomUUID().toString()).append(fileExtension).toString();
		String realPath = new StringBuffer()
				.append(servletContext.getRealPath(""))
				.append(location)
				.append(fileUuidName)
				.toString();
		File staticResource = new File(realPath);
		try {
			if(!staticResource.exists())
				staticResource.createNewFile();
			file.transferTo(staticResource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileUuidName;
	}

//	@PostMapping("/member/demoAjaxGG")
//	@ResponseBody
//	public ResponseEntity<?> demoAjax(String username,String email,String password,HttpServletRequest request){
//		Map<String,Object> map= new HashMap<String,Object>();
//		String baseUrl = String.format("%s://%s:%d/",request.getScheme(),  request.getServerName(), request.getServerPort());
//		map.put("test", "hello");
//		map.put("username",username);
//		map.put("email",email);
//		map.put("password",password);
//		map.put("scheme",request.getScheme());
//		map.put("serverName",request.getServerName());
//		map.put("serverPort",request.getServerPort());
//		map.put("requestURI",request.getRequestURI());
//		map.put("requestURL",request.getRequestURL());
//		map.put("jjjjjj",domainNameGenerator(request.getRequestURL(), request.getRequestURI(), true));
//		return ResponseEntity.ok(map);
//	}
	
//	@RequestMapping("/member/demodemo")
//	@ResponseBody
//	public ResponseEntity<?> demodemo(String text,String email,String password,String tel,String url){
//		Map<String,Object> map= new HashMap<String,Object>();
//		System.out.println(text+" is text");
//		System.out.println(email+" is email");
//		System.out.println(password+" is password");
//		System.out.println(tel+" is tel");
//		System.out.println(url+" is rul");
//		System.out.println(text+" is text");
//		System.out.println(text+" is text");
//		return ResponseEntity.ok(map);
//	}
	
//	@RequestMapping("/member/demouuid")
//	public String demouuid(RedirectAttributes redirectAttributes) {
//		UUID temp = UUID.randomUUID();
//		System.out.println(temp.toString());
//		redirectAttributes.addAttribute("this is not fun", temp);
//		return "redirect:/member/demo.html";
//	}
	
//	@RequestMapping("/member/demofileload")
//	@ResponseBody
//	public String demofileload() {
//		String result=null;
//		try {
//			InputStream is = new FileInputStream(new File(servletContext.getRealPath("")+"member/resources/xxxddd.html")) ;
//			try {
//				System.out.println(IOUtils.toString(is));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
	
//	@GetMapping(path= {"/emailtest"},produces= {"application/json"})
//	@ResponseBody
//	public ResponseEntity<?> emailTest(){
//		
//		Map<String,String> result = new HashMap<String,String>();
//		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//		MimeMessageHelper mimeMessageHelper =  new MimeMessageHelper(mimeMessage);
//		try {
//			mimeMessageHelper.setSubject("行車發大財註冊通知");
//			mimeMessageHelper.setTo("hamonicnocturne@gmail.com");
//			mimeMessageHelper.setText("<body>\r\n" + 
//					"	<p>歡迎加入行車發大財</p>\r\n" + 
//					"	<p>您收到這封信件是因為您在行車發大財註冊</p>\r\n" + 
//					"	<p>請點擊以下網址啟動您的帳戶</p>\r\n" + 
//					"	<p>-----------------------------------------------------</p>\r\n" + 
//					""+
//					"	<p>-----------------------------------------------------</p>\r\n" + 
//					"	<p>感謝您的使用，祝您行車發大財</p>\r\n" + 
//					"</body>", true);;
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
//		javaMailSender.send(mimeMessage);
//		return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON_UTF8).body(result);
//	}
}
