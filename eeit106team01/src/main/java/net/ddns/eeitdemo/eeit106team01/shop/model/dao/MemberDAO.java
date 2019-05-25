package net.ddns.eeitdemo.eeit106team01.shop.model.dao;

import net.ddns.eeitdemo.eeit106team01.shop.model.Member;

public interface MemberDAO {

	abstract Member insertMember(Member member);

	abstract Member findByMemberId(Long id);

}
