package person;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.Serializable;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public abstract class Person implements Serializable{
    protected byte[] salt;
    protected String passwordAndSaltHash;
    protected String accountLogin;

    public static final String ADMIN = "ADMIN";
    public static final String GUEST = "GUEST";
    public static final String USER = "USER";

    //protected static final int ITERATION_COUNT = 65536;
    //protected static final int KEY_LENGTH = 256;

    Person(String password, String accountLogin, byte[] salt) {
        //setPasswordAndSalt(password);
        passwordAndSaltHash = password;
        this.salt = salt;
        this.accountLogin=accountLogin;
    }

    public byte[] getSalt() {
        return salt;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin() {

    }

    public String getPasswordAndSaltHash() {
        return passwordAndSaltHash;
    }

    public void setPasswordAndSalt(String password) {
        /*try {
            this.passwordAndSaltHash = getSaltedHash(password);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        this.passwordAndSaltHash = password;
    }
    /*public String getSaltedHash(String password) {
        String saltedHash = null;
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] enteredPasswordHash = factory.generateSecret(spec).getEncoded();
            saltedHash = Base64.getEncoder().encodeToString(enteredPasswordHash);

        } catch (Exception e) {
            System.out.println("No algorithm");
            e.printStackTrace();
        }
        return saltedHash;
    }

    public boolean checkPassword(String checkedPassword) {
        boolean answer;
        if (getSaltedHash(getSaltedHash(checkedPassword)) == passwordAndSaltHash) {
            answer = true;
        } else {
            answer = false;
        }
        return answer;
    }*/


}
