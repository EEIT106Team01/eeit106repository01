package net.ddns.eeitdemo.eeit106team01.member.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(indexes = {@Index(columnList = "memberId,username"),@Index(columnList = "memberId,email")})
public class MemberEntity {
	private Long memberId;
	private String username;
	private String email;
	private String password;
	private Date registrationTimestamp;
	private String image;
	private Date birth;
	private List<AuthorityEntity> authorities;
	private Boolean isThirdPartyMember;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	@NotNull
	@Size(min = 3, max=50)
	@Column(nullable = false,unique = true)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@NotNull
	@Email
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@NotNull
	@Size(min = 6, max = 255)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "datetime default getdate()",insertable = false)
	public Date getRegistrationTimestamp() {
		return registrationTimestamp;
	}
	public void setRegistrationTimestamp(Date registrationTimestamp) {
		this.registrationTimestamp = registrationTimestamp;
	}
	@Column(columnDefinition = "varchar(max)")
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Temporal(TemporalType.DATE)
	@Column(columnDefinition = "date")
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	public Boolean getIsThirdPartyMember() {
		return isThirdPartyMember;
	}
	public void setIsThirdPartyMember(Boolean isThirdPartyMember) {
		this.isThirdPartyMember = isThirdPartyMember;
	}

	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	public List<AuthorityEntity> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<AuthorityEntity> authorities) {
		this.authorities = authorities;
	}
}
