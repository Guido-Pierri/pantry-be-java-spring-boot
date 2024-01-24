package com.guidopierri.pantrybe.utils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class PublicKeyReader {

    private static final String SSH_RSA = "ssh-rsa";

    public static PublicKey getPublicKey(String key) {
        String[] parts = key.split("\\s+");
        if (parts.length < 2 || !SSH_RSA.equals(parts[0])) {
            throw new IllegalArgumentException("Invalid key format");
        }
        byte[] bytes = Base64.getDecoder().decode(parts[1]);
        try (DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes))) {
            String type = readType(dis);
            if (!SSH_RSA.equals(type)) {
                throw new IllegalArgumentException("Invalid key type: " + type);
            }
            BigInteger e = readBigInteger(dis);
            BigInteger n = readBigInteger(dis);
            RSAPublicKeySpec spec = new RSAPublicKeySpec(n, e);
            return KeyFactory.getInstance("RSA").generatePublic(spec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readType(DataInputStream dis) throws IOException {
        int len = dis.readInt();
        byte[] bytes = new byte[len];
        dis.readFully(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private static BigInteger readBigInteger(DataInputStream dis) throws IOException {
        int len = dis.readInt();
        byte[] bytes = new byte[len];
        dis.readFully(bytes);
        return new BigInteger(bytes);
    }
}