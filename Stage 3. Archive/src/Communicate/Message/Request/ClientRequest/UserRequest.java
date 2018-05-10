package Communicate.Message.Request.ClientRequest;

import Communicate.Message.Request.Request;
import Users.PersonnelFile;

public class UserRequest extends Request {

    public UserRequest(RequestType requestType, PersonnelFile handlingPersonnelFile, String passportID) {
        super(requestType, handlingPersonnelFile, passportID);
    }
}
