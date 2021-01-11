package pl.kopka.summary.constant;

public class SecurityConst {
    public static final long EXPIRATION_TIME = 604_800_000; // 7 days in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String ISSUER = "Jakub Kopka, PL";
    public static final String GET_ADMINISTRATION = "Revolut Stock Calculator";
    public static final String ROLES = "roles";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = { "/user/login", "/user/register", "/user/reset-password" };
    public static final String[] ADMIN_URLS = { "/admin" };
    //public static final String[] PUBLIC_URLS = { "**" };
}
