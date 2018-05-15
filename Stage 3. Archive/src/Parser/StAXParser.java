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
    public boolean checkPassportIDOnContainsInXML(String passportID) {
        return false;
    }

    @Override
    public PersonnelFile getPersonnelFile(String passportID) {
        return null;
    }

    @Override
    public void insertPersonnelFileInXML(PersonnelFile personnelFile) {

    }

    @Override
    public void removePersonnelFileFromXML(String passportID) {

    }

    @Override
    public void saveInFile() {

    }

    @Override
    public void updatePersonInXML(PersonnelFile personnelFile, String oldPassportID) {

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
