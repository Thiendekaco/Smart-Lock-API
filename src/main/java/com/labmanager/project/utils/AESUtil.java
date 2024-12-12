package com.labmanager.project.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {

    private static final String AES = "AES";
    private static final ThreadLocal<Cipher> ENCRYPT_CIPHER = ThreadLocal.withInitial(() -> {
        try {
            return Cipher.getInstance(AES);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize encryption cipher", e);
        }
    });
    private static final ThreadLocal<Cipher> DECRYPT_CIPHER = ThreadLocal.withInitial(() -> {
        try {
            return Cipher.getInstance(AES);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize decryption cipher", e);
        }
    });

    private static SecretKey getSecretKey(String secretKey) {
        return new SecretKeySpec(secretKey.getBytes(), AES);
    }

    public static String encrypt(String data, String secretKey) throws Exception {
        Cipher cipher = ENCRYPT_CIPHER.get();
        SecretKey key = getSecretKey(secretKey);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedData, String secretKey) throws Exception {
        Cipher cipher = DECRYPT_CIPHER.get();
        SecretKey key = getSecretKey(secretKey);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}