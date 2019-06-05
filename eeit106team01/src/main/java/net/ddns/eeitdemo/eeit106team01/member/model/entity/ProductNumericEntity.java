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
@Table(name = "product_numeric",indexes = {@Index(columnList = "id,table_id"),
		@Index(columnList = "id,granter_id")})
public class ProductNumericEntity  extends AbstractPropertyEntity<ProductBasicEntity, NumericGranterEntity> implements Serializable{

	private static final long serialVersionUID = -7640920619761535613L;
	private BigDecimal numericContent;
	@Column(name="numeric_content",nullable = false)
	public BigDecimal getNumericContent() {
		return numericContent;
	}
	public void setNumericContent(BigDecimal numericContent) {
		this.numericContent = numericContent;
	}

}
