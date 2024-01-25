package com.guidopierri.pantrybe.config;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretGenerator {
    public static String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom(); //threadsafe
        byte[] random = new byte[32]; // 256 bit
        secureRandom.nextBytes(random);
        return Base64.getEncoder().encodeToString(random);
    }

}