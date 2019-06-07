package net.ddns.eeitdemo.eeit106team01.websocket.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationService {

	@Autowired
	private NotificationDAO notificationDAO;

	public NotificationBean createFirstRecord(String name) {
		NotificationBean notificationBean = new NotificationBean();
		notificationBean.setName(name);
		notificationBean.setIndex(1);
		return notificationDAO.insert(notificationBean);
	}

	public NotificationBean createNewRecord(NotificationBean notificationBean) {
		NotificationBean newRecord = new NotificationBean();
		newRecord.setName(notificationBean.getName());
		newRecord.setIndex(notificationBean.getIndex() + 1);
		notificationBean.setStatus("inactive");
		List<NotificationMsg> oldMessages = notificationBean.getMessages();
		int size = oldMessages.size();
		for (int i = size - 1; i >= size - 5; i--) {
			newRecord.getMessages().add(oldMessages.get(i));
			oldMessages.remove(i);
		}
		notificationDAO.update(notificationBean);
		return notificationDAO.insert(newRecord);
	}

	public NotificationBean findActiveByUser(String username) {
		NotificationBean result = null;
		List<NotificationBean> results = null;
		String hql = "from NotificationBean nb where (nb.name='" + username + "') and (nb.status='active')";
		results = notificationDAO.queryList(hql, null, null);
		if (results != null && !results.isEmpty()) {
			result = results.get(0);
		}
		return result;
	}

	public NotificationBean findByUsersAndIndex(String username, Integer index) {
		NotificationBean result = null;
		List<NotificationBean> results = null;
		String hql = "from NotificationBean nb where (nb.name='" + username + "') and (nb.index='" + index + "')";
		results = notificationDAO.queryList(hql, null, null);
		if (results != null && !results.isEmpty()) {
			result = results.get(0);
		}
		return result;
	}

	public NotificationBean addMessage(String username, NotificationMsg notificationMsg) {
		NotificationBean result = null;
		NotificationBean notificationBean = this.findActiveByUser(username);
		if (notificationBean == null) {
			notificationBean = this.createFirstRecord(username);
		}
		notificationBean.getMessages().add(notificationMsg);
		result = notificationDAO.update(notificationBean);
		if (result != null && result.getMessages().size() >= 10) {
			this.createNewRecord(result);
		}
		return result;
	}

	public NotificationBean addOfflineMessage(String username, NotificationMsg notificationMsg) {
		NotificationBean result = null;
		NotificationBean notificationBean = this.findActiveByUser(username);
		if (notificationBean == null) {
			notificationBean = this.createFirstRecord(username);
		}
		notificationBean.getOfflineMessages().add(notificationMsg);
		result = notificationDAO.update(notificationBean);
		return result;
	}

	public NotificationBean clearOfflineMessage(String username) {
		NotificationBean result = null;
		NotificationBean notificationBean = this.findActiveByUser(username);
		if (notificationBean != null) {
			notificationBean.getMessages().addAll(notificationBean.getOfflineMessages());
			notificationBean.setOfflineMessages(new ArrayList<NotificationMsg>());
			result = notificationDAO.update(notificationBean);
		}
		return result;
	}
}
