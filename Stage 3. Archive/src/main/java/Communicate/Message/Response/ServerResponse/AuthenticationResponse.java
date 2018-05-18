package Communicate.Message.Response.ServerResponse;

import Communicate.Message.Response.Response;
import Law.Laws;
import Users.Account;

import java.util.List;
import java.util.Map;

public class AuthenticationResponse extends Response {
    Map<String,Account> accountMap;

    public AuthenticationResponse(ResponseType responseType,
                                  String message, Map<String, String> listOfPersonnelFiles,
                                  List<Laws> userLaws,  Map<String,Account> accountMap) {
        super(responseType, message, listOfPersonnelFiles, null, userLaws);
        this.accountMap = accountMap;
    }

    public  Map<String,Account> getAccountMap() {
        return accountMap;
    }
}
