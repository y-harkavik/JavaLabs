package person;

import law.Laws;

import java.io.Serializable;

public abstract class Person implements Serializable {
    protected byte[] salt;
    protected String passwordAndSaltHash;
    protected String accountLogin;
    protected Laws userLaws;

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    public Person(String password, String accountLogin, byte[] salt, Laws userLaws) {
        passwordAndSaltHash = password;
        this.salt = salt;
        this.accountLogin = accountLogin;
        this.userLaws = userLaws;
    }

    public byte[] getSalt() {
        return salt;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public String getPasswordAndSaltHash() {
        return passwordAndSaltHash;
    }

    public void setUserLaws() {

    }

    public Laws getUserLaws() {
        return userLaws;
    }
}