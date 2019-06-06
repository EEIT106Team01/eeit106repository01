package net.ddns.eeitdemo.eeit106team01.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.ddns.eeitdemo.eeit106team01.member.model.dao.MemberDao;
import net.ddns.eeitdemo.eeit106team01.member.model.entity.UserBasicEntity;

@Component
public class CustomizedUserDetailsService implements UserDetailsService {

//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MemberDao<UserBasicEntity> memberDao;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//username actually is email	
		UserBasicEntity bean = memberDao.getUserInformationBeanByEmail(username);		
		UserDetails result = 
				User.builder()
				.username(bean.getEmail())
				.password(bean.getPassword())
				.accountExpired(false)
				.accountLocked(false)
				.authorities("admin")
				.credentialsExpired(false)
				.disabled(false)
				.build();
		return result;
	}

}
