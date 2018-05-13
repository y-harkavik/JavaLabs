package Communicate.Message.Request;

import Communicate.Message.Request.ClientRequest.RequestType;
import Users.PersonnelFile;

import java.io.Serializable;

public abstract class Request implements Serializable {
    protected RequestType requestType;
    protected PersonnelFile handlingPersonnelFile;
    protected String previousPassportID;

    public Request() {
    }

    public Request(RequestType requestType, PersonnelFile personnelFile, String previousPassportID) {
        this.requestType = requestType;
        this.handlingPersonnelFile = personnelFile;
        this.previousPassportID = previousPassportID;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getPreviousPassportID() {
        return previousPassportID;
    }

    public PersonnelFile getHandlingPersonnelFile() {
        return handlingPersonnelFile;
    }
}
