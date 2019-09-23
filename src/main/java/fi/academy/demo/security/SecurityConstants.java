package fi.academy.demo.security;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWT";
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/user/sign-up";
    public static final String KARATEKAS_URL = "/api/karatekas";
    public static final String STYLES_URL = "/api/styles";
    public static final String KARATEKA_URL = "/api/karatekas/{id}";
    public static final String STYLE_URL = "/api/styles/{id}";
}
