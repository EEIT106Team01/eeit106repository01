package net.ddns.eeitdemo.eeit106team01.forum.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import net.ddns.eeitdemo.eeit106team01.forum.model.MemberBean;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request.getMethod().equalsIgnoreCase("get")) {
			return true;
		} else {
			MemberBean memberBean = (MemberBean) request.getSession().getAttribute("MemberBean");
			if (memberBean != null) {
				return true;
			} else {
				response.setStatus(401);
			}
		}
		return false;
	}

}
