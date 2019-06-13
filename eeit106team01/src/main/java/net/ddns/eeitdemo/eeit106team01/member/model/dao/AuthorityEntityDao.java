package net.ddns.eeitdemo.eeit106team01.member.model.dao;

import java.util.Date;
import java.util.List;

import net.ddns.eeitdemo.eeit106team01.member.model.entity.AuthorityEntity;

public interface AuthorityEntityDao {
	
	public AuthorityEntity getAuthorityEntityById(Long id);
	public List<AuthorityEntity> getAuthorityEntityByAuthorityName(String authorityName);
	public List<AuthorityEntity> getAuthorityEntityNotExpirated();
	public List<AuthorityEntity> getAuthorityEntityNotExpirated(Date expirationTimestamp);
	
	public AuthorityEntity persistAuthorityEntity(AuthorityEntity src);
	public AuthorityEntity updateAuthorityEntity(AuthorityEntity src);
	public boolean removeAuthorityEntity(Long authorityId);
}
