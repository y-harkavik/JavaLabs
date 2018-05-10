package Communicate.Message.Request.ClientRequest;

import Communicate.Message.Request.Request;
import Users.Account;
import Users.PersonnelFile;

public class AdministratorRequest extends Request {
    private Account changingAccount;

    public AdministratorRequest(RequestType requestType, PersonnelFile handlingPersonnelFile, String passportID, Account changingAccount) {
        super(requestType, handlingPersonnelFile, passportID);
        this.changingAccount = changingAccount;
    }
}
