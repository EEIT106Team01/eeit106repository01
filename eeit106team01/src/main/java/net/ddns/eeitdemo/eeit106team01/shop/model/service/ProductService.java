package net.ddns.eeitdemo.eeit106team01.shop.model.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ddns.eeitdemo.eeit106team01.shop.model.ProductBean;
import net.ddns.eeitdemo.eeit106team01.shop.model.dao.ProductDAO;

@Service
@Transactional
public class ProductService {
	@Autowired
	private ProductDAO productDAO;

	public ProductBean insertProduct() {
		ProductBean result = null;

		return result;
	};

	public ProductBean findProductByPrimaryKey(Long id) {
		return productDAO.findProductByPrimaryKey(id);
	};

	public ProductBean updateProduct(ProductBean productBean) {
		ProductBean findOne = findProductByPrimaryKey(productBean.getId());
		if (findOne != null) {
			if (productBean.getBrand() != null) {
				findOne.setBrand(productBean.getBrand());
			}
			if (productBean.getUpdatedTime() != null) {
				findOne.setUpdatedTime();
			}
			if (productBean.getDescription() != null) {
				findOne.setDescription(productBean.getDescription());
			}
			if (productBean.getImageLink() != null) {
				findOne.setImageLink(productBean.getImageLink());
			}
			if (productBean.getInformation() != null) {
				findOne.setInformation(productBean.getInformation());
			}
			if (productBean.getName() != null) {
				findOne.setName(productBean.getName());
			}
			if (productBean.getPrice() != null) {
				findOne.setPrice(productBean.getPrice());
			}
			if (productBean.getType() != null) {
				findOne.setType(productBean.getType());
			}
			if (productBean.getCreateTime() != null) {
				findOne.setCreateTime();
			}
			return productDAO.updateProduct(findOne);
		}
		return null;
	}

//	@SuppressWarnings("unchecked")
//	public List<ProductBean> recommendProducts (String name) {
//		ProductBean findOne = productDAO.findProductsByName(name);
//		if(findOne != null) {
//			List<ProductBean> rp = productDAO.findProductsByType(findOne.getType());
//			Query query = this.getSession().createQuery("select top(10) from SerialNumberBean s join ProductBean p where s.availabilityStatus = sold and p.type = :type order by totalSold desc",ProductBean.class);
//			query.setParameter("id", "id");
//			query.setParameter("type", findOne.getType());
//			return query.getResultList();
//		}
//		return null;
//	}

}
