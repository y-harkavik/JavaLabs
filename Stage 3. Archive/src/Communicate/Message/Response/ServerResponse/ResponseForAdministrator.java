package Communicate.Message.Response.ServerResponse;

import Communicate.Message.Response.Response;
import Law.Laws;
import Users.Account;
import Users.PersonnelFile;

import java.util.List;
import java.util.Map;

public class ResponseForAdministrator extends Response {
    private List<Account> listOfAccounts;

    public ResponseForAdministrator(ResponseType responseType, String message, Map<String, String> listOfPersonnelFiles, PersonnelFile personnelFileOfSpecificMen, List<Laws> userLaws, List<Account> listOfAccounts) {
        super(responseType, message, listOfPersonnelFiles, personnelFileOfSpecificMen, userLaws);
        this.listOfAccounts = listOfAccounts;
    }

    public List<Account> getAccountList() {
        return listOfAccounts;
    }
}
