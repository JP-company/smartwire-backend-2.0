package jpcompany.smartwire2.repository;

import jpcompany.smartwire2.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Long save(Member member);
    void updateRole(Long memberId, Member.Role role);
    Optional<Member> findById(Long id);
    Optional<Member> findByLoginEmail(String loginEmail);
}
