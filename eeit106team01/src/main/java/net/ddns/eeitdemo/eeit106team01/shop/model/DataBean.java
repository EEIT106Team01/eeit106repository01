package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;

public class DataBean implements Serializable {

	private static final long serialVersionUID = -1294292249430188510L;

	private String data;
	private Long id;
	private Integer count;

	@Override
	public String toString() {
		return "DataBean [data=" + data + ", id=" + id + ", count=" + count + "]";
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
