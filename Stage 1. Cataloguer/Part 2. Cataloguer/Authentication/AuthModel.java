package Authentication;


import Persons.Base;


/**
 * Class that contains business logic of AuthenticationWindow window.
 * @author Yauheni
 *@version 1.0
 */
public class AuthModel {
    /**
     * Alert Message that shows for user if he inputted incorrect username or Database.*/
    public static final String LOGIN_ERROR = "Username or Database entered incorrect.";
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
     * @param password Inputted Database
     * @return index Returns index, if user entered correct login/Database
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