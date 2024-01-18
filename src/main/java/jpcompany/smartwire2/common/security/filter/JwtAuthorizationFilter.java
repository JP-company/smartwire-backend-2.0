package jpcompany.smartwire2.common.security.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpcompany.smartwire2.common.jwt.JwtTokenService;
import jpcompany.smartwire2.common.jwt.constant.JwtConstant;
import jpcompany.smartwire2.common.security.common.PrincipalDetails;
import jpcompany.smartwire2.common.security.token.JwtAuthenticationToken;
import jpcompany.smartwire2.domain.Member;
import jpcompany.smartwire2.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String jwtHeader = request.getHeader(JwtConstant.HEADER_STRING);
        if (jwtHeader == null || validateToken(jwtHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwtToken = jwtHeader.replace(JwtConstant.TOKEN_PREFIX, "");
            Long memberId = jwtTokenService.extractMemberIdFromLoginAuthToken(jwtToken);

            Member member = memberService.findMember(memberId);
            PrincipalDetails principalDetails = new PrincipalDetails(member);

            Authentication authentication =
                    new JwtAuthenticationToken(principalDetails.getMember(), null, principalDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (IllegalAccessError | TokenExpiredException | SignatureVerificationException e) {
            filterChain.doFilter(request, response);
        }
    }

    private boolean validateToken(String jwtHeader) {
        return !jwtHeader.startsWith(JwtConstant.TOKEN_PREFIX);
    }
}
