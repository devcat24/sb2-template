package com.github.devcat24.config.security;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;


@SuppressWarnings({"SameParameterValue", "WeakerAccess", "unused"})
public class AESUtil {
    /*     ==> exposing to property 'salt & iv & passPhrase' is more dangerous !!!
    @Value("${spring.security.auth.encrypt.iv}")
    private String iv;

    @Value("${spring.security.auth.encrypt.salt}")
    private String salt;

    @Value("${spring.security.auth.encrypt.passPhrase}")
    private String passPhrase;*/

    private static final String saltCandidateChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#";
    private int keySize;
    private int iterationCount;
    private Cipher cipher;

    // 'iv & salt' can be generated using 'generateSaltFromString(String src, int length)'
    //    -> length of salt should be multiple of iv
    //       ex. iv => 16, salt => 16, 32
    //    -> salt is converted to HEX String -> should be twice of original length !
    private String iv = "4e65772053616c742054657874773033";
    private String salt = "4e65772053616c742054657874577158466b45616468414b39394e5a4d34376e";

    private String passPhrase = "Spring Boot Actuator is a sub-project of Spring Boot. It adds several production grade services to your application with little effort on your part. In this guide, you’ll build an application and then see how to add these services.";




    public AESUtil() {
        this.keySize = 128;
        this.iterationCount = 10000;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalStateException(e);
        }
    }

    public String encrypt (String plainText){
        return encryptText(plainText);
        // --> do not pass 'salt / iv / passPhrase' to public method parameter
        //        && hide 'salt' in private method & field
        //return encrypt(salt, iv, passPhrase, plainText);

    }

    public String decrypt (String cipherText){
        return decryptText(cipherText);
    }


    public AESUtil(int keySize, int iterationCount) {
        this.keySize = keySize;
        this.iterationCount = iterationCount;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalStateException(e);
        }
    }

    private String encryptText(String plainText) {
        //public String encrypt(String salt, String iv, String passPhrase, String plainText) {

        String result;
        try {
            SecretKey key = generateKey(salt, passPhrase);
            byte[] encrypted = doFinal(Cipher.ENCRYPT_MODE, key, iv, plainText.getBytes("UTF-8"));
            result = Base64.encodeBase64String(encrypted);

        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    private String decryptText(String cipherText) {
        //public String decrypt(String salt, String iv, String passPhrase, String cipherText) {
        String result;
        try {
            SecretKey key = generateKey(salt, passPhrase);
            byte[] decrypted = doFinal(Cipher.DECRYPT_MODE, key, iv, Base64.decodeBase64(cipherText));

            result = new String(decrypted, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    private byte[] doFinal(int encryptMode, SecretKey key, String iv, byte[] bytes) {
        byte[] result ;
        try {
            cipher.init(encryptMode, key, new IvParameterSpec(Hex.decodeHex(iv.toCharArray())));
            result = cipher.doFinal(bytes);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException
                | IllegalBlockSizeException | BadPaddingException | DecoderException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    private SecretKey generateKey(String salt, String passphrase) {
        SecretKey key;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), Hex.decodeHex(salt.toCharArray()), iterationCount, keySize);
            key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | DecoderException e) {
            throw new IllegalStateException(e);
        }
        return key;
    }

    public static String generateRandomSalt(int length) throws UnsupportedEncodingException {
        StringBuilder sourceString = new StringBuilder();

        for(int ins=0; ins < length ; ins++){
            sourceString.append(saltCandidateChar.charAt((new Random()).nextInt(saltCandidateChar.length())));
        }
        byte[] salt = sourceString.toString().getBytes("UTF-8");
        return Hex.encodeHexString(salt);
    }
    public static String generateSaltFromString(String src, int length) throws UnsupportedEncodingException {
        StringBuilder sourceString = new StringBuilder();
        for(int ins=0; ins < length ; ins++){
            sourceString.append(saltCandidateChar.charAt((new Random()).nextInt(saltCandidateChar.length())));
        }
        if(src != null && src.length() > length){
            sourceString = new StringBuilder(src.substring(0, length));
        } else if (src != null && src.length() <= length){
            sourceString = new StringBuilder(src + sourceString.substring(0, length - src.length()));
        }
        byte[] salt = sourceString.toString().getBytes("UTF-8");
        return Hex.encodeHexString(salt);
    }

}