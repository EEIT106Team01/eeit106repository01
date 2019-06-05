package net.ddns.eeitdemo.eeit106team01.member.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Entity
@Table(name = "timestamp_index")
public class TimestampIndexEntity extends AbstractEntity<Long> implements Serializable {

	private static final long serialVersionUID = 3537930402483258745L;

	private Date timestamp;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "DATETIME DEFAULT GETDATE()",insertable = false)
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
