package net.ddns.eeitdemo.eeit106team01.chat.model;

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
		return regionMessageDAO.insert(regionMessageBean);
	}

	public RegionMessageBean createNewRecord(RegionMessageBean regionMessageBean) {
		RegionMessageBean newRecord = new RegionMessageBean();
		newRecord.setRegion(regionMessageBean.getRegion());
		newRecord.setIndex(regionMessageBean.getIndex() + 1);
		regionMessageBean.setStatus("inactive");
		regionMessageDAO.update(regionMessageBean);
		return regionMessageDAO.insert(newRecord);
	}

	public List<RegionMessageBean> findAllByRegion(String region) {
		List<RegionMessageBean> result = null;
		if (region != null) {
			String hql = "from RegionMessageBean rmb where rmb.region ='" + region + "'";
			result = regionMessageDAO.queryList(hql, null, null);
		}
		return result;
	}

	public RegionMessageBean findActiveByRegion(String region) {
		RegionMessageBean result = null;
		if (region != null) {
			String hql = "from RegionMessageBean rmb where rmb.region ='" + region + "' and rmb.status='active'";
			List<RegionMessageBean> resultList = regionMessageDAO.queryList(hql, 0, 1);
			if (resultList != null && !resultList.isEmpty()) {
				result = resultList.get(0);
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

	public RegionMessageBean addMessage(String region, RegionMsg message) {
		RegionMessageBean result = null;
		if (region != null) {
			RegionMessageBean regionMessageBean = this.findActiveByRegion(region);
			if (regionMessageBean == null) {
				regionMessageBean = this.createFirstRecord(region);
			}
			regionMessageBean.getMessages().add(message);
			result = regionMessageDAO.update(regionMessageBean);
			if (result != null && result.getMessages().size() > 30) {
				this.createNewRecord(result);
			}
		}
		return result;
	}

}
