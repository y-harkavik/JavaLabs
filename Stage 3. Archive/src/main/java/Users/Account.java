package Users;

import Law.Laws;

import java.io.Serializable;
import java.util.List;

public abstract class Account implements Serializable {
    protected byte[] salt;
    protected String passwordAndSaltHash;
    protected String accountLogin;
    protected List<Laws> lawsList;

    public Account(String password, String accountLogin, byte[] salt, List<Laws> lawsList) {
        passwordAndSaltHash = password;
        this.salt = salt;
        this.accountLogin = accountLogin;
        this.lawsList = lawsList;
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

    public List<Laws> getLawsList() {
        return lawsList;
    }

    public void setLawsList(List<Laws> lawsList) {
        this.lawsList = lawsList;
    }
}