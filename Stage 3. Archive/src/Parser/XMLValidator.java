package Parser;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class XMLValidator {
    private static volatile XMLValidator instance = null;
    public static final String XML_FILE = "person.xml";
    public static final String SCHEMA_FILE = "personScheme.xsd";
    private static Validator validator;

    public static XMLValidator getInstance() {
        XMLValidator localInstance = instance;
        if (localInstance == null) {
            synchronized (XMLValidator.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new XMLValidator();
                }
            }
        }
        return localInstance;
    }

    private XMLValidator() {
        try {
            Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File(getResource(SCHEMA_FILE)));
            validator = schema.newValidator();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private String getResource(String filename) throws FileNotFoundException {
        URL resource = getClass().getClassLoader().getResource(filename);
        Objects.requireNonNull(resource);

        return resource.getFile();
    }

    public boolean validateXML() {
        try {
            validator.validate(new StreamSource(new File(getResource(XML_FILE))));
        } catch (SAXException | IOException e) {
            return false;
        }
        return true;
    }
}
