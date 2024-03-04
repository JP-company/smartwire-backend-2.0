package jpcompany.smartwire2.common.encryptor;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OneWayEncryptor {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String encrypt(String rawData) {
        return bCryptPasswordEncoder.encode(rawData);
    }

    public boolean matches(String s1, String s2) {
        return bCryptPasswordEncoder.matches(s1, s2);
    }
}
