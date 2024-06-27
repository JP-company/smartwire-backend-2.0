package jpcompany.smartwire2.repository.jpa;

import jakarta.persistence.EntityManager;
import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public Optional<Member> findByLoginEmail(String loginEmail) {
        String jpql =
                """
                SELECT m
                FROM Member m
                WHERE m.loginEmail = :loginEmail
                """;

        List<Member> result = em.createQuery(jpql, Member.class)
                .setParameter("loginEmail", loginEmail)
                .setMaxResults(1)
                .getResultList();

        Member member = result.isEmpty() ? null : result.get(0);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return Optional.ofNullable(
                em.find(Member.class, memberId)
        );
    }

    @Override
    public void updateRole(Long memberId, Member.Role role) {
        Member member = em.find(Member.class, memberId);
        member.changeRole(role);
    }
}
