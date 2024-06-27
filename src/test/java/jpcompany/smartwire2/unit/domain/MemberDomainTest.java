package jpcompany.smartwire2.unit.domain;

import jpcompany.smartwire2.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MemberDomainTest {

    @Test
    @DisplayName("유효한 회원가입 형식 입력 시 Member 도메인 객체 생성, 초기화")
    void test1() {
        // given
        String loginEmail = "wjsdj2008@gmail.com";
        String loginPassword = "Qweasdzxc1!";
        String companyName = "회사이름";

        // when
        Member member = Member.create(loginEmail, loginPassword, companyName);

        // then
        Assertions.assertThat(member.getId()).isNull();
        Assertions.assertThat(member.getLoginEmail()).isEqualTo(loginEmail);
        Assertions.assertThat(member.getLoginPassword()).isEqualTo(loginPassword);
        Assertions.assertThat(member.getCompanyName()).isEqualTo(companyName);
        Assertions.assertThat(member.getRole()).isEqualTo(Member.Role.EMAIL_UNAUTHORIZED);
        Assertions.assertThat(member.getCreatedDateTime()).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("잘못된 이메일 형식으로 Member 도메인 객체 생성 시 예외 발생")
    @ValueSource(strings = {"wjsdj2008", "wjsdj2008gmail.com", "wjsdj2008@", "@wksd.com", "wjsdj2008@.", "", " "})
    void test2(String loginEmail) {
        // given
        String loginPassword = "Qweasdzxc1!";
        String companyName = "회사이름";

        // when, then
        Assertions.assertThatThrownBy(() -> Member.create(loginEmail, loginPassword, companyName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("잘못된 비밀번호 형식으로 Member 도메인 객체 생성 시 예외 발생")
    @ValueSource(strings = {"123", "123456789012345678901", "rkskekfk1!", "Arkskekfk!", "Arkskekfk1", "ARKSKEKFK1!","Arkske kfk1!", "", " "})
    void test3(String loginPassword) {
        // given
        String loginEmail = "wjsdj2008@gmail.com";
        String companyName = "회사이름";

        // when, then
        Assertions.assertThatThrownBy(() -> Member.create(loginEmail, loginPassword, companyName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("잘못된 회사 이름 형식으로 Member 도메인 객체 생성 시 예외 발생")
    @ValueSource(strings = {""," ", "회사이름회사이름회사이름회사이름회사이름회"})
    void test4(String companyName) {
        // given
        String loginEmail = "wjsdj2008@gmail.com";
        String loginPassword = "Qweasdzxc1!";

        // when, then
        Assertions.assertThatThrownBy(() -> Member.create(loginEmail, loginPassword, companyName))
                .isInstanceOf(IllegalArgumentException.class);
    }
}