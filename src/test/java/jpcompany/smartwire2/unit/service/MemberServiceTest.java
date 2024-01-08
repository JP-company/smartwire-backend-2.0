package jpcompany.smartwire2.unit.service;

import jpcompany.smartwire2.common.email.EmailService;
import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.service.MemberService;
import jpcompany.smartwire2.service.dto.MemberJoinCommand;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @MockBean
    private EmailService emailService;


    @Test
    @DisplayName("정상 회원가입 서비스 메서드 호출")
    @Transactional
    void join() {
        // given
        MemberJoinCommand memberJoinCommand =
                MemberJoinCommand.builder()
                        .loginEmail("wjsdj2008@naver.com")
                        .loginPassword("Qweasdzxc1!")
                        .companyName("회사이름")
                        .build();

        // when, then
        memberService.join(memberJoinCommand);
    }

    @Test
    @DisplayName("이메일로 계정 찾기, 비밀번호 암호화")
    @Transactional
    void test2() {
        // given
        String loginEmail = "wjsdj2008@naver.com";
        String loginPassword = "Qweasdzxc1!";
        String companyName = "회사이름";
        MemberJoinCommand memberJoinCommand =
                MemberJoinCommand.builder()
                        .loginEmail(loginEmail)
                        .loginPassword(loginPassword)
                        .companyName(companyName)
                        .build();
        memberService.join(memberJoinCommand);

        // when
        Member member = memberService.findMember(loginEmail);

        // then
        assertThat(member.getId()).isGreaterThan(0);
        assertThat(member.getLoginEmail()).isEqualTo(loginEmail);
        assertThat(member.getLoginPassword()).isNotEqualTo(loginPassword);
        assertThat(member.getCompanyName()).isEqualTo(companyName);
    }

    @Test
    @DisplayName("없는 이메일 조회 시 예외 발생")
    void test3() {
        // given
        String loginEmail = "wjsdj2008@naver.com";

        // when, then
        assertThatThrownBy(() -> memberService.findMember(loginEmail))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}