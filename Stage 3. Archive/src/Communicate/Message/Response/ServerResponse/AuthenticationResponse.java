package Communicate.Message.Response.ServerResponse;

import Communicate.Message.Response.Response;
import Law.Laws;
import Users.Account;
import Users.PersonnelFile;

import java.util.List;
import java.util.Map;

public class AuthenticationResponse extends Response {
    List<Account> accountList;

    public AuthenticationResponse(ResponseType responseType,
                                  String message, Map<String, String> listOfPersonnelFiles,
                                  List<Laws> userLaws, List<Account> accountList) {
        super(responseType, message, listOfPersonnelFiles, null, userLaws);
        this.accountList = accountList;
    }

    public List<Account> getAccountList() {
        return accountList;
    }
}
