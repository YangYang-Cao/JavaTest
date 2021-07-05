package com.preson.object.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JwtUtils {


    private static final String SING = "5!toole$#%845FG";

    /**
     *
     */
    public static String getToken(Map<String, String> map) {

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);
        JWTCreator.Builder builder = JWT.create();

        map.forEach((k, v) -> {
            builder.withClaim(k, v);

        });

        String sign = builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SING));

        return sign;
    }


    /**
     * 验证token  合法性
     */
    public static DecodedJWT verify(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        return jwt;
    }

}
