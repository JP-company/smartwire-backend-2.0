package jpcompany.smartwire2.unit.common.encryptor;

import jpcompany.smartwire2.common.encryptor.TwoWayEncryptor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;

@SpringBootTest
class TwoWayEncryptorTest {

    @Autowired
    private TwoWayEncryptor twoWayEncryptor;

    @Test
    @DisplayName("양방향 암호화 복호화 시 원문 반환")
    void encrypt() {
        String originMessage = "secretData";

        String encryptedMessage = twoWayEncryptor.encrypt(originMessage);
        String decryptedMessage = twoWayEncryptor.decrypt(encryptedMessage);

        Assertions.assertThat(originMessage).isEqualTo(decryptedMessage);
    }

    @Test
    @DisplayName("깉은 원문에 같은 암호화 반환 대칭키 방식")
    void encrypt2() {
        String originMessage = "secretData";

        String encryptedMessage1 = twoWayEncryptor.encrypt(originMessage);
        String encryptedMessage2 = twoWayEncryptor.encrypt(originMessage);

        Assertions.assertThat(encryptedMessage1).isEqualTo(encryptedMessage2);
    }
}