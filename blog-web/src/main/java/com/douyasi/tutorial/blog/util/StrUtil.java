package com.douyasi.tutorial.blog.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StrUtil
 *
 * @author raoyc
 */
public class StrUtil {

    /**
     * md5 - get plain text md5 value
     *
     * @param plain plain text string
     * @return md5 hash value
     */
    public static String md5(String plain) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] arrPlain = md.digest(plain.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : arrPlain) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * encryptPassword
     *
     * @param password original password
     * @param salt salt string
     * @return encrypted hashed password
     */
    public static String encryptPassword(String password, String salt) {
        if (salt == null) {
            salt = "Hayitech&168";
        }
        String newPassword = md5(password).substring(6, 24) + salt;
        return md5(newPassword);
    }

    /**
     * checkPasswordStrength
     *
     * ref: https://mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
     *
     * string length must be great than 6,
     * must be contain digital [0-9],
     * must be contain lower and upper case latin alpha [a-zA-Z],
     * and must be contain at lease one special charter from !@#$%^&*()...
     *
     * @param password plaintext password
     * @return true or false
     */
    public static boolean checkPasswordStrength(String password) {
        String patternStr = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,20}$";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
