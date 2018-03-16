package Authentication;


import person.Base;


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