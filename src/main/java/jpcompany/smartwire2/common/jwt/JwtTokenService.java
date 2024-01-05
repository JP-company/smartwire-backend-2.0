package jpcompany.smartwire2.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jpcompany.smartwire2.common.jwt.constant.JwtConstant;
import jpcompany.smartwire2.common.jwt.dto.MemberTokenDto;
import jpcompany.smartwire2.repository.jdbctemplate.constant.MemberConstant;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenService {
    public String createEmailAuthJwtToken(Long memberId) {
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtConstant.MAIL_TOKEN_EXPIRATION_TIME))
                .withClaim(MemberConstant.ID, memberId)
                .sign(Algorithm.HMAC512(JwtConstant.MAIL_TOKEN_AUTHENTICATION_SECRET));
    }

    public String createLoginAuthJwtToken(Long memberId, String loginEmail) {
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtConstant.MAIL_TOKEN_EXPIRATION_TIME))
                .withClaim(MemberConstant.ID, memberId)
                .withClaim(MemberConstant.LOGIN_EMAIL, loginEmail)
                .sign(Algorithm.HMAC512(JwtConstant.MAIL_TOKEN_AUTHENTICATION_SECRET));
    }

    public Long extractMemberIdFrom(String authToken) {
        return JWT.require(Algorithm.HMAC512(JwtConstant.MAIL_TOKEN_AUTHENTICATION_SECRET)).build()
                .verify(authToken) // TokenExpiredException 유효 시간, SignatureVerificationException 시그니쳐
                .getClaim(MemberConstant.ID)
                .asLong();
    }

    public MemberTokenDto extractMemberTokenDtoFrom(String authToken) {
        DecodedJWT decodedJWT =
                JWT.require(Algorithm.HMAC512(JwtConstant.MAIL_TOKEN_AUTHENTICATION_SECRET)).build()
                .verify(authToken); // TokenExpiredException 유효 시간, SignatureVerificationException 시그니쳐

        Long memberId = decodedJWT.getClaim(MemberConstant.ID).asLong();
        String loginEmail = decodedJWT.getClaim(MemberConstant.LOGIN_EMAIL).asString();

        return new MemberTokenDto(memberId, loginEmail);
    }
}