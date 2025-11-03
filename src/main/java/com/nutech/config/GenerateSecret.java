package com.nutech.config;

import io.jsonwebtoken.io.Encoders;

public class GenerateSecret {
    public static void main(String[] args) {
        String secret = Encoders.BASE64.encode("mySuperSecretKeyForNutechIntegration2024ProjectSpringBootJava21WithJWTTokenSecurity".getBytes());
        System.out.println("JWT Secret: " + secret);
    }
}