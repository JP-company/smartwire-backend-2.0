package jpcompany.smartwire2.unit.repository.jpa;

import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryJPATest {
    @Autowired MemberRepository memberRepository;

    private String loginEmail = "wjsdj2008@gmail.com";
    private String loginPassword = "Arkskekfk1!";
    private String companyName = "회사이름 테스트";

    @Test
    @DisplayName("계정 저장, id로 계정 찾기")
    public void test2() {
        // given
        Member member = createMember();
        Long memberId = memberRepository.save(member);

        // when
        Member findMember = memberRepository.findById(memberId).orElseThrow();

        // then
        assertThat(findMember.getId()).isEqualTo(memberId);
    }

    @Test
    @DisplayName("계정 저장, 이메일로 계정 찾기")
    public void test3() {
        // given
        Member member = createMember();
        memberRepository.save(member);

        // when
        Member findMember = memberRepository.findByLoginEmail(loginEmail).orElseThrow();

        // then
        assertThat(findMember.getLoginEmail()).isEqualTo(loginEmail);
    }

    @Test
    @DisplayName("계정 권한 업데이트")
    public void test4() {
        // given
        Member member = createMember();
        Long memberId = memberRepository.save(member);

        // before
        Member findMemberBeforeUpdate = memberRepository.findById(memberId).orElseThrow();
        assertThat(findMemberBeforeUpdate.getRole()).isEqualTo(Member.Role.EMAIL_UNAUTHORIZED);

        // when
        Member.Role memberRole = Member.Role.MEMBER;
        memberRepository.updateRole(memberId, memberRole);

        // then
        Member findMemberAfterUpdate = memberRepository.findById(memberId).orElseThrow();
        assertThat(findMemberAfterUpdate.getRole()).isEqualTo(Member.Role.MEMBER);
    }

    @Test
    @DisplayName("없는 id로 계정 조회 시 Optional<null> 객체 반환")
    public void test5() {
        // given
        Long memberId = 1L;

        // when, then
        assertThat(memberRepository.findById(memberId).isPresent()).isFalse();
    }

    @Test
    @DisplayName("없는 loginEmail로 계정 조회 시 Optional<null> 객체 반환")
    public void test6() {
        // when, then
        assertThat(memberRepository.findByLoginEmail(loginEmail).isPresent()).isFalse();
    }

    private Member createMember() {
        return Member.create(
                loginEmail,
                loginPassword,
                companyName
        );
    }
}
