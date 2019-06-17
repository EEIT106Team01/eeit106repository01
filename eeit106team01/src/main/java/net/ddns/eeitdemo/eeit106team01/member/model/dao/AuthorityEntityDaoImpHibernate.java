package net.ddns.eeitdemo.eeit106team01.member.model.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ddns.eeitdemo.eeit106team01.member.model.entity.AuthorityEntity;
@Repository
public class AuthorityEntityDaoImpHibernate implements AuthorityEntityDao{

	@Autowired
	private SessionFactory factory;
	
	@Autowired
	private DaoUtils daoUtils;
	
	private Session getSession() {
		return factory.getCurrentSession();
	}
	
	@Override
	public AuthorityEntity getAuthorityEntityById(Long id) {
		return getSession().get(AuthorityEntity.class,id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthorityEntity> getAuthorityEntityByAuthorityName(String authorityName) {
		List<AuthorityEntity> result = null;
		String queryString = "From AuthorityEntity Where authorityName = :authorityName";
		List<?> list = getSession().createQuery(queryString)
				.setParameter("authorityName", authorityName).getResultList();
		if(list!=null && list.size()!=0) {
			result = (List<AuthorityEntity>)list;
		}
		return result;
	}

	@Override
	public List<AuthorityEntity> getAuthorityEntityNotExpirated() {
		return getAuthorityEntityNotExpirated(new Date());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthorityEntity> getAuthorityEntityNotExpirated(Date expirationTimestamp) {
		List<AuthorityEntity> result = null;
		String queryString = "From AuthorityEntity Where expirationTimestamp >= :expirationTimestamp";
		List<?> list = getSession().createQuery(queryString)
				.setParameter("expirationTimestamp", expirationTimestamp).getResultList();
		if(list!=null && list.size()!=0) {
			result = (List<AuthorityEntity>)list;
		}
		return result;
	}

	@Override
	public AuthorityEntity persistAuthorityEntity(AuthorityEntity src) {
		AuthorityEntity result = null;
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
	public AuthorityEntity updateAuthorityEntity(AuthorityEntity src) {
		AuthorityEntity result = getAuthorityEntityById(src.getAuthorityId());	
		daoUtils.notNullCopyProperties(src, result);
		getSession().update(result);
		return result;
	}

	@Override
	public boolean removeAuthorityEntity(Long authorityId) {
		AuthorityEntity temp = new AuthorityEntity();
		temp.setAuthorityId(authorityId);
		try {
			getSession().remove(temp);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public AuthorityEntity getAuthorityEntityByUuid(String uuid) {
		AuthorityEntity result = null;
		String queryString = "From AuthorityEntity Where authorityUuid = :authorityUuid";
		List<?> list = getSession().createQuery(queryString)
				.setParameter("authorityUuid", uuid).getResultList();
		if(list!=null && list.size()!=0) {
			result = (AuthorityEntity)list.get(0);
		}
		return result;
	}

	
}
