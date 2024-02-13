package jpcompany.smartwire2.unit.repository.jpa;

import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberRepositoryJPATest {
    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("저장")
    @Rollback(false)
    public void test1() {
        // given
        Member member = Member.create(
                "wjsdj2008@gmail.com",
                "Arkskekfk1!",
                "테스트"
        );

        memberRepository.save(member);

        // when

        // then
    }

    @Test
    @DisplayName("찾기")
    public void test2() {
        // given

        // when

        // then
    }

}