package Communicate.Message.Request.ClientRequest;

import Communicate.Message.Request.Request;

public class UserRequest extends Request {

    public UserRequest(RequestType requestType, PersonnelFile handlingPersonnelFile, String passportID) {
        super(requestType, handlingPersonnelFile, passportID);
    }
}
