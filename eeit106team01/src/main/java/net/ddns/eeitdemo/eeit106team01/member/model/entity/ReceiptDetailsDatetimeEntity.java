package net.ddns.eeitdemo.eeit106team01.member.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Entity
@Table(name = "receipt_details_datetime",indexes = {@Index(columnList = "id,table_id"),
		@Index(columnList = "id,granter_id")})
public class ReceiptDetailsDatetimeEntity extends AbstractPropertyEntity<ReceiptDetailsEntity, DatetimeGranterEntity>implements Serializable {

	private static final long serialVersionUID = -1825068572035368573L;
	private TimestampIndexEntity timestampInfo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="timestamp_index_id",nullable = false)
	public TimestampIndexEntity getTimestampInfo() {
		return timestampInfo;
	}
	public void setTimestampInfo(TimestampIndexEntity timestampInfo) {
		this.timestampInfo = timestampInfo;
	}
}
