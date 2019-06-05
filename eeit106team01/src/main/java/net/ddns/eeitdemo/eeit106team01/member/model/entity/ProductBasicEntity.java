package net.ddns.eeitdemo.eeit106team01.member.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
@Entity
@Table(name = "product_basic",indexes = {@Index(columnList = "id,product_name")})
public class ProductBasicEntity extends AbstractEntity<Long> implements Serializable{
	private static final long serialVersionUID = -2528074578236767245L;
	private String productName;
	private List<ProductDatetimeEntity> productDatetimeEntities;
	private List<ProductIntegerEntity> productIntegerEntities;
	private List<ProductNumericEntity> productNumericEntities;
	private List<ProductTextEntity> productTextEntities;
	
	@OneToMany(mappedBy = "tableInfo",fetch = FetchType.LAZY)
	public List<ProductDatetimeEntity> getProductDatetimeEntities() {
		return productDatetimeEntities;
	}
	public void setProductDatetimeEntities(List<ProductDatetimeEntity> productDatetimeEntities) {
		this.productDatetimeEntities = productDatetimeEntities;
	}
	@OneToMany(mappedBy = "tableInfo",fetch = FetchType.LAZY)
	public List<ProductIntegerEntity> getProductIntegerEntities() {
		return productIntegerEntities;
	}
	public void setProductIntegerEntities(List<ProductIntegerEntity> productIntegerEntities) {
		this.productIntegerEntities = productIntegerEntities;
	}
	@OneToMany(mappedBy = "tableInfo",fetch = FetchType.LAZY)
	public List<ProductNumericEntity> getProductNumericEntities() {
		return productNumericEntities;
	}
	public void setProductNumericEntities(List<ProductNumericEntity> productNumericEntities) {
		this.productNumericEntities = productNumericEntities;
	}
	@OneToMany(mappedBy = "tableInfo",fetch = FetchType.LAZY)
	public List<ProductTextEntity> getProductTextEntities() {
		return productTextEntities;
	}
	public void setProductTextEntities(List<ProductTextEntity> productTextEntities) {
		this.productTextEntities = productTextEntities;
	}
	@Column(name="product_name",nullable = false,unique = true)
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
}
