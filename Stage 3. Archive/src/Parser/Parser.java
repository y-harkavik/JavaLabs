package Parser;

import Users.PersonnelFile;
import org.w3c.dom.Element;

import java.util.Map;

public interface Parser {
    boolean checkPassportIDOnContainsInXML(String passportID);

    PersonnelFile getPersonnelFile(String passportID);

    void insertPersonnelFileInXML(PersonnelFile personnelFile);

    void removePersonnelFileFromXML(String passportID);

    void saveInFile();

    void updatePersonInXML(PersonnelFile personnelFile, String oldPassportID);

    Map<String, String> getMapOfLastNameAndID();

    Element validatePersonnelFile(PersonnelFile personnelFile);
}
