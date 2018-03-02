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

public class AuthModel {
    public static final String LOGIN_ERROR = "Username or password entered incorrect.";

    private Base baseOfAccounts = new Base();

    public Base getBaseOfAccounts() {
        return baseOfAccounts;
    }

    int checkEnteredInformation(String login, String password) {
        int index = baseOfAccounts.checkAccount(login, password);
        if (index >= 0) {
            return index;
        }
        return -1;
    }
}