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
import java.util.List;

import static Database.Password.getHashedPassword;
import static Database.Password.getSalt;

public class UsersBase {
    private ArrayList<Account> listOfAccounts = new ArrayList<>();

    void createPerson(String password, String login) {
        List<Laws> lawsList = new ArrayList<>();

        byte[] salt = getSalt();
        String hashedPassword = getHashedPassword(password, salt);

        listOfAccounts.add(new User(hashedPassword, login, salt, lawsList));
    }

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
        if (!listOfAccounts.isEmpty()) {
            for (Account account : listOfAccounts) {
                if (account.getAccountLogin().equals(login)) {
                    return account;
                }
            }
        }
        return null;
    }

    public List<Account> getListOfAccounts() {
        return listOfAccounts;
    }

    public void setListOfAccounts(ArrayList<Account> listOfAccounts) {
        this.listOfAccounts = listOfAccounts;
    }

    public void saveBase() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("users.ser"))) {
            objectOutputStream.writeObject(listOfAccounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getBaseFromFile() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("users.ser"))) {
            listOfAccounts = ((ArrayList<Account>) objectInputStream.readObject());
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        new UsersBase().example();
    }

    void example() {
        User user;
        byte[] salt = getSalt();
        String hashedPassword = getHashedPassword("admin", salt);
        listOfAccounts.add(new Administrator(hashedPassword, "admin", salt));

        salt = getSalt();
        hashedPassword = getHashedPassword("user1", salt);
        List<Laws> laws = new ArrayList<>();
        laws.add(Laws.CREATE);
        laws.add(Laws.READ);
        laws.add(Laws.DELETE);
        laws.add(Laws.UPDATE);
        user = new User(hashedPassword, "user1", salt, laws);
        listOfAccounts.add(user);

        salt = getSalt();
        laws = new ArrayList<>();
        laws.add(Laws.READ);
        laws.add(Laws.DELETE);
        laws.add(Laws.UPDATE);
        hashedPassword = getHashedPassword("user2", salt);
        user = new User(hashedPassword, "user2", salt, laws);
        listOfAccounts.add(user);

        saveBase();
    }
}
