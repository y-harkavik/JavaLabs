package person;

import java.io.Serializable;

public abstract class Person implements Serializable {
    protected byte[] salt;
    protected String passwordAndSaltHash;
    protected String accountLogin;

    public Person(String password, String accountLogin, byte[] salt) {
        passwordAndSaltHash = password;
        this.salt = salt;
        this.accountLogin = accountLogin;
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
}