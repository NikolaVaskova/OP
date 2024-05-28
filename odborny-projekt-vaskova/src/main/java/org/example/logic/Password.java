package org.example.logic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Password {
    //Metoda hashuje hesla
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        // Vygenerovani soli
        byte[] salt = generateSalt();

        // Pridani soli k heslu
        String saltedPassword = Base64.getEncoder().encodeToString(salt) + password;

        // Vytvoření instance SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(saltedPassword.getBytes());

        // Vytvoreni hash
        byte[] hashedPassword = md.digest();

        // Vraceni hash + sůl
        return Base64.getEncoder().encodeToString(hashedPassword) + ":" + Base64.getEncoder().encodeToString(salt);
    }

    // Metoda pro vytvoreni soli
    private static byte[] generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    // Metoda overuje hesla
    public static boolean verifyPassword(String enteredPassword, String storedPassword) throws NoSuchAlgorithmException {
        // Rozdeluje ulozene heslo na hash a sul
        String[] parts = storedPassword.split(":");
        byte[] hash = Base64.getDecoder().decode(parts[0]);
        byte[] salt = Base64.getDecoder().decode(parts[1]);


        // kombinace soly a zadaneho hesla
        String saltedPassword = Base64.getEncoder().encodeToString(salt) + enteredPassword;

        // Vytvoreni instance SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(saltedPassword.getBytes());

        // Vytvoreni hash
        byte[] enteredHash = md.digest();

        // Porovnani hashu
        return MessageDigest.isEqual(hash, enteredHash);
    }
}