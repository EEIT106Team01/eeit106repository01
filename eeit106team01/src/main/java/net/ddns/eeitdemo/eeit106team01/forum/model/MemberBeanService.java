package net.ddns.eeitdemo.eeit106team01.forum.model;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

	public MemberTempBean updateIgnoreNullColumn(MemberTempBean bean) {
		MemberTempBean findOne = findByPrimaryKey(bean.getId());
		if (findOne != null) {
			if (bean.getName() != null) {
				findOne.setName(bean.getName());
			}
			if (bean.getPassword() != null) {
				findOne.setPassword(bean.getPassword());
			}
			if (bean.getImage() != null) {
				findOne.setImage(bean.getImage());
			}
			if (bean.getEmail() != null) {
				findOne.setEmail(bean.getEmail());
			}
			if (bean.getBirth() != null) {
				findOne.setBirth(bean.getBirth());
			}
			if (bean.getLevel() != null) {
				findOne.setLevel(bean.getLevel());
			}
			if (bean.getLevelTime() != null) {
				findOne.setLevelTime(bean.getLevelTime());
			}
			if (bean.getMemberCreateTime() != null) {
				//不應該在這修改
//				findOne.setMemberCreateTime(bean.getMemberCreateTime());
			}
			
			return memberTempDAO.update(findOne);
		}
		return null;
	};
	
	public MemberTempBean memberTempsLevelup(int id) {
		MemberTempBean findOne = findByPrimaryKey(id);
		if (findOne != null) {
			findOne.setLevel("VIP");
			
			Calendar nowCalendar = Calendar.getInstance();
			nowCalendar.setTime(new java.util.Date());
			nowCalendar.add(Calendar.MONTH, 1); // 比較月份，月份+1
			findOne.setLevelTime(nowCalendar.getTime());
			
			return memberTempDAO.update(findOne);
		}
		return null;
	};
	
}
