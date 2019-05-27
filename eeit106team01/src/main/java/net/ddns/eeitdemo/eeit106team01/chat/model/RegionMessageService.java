package net.ddns.eeitdemo.eeit106team01.chat.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegionMessageService {

	@Autowired
	private RegionMessageDAO regionMessageDAO;
	
	public RegionMessageBean createFirstRecord(String region) {
		RegionMessageBean regionMessageBean = new RegionMessageBean();
		regionMessageBean.setRegion(region);
		regionMessageBean.setIndex(1);
		regionMessageBean.setMessage(new ArrayList<RegionMessage>());
		return regionMessageDAO.insert(regionMessageBean);
	}

	public List<RegionMessageBean> findAllByRegion(String region) {
		List<RegionMessageBean> result = null;
		if (region != null) {
			String hql = "from RegionMessageBean rmb where rmb.region ='" + region + "'";
			result = regionMessageDAO.queryList(hql, null, null);
		}
		return result;
	}

	public RegionMessageBean findNewestByRegion(String region) {
		RegionMessageBean result = null;
		if (region != null) {
			String hql = "from RegionMessageBean rmb where rmb.region ='" + region + "' order by rmb.id desc";
			List<RegionMessageBean> resultList = regionMessageDAO.queryList(hql, 0, 1);
			if (resultList != null && !resultList.isEmpty()) {
				result = resultList.get(0);
			} else {
				return this.createFirstRecord(region);
			}
		}
		return result;
	}

	public RegionMessageBean findByRegionAndIndex(String region, Integer index) {
		RegionMessageBean result = null;
		if (region != null) {
			String hql = "from RegionMessageBean rmb where rmb.region ='" + region + "' and rmb.index ='" + index + "'";
			List<RegionMessageBean> resultList = regionMessageDAO.queryList(hql, null, null);
			if (resultList != null && !resultList.isEmpty()) {
				result = resultList.get(0);
			}
		}
		return result;
	}

	public RegionMessageBean insertMessage(RegionMessageBean regionMessageBean, RegionMessage message) {
		RegionMessageBean result = null;
		if (regionMessageBean != null) {
			ArrayList<RegionMessage> messages = regionMessageBean.getMessage();
			if (messages != null) {
				if (messages.size() < 30) {
					messages.add(message);
					regionMessageBean.setMessage(messages);
					result = regionMessageDAO.update(regionMessageBean);
				} else {
					RegionMessageBean newRecord = regionMessageBean.generateNewRecord();
					newRecord.getMessage().add(message);
					result = regionMessageDAO.insert(newRecord);
					System.out.println(result.getMessage().get(0).getMessage());
				}
			}
		}
		return result;
	}

}
