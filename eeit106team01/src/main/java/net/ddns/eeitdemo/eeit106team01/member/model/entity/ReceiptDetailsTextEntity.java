package net.ddns.eeitdemo.eeit106team01.member.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Entity
@Table(name = "receipt_detail_text",indexes = {@Index(columnList = "id,table_id"),
		@Index(columnList = "id,granter_id")})
public class ReceiptDetailsTextEntity extends AbstractPropertyEntity<ReceiptDetailsIntegerEntity, TextGranterEntity> implements Serializable{

	private static final long serialVersionUID = -5854105206158951519L;
	private String textContent;
	
	@Column(name="text_content",nullable = false)
	public String getTextContent() {
		return textContent;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
}
