package jpcompany.smartwire2.unit.common.jwt;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import jpcompany.smartwire2.common.jwt.JwtTokenService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenServiceTest {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Test
    @DisplayName("다른 토큰으로 인증 시 예외 발생")
    void test1() {
        // given
        Long memberId = 123L;
        String loginAuthToken = jwtTokenService.createLoginAuthToken(memberId);
        String emailAuthToken = jwtTokenService.createEmailAuthToken(memberId);

        // when, then
        Assertions.assertThatThrownBy(() -> jwtTokenService.extractMemberIdFromLoginAuthToken(emailAuthToken))
                .isInstanceOf(SignatureVerificationException.class);

        Assertions.assertThatThrownBy(() -> jwtTokenService.extractMemberIdFromEmailAuthToken(loginAuthToken))
                .isInstanceOf(SignatureVerificationException.class);
    }

    @Test
    @DisplayName("토큰 생성, 값 추출")
    void test2() {
        // given
        Long memberId = 123L;
        String loginAuthToken = jwtTokenService.createLoginAuthToken(memberId);
        String emailAuthToken = jwtTokenService.createEmailAuthToken(memberId);

        // when, then
        Assertions.assertThat(jwtTokenService.extractMemberIdFromLoginAuthToken(loginAuthToken)).isEqualTo(123L);
        Assertions.assertThat(jwtTokenService.extractMemberIdFromEmailAuthToken(emailAuthToken)).isEqualTo(123L);
    }
}