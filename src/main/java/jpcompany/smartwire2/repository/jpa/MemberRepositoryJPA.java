package jpcompany.smartwire2.repository.jpa;

import jakarta.persistence.EntityManager;
import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryJPA implements MemberRepository {

    private final EntityManager em;

    @Override
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    @Override
    public void updateRole(Long memberId, Member.Role role) {
        Member member = em.find(Member.class, memberId);
        member.changeRole(role);
    }

    @Override
    public Optional<Member> findByLoginEmail(String loginEmail) {
        String jpql =
                """
                SELECT m
                FROM Member m
                WHERE m.loginEmail = :loginEmail
                """;
        Member member = em.createQuery(jpql, Member.class)
                .setParameter("loginEmail", loginEmail)
                .getSingleResult();
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return Optional.ofNullable(
                em.find(Member.class, memberId)
        );
    }
}
