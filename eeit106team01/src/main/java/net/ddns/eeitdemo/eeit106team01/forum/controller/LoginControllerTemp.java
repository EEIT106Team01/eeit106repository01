package net.ddns.eeitdemo.eeit106team01.forum.controller;

import java.nio.charset.Charset;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.util.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.ddns.eeitdemo.eeit106team01.forum.model.MemberBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.MemberBeanService;

@Controller
public class LoginControllerTemp {
	@Autowired
	private MemberBeanService memberBeanService;

	@PostMapping(path = { "/forum/login" }, produces = { "application/json" })
	public ResponseEntity<?> login(@RequestParam Integer id, HttpSession httpSession, HttpServletResponse response)
			throws JsonProcessingException {
		if (id != null && id != 0) {
			MemberBean result = memberBeanService.login(id);
			if (result != null) {
				httpSession.setAttribute("MemberBean", result);
				MemberBean mb = new MemberBean();
				mb.setId(result.getId());
				mb.setName(result.getName());
				ObjectMapper mapper = new ObjectMapper();
				String jsonStr = mapper.writeValueAsString(mb);
				String encodeJson = new URLEncoder().encode(jsonStr, Charset.forName("UTF-8"));
				Cookie memberBeanCookie = new Cookie("MemberBean", encodeJson);
				response.addCookie(memberBeanCookie);
				return ResponseEntity.ok(mb);
			}
		}
		return ResponseEntity.notFound().build();
	}
}
