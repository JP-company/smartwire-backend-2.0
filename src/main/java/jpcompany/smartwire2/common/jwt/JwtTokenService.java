package jpcompany.smartwire2.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jpcompany.smartwire2.common.jwt.constant.JwtConstant;
import jpcompany.smartwire2.domain.constant.MemberConstant;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenService {
    public String createEmailAuthToken(Long memberId) {
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtConstant.MAIL_TOKEN_EXPIRATION_TIME))
                .withClaim(MemberConstant.ID, memberId)
                .sign(Algorithm.HMAC512(JwtConstant.MAIL_TOKEN_SECRET));
    }

    public String createLoginAuthToken(Long memberId) {
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtConstant.LOGIN_TOKEN_EXPIRATION_TIME))
                .withClaim(MemberConstant.ID, memberId)
                .sign(Algorithm.HMAC512(JwtConstant.LOGIN_TOKEN_SECRET));
    }

    public Long extractMemberIdFromEmailAuthToken(String authToken) {
        DecodedJWT decodedJWT =
                JWT.require(Algorithm.HMAC512(JwtConstant.MAIL_TOKEN_SECRET)).build()
                .verify(authToken); // TokenExpiredException 유효 시간, SignatureVerificationException 시그니쳐

        return decodedJWT.getClaim(MemberConstant.ID).asLong();
    }

    public Long extractMemberIdFromLoginAuthToken(String authToken) {
        DecodedJWT decodedJWT =
                JWT.require(Algorithm.HMAC512(JwtConstant.LOGIN_TOKEN_SECRET)).build()
                        .verify(authToken); // TokenExpiredException 유효 시간, SignatureVerificationException 시그니쳐

        return decodedJWT.getClaim(MemberConstant.ID).asLong();
    }
}