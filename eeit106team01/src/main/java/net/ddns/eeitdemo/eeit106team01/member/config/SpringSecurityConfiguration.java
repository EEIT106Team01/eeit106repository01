package net.ddns.eeitdemo.eeit106team01.member.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.google.gson.Gson;

@Configuration
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserDetailsService userDetailsService;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//		auth.inMemoryAuthentication().withUser("john").password(passwordEncoder.encode("john")).authorities("ROLE_USER");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/member/secure.html").hasAuthority("ROLE_USER")
		.antMatchers("/**")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.loginPage("/member/member.html")
		.loginProcessingUrl("/member/login")
		.successHandler(authenticationSuccessHandler())
		.failureHandler(authenticationFailureHandler())
		.and()
		.oauth2Login()
		.loginPage("/member/member.html")
//		.successHandler(authenticationSuccessHandler())
//		.failureHandler(authenticationFailureHandler())
		.and()
		.logout()
		.logoutUrl("/member/logout")
		.clearAuthentication(true)
		.deleteCookies("JSESSIONID")
		.logoutSuccessUrl("/member/logoutSuccess.html")
		.and()
        .csrf()
        .disable()
		;
	}
	
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
			return new SimpleUrlAuthenticationFailureHandler () {
				@Override
				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
						AuthenticationException exception) throws IOException, ServletException {
					SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
					Gson gson = new Gson();
					Map<String,String> map = new HashMap<String,String>();
					response.setContentType("application/json;charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setStatus(400);
					map.put("reason", "fail login");
					 if(savedRequest!=null) {
						 map.put("url",savedRequest.getRedirectUrl());
					 }else {
						 map.put("url","/");
					 }
					 response.getWriter().append(gson.toJson(map)).println();
				}
			};
	}
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new SavedRequestAwareAuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws ServletException, IOException {
				 SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
				 Gson gson = new Gson();
				 Map<String,String> map = new HashMap<String,String>();
				 response.setContentType("application/json;charset=UTF-8");
				 response.setCharacterEncoding("UTF-8");
				 if(savedRequest!=null) {
					 map.put("url",savedRequest.getRedirectUrl());
				 }else {
					 map.put("url","/");
				 }
				 response.getWriter().append(gson.toJson(map)).println();
			}		
		};
	}
	
//	@Bean
//	public AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler() {
//		return new SavedRequestAwareAuthenticationSuccessHandler() {
//			@Override
//			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//					Authentication authentication) throws ServletException, IOException {
//				System.err.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//				HttpSession session = request.getSession();
//				for(Enumeration<String> e = session.getAttributeNames();e.hasMoreElements();) {
//					 String temp = e.nextElement();
//					 System.out.println(temp+" : "+ session.getAttribute(temp));
//				 }
//				 super.onAuthenticationSuccess(request, response, authentication);
//			}		
//		};
//	}
}
