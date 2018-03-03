package Authentication;

import person.Administrator;
import person.Base;
import person.Person;
import person.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;

/**
 * Class that contains business logic of Authentication window.
 * @author Yauheni
 *@version 1.0
 */
public class AuthModel {
    /**
     * Alert message that shows for user if he inputted incorrect username or password.*/
    public static final String LOGIN_ERROR = "Username or password entered incorrect.";
    /**
     * Variable that provides access for Base
     */
    private Base baseOfAccounts = new Base();

    /**
     * This method returns a base of accounts
     * @return baseOfAccounts
     */
    public Base getBaseOfAccounts() {
        return baseOfAccounts;
    }

    /**
     *
     * @param login Inputted login
     * @param password Inputted password
     * @return index Returns index, if user entered correct login/password
     * @return -1 Returns -1, if account not found.
     */
    int checkEnteredInformation(String login, String password) {
        int index = baseOfAccounts.checkAccount(login, password);
        if (index >= 0) {
            return index;
        }
        return -1;
    }
}