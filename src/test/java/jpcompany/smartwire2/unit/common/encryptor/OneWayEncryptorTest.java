package jpcompany.smartwire2.unit.common.encryptor;

import jpcompany.smartwire2.common.encryptor.OneWayEncryptor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class OneWayEncryptorTest {

    @Autowired
    private OneWayEncryptor oneWayEncryptor;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @DisplayName("단방향 암호화 같은 원문을 암호화해도 다른 암호문 반환")
    void encrypt() {
        String originalMessage = "secretMessage";

        String encodedMessage1 = oneWayEncryptor.encrypt(originalMessage);
        String encodedMessage2 = oneWayEncryptor.encrypt(originalMessage);

        Assertions.assertThat(encodedMessage1).isNotEqualTo(encodedMessage2);
    }

    @Test
    @DisplayName("단방향 암호화 같은 원문에서 나온 암호문들의 일치여부 확인")
    void encrypt2() {
        String originalMessage = "secretMessage";

        String encodedMessage1 = bCryptPasswordEncoder.encode(originalMessage);
        String encodedMessage2 = bCryptPasswordEncoder.encode(originalMessage);

        Assertions.assertThat(bCryptPasswordEncoder.matches(originalMessage, encodedMessage1)).isTrue();
        Assertions.assertThat(bCryptPasswordEncoder.matches(originalMessage, encodedMessage2)).isTrue();
    }
}