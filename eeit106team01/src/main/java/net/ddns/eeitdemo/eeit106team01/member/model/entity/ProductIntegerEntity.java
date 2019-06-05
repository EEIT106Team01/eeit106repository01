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
@Table(name = "product_integer",indexes = {@Index(columnList = "id,table_id"),
		@Index(columnList = "id,granter_id")})
public class ProductIntegerEntity extends AbstractPropertyEntity<ProductBasicEntity, IntegerGranterEntity>
		implements Serializable {
	private static final long serialVersionUID = -7593217400587747379L;
	private Long integerContent;

	@Column(name = "integer_content", nullable = false)
	public Long getIntegerContent() {
		return integerContent;
	}

	public void setIntegerContent(Long integerContent) {
		this.integerContent = integerContent;
	}
}
