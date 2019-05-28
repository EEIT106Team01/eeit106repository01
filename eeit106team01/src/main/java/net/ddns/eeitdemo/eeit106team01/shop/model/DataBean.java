package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;

public class DataBean implements Serializable {

	private static final long serialVersionUID = -1294292249430188510L;

	private String data;

	@Override
	public String toString() {
		return "DataBean [data=" + data + "]";
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
