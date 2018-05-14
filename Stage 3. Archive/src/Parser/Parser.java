package Parser;

import Users.PersonnelFile;

import java.util.Map;

public interface Parser {
    void makeXML();

    void findPersonnelFileInXML();

    PersonnelFile getPersonnelFile(String passportID);

    void insertPersonnelFileInXML();

    void removePersonnelFileFromXML();

    void saveInFile();

    Map<String, String> getMapOfLastNameAndID();
}
