package net.ddns.eeitdemo.eeit106team01.member.model.service;

import java.util.Set;

import net.ddns.eeitdemo.eeit106team01.member.model.entity.MemberEntity;

public interface MemberService {
	public String register(String username,String email,String password);
	public MemberEntity registerConfirm(String uuid);
	public boolean forgetPassword(String username,String email,String tempPassword);
	public boolean isAuthorityNotExpired(MemberEntity bean,String authorityName);
	public Set<String> notExpiredAuthorities(MemberEntity bean);
}
