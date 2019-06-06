package net.ddns.eeitdemo.eeit106team01.member.model.dao;

import java.util.List;
import java.util.Objects;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.member.model.entity.UserBasicEntity;

@Repository
public class MemberDaoImpHibernate implements MemberDao<UserBasicEntity> {

	@Autowired
	private SessionFactory factory;
	
	@Override
	public UserBasicEntity getUserInformationBeanByEmail(String email) {
		UserBasicEntity userBasicEntity = null;
		String queryString = "from UserBasicEntity where email = :email";
		List<?> list = factory.getCurrentSession().createQuery(queryString).setParameter("email", email).getResultList();
		if(!Objects.equals(list, null) && !Objects.equals(list.size(),0)) {
			userBasicEntity = (UserBasicEntity) list.get(0);
		}
		return userBasicEntity;
	}
}
