package Communicate.Message.Response;

import Communicate.Message.Response.ServerResponse.ResponseType;
import Law.Laws;
import Users.PersonnelFile;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class Response implements Serializable {
    protected ResponseType responseType;
    protected String message;
    protected Map<String, String> listOfPersonnelFiles;
    protected PersonnelFile personnelFileOfSpecificMen;
    protected List<Laws> userLaws;

    public Response(ResponseType responseType,
                    String message, Map<String, String> listOfPersonnelFiles,
                    PersonnelFile personnelFileOfSpecificMen,
                    List<Laws> userLaws) {

        this.responseType = responseType;
        this.message = message;
        this.listOfPersonnelFiles = listOfPersonnelFiles;
        this.personnelFileOfSpecificMen = personnelFileOfSpecificMen;
        this.userLaws = userLaws;
    }

    public Response() {
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getMapOfPersonnelFiles() {
        return listOfPersonnelFiles;
    }

    public PersonnelFile getPersonnelFileOfSpecificMen() {
        return personnelFileOfSpecificMen;
    }

    public List<Laws> getUserLaws() {
        return userLaws;
    }
}
