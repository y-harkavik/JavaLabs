package Parser;

import Users.PersonnelFile;

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
    public void makeXML() {

    }

    @Override
    public void findPersonnelFileInXML() {

    }

    @Override
    public PersonnelFile getPersonnelFile(String passportID) {
        return null;
    }

    @Override
    public void insertPersonnelFileInXML() {

    }

    @Override
    public void removePersonnelFileFromXML() {

    }

    @Override
    public Map<String, String> getMapOfLastNameAndID() {
        return null;
    }
}
