package icu.chenz.commentx.utils.cryption;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * @author : Chenz
 * @date : 2023-01-12 0:47
 */

public class JWTEncryption {
    private static Algorithm algorithm;

    public static void initAlgorithm(String secret) {
        algorithm = Algorithm.HMAC512(secret);
    }

    public static String createToken(Long user) {
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000))
                .withClaim("user", user).sign(algorithm);
    }

    public static Long verifyToken(String token) {
        return JWT.require(algorithm).build().verify(token).getClaim("user").asLong();
    }
}
