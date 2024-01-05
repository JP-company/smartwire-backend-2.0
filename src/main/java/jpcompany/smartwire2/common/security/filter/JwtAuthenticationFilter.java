package jpcompany.smartwire2.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpcompany.smartwire2.common.security.token.JwtAuthenticationToken;
import jpcompany.smartwire2.controller.dto.request.MemberLoginDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public JwtAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        MemberLoginDto memberLoginDto = new ObjectMapper().readValue(request.getInputStream(), MemberLoginDto.class);

        JwtAuthenticationToken jwtAuthenticationToken =
                new JwtAuthenticationToken(memberLoginDto.getLoginEmail(), memberLoginDto.getLoginPassword());

        return getAuthenticationManager().authenticate(jwtAuthenticationToken);
    }
}
