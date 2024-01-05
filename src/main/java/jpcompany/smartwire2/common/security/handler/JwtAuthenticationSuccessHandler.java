package jpcompany.smartwire2.common.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpcompany.smartwire2.common.encryptor.TwoWayEncryptor;
import jpcompany.smartwire2.common.jwt.JwtTokenService;
import jpcompany.smartwire2.common.jwt.constant.JwtConstant;
import jpcompany.smartwire2.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenService jwtTokenService;
    private final TwoWayEncryptor twoWayEncryptor;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        Member member = (Member) authentication.getPrincipal();

        String encodedLoginEmail = twoWayEncryptor.encrypt(member.getLoginEmail());
        String loginAuthJwtToken = jwtTokenService.createLoginAuthJwtToken(member.getId(), encodedLoginEmail);

        response.addHeader(JwtConstant.HEADER_STRING, JwtConstant.TOKEN_PREFIX + loginAuthJwtToken);
        response.setContentType("application/json; charset=utf-8");
    }
}
