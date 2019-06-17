package net.ddns.eeitdemo.eeit106team01.member.model.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(indexes = {@Index(columnList = "authorityName,authorityId"),
		@Index(columnList = "expirationTimestamp,authorityId"),
		@Index(columnList = "authorityUuid,authorityId")})
public class AuthorityEntity {
	private Long authorityId;
	private String authorityName;
	private String authorityUuid;
	private MemberEntity member;
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
	public String getAuthorityUuid() {
		return authorityUuid;
	}
	public void setAuthorityUuid(String authorityUuid) {
		this.authorityUuid = authorityUuid;
	}
	@ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE}) 
	public MemberEntity getMember() {
		return member;
	}
	public void setMember(MemberEntity member) {
		this.member = member;
	}
	@Override
	public String toString() {
		return "AuthorityEntity [authorityId=" + authorityId + ", authorityName=" + authorityName + ", authorityUuid="
				+ authorityUuid + ", member=" + member + ", expirationTimestamp=" + expirationTimestamp + "]";
	}
	

}
