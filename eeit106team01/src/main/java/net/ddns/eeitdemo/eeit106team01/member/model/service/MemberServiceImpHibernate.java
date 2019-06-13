package net.ddns.eeitdemo.eeit106team01.member.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ddns.eeitdemo.eeit106team01.member.model.dao.MemberEntityDao;
import net.ddns.eeitdemo.eeit106team01.member.model.entity.AuthorityEntity;
import net.ddns.eeitdemo.eeit106team01.member.model.entity.MemberEntity;

@Service
@Transactional(noRollbackFor = Exception.class)
public class MemberServiceImpHibernate implements UserDetailsService,MemberService {

	@Autowired
	private MemberEntityDao memberEntityDao;	
	
	//Spring Security Login Solution
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {			
		MemberEntity member = memberEntityDao.getMemberEntityByUsername(username);
		List<AuthorityEntity> list = member.getAuthorities();
		String[] authorities = null;
		if(list!=null && list.size()!=0) {
			Set<String> container = new HashSet<String>(); 
			for(int i=0;i<list.size();i++) {
				AuthorityEntity temp = list.get(i);
				Date expiration =  temp.getExpirationTimestamp();
				Date current = new Date();
				if(current.getTime()<=expiration.getTime()) {
					container.add(temp.getAuthorityName());
				}
			}
			authorities = container.toArray(new String[container.size()]);
		}
		UserDetails result = 
				User.builder()
				.username(member.getUsername())
				.password(member.getPassword())
				.accountExpired(false)
				.accountLocked(false)
				.authorities(authorities)
				.credentialsExpired(false)
				.disabled(false)
				.build();
		return result;
	}

	@Override
	public MemberEntity register(String username, String email, String password) {
		MemberEntity result = new MemberEntity();
		result.setUsername(username);
		result.setEmail(email);
		result.setPassword(password);
		result.setIsThirdPartyMember(false);
		List<AuthorityEntity> list = new ArrayList<AuthorityEntity>();
		AuthorityEntity authority = new AuthorityEntity();
		authority.setAuthorityName("ROLE_USER");
		authority.setExpirationTimestamp(new Date(9191059735261L));
		list.add(authority);
		result.setAuthorities(list);
		return memberEntityDao.persistMemberEntity(result);
	}

}
