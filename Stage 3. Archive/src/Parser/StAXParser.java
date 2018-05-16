package Parser;

import Users.PersonnelFile;
import org.w3c.dom.Element;

import java.util.Map;

public class StAXParser implements Parser {
    private static volatile StAXParser instance = null;

    public static StAXParser getInstance() {
        StAXParser localInstance = instance;
        if (localInstance == null) {
            synchronized (StAXParser.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new StAXParser();
                }
            }
        }
        return localInstance;
    }

    @Override
    public PersonnelFile getPersonnelFile(String passportID) {
        return null;
    }

    @Override
    public String insertPersonnelFileInXML(PersonnelFile personnelFile, String oldPassportID) {
        return null;
    }

    @Override
    public void removePersonnelFileFromXML(String passportID) {

    }

    @Override
    public void saveInFile() {

    }

    @Override
    public String updatePersonInXML(PersonnelFile personnelFile, String oldPassportID) {
        return null;
    }

    @Override
    public Map<String, String> getMapOfLastNameAndID() {
        return null;
    }

    @Override
    public Element validatePersonnelFile(PersonnelFile personnelFile) {
        return null;
    }
}
