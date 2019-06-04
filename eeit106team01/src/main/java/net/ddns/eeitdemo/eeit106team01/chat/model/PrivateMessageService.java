package net.ddns.eeitdemo.eeit106team01.chat.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PrivateMessageService {
	@Autowired
	private PrivateMessageDAO privateMessageDAO;
//	@Autowired
//	private PrivateMessageListDAO privateMessageListDAO;

	public PrivateMessageBean createFirstRecord(String userOne, String userTwo) {
		PrivateMessageBean privateMessageBean = new PrivateMessageBean();
		privateMessageBean.setUserOne(userOne);
		privateMessageBean.setUserTwo(userTwo);
		privateMessageBean.setIndex(1);
		return privateMessageDAO.insert(privateMessageBean);
	}

	public PrivateMessageBean createNewRecord(PrivateMessageBean privateMessageBean) {
		PrivateMessageBean newRecord = new PrivateMessageBean();
		newRecord.setUserOne(privateMessageBean.getUserOne());
		newRecord.setUserTwo(privateMessageBean.getUserTwo());
		newRecord.setIndex(privateMessageBean.getIndex() + 1);
		privateMessageBean.setStatus("inactive");
		List<PrivateMsg> oldMessages = privateMessageBean.getMessages();
		int size = oldMessages.size();
		for (int i = size - 1; i >= size - 10; i--) {
			newRecord.getMessages().add(oldMessages.get(i));
			oldMessages.remove(i);
		}
		privateMessageDAO.update(privateMessageBean);
		return privateMessageDAO.insert(newRecord);
	}

//	public PrivateMessageListBean createPrivateMessageListRecord(String username) {
//		PrivateMessageListBean privateMessageListBean = new PrivateMessageListBean();
//		privateMessageListBean.setName(username);
//		return privateMessageListDAO.insert(privateMessageListBean);
//	}
//
//	public ArrayList<String> getContactList(String username) {
//		ArrayList<String> result = null;
//		PrivateMessageListBean privateMessageListBean = privateMessageListDAO.findByUser(username);
//		if (privateMessageListBean != null) {
//			result = privateMessageListBean.getContactList();
//		} else {
//			PrivateMessageListBean insert = this.createPrivateMessageListRecord(username);
//			if (insert != null) {
//				result = insert.getContactList();
//			}
//		}
//		return result;
//	}
//
//	public ArrayList<String> addContact(String username, String contact) {
//		ArrayList<String> result = null;
//		PrivateMessageListBean privateMessageListBean = privateMessageListDAO.findByUser(username);
//		if (privateMessageListBean != null && privateMessageListBean.getContactList() != null) {
//			privateMessageListBean.getContactList().add(contact);
//			PrivateMessageListBean updated = privateMessageListDAO.update(privateMessageListBean);
//			if (updated != null && updated.getContactList() != null) {
//				result = updated.getContactList();
//			}
//		}
//		return result;
//	}

	public List<PrivateMessageBean> findAllActiveByUser(String username) {
		List<PrivateMessageBean> results = null;
		String hql = "from PrivateMessageBean pmb where (pmb.userOne='" + username + "' or pmb.userTwo='" + username
				+ "') and (pmb.status='active')";
		results = privateMessageDAO.queryList(hql, null, null);
		return results;
	}

	public PrivateMessageBean findByUsersAndIndex(String userOne, String userTwo, Integer index) {
		PrivateMessageBean result = null;
		List<PrivateMessageBean> results = null;
		String hql = "from PrivateMessageBean pmb where (pmb.userOne='" + userOne + "' and pmb.userTwo='" + userTwo
				+ "') or (pmb.userOne='" + userTwo + "' and pmb.userTwo='" + userOne + "')";
		if (index != null && index != 0) {
			hql = hql + " and (pmb.index='" + index + "')";
			results = privateMessageDAO.queryList(hql, null, null);
		} else {
			hql = hql + " order by pmb.index desc";
			results = privateMessageDAO.queryList(hql, 0, 1);
		}
		if (results != null && !results.isEmpty()) {
			result = results.get(0);
		}
		return result;
	}

	public PrivateMessageBean addMessage(String userOne, String userTwo, PrivateMsg privateMsg) {
		PrivateMessageBean result = null;
		PrivateMessageBean pmb = this.findByUsersAndIndex(userOne, userTwo, null);
		if (pmb == null) {
			pmb = this.createFirstRecord(userOne, userTwo);
		}
		pmb.getMessages().add(privateMsg);
		result = privateMessageDAO.update(pmb);
		if (result != null && result.getMessages().size() >= 30) {
			this.createNewRecord(result);
		}
		return result;
	}

	public PrivateMessageBean addOfflineMessage(String userOne, String userTwo, String offlineUser,
			PrivateMsg privateMsg) {
		PrivateMessageBean result = null;
		PrivateMessageBean pmb = this.findByUsersAndIndex(userOne, userTwo, null);
		if (pmb == null) {
			pmb = this.createFirstRecord(userOne, userTwo);
		}
		if (pmb.getUserOne().equals(offlineUser)) {
			pmb.getUserOneOfflineMessages().add(privateMsg);
		} else if (pmb.getUserTwo().equals(offlineUser)) {
			pmb.getUserTwoOfflineMessages().add(privateMsg);
		}
		result = privateMessageDAO.update(pmb);
		return result;
	}

	public PrivateMessageBean clearOfflineMessage(String userOne, String userTwo, String offlineUser) {
		PrivateMessageBean result = null;
		PrivateMessageBean pmb = this.findByUsersAndIndex(userOne, userTwo, null);
		if (pmb != null) {
			if (pmb.getUserOne().equals(offlineUser)) {
				pmb.getMessages().addAll(pmb.getUserOneOfflineMessages());
				pmb.setUserOneOfflineMessages(new ArrayList<PrivateMsg>());
			} else if (pmb.getUserTwo().equals(offlineUser)) {
				pmb.getMessages().addAll(pmb.getUserTwoOfflineMessages());
				pmb.setUserTwoOfflineMessages(new ArrayList<PrivateMsg>());
			}
			result = privateMessageDAO.update(pmb);
		}
		return result;
	}

}
