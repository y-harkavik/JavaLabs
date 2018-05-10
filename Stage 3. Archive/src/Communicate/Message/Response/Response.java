package Communicate.Message.Response;

import Communicate.Message.Response.ServerResponse.ResponseType;
import Law.Laws;
import person.PersonnelFile;

import java.util.List;
import java.util.Map;

public abstract class Response {
    protected ResponseType responseType;
    protected String message;
    protected Map<String, Integer> listOfPersonnelFiles;
    protected PersonnelFile personnelFileOfSpecificMen;
    protected List<Laws> userLaws;

    public Response(ResponseType responseType,
                    String message, Map<String, Integer> listOfPersonnelFiles,
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

    public Map<String, Integer> getlistOfPersonnelFiles() {
        return listOfPersonnelFiles;
    }

    public PersonnelFile getpersonnelFileOfSpecificMen() {
        return personnelFileOfSpecificMen;
    }

    public List<Laws> getUserLaws() {
        return userLaws;
    }
}
