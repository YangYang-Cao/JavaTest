package com.preson.object.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.HashMap;

public class JwtTest {


    @Test
    public void test() {
        HashMap<String, Object> map = new HashMap<>(256);

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 60*60);

        String token = JWT.create()
                /** header */
                .withHeader(map)
                /** payload */
                .withClaim("userId", 21L)
                .withClaim("username", "ZhangSan")
                /** 方法作用指定令牌过期时间 */
                .withExpiresAt(instance.getTime())
                /** 签名 */
                .sign(Algorithm.HMAC256("5!toole"));


        System.out.println(token);

    }


    @Test
    public void verificationTest() {

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("5!toole")).build();
        DecodedJWT verify = jwtVerifier.verify(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDYzNzU2MTksInVzZXJJZCI6MjEsInVzZXJuYW1lIjoiWmhhbmdTYW4ifQ.5P--aAL2PXFe9fwfxTZwoHZL6KkkcaEm3a5IC2viw5o");

        System.out.println(verify.getClaim("userId").asInt());
        System.out.println(verify.getClaim("username").asString());
        System.out.println(verify.getExpiresAt());

    }

}
