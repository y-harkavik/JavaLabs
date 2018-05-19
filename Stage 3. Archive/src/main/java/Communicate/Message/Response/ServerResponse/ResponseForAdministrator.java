package Communicate.Message.Response.ServerResponse;

import Communicate.Message.Response.Response;
import Law.Laws;
import Users.Account;
import Users.PersonnelFile;

import java.util.List;
import java.util.Map;

public class ResponseForAdministrator extends Response {
    private Map<String, List<Laws>> accountMap;

    public ResponseForAdministrator(ResponseType responseType, String message, Map<String, String> listOfPersonnelFiles, PersonnelFile personnelFileOfSpecificMen, Map<String, List<Laws>> listOfAccounts) {
        super(responseType, message, listOfPersonnelFiles, personnelFileOfSpecificMen, null);
        this.accountMap = listOfAccounts;
    }

    public Map<String, List<Laws>> getAccountMap() {
        return accountMap;
    }
}
