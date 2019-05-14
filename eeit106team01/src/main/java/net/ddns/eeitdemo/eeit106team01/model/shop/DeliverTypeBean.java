package net.ddns.eeitdemo.eeit106team01.model.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="DeliverType")
public class DeliverTypeBean implements Serializable{

	private static final long serialVersionUID = 2054965599453849024L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer Id;
	private String Name;
	private Integer Price;
	@OneToMany(mappedBy = "deliverType", cascade = CascadeType.ALL)
	private List<DeliverTypeBean> deliverTypeList = new ArrayList<>();
	
	public DeliverTypeBean() {
		super();
	}

	public DeliverTypeBean(Integer id, String name, Integer price, List<DeliverTypeBean> deliverTypeList) {
		super();
		Id = id;
		Name = name;
		Price = price;
		this.deliverTypeList = deliverTypeList;
	}
	
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Integer getPrice() {
		return Price;
	}

	public void setPrice(Integer price) {
		Price = price;
	}

	public List<DeliverTypeBean> getDeliverTypeList() {
		return deliverTypeList;
	}

	public void setDeliverTypeList(List<DeliverTypeBean> deliverTypeList) {
		this.deliverTypeList = deliverTypeList;
	}
}
