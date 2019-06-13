package net.ddns.eeitdemo.eeit106team01.member.model.dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.member.model.entity.MemberEntity;

@Repository
public class MemberEntityDaoImpHibernate implements MemberEntityDao {

	@Autowired
	private DaoUtils daoUtils;
	
	@Autowired
	private SessionFactory factory;
	
	private Session getSession() {
		return factory.getCurrentSession();
	}
	
	@Override
	public MemberEntity getMemberEntityById(Long memberId) {
		return getSession().get(MemberEntity.class, memberId);
	}

	@Override
	public MemberEntity getMemberEntityByUsername(String username) {
		MemberEntity result = null;
		String queryString = "From MemberEntity Where username = :username";
		List<?> list = getSession().createQuery(queryString).setParameter("username", username).getResultList();
		if(list!=null && list.size()!=0)
			result = (MemberEntity)list.get(0);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberEntity> getMemberEntityByEmailAndThirdParty(String email,boolean isThirdPartyMember) {
		List<MemberEntity> result = null;
		String queryString = "From MemberEntity Where email = :email And isThirdPartyMember = :tp";
		List<?> list = getSession().createQuery(queryString)
				.setParameter("email", email)
				.setParameter("tp", isThirdPartyMember)
				.getResultList();
		if(list!=null && list.size()!=0)
			result = (List<MemberEntity>)list;
		return result;
	}

	@Override
	public MemberEntity persistMemberEntity(MemberEntity src) {
		MemberEntity result = null;
		if(src != null)
			try {
				getSession().persist(src);
				result = src;
			} catch (Exception e) {
				e.printStackTrace();
			}
		return result;
	}

	@Override
	public MemberEntity updateNotNullMemberEntity(MemberEntity src) {
		MemberEntity result = getMemberEntityById(src.getMemberId());	
		daoUtils.notNullCopyProperties(src, result);
		getSession().update(result);
		return result;
	}

	@Override
	public boolean removeMemberEntityById(Long memberId) {
		MemberEntity temp = new MemberEntity();
		temp.setMemberId(memberId);
		try {
			getSession().remove(temp);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
