package communicate.message.Response.ServerResponse;

import communicate.message.Response.Response;
import Law.Laws;
import person.Account;
import person.PersonnelFile;

import java.util.List;
import java.util.Map;

public class ResponseForAdministrator extends Response {
    List<Account> userList;

    public ResponseForAdministrator(ResponseType responseType, String message, Map<String, Integer> listOfPersonnelFiles, PersonnelFile personnelFileOfSpecificMen, List<Laws> userLaws, List<Account> userList) {
        super(responseType, message, listOfPersonnelFiles, personnelFileOfSpecificMen, userLaws);
        this.userList = userList;
    }
}
