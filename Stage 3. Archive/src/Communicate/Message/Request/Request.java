package Communicate.Message.Request;

import Communicate.Message.Request.ClientRequest.RequestType;

public abstract class Request {
    protected RequestType requestType;
    protected PersonnelFile handlingPersonnelFile;
    protected String passportID;

    public Request() {
    }

    public Request(RequestType requestType, PersonnelFile personnelFile, String passportID) {
        this.requestType = requestType;
        this.handlingPersonnelFile = personnelFile;
        this.passportID = passportID;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
