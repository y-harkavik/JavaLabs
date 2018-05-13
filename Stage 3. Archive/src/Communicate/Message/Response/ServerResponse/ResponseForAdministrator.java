package Communicate.Message.Response.ServerResponse;

import Communicate.Message.Response.Response;
import Law.Laws;
import Users.Account;
import Users.PersonnelFile;

import java.util.List;
import java.util.Map;

public class ResponseForAdministrator extends Response {
    private Map<String, Account> mapOfAccounts;

    public ResponseForAdministrator(ResponseType responseType, String message, Map<String, String> listOfPersonnelFiles, PersonnelFile personnelFileOfSpecificMen, List<Laws> userLaws, Map<String, Account> mapOfAccounts) {
        super(responseType, message, listOfPersonnelFiles, personnelFileOfSpecificMen, userLaws);
        this.mapOfAccounts = mapOfAccounts;
    }

    public Map<String, Account> getUserList() {
        return mapOfAccounts;
    }
}
