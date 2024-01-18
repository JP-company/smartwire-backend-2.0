package jpcompany.smartwire2.common.security.provider;

import jpcompany.smartwire2.common.encryptor.OneWayEncryptor;
import jpcompany.smartwire2.common.error.CustomException;
import jpcompany.smartwire2.common.security.common.PrincipalDetails;
import jpcompany.smartwire2.common.security.token.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import static jpcompany.smartwire2.common.error.ErrorCode.INVALID_MEMBER;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final OneWayEncryptor oneWayEncryptor;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginEmail = authentication.getName();
        String loginPassword = (String) authentication.getCredentials();

        PrincipalDetails principalDetails = (PrincipalDetails) userDetailsService.loadUserByUsername(loginEmail);

        if (!oneWayEncryptor.matches(loginPassword, principalDetails.getPassword())) {
            throw new CustomException(INVALID_MEMBER);
        }

        // 로그인 시에는 이때 전역으로 접근되는 토큰 발생됨
        return new JwtAuthenticationToken(principalDetails.getMember(), null, principalDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}