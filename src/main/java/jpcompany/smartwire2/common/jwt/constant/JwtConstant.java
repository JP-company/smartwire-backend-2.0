package jpcompany.smartwire2.common.jwt.constant;

public enum JwtConstant {
    ;

    public static final String LOGIN_TOKEN_SECRET = "!login@secret!";
    public static final String MAIL_TOKEN_SECRET = "!email@secret!";
    public static final Long MAIL_TOKEN_EXPIRATION_TIME = 300_000L; // 5분
    public static final Long LOGIN_TOKEN_EXPIRATION_TIME = 94_608_000_000L; // 3년
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}