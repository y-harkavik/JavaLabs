package communicate.message.Request.ClientRequest;

import communicate.message.Request.Request;
import person.PersonnelFile;

public class UserRequest extends Request {

    public UserRequest(RequestType requestType, PersonnelFile handlingPersonnelFile, String passportID) {
        super(requestType, handlingPersonnelFile, passportID);
    }
}
