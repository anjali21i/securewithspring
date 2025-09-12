package com.ianjali.securewithspring.utility;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Base64;

public class JwtKeyGenerator {

    public static SecretKey generateSecretKey(String algo) {
        switch (algo) {
            case "HS256":
                return Keys.secretKeyFor(SignatureAlgorithm.HS256);
            case "HS384":
                return Keys.secretKeyFor(SignatureAlgorithm.HS384);
            case "HS512":
                return Keys.secretKeyFor(SignatureAlgorithm.HS512);
            default:
                break;
        }
        return null;
    }

    public static String getBase64Key(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static void main(String[] args) {

        System.out.println("Your JWT Secret Key: " + generateSecretKey("HS256"));
    }
}
