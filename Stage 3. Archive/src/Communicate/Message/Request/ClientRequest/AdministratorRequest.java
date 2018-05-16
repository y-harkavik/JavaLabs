package Communicate.Message.Request.ClientRequest;

import Communicate.Message.Request.Request;
import Users.Account;
import Users.PersonnelFile;

import java.util.List;

public class AdministratorRequest extends Request {
    private List<Account> changingAccountList;

    public AdministratorRequest(RequestType requestType, PersonnelFile handlingPersonnelFile, String passportID, List<Account> changingAccountList) {
        super(requestType, handlingPersonnelFile, passportID);
        this.changingAccountList = changingAccountList;
    }

    public List<Account> getChangingAccountList() {
        return changingAccountList;
    }
}
