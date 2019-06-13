package net.ddns.eeitdemo.eeit106team01.member.model.service;

import net.ddns.eeitdemo.eeit106team01.member.model.entity.MemberEntity;

public interface MemberService {
	public MemberEntity register(String username,String email,String password);
}
