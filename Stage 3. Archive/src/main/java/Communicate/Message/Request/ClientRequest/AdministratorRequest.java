package Communicate.Message.Request.ClientRequest;

import Communicate.Message.Request.Request;
import Law.Laws;
import Users.PersonnelFile;

import java.util.List;
import java.util.Map;

public class AdministratorRequest extends Request {
    private Map<String, List<Laws>> changingAccountMap;

    public AdministratorRequest(RequestType requestType, PersonnelFile handlingPersonnelFile, String passportID, Map<String, List<Laws>> changingAccountMap) {
        super(requestType, handlingPersonnelFile, passportID);
        this.changingAccountMap = changingAccountMap;
    }

    public Map<String, List<Laws>> getChangingAccountMap() {
        return changingAccountMap;
    }
}
