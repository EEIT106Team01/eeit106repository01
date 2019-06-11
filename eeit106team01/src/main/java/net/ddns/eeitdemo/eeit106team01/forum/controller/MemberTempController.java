package net.ddns.eeitdemo.eeit106team01.forum.controller;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.util.URLEncoder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.ddns.eeitdemo.eeit106team01.forum.model.MemberTempBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.MemberBeanService;

@Controller
public class MemberTempController {

	@Autowired
	private MemberBeanService memberBeanService;

	@GetMapping(path = { "/memberTemps/{id}" }, produces = { "application/json" })
	public ResponseEntity<?> getMember(@PathVariable(name = "id") int id) {
		System.out.println("getMember method running");
		MemberTempBean findOne = memberBeanService.findByPrimaryKey(id);
		if (findOne != null) {
			findOne.setPassword(null);
			return ResponseEntity.ok(findOne);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(path = { "/checkMemberName" }, produces = { "application/json" })
	public ResponseEntity<?> checkMemberName(@RequestParam String name) {
		System.out.println("checkMemberName method running");
		if ((name != null) && (name.length() != 0)) {
			MemberTempBean checkResult = memberBeanService.findByName(name);
			if (checkResult != null) {
				MemberTempBean resultTemp = new MemberTempBean();
				resultTemp.setName(checkResult.getName());
				return ResponseEntity.ok(resultTemp);
			}
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping(path = { "/getMemberTempsImages/{id}" }, produces = { "application/json" })
	public ResponseEntity<?> getMemberImage(@PathVariable(name = "id") int id) {
		System.out.println("getMemberImage method running");
		MemberTempBean findOne = memberBeanService.findByPrimaryKey(id);
		if (findOne != null) {
			MemberTempBean resultTemp = new MemberTempBean();
			resultTemp.setId(findOne.getId());
			resultTemp.setImage(findOne.getImage());
			return ResponseEntity.ok(resultTemp);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(path = { "/memberTemps" }, consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<?> postMemberTemp(@RequestBody MemberTempBean requestbody, BindingResult bindingResult,
			HttpSession httpSession, HttpServletResponse response) throws JsonProcessingException {
		System.out.println("postMemberTemp method running");
		if (requestbody.getName() != null) {
			MemberTempBean checkResult = memberBeanService.findByName(requestbody.getName());
			if (checkResult != null) {
				return ResponseEntity.noContent().build();
			}
		}

		Date nowDate = new Date();
		System.err.println(nowDate);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(nowDate);
		calendar.add(Calendar.DATE, 1);
		Date levelTime = calendar.getTime();
		System.err.println(levelTime);

		requestbody.setLevel("normal");
		requestbody.setLevelTime(levelTime);
		requestbody.setMemberCreateTime(nowDate);

		MemberTempBean insertResult = memberBeanService.insert(requestbody);
		if (insertResult != null) {
			httpSession.setAttribute("MemberBean", insertResult);
			MemberTempBean mb = new MemberTempBean();
			mb.setId(insertResult.getId());
			mb.setName(insertResult.getName());
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(mb);
			String encodeJson = new URLEncoder().encode(jsonStr, Charset.forName("UTF-8"));
			Cookie memberBeanCookie = new Cookie("MemberBean", encodeJson);
			response.addCookie(memberBeanCookie);
			return ResponseEntity.ok(mb);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@PutMapping(path = { "/memberTemps/{id}" }, consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<?> updateMemberTemps(@PathVariable(name = "id") int id,
			@RequestBody MemberTempBean requestbody, HttpSession httpSession, HttpServletResponse response)
			throws JsonProcessingException {
		System.out.println("updateMemberTemp method running");
		System.out.println(requestbody.toString());
		if (id == requestbody.getId().intValue()) {
//			requestbody.setTopicUpdateTime(new java.util.Date());
//			requestbody.setUpdateMessage("使用者修改");
			MemberTempBean updateResult = memberBeanService.updateIgnoreNullColumn(requestbody);
			if (updateResult != null) {
				httpSession.setAttribute("MemberBean", updateResult);
				MemberTempBean mb = new MemberTempBean();
				mb.setId(updateResult.getId());
				mb.setName(updateResult.getName());
				ObjectMapper mapper = new ObjectMapper();
				String jsonStr = mapper.writeValueAsString(mb);
				String encodeJson = new URLEncoder().encode(jsonStr, Charset.forName("UTF-8"));
				Cookie memberBeanCookie = new Cookie("MemberBean", encodeJson);
				response.addCookie(memberBeanCookie);
				return ResponseEntity.ok(mb);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping(path = { "/forumlogin" }, produces = { "application/json" })
	public ResponseEntity<?> login(@RequestParam String name, @RequestParam String password, HttpSession httpSession,
			HttpServletResponse response) throws JsonProcessingException {
		System.out.println("login method running");
		System.out.println("帳號： " + name);
		System.out.println("密碼： " + password);
		if ((name != null && name.length() != 0) && (password != null && password.length() != 0)) {
			MemberTempBean result = memberBeanService.login(name, password);
			if (result != null) {
				httpSession.setAttribute("MemberBean", result);
				MemberTempBean mb = new MemberTempBean();
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

	@GetMapping(path = { "/forumlogout" }, produces = { "application/json" })
	public ResponseEntity<?> logout(HttpSession httpSession, HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {
		System.err.println("logout method running");
//		httpSession.invalidate();
		httpSession.removeAttribute("MemberBean");

		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			if ("MemberBean".equals(cookies[i].getName())) {
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
				return ResponseEntity.ok().build();
			}
		}

		Cookie emptyMemberCookie = new Cookie("MemberBean", null);
		emptyMemberCookie.setMaxAge(0);
		response.addCookie(emptyMemberCookie);

		return ResponseEntity.ok().build();

//		ObjectMapper mapper = new ObjectMapper();
//		String jsonStr = mapper.writeValueAsString(mb);
//		String encodeJson = new URLEncoder().encode(jsonStr, Charset.forName("UTF-8"));
//		Cookie memberBeanCookie = new Cookie("MemberBean", encodeJson);
//		response.addCookie(memberBeanCookie);

//		return ResponseEntity.notFound().build();
	}

//	@PostMapping(path = { "/memberTempsImage" }, produces = { "application/json" })
//	public ResponseEntity<?> uploadImage(@RequestParam String imageInput, HttpSession httpSession)
//			throws IOException {
//		System.out.println("uploadImage method running");
//		StringBuilder sb = new StringBuilder();
//		if (!imageFile.isEmpty()) {
//			if ("image/png".equals(imageFile.getContentType())) {
//				sb.append("data:image/png;base64,");
//			} else if ("image/jpeg".equals(imageFile.getContentType())) {
//				sb.append("data:image/jpeg;base64,");
//			} else {
//				return ResponseEntity.badRequest().build();
//			}
//			sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(imageFile.getBytes(), false)));
//		}
//		System.out.println(sb);
//		return ResponseEntity.ok().build();
//	}

}
