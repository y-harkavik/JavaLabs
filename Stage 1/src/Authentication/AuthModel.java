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

    public boolean checkPassword(String enteredPassword, String accountPassword) {
        boolean answer;
        if (enteredPassword == accountPassword) {
            answer = true;
        } else {
            answer = false;
        }
        return answer;
    }

    public ArrayList<Person> getListOfAccounts() {
        return listOfAccounts;
    }

    public void setListOfAccounts(ArrayList<Person> listOfAccounts) {
        this.listOfAccounts = listOfAccounts;
    }
}
