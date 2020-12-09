package com.github.sulo.core.common.jwt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author sorata 2020-11-26:16:28
 */
public abstract class Design {

    public static String encode(String origin, String keyForBase64) {
        if (origin == null || origin.isEmpty()) {
            return origin;
        }
        try {
            final SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(keyForBase64), "AES");
            final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            cipher.update(origin.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(cipher.doFinal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decode(String secForBase64, String keyForBase64) {
        if (secForBase64 == null || secForBase64.isEmpty()) {
            return secForBase64;
        }
        try {
            final SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(keyForBase64), "AES");
            final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            cipher.update(Base64.getDecoder().decode(secForBase64));
            return new String(cipher.doFinal(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String generatorKey(int len) {
        try {
            final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(len, new SecureRandom());
            return Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
