package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.nio.charset.Charset;
import java.util.Calendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.util.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class MemberBeanService {

	@Autowired
	private MemberTempDAO memberTempDAO;

	public MemberTempBean login(String name, String password) {
		if ((password != null) && (password.length() != 0)) {
			MemberTempBean bean = findByName(name);
			if (bean != null) {
				if(password.equals(bean.getPassword())) {
					return bean;
				}
			}
		}
		return null;
	}

	public MemberTempBean findByPrimaryKey(int id) {
		return memberTempDAO.findByPrimaryKey(id);
	}

	public MemberTempBean findByPrimaryKeyAsProxy(int id) {
		return memberTempDAO.findByPrimaryKeyAsProxy(id);
	}

	public MemberTempBean findByName(String name) {
		return memberTempDAO.findByName(name);
	}
	
	public MemberTempBean insert(MemberTempBean bean) {
		if (bean != null) {
			return memberTempDAO.insert(bean);
		}
		return null;
	};

	public MemberTempBean updateIgnoreNullColumn(MemberTempBean bean) {
		MemberTempBean findOne = findByPrimaryKey(bean.getId());
		if (findOne != null) {
			if (bean.getName() != null) {
				findOne.setName(bean.getName());
			}
			if (bean.getPassword() != null) {
				findOne.setPassword(bean.getPassword());
			}
			if (bean.getImage() != null) {
				findOne.setImage(bean.getImage());
			}
			if (bean.getEmail() != null) {
				findOne.setEmail(bean.getEmail());
			}
			if (bean.getBirth() != null) {
				findOne.setBirth(bean.getBirth());
			}
			if (bean.getLevel() != null) {
				findOne.setLevel(bean.getLevel());
			}
			if (bean.getLevelTime() != null) {
				findOne.setLevelTime(bean.getLevelTime());
			}
			if (bean.getMemberCreateTime() != null) {
				//不應該在這修改
//				findOne.setMemberCreateTime(bean.getMemberCreateTime());
			}
			
			return memberTempDAO.update(findOne);
		}
		return null;
	};
	
	public MemberTempBean memberTempsLevelup(int id, HttpSession httpSession, HttpServletResponse response) throws JsonProcessingException {
		MemberTempBean findOne = findByPrimaryKey(id);
		if (findOne != null) {
			findOne.setLevel("VIP");
			
			Calendar nowCalendar = Calendar.getInstance();
			nowCalendar.setTime(new java.util.Date());
			nowCalendar.add(Calendar.MONTH, 1); // 比較月份，月份+1
			findOne.setLevelTime(nowCalendar.getTime());
			
			MemberTempBean levelupResult = memberTempDAO.update(findOne);
			httpSession.setAttribute("MemberBean", levelupResult);
			MemberTempBean mb = new MemberTempBean();
			mb.setId(levelupResult.getId());
			mb.setName(levelupResult.getName());
			mb.setEmail(levelupResult.getEmail());
			mb.setLevel(levelupResult.getLevel());
			mb.setLevelTime(levelupResult.getLevelTime());
			mb.setMemberCreateTime(levelupResult.getMemberCreateTime());
			mb.setPhone(levelupResult.getPhone());
			mb.setAddress(levelupResult.getAddress());
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(mb);
			String encodeJson = new URLEncoder().encode(jsonStr, Charset.forName("UTF-8"));
			Cookie memberBeanCookie = new Cookie("MemberBean", encodeJson);
			response.addCookie(memberBeanCookie);
			
			return levelupResult;
		}
		return null;
	};
	
}
