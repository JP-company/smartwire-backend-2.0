package jpcompany.smartwire2.common.encryptor;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class TwoWayEncryptor {

    private final AesBytesEncryptor aesBytesEncryptor;

    public String encrypt(String rawData) {
        return Base64.getEncoder().encodeToString(
                aesBytesEncryptor.encrypt(rawData.getBytes(StandardCharsets.UTF_8))
        );
    }

    public String decrypt(String encryptedData) {
        return new String(
                aesBytesEncryptor.decrypt(
                        Base64.getDecoder().decode(encryptedData)
                ),  StandardCharsets.UTF_8
        );
    }
}
