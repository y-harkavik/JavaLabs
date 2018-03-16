package person;

import Catalog.Model;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;

public class Base {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 256;
    private static final int NUM_OF_BYTES = 32;
    private byte[] salt;

    private ArrayList<Person> listOfAccounts = new ArrayList<>();

    void createPerson(String password, String login) {
        getSalt();
        String hashedPassword = getSaltedHash(password, salt);
        listOfAccounts.add(new User(hashedPassword, login, salt));
    }

    void example() {
        User user;
        getSalt();
        String hashedPassword = getSaltedHash("admin", salt);
        listOfAccounts.add(new Administrator(hashedPassword, "admin", salt));

        getSalt();
        hashedPassword = getSaltedHash("user1", salt);
        user = new User(hashedPassword, "user1", salt);
        user.setLastUpdated(LocalDate.now());
        listOfAccounts.add(user);

        getSalt();
        hashedPassword = getSaltedHash("user2", salt);
        user = new User(hashedPassword, "user2", salt);
        user.setLastUpdated(LocalDate.of(2018, 03, 2));
        listOfAccounts.add(user);

        saveBase();
    }

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

    public int checkAccount(String login, String password) {
        Person account = checkLogin(login);
        if (account != null) {
            if (checkPassword(getSaltedHash(password, account.getSalt()), account.getPasswordAndSaltHash())) {
                return listOfAccounts.indexOf(account);
            }
        }
        return -1;
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

    public void saveBase() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("login.ser"))) {
            objectOutputStream.writeObject(listOfAccounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getBaseFromFile() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("login.ser"))) {
            listOfAccounts = ((ArrayList<Person>) objectInputStream.readObject());
        } catch (Exception e) {
            Catalog.Model.createAlertError(Model.READ_ERROR);
        }
    }
    public static void main(String[] args) {
        new Base().example();
    }
}
