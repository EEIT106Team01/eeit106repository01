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
@Table(name="receipt_overview",indexes = {@Index(columnList = "id,user_basic_id")})
public class ReceiptBasicEntity extends AbstractEntity<Long> implements Serializable{

	private static final long serialVersionUID = -6947601242453363829L;
	private UserBasicEntity userBasicEntity;
	private List<ReceiptDatetimeEntity> receiptDatetimeEntity;
	private List<ReceiptIntegerEntity> receiptIntegerEntities;
	private List<ReceiptNumericEntity> receiptNumericEntity;
	private List<ReceiptTextEntity> receiptTextEntity;
	@OneToMany(mappedBy = "tableInfo",fetch = FetchType.LAZY)
	public List<ReceiptDatetimeEntity> getReceiptDatetimeEntity() {
		return receiptDatetimeEntity;
	}
	public void setReceiptDatetimeEntity(List<ReceiptDatetimeEntity> receiptDatetimeEntity) {
		this.receiptDatetimeEntity = receiptDatetimeEntity;
	}
	@OneToMany(mappedBy = "tableInfo",fetch = FetchType.LAZY)
	public List<ReceiptIntegerEntity> getReceiptIntegerEntities() {
		return receiptIntegerEntities;
	}
	public void setReceiptIntegerEntities(List<ReceiptIntegerEntity> receiptIntegerEntities) {
		this.receiptIntegerEntities = receiptIntegerEntities;
	}
	@OneToMany(mappedBy = "tableInfo",fetch = FetchType.LAZY)
	public List<ReceiptNumericEntity> getReceiptNumericEntity() {
		return receiptNumericEntity;
	}
	public void setReceiptNumericEntity(List<ReceiptNumericEntity> receiptNumericEntity) {
		this.receiptNumericEntity = receiptNumericEntity;
	}
	@OneToMany(mappedBy = "tableInfo",fetch = FetchType.LAZY)
	public List<ReceiptTextEntity> getReceiptTextEntity() {
		return receiptTextEntity;
	}
	public void setReceiptTextEntity(List<ReceiptTextEntity> receiptTextEntity) {
		this.receiptTextEntity = receiptTextEntity;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_basic_id",referencedColumnName = "id",nullable = false)
	public UserBasicEntity getUserBasicEntity() {
		return userBasicEntity;
	}
	public void setUserBasicEntity(UserBasicEntity userBasicEntity) {
		this.userBasicEntity = userBasicEntity;
	}
	
}
