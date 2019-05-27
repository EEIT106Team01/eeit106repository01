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

	@Column(name = "Types")
	private String type;

	@Override
	public String toString() {
		return "DataBean [type=" + type + "]";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
