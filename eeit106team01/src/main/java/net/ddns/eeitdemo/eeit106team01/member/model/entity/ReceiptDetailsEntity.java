package net.ddns.eeitdemo.eeit106team01.member.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Entity
@Table(name="receipt_details",indexes = {@Index(columnList = "id,product_basic_id")})
public class ReceiptDetailsEntity extends AbstractEntity<Long> implements Serializable{
	private static final long serialVersionUID = 792467069992618168L;
	private ProductBasicEntity productBasicEntity;
	private List<ReceiptDetailsDatetimeEntity> receiptDetailsDatetimeEntities;
	private List<ReceiptDetailsIntegerEntity> receiptDetailsIntegerEntities;
	private List<ReceiptDetailsNumericEntity> receiptDetailsNumericEntities;
	private List<ReceiptDetailsTextEntity> receiptDetailsTextEntities;
	
	@OneToMany(mappedBy = "tableInfo",fetch = FetchType.LAZY)
	public List<ReceiptDetailsDatetimeEntity> getReceiptDetailsDatetimeEntities() {
		return receiptDetailsDatetimeEntities;
	}
	public void setReceiptDetailsDatetimeEntities(List<ReceiptDetailsDatetimeEntity> receiptDetailsDatetimeEntities) {
		this.receiptDetailsDatetimeEntities = receiptDetailsDatetimeEntities;
	}
	@OneToMany(mappedBy = "tableInfo",fetch = FetchType.LAZY)
	public List<ReceiptDetailsIntegerEntity> getReceiptDetailsIntegerEntities() {
		return receiptDetailsIntegerEntities;
	}
	public void setReceiptDetailsIntegerEntities(List<ReceiptDetailsIntegerEntity> receiptDetailsIntegerEntities) {
		this.receiptDetailsIntegerEntities = receiptDetailsIntegerEntities;
	}
	@OneToMany(mappedBy = "tableInfo",fetch = FetchType.LAZY)
	public List<ReceiptDetailsNumericEntity> getReceiptDetailsNumericEntities() {
		return receiptDetailsNumericEntities;
	}
	public void setReceiptDetailsNumericEntities(List<ReceiptDetailsNumericEntity> receiptDetailsNumericEntities) {
		this.receiptDetailsNumericEntities = receiptDetailsNumericEntities;
	}
	@OneToMany(mappedBy = "tableInfo",fetch = FetchType.LAZY)
	public List<ReceiptDetailsTextEntity> getReceiptDetailsTextEntities() {
		return receiptDetailsTextEntities;
	}
	public void setReceiptDetailsTextEntities(List<ReceiptDetailsTextEntity> receiptDetailsTextEntities) {
		this.receiptDetailsTextEntities = receiptDetailsTextEntities;
	}
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name="product_basic_id",referencedColumnName = "id",nullable = false)
	public ProductBasicEntity getProductBasicEntity() {
		return productBasicEntity;
	}
	public void setProductBasicEntity(ProductBasicEntity productBasicEntity) {
		this.productBasicEntity = productBasicEntity;
	}
	
}
