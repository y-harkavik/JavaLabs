package Parser;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XMLValidator {
    private static volatile XMLValidator instance = null;
    public static final String XML_FILE = "persons.xml";
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
            Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File(SCHEMA_FILE));
            validator = schema.newValidator();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

    public String validateXML() {
        try {
            validator.validate(new StreamSource(new File(XML_FILE)));
        } catch (SAXException | IOException e) {
            return e.getMessage();
        }
        return null;
    }

    public static void main(String[] args) {
    }
}
