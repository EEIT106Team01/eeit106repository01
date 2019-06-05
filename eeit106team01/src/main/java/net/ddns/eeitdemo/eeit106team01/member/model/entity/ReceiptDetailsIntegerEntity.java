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
@Table(name = "receipt_details_integer",indexes = {@Index(columnList = "id,table_id"),
		@Index(columnList = "id,granter_id")})
public class ReceiptDetailsIntegerEntity
		extends AbstractPropertyEntity<ReceiptDetailsIntegerEntity, IntegerGranterEntity> implements Serializable {

	private static final long serialVersionUID = 748311144536856618L;
	private Long integerContent;

	@Column(name = "integer_content", nullable = false)
	public Long getIntegerContent() {
		return integerContent;
	}

	public void setIntegerContent(Long integerContent) {
		this.integerContent = integerContent;
	}
}
