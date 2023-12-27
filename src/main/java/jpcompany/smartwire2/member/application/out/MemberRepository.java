package jpcompany.smartwire2.member.application.out;

import jpcompany.smartwire2.member.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    void save(Member member);
    Optional<Member> findByLoginEmail(String loginEmail);
}