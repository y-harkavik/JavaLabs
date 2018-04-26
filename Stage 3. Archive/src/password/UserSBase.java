package password;

import person.Person;
import person.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserSBase {
    private ArrayList<Person> listOfAccounts = new ArrayList<>();

    void createPerson(String password, String login) {
        List<Laws> lawsList = new ArrayList<>();

        byte[] salt = Password.getSalt();
        String hashedPassword = Password.getHashedPassword(password, salt);

        listOfAccounts.add(new User(hashedPassword, login, salt, lawsList));
    }


    /*void example() {
        User user;
        getSalt();
        String hashedPassword = getHashedPassword("admin", salt);
        listOfAccounts.add(new Administrator(hashedPassword, "admin", salt));

        getSalt();
        hashedPassword = getHashedPassword("user1", salt);
        user = new User(hashedPassword, "user1", salt);
        user.setLastUpdated(LocalDate.now());
        listOfAccounts.add(user);

        getSalt();
        hashedPassword = getHashedPassword("user2", salt);
        user = new User(hashedPassword, "user2", salt);
        user.setLastUpdated(LocalDate.of(2018, 03, 2));
        listOfAccounts.add(user);

        saveBase();
    }*/

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
        new UserSBase().example();
    }
}
