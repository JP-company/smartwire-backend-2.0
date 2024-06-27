package jpcompany.smartwire2.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpcompany.smartwire2.common.security.token.JwtAuthenticationToken;
import jpcompany.smartwire2.dto.request.MemberLoginForm;
import jpcompany.smartwire2.domain.constant.MemberConstant;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public JwtAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/v1/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        MemberLoginForm memberLoginForm;
        if (request.getParameter(MemberConstant.LOGIN_EMAIL) != null && request.getParameter(MemberConstant.LOGIN_PASSWORD) != null) {
            memberLoginForm = MemberLoginForm.builder()
                   .loginEmail(request.getParameter(MemberConstant.LOGIN_EMAIL))
                   .loginPassword(request.getParameter(MemberConstant.LOGIN_PASSWORD))
                   .build();
        } else {
            memberLoginForm = new ObjectMapper().readValue(request.getInputStream(), MemberLoginForm.class);
        }
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(memberLoginForm);
        return getAuthenticationManager().authenticate(jwtAuthenticationToken);
    }
}