package Communicate.Message.Response.ServerResponse;

import Communicate.Message.Response.Response;
import Law.Laws;
import Users.PersonnelFile;

import java.util.List;
import java.util.Map;

public class ResponseForUser extends Response {

    public ResponseForUser(ResponseType responseType, String message, Map<String, Integer> listOfPersonnelFiles, PersonnelFile personnelFileOfSpecificMen, List<Laws> userLaws) {
        super(responseType, message, listOfPersonnelFiles, personnelFileOfSpecificMen, userLaws);
    }
}
