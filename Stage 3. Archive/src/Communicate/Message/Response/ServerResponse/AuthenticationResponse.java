package Communicate.Message.Response.ServerResponse;

import Communicate.Message.Response.Response;
import Law.Laws;
import Users.PersonnelFile;

import java.util.List;
import java.util.Map;

public class AuthenticationResponse extends Response {

    public AuthenticationResponse(ResponseType responseType,
                                  String message, Map<String, Integer> listOfPersonnelFiles,
                                  PersonnelFile personnelFileOfSpecificMen,
                                  List<Laws> userLaws) {
        super(responseType, message, listOfPersonnelFiles, personnelFileOfSpecificMen, userLaws);
    }
}
