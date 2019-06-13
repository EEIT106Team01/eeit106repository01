package net.ddns.eeitdemo.eeit106team01.member.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(indexes = {@Index(columnList = "authorityName,authorityId"),@Index(columnList = "expirationTimestamp,authorityId")})
public class AuthorityEntity {
	private Long authorityId;
	private String authorityName;
	private Date expirationTimestamp;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getAuthorityId() {
		return authorityId;
	}
	public void setAuthorityId(Long authorityId) {
		this.authorityId = authorityId;
	}
	public String getAuthorityName() {
		return authorityName;
	}
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "datetime")
	public Date getExpirationTimestamp() {
		return expirationTimestamp;
	}
	public void setExpirationTimestamp(Date expirationTimestamp) {
		this.expirationTimestamp = expirationTimestamp;
	}
	@Override
	public String toString() {
		return "AuthorityEntity [authorityId=" + authorityId + ", authorityName=" + authorityName
				+ ", expirationTimestamp=" + expirationTimestamp + "]";
	}

}
