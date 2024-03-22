package net.ictcampus.apithena.controller.security;

public class SecurityConstants {

    // alle diese Konstante dürfen nicht verändert werden


    // diese  kann der user ohne login verwenden, wird in SecurityConfiguration definiert
    public static final String SIGN_UP_URL = "/users/sign-up";
    public static final String All_GODS_URL = "/gods/**";





    public static final String SECRET = "SecretKeyToGenJWTs"; //Für die Generation
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days in miliseconds
    public static final String TOKEN_PREFIX = "Bearer "; // needed as prefix for the json
    public static final String HEADER_STRING = "Authorization";

    public static final String[] API_DOCUMENTATION_URLS = {
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**"
    };


}
