package jpcompany.smartwire2.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jpcompany.smartwire2.common.jwt.constant.JwtConstant;
import jpcompany.smartwire2.repository.jdbctemplate.constant.MemberConstant;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtToken {
    public String createEmailAuthToken(Long memberId) {
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtConstant.MAIL_TOKEN_EXPIRATION_TIME))
                .withClaim(MemberConstant.ID, memberId)
                .sign(Algorithm.HMAC512(JwtConstant.MAIL_TOKEN_AUTHENTICATION_SECRET));
    }

    public Long extractMemberIdFromEmailAuthToken(String authToken) {
        return JWT.require(Algorithm.HMAC512(JwtConstant.MAIL_TOKEN_AUTHENTICATION_SECRET)).build()
                .verify(authToken) // TokenExpiredException 유효 시간, SignatureVerificationException 시그니쳐
                .getClaim(MemberConstant.ID)
                .asLong();
    }
}