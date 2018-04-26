package password;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Password {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATION_COUNT = 32768;
    private static final int KEY_LENGTH = 128;
    private static final int NUM_OF_BYTES = 16;
    private static SecretKeyFactory factory;

    static {
        try {
            factory = SecretKeyFactory.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No algorithm");
            e.printStackTrace();
        }
    }

    public static String getHashedPassword(String password, byte[] salt) {
        String saltedHash = null;

        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
            byte[] enteredPasswordHash = factory.generateSecret(spec).getEncoded();
            saltedHash = Base64.getEncoder().encodeToString(enteredPasswordHash);
        } catch (Exception e) {
            System.out.println("No algorithm");
            e.printStackTrace();
        }
        return saltedHash;
    }

    public static byte[] getSalt() {
        byte[] salt = null;

        try {
            salt = SecureRandom.getInstanceStrong().generateSeed(NUM_OF_BYTES);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return salt;
    }
}
