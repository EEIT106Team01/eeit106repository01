package net.ddns.eeitdemo.eeit106team01.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Shop")
public class ShopBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7328307529164949742L;
	
	@Id
	private Integer id;
	private String name;
	private Integer price;
	private String imgLink;
	private String imgSetLink;
	private String platformName;
	private String platformLink;
	private Integer totalCount;
	private Integer totalPage;

	@Override
	public String toString() {
		return "ShopBean [id=" + id + ", name=" + name + ", price=" + price + ", imgLink=" + imgLink + ", imgSetLink="
				+ imgSetLink + ", platformName=" + platformName + ", platformLink=" + platformLink + ", totalCount="
				+ totalCount + ", totalPage=" + totalPage + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(String price) {
		Integer result = Integer.parseInt(price);
		this.price = result;
	}

	public String getImgLink() {
		return imgLink;
	}

	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}

	public String getImgSetLink() {
		return imgSetLink;
	}

	public void setImgSetLink(String imgSetLink) {
		this.imgSetLink = imgSetLink;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getPlatformLink() {
		return platformLink;
	}

	public void setPlatformLink(String platformLink) {
		this.platformLink = platformLink;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		Integer result = Integer.parseInt(totalCount);
		this.totalCount = result;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		Integer result = Integer.parseInt(totalPage);
		this.totalPage = result;
	}

}
