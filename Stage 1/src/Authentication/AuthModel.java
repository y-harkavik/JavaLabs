package Authentication;

import person.Person;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class AuthModel {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 256;
    public static final String LOGIN_ERROR = "Username or password entered incorrect.";

    private ArrayList<Person> listOfAccounts = new ArrayList<>();

    public String getSaltedHash(String password, byte[] salt) {
        String saltedHash = null;
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] enteredPasswordHash = factory.generateSecret(spec).getEncoded();
            saltedHash = Base64.getEncoder().encodeToString(enteredPasswordHash);

        } catch (Exception e) {
            System.out.println("No algorithm");
            e.printStackTrace();
        }
        return saltedHash;
    }

    private boolean checkPassword(String enteredPassword, String accountPassword) {
        boolean answer;
        if (enteredPassword == accountPassword) {
            answer = true;
        } else {
            answer = false;
        }
        return answer;
    }

    private Person checkLogin(String login) {
        if (!listOfAccounts.isEmpty()) {
            for (Person account : listOfAccounts) {
                if (account.getAccountLogin() == login) return account;
            }
        }
        return null;
    }

    public ArrayList<Person> getListOfAccounts() {
        return listOfAccounts;
    }

    public void setListOfAccounts(ArrayList<Person> listOfAccounts) {
        this.listOfAccounts = listOfAccounts;
    }

    Person checkEnteredInformation(String login, String password) {
        Person account = checkLogin(login);
        if (account != null) {
            if (checkPassword(getSaltedHash(password, account.getSalt()), account.getPasswordAndSaltHash())) {
                return account;
            }
        }
        return null;
    }
}