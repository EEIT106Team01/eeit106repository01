package net.ddns.eeitdemo.eeit106team01.member.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Entity
@Table(name = "receipt_numeric",indexes = {@Index(columnList = "id,table_id"),
		@Index(columnList = "id,granter_id")})
public class ReceiptNumericEntity extends AbstractPropertyEntity<ReceiptBasicEntity, NumericGranterEntity> implements Serializable {
	private static final long serialVersionUID = 9035877049684854538L;
	private BigDecimal numericContent;
	@Column(name="numeric_content",nullable = false)
	public BigDecimal getNumericContent() {
		return numericContent;
	}
	public void setNumericContent(BigDecimal numericContent) {
		this.numericContent = numericContent;
	}
}
