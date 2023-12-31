package jpcompany.smartwire2.unit.service;

import jpcompany.smartwire2.repository.jdbctemplate.MemberRepositoryJdbcTemplate;
import jpcompany.smartwire2.service.EmailService;
import jpcompany.smartwire2.service.MemberService;
import jpcompany.smartwire2.service.dto.MemberJoinCommand;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepositoryJdbcTemplate memberRepositoryJdbcTemplate;
    @Mock
    private EmailService emailService;

    @Test
    @DisplayName("정상 회원가입 서비스 메서드 호출")
    void join() {
        // given
        MemberJoinCommand memberJoinCommand =
                new MemberJoinCommand(
                        "wjsdj2008@naver.com",
                        "Qweasdzxc1!",
                        "회사이름"
                );

        // when, then
        memberService.join(memberJoinCommand);
    }

    @ParameterizedTest
    @DisplayName("잘못된 이메일 형식 입력 시 예외 발생")
    @ValueSource(strings = {"wjsdj2008", "wjsdj2008gmail.com", "wjsdj2008@", "@wksd.com", "wjsdj2008@.", "", " "})
    void invalidInput(String email) {
        // given
        MemberJoinCommand memberJoinCommand =
                new MemberJoinCommand(
                        email,
                        "Qweasdzxc1!",
                        "회사이름"
                );

        // when, then
        Assertions.assertThatThrownBy(() -> memberService.join(memberJoinCommand))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("잘못된 비밀번호 형식 입력 시 예외 발생")
    @ValueSource(strings = {"123", "123456789012345678901", "rkskekfk1!", "Arkskekfk!", "Arkskekfk1", "ARKSKEKFK1!","Arkske kfk1!", "", " "})
    void invalidPasswordForm(String password) {
        // given
        MemberJoinCommand memberJoinCommand =
                new MemberJoinCommand(
                        "wjsdj2008@gmail.com",
                        password,
                        "회사이름"
                );

        // when, then
        Assertions.assertThatThrownBy(() -> memberService.join(memberJoinCommand))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @ParameterizedTest
    @DisplayName("잘못된 회사 이름 형식 입력 시 예외 발생")
    @ValueSource(strings = {""," ", "회사이름회사이름회사이름회사이름회사이름회"})
    void invalidCompanyNameForm(String companyName) {
        // given
        MemberJoinCommand memberJoinCommand =
                new MemberJoinCommand(
                        "wjsdj2008@gmail.com",
                        "Qweasdzxc1!",
                        companyName
                );

        // when, then
        Assertions.assertThatThrownBy(() -> memberService.join(memberJoinCommand))
                .isInstanceOf(IllegalArgumentException.class);
    }
}