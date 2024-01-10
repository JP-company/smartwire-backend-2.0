package jpcompany.smartwire2.unit.common.encryptor;

import jpcompany.smartwire2.common.encryptor.OneWayEncryptor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OneWayEncryptorTest {

    @Autowired
    private OneWayEncryptor oneWayEncryptor;

    @Test
    @DisplayName("단방향 암호화 같은 원문을 암호화해도 다른 암호문 반환")
    void encrypt() {
        // given
        String originalMessage = "secretMessage";

        // when
        String encodedMessage1 = oneWayEncryptor.encrypt(originalMessage);
        String encodedMessage2 = oneWayEncryptor.encrypt(originalMessage);

        // then
        Assertions.assertThat(encodedMessage1).isNotEqualTo(encodedMessage2);
    }

    @Test
    @DisplayName("단방향 암호화 같은 원문에서 나온 암호문들의 일치여부 확인")
    void encrypt2() {
        // given
        String originalMessage = "secretMessage";

        // when
        String encodedMessage1 = oneWayEncryptor.encrypt(originalMessage);
        String encodedMessage2 = oneWayEncryptor.encrypt(originalMessage);

        // then
        Assertions.assertThat(oneWayEncryptor.matches(originalMessage, encodedMessage1)).isTrue();
        Assertions.assertThat(oneWayEncryptor.matches(originalMessage, encodedMessage2)).isTrue();
    }
}