package Communicate.Message.Request.ClientRequest;

import Communicate.Message.Request.Request;
import Users.Account;
import Users.PersonnelFile;

import java.util.Map;

public class AdministratorRequest extends Request {
    private Map<String, Account> changingAccountMap;

    public AdministratorRequest(RequestType requestType, PersonnelFile handlingPersonnelFile, String passportID, Map<String, Account> changingAccountMap) {
        super(requestType, handlingPersonnelFile, passportID);
        this.changingAccountMap = changingAccountMap;
    }

    public Map<String, Account> getChangingAccountMap() {
        return changingAccountMap;
    }
}
