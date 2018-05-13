package Parser;

import Users.PersonnelFile;

import java.util.Map;

public class SAXParser implements Parser {
    private static volatile SAXParser instance = null;

    public static SAXParser getInstance() {
        SAXParser localInstance = instance;
        if (localInstance == null) {
            synchronized (SAXParser.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SAXParser();
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
