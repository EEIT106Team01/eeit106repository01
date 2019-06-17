package net.ddns.eeitdemo.eeit106team01.member.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ddns.eeitdemo.eeit106team01.member.model.dao.AuthorityEntityDao;
import net.ddns.eeitdemo.eeit106team01.member.model.dao.MemberEntityDao;
import net.ddns.eeitdemo.eeit106team01.member.model.entity.AuthorityEntity;
import net.ddns.eeitdemo.eeit106team01.member.model.entity.MemberEntity;

@Service
@Transactional(noRollbackFor = Exception.class)
public class MemberServiceImpHibernate implements UserDetailsService,MemberService {

	@Autowired
	private MemberEntityDao memberEntityDao;
	
	@Autowired
	private AuthorityEntityDao authorityEntityDao;
	
	//Spring Security Login Solution
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {			
		MemberEntity member = memberEntityDao.getMemberEntityByUsername(username);
		Set<String> authnames = notExpiredAuthorities(member);
		String[] authorities = authnames.toArray(new String[authnames.size()]);
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
	public boolean forgetPassword(String username, String email,String tempPassword) {
		MemberEntity entity = memberEntityDao.getMemberEntityByUsername(username);
		if(entity!=null) {
			if(email.equals(entity.getEmail())) {
				entity.setPassword(tempPassword);
				entity = memberEntityDao.persistMemberEntity(entity);
				return true;
			}
		}
		return false;
	}


	@Override
	public String register(String username, String email, String password) {
		MemberEntity bean = new MemberEntity();
		bean.setUsername(username);
		bean.setEmail(email);
		bean.setPassword(password);
		List<AuthorityEntity> list = new ArrayList<AuthorityEntity>();
		AuthorityEntity authority = new AuthorityEntity();
		UUID temp = UUID.randomUUID();
		authority.setAuthorityName("UNCONFIRM");
		authority.setAuthorityUuid(temp.toString());
		authority.setExpirationTimestamp(new Date(System.currentTimeMillis()+86400000L));
		authority.setMember(bean);
		list.add(authority);
		bean.setAuthorities(list);
		MemberEntity entity = memberEntityDao.persistMemberEntity(bean);
		if(entity!= null)
			return temp.toString();
		else
			return null;
	}

	@Override
	public MemberEntity registerConfirm(String uuid) {
		AuthorityEntity entity = authorityEntityDao.getAuthorityEntityByUuid(uuid);
		MemberEntity result = null;
		if(entity!=null) {
			result = entity.getMember();
			List<AuthorityEntity> list = new ArrayList<AuthorityEntity>();
			AuthorityEntity authorityEntity= new AuthorityEntity();
			authorityEntity.setAuthorityName("ROLE_USER");
			authorityEntity.setAuthorityUuid(UUID.randomUUID().toString());
			authorityEntity.setExpirationTimestamp(new Date(99999999999999L));
			authorityEntity.setMember(result);
			list.add(authorityEntity);
			result.setAuthorities(list);
			result= memberEntityDao.persistMemberEntity(result);
		}
		return result;
	}

	@Override
	public boolean isAuthorityNotExpired(MemberEntity bean, String authorityName) {
		List<AuthorityEntity> list = bean.getAuthorities();
		for(int i=0;i<list.size();i++) {
			AuthorityEntity temp = list.get(i);
			if(authorityName.equals(temp.getAuthorityName())) {
				Date expiration =  temp.getExpirationTimestamp();
				Date current = new Date();
				if(current.getTime()<=expiration.getTime()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Set<String> notExpiredAuthorities(MemberEntity bean) {
		List<AuthorityEntity> list = bean.getAuthorities();
		Set<String> container = null;
		if(list!=null && list.size()!=0) {
			container = new HashSet<String>(); 
			for(int i=0;i<list.size();i++) {
				AuthorityEntity temp = list.get(i);
				Date expiration =  temp.getExpirationTimestamp();
				Date current = new Date();
				if(current.getTime()<=expiration.getTime()) {
					container.add(temp.getAuthorityName());
				}
			}
		}
		return container;
	}

}
