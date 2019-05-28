package net.ddns.eeitdemo.eeit106team01.shop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class DataBean implements Serializable {

	private static final long serialVersionUID = -1294292249430188510L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DataID", columnDefinition = "bigint")
	private Long id;

	@Column(name = "Data")
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
