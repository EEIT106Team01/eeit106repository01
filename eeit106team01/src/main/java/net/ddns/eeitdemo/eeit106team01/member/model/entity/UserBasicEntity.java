package net.ddns.eeitdemo.eeit106team01.member.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Entity
@Table(name = "user_basic",indexes = {@Index(columnList = "id,email")})
public class UserBasicEntity extends AbstractEntity<Long> implements Serializable{
	private static final long serialVersionUID = 5489625168394668422L;
	private String email;
	private String password;
	private String username;
	private List<UserDatetimeEntity> userDatetimeEntity;
	private List<UserIntegerEntity> userIntegerEntity;
	private List<UserNumericEntity> userNumericEntity;
	private List<UserTextEntity> userTextEntity;
	@OneToMany(mappedBy = "tableInfo",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
	public List<UserDatetimeEntity> getUserDatetimeEntity() {
		return userDatetimeEntity;
	}
	public void setUserDatetimeEntity(List<UserDatetimeEntity> userDatetimeEntity) {
		this.userDatetimeEntity = userDatetimeEntity;
	}
	@OneToMany(mappedBy = "tableInfo",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
	public List<UserIntegerEntity> getUserIntegerEntity() {
		return userIntegerEntity;
	}
	public void setUserIntegerEntity(List<UserIntegerEntity> userIntegerEntity) {
		this.userIntegerEntity = userIntegerEntity;
	}
	@OneToMany(mappedBy = "tableInfo",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
	public List<UserNumericEntity> getUserNumericEntity() {
		return userNumericEntity;
	}
	public void setUserNumericEntity(List<UserNumericEntity> userNumericEntity) {
		this.userNumericEntity = userNumericEntity;
	}
	@OneToMany(mappedBy = "tableInfo",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
	public List<UserTextEntity> getUserTextEntity() {
		return userTextEntity;
	}
	public void setUserTextEntity(List<UserTextEntity> userTextEntity) {
		this.userTextEntity = userTextEntity;
	}
	@Column(nullable = false,unique = true,name="email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(nullable = false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(nullable=false,length=50)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
