package Database;

import Law.Laws;
import Users.Account;
import Users.Administrator;
import Users.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Database.Password.getHashedPassword;
import static Database.Password.getSalt;

public class UserBase {
    private Map<String, Account> mapOfAccounts = new HashMap<>();

    public Account checkAccount(String login, String password) {
        Account account = checkLogin(login);
        if (account != null) {
            if (checkPassword(getHashedPassword(password, account.getSalt()), account.getPasswordAndSaltHash())) {
                return account;
            }
        }
        return null;
    }

    private boolean checkPassword(String enteredPassword, String accountPassword) {
        if (enteredPassword.equals(accountPassword)) {
            return true;
        }
        return false;
    }

    private Account checkLogin(String login) {
        if (!mapOfAccounts.isEmpty()) {
            for (Account account : mapOfAccounts.values()) {
                if (account.getAccountLogin().equals(login)) {
                    return account;
                }
            }
        }
        return null;
    }

    public Map<String, Account> getMapOfAccounts() {
        return mapOfAccounts;
    }

    public void setMapOfAccounts(Map<String, Account> mapOfAccounts) {
        this.mapOfAccounts = mapOfAccounts;
    }

    public void saveBase() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("users.ser"))) {
            objectOutputStream.writeObject(mapOfAccounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getBaseFromFile() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("users.ser"))) {
            mapOfAccounts = ((Map<String, Account>) objectInputStream.readObject());
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        new UserBase().example();
    }

    void example() {
        User user;
        byte[] salt = getSalt();
        String hashedPassword = getHashedPassword("admin", salt);
        mapOfAccounts.put("admin", new Administrator(hashedPassword, "admin", salt));

        salt = getSalt();
        hashedPassword = getHashedPassword("user1", salt);
        List<Laws> laws = new ArrayList<>();
        laws.add(Laws.CREATE);
        laws.add(Laws.DELETE);
        laws.add(Laws.UPDATE);
        user = new User(hashedPassword, "user1", salt, laws);
        mapOfAccounts.put("user1", user);

        salt = getSalt();
        laws = new ArrayList<>();
        laws.add(Laws.DELETE);
        laws.add(Laws.UPDATE);
        hashedPassword = getHashedPassword("user2", salt);
        user = new User(hashedPassword, "user2", salt, laws);
        mapOfAccounts.put("user2", user);

        salt = getSalt();
        laws = new ArrayList<>();
        laws.add(Laws.DELETE);
        laws.add(Laws.UPDATE);
        hashedPassword = getHashedPassword("user3", salt);
        user = new User(hashedPassword, "user3", salt, laws);
        mapOfAccounts.put("user3", user);

        salt = getSalt();
        laws = new ArrayList<>();
        laws.add(Laws.DELETE);
        laws.add(Laws.UPDATE);
        hashedPassword = getHashedPassword("user4", salt);
        user = new User(hashedPassword, "user4", salt, laws);
        mapOfAccounts.put("user4", user);

        saveBase();
    }
}
