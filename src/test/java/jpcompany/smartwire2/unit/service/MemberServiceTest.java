//package jpcompany.smartwire2.unit.service;
//
//import jpcompany.smartwire2.common.email.EmailService;
//import jpcompany.smartwire2.common.error.CustomException;
//import jpcompany.smartwire2.common.error.ErrorCode;
//import jpcompany.smartwire2.common.jwt.JwtTokenService;
//import jpcompany.smartwire2.domain.Member;
//import jpcompany.smartwire2.service.MemberService;
//import jpcompany.smartwire2.service.dto.MemberJoinDto;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//@SpringBootTest
//class MemberServiceTest {
//
//    @Autowired
//    private MemberService memberService;
//    @Autowired
//    private JwtTokenService jwtTokenService;
//    @MockBean
//    private EmailService emailService;
//
//
//    @Test
//    @DisplayName("정상 회원가입 서비스 메서드 호출")
//    @Transactional
//    void join() {
//        // given
//        MemberJoinDto memberJoinDto =
//                MemberJoinDto.builder()
//                        .loginEmail("wjsdj2008@nate.com")
//                        .loginPassword("Qweasdzxc1!")
//                        .companyName("회사이름")
//                        .build();
//
//        // when, then
//        memberService.join(memberJoinDto);
//    }
//
//    @Test
//    @DisplayName("이메일로 계정 찾기, 비밀번호 암호화")
//    void test2() {
//        // given
//        String loginEmail = "wjsdj2008@naver.com";
//
//        // when
//        Member member = memberService.findMember(loginEmail);
//
//        // then
//        assertThat(member.getId()).isGreaterThan(0);
//        assertThat(member.getLoginEmail()).isEqualTo(loginEmail);
//    }
//
//    @Test
//    @DisplayName("없는 이메일로 계정 조회 시 예외 발생")
//    void test3() {
//        // given
//        String loginEmail = "wjsdj2008@nate.com";
//
//        // when, then
//        assertThatThrownBy(() -> memberService.findMember(loginEmail))
//                .isInstanceOf(CustomException.class)
//                .hasMessage(ErrorCode.INVALID_MEMBER.getReason());
//    }
//
//    @Test
//    @DisplayName("memberId로 계정 찾기")
//    void test4() {
//        // given
//        Long memberId = 1L;
//
//        // when
//        Member member = memberService.findMember(memberId);
//
//        // then
//        assertThat(member.getId()).isEqualTo(memberId);
//    }
//
//    @Test
//    @DisplayName("없는 memberId로 계정 조회 시 예외 발생")
//    void test5() {
//        // given
//        Long memberId = 100000L;
//
//        // when, then
//        assertThatThrownBy(() -> memberService.findMember(memberId))
//                .isInstanceOf(CustomException.class)
//                .hasMessage(ErrorCode.INVALID_MEMBER.getReason());
//    }
//
//    @Test
//    @DisplayName("유효한 토큰으로 계정 권한 업데이트")
//    @Transactional
//    void test6() {
//        // given
//        String emailAuthToken = jwtTokenService.createEmailAuthToken(1L);
//
//        // when, then
//        memberService.authenticateEmail(emailAuthToken);
//    }
//
//    @Test
//    @DisplayName("유효하지 않은 토큰으로 계정 권한 업데이트 시 예외 발생")
//    @Transactional
//    void test7() {
//        // given
//        String emailAuthToken = "123"; // JWTDecodeException
//
//        // when, then
//        Assertions.assertThatThrownBy(() -> memberService.authenticateEmail(emailAuthToken))
//                .isInstanceOf(CustomException.class)
//                .hasMessage(ErrorCode.INVALID_TOKEN.getReason());
//    }
//
//    @Test
//    @DisplayName("없는 계정의 권한 업데이트 시 예외 발생")
//    @Transactional
//    void test8() {
//        // given
//        String emailAuthToken = jwtTokenService.createEmailAuthToken(100000L);
//
//        // when, then
//        Assertions.assertThatThrownBy(() -> memberService.authenticateEmail(emailAuthToken))
//                .isInstanceOf(CustomException.class)
//                .hasMessage(ErrorCode.INVALID_MEMBER.getReason());
//    }
//}