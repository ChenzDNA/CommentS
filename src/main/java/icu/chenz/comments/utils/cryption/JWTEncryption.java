package icu.chenz.comments.utils.cryption;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

/**
 * @author : Chenz
 * @date : 2023-01-12 0:47
 */

public class JWTEncryption {
    private static Algorithm algorithm;

    private static Long expireTime;

    public static void initAlgorithm(String secret, Long exp) {
        algorithm = Algorithm.HMAC512(secret);
        expireTime = exp;
    }

    public static String createToken(Long user) {
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                .withClaim("user", user).sign(algorithm);
    }

    public static Long verifyToken(String token) {
        return JWT.require(algorithm).build().verify(token).getClaim("user").asLong();
    }
}
