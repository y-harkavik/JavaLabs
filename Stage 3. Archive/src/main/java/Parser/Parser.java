package Parser;

import Users.PersonnelFile;

import java.util.Map;

public interface Parser {
    PersonnelFile getPersonnelFile(String passportID);

    String insertPersonnelFileInXML(PersonnelFile personnelFile, String oldPassportID);

    void removePersonnelFileFromXML(String passportID);

    void saveInFile();

    String updatePersonInXML(PersonnelFile personnelFile, String oldPassportID);

    Map<String, String> getMapOfLastNameAndID();
}
