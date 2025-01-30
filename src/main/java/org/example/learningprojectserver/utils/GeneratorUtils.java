package org.example.learningprojectserver.utils;


import jakarta.xml.bind.DatatypeConverter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;


public class GeneratorUtils {

    public static Random r = new Random();
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static void main(String[] args) {
        System.out.println(isValidEmail("l@shai.co"));
    }
    public static String generatorCode(){
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int randomLetters = r.nextInt(letters.length() -1);
        String verifyCode  = String.valueOf(letters.charAt(randomLetters));
        verifyCode += String.format("%04d",r.nextInt(1000));
        return verifyCode;
    }

    public static String generatorPassword(){
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String specialChar = "!@#$%^&*()-+=_";
        int randomLetters = r.nextInt(letters.length() -1);
        int randomSpecialChar = r.nextInt(specialChar.length() -1);
        String verifyCode  = String.valueOf(letters.charAt(randomLetters));
        verifyCode += String.valueOf(specialChar.charAt(randomSpecialChar));
        verifyCode += String.format("%08d",r.nextInt(10000000));
        return verifyCode;
    }

    public static String generateSalt() {
        return UUID.randomUUID().toString();
    }

    public static String hashPassword(String password, String salt) {
        try {
            String saltedPassword = password + salt;
            return DatatypeConverter.printHexBinary(
                    MessageDigest.getInstance("SHA-256").digest(saltedPassword.getBytes("UTF-8"))
            );
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10) {
            return false;
        }

        if (!phoneNumber.startsWith("05")) {
            return false;
        }


        for (char c : phoneNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return false;
        }

        String[] emailParts = email.split("@");
        if (emailParts.length != 2 || emailParts[1].startsWith(".") || emailParts[1].endsWith(".")) {
            return false;
        }

        String domain = emailParts[1];
        if (!domain.contains(".")) {
            return false;
        }

        // בדיקה אם הסיומת של הדומיין היא באורך תקין (2-63 תווים)
        String[] domainParts = domain.split("\\.");
        String tld = domainParts[domainParts.length - 1];
        if (tld.length() < 2 || tld.length() > 63) {
            return false;
        }

        return true;
    }
}