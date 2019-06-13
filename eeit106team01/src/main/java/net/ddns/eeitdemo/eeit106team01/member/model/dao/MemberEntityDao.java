package net.ddns.eeitdemo.eeit106team01.member.model.dao;

import java.util.List;

import net.ddns.eeitdemo.eeit106team01.member.model.entity.MemberEntity;

public interface MemberEntityDao {
	
	public MemberEntity getMemberEntityById(Long memberId);
	public MemberEntity getMemberEntityByUsername(String username);
	public List<MemberEntity> getMemberEntityByEmailAndThirdParty(String email,boolean isThirdPartyMember);
	
	public MemberEntity persistMemberEntity(MemberEntity src);
	public MemberEntity updateNotNullMemberEntity(MemberEntity src);
	public boolean removeMemberEntityById(Long memberId);
}
