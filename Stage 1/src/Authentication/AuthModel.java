package Authentication;

import person.Administrator;
import person.Person;
import person.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;

public class AuthModel {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 256;
    private static final int NUM_OF_BYTES = 32;
    public static final String LOGIN_ERROR = "Username or password entered incorrect.";
    private byte[] salt;

    private ArrayList<Person> listOfAccounts = new ArrayList<>();

    /*void createPerson(String password, String login) {
        salt = getSalt();
        String hashedPassword = getSaltedHash(password,salt);
        listOfAccounts.add(new User(hashedPassword,login,salt));
    }*/
    /*void example() {
        salt = getSalt();
        String hashedPassword = getSaltedHash("admin",salt);
        listOfAccounts.add(new Administrator(hashedPassword,"admin",salt));

        salt = getSalt();
        hashedPassword = getSaltedHash("user",salt);
        listOfAccounts.add(new Administrator(hashedPassword,"user",salt));

        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("login.ser"))){
            objectOutputStream.writeObject(listOfAccounts);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }*/
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

    public void getSalt() {
        try {
            salt = SecureRandom.getInstanceStrong().generateSeed(NUM_OF_BYTES);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPassword(String enteredPassword, String accountPassword) {
        boolean answer;
        if (enteredPassword.equals(accountPassword)) {
            answer = true;
        } else {
            answer = false;
        }
        return answer;
    }

    private Person checkLogin(String login) {
        if (!listOfAccounts.isEmpty()) {
            for (Person account : listOfAccounts) {

                if (account.getAccountLogin().equals(login)) {
                    return account;
                }
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