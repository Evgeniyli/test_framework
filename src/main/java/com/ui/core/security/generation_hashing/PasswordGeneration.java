package com.ui.core.security.generation_hashing;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class PasswordGeneration {
    private static final String KEY_PAIR_PRIVATE_KEY_TXT = "src/test/resources/key_pair/private_key.txt";
    private static final String PUBLIC_KEY_TXT = "src/test/resources/key_pair/public_key.txt";
    private Cipher cipher;
    private PublicKey publicKey;
    private PasswordGeneration asymmetricInstance;


    /**
     * Initialization password
     */
    public PasswordGeneration() {
        try {
            this.cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Init all keys
     *
     * @return current instance
     */
    public PasswordGeneration initKeys() {
        try {
            asymmetricInstance = new PasswordGeneration();
            publicKey = asymmetricInstance.getPublic();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    private PublicKey getPublic() throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(PUBLIC_KEY_TXT).toPath().toAbsolutePath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    private String decryptText(String msg, PublicKey key) throws InvalidKeyException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException {
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.decodeBase64(msg)), "UTF-8");
    }

    /**
     * Get full message
     *
     * @param initialMessage is massage of password
     * @return full massage
     */
    public String getPasswordUpdate(String initialMessage) {
        try {
            return asymmetricInstance.decryptText(initialMessage, publicKey);
        } catch (InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
