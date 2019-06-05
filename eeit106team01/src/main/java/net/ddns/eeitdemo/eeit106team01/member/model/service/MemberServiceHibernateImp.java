package net.ddns.eeitdemo.eeit106team01.member.model.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ddns.eeitdemo.eeit106team01.member.model.entity.UserBasicEntity;


@Service
@Transactional(rollbackFor = Exception.class)
public class MemberServiceHibernateImp implements MemberService<UserBasicEntity> {

	@Autowired
	SessionFactory factory;
	
	@Override
	public boolean demoRegister(String email, String password, String username) {
		boolean result = false;
		UserBasicEntity userBasicEntity = new UserBasicEntity();
		userBasicEntity.setEmail(email);
		userBasicEntity.setPassword(password);
		userBasicEntity.setUsername(username);
		factory.getCurrentSession().persist(userBasicEntity);
		if(userBasicEntity.getId()!=null)
			result = true;
		return result;
	}
	
}
