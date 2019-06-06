package net.ddns.eeitdemo.eeit106team01.forum.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
