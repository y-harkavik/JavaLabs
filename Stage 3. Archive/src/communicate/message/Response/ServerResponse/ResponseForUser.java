package communicate.message.Response.ServerResponse;

import communicate.message.Response.Response;
import Law.Laws;
import person.PersonnelFile;

import java.util.List;
import java.util.Map;

public class ResponseForUser extends Response {

    public ResponseForUser(ResponseType responseType, String message, Map<String, Integer> listOfPersonnelFiles, PersonnelFile personnelFileOfSpecificMen, List<Laws> userLaws) {
        super(responseType, message, listOfPersonnelFiles, personnelFileOfSpecificMen, userLaws);
    }
}
