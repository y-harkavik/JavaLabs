package Parser;

import Users.PersonnelFile;
import Users.Work;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DOMParser implements Parser {
    private static volatile DOMParser instance = null;
    private Document documentOfPersonnelFiles;
    private Node personsNode;

    public static DOMParser getInstance() {
        DOMParser localInstance = instance;
        if (localInstance == null) {
            synchronized (DOMParser.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DOMParser();
                }
            }
        }
        return localInstance;
    }

    private DOMParser() {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            documentOfPersonnelFiles = documentBuilder.parse("persons.xml");
            personsNode = documentOfPersonnelFiles.getDocumentElement();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void makeXML() {

    }

    @Override
    public void findPersonnelFileInXML() {

    }

    @Override
    public PersonnelFile getPersonnelFile(String passportID) {
        PersonnelFile personnelFile = new PersonnelFile();
        personnelFile.getBasicInformation().setPassport(passportID);

        Node personNode = findPersonNode(personsNode.getChildNodes(), passportID);
        parsePersonNode(personNode, personnelFile);
        return personnelFile;
    }

    void parsePersonNode(Node personNode, PersonnelFile personnelFile) {
        NodeList personNodeList = personNode.getChildNodes();

        for (int i = 0; i < personNodeList.getLength(); i++) {
            Node node = personNodeList.item(i);
            if (node.getNodeName().equals("basicInformation")) {
                parseBasicInformation(node, personnelFile);
            }
            if (node.getNodeName().equals("contactInformation")) {
                parseContactInformation(node, personnelFile);
            }
            if (node.getNodeName().equals("works")) {
                parseWorks(node, personnelFile);
            }
        }
    }

    void parseBasicInformation(Node basicInformationNode, PersonnelFile personnelFile) {
        NamedNodeMap attributes = basicInformationNode.getAttributes();

        personnelFile.getBasicInformation().setGender(attributes.getNamedItem("sex").getNodeValue());
        personnelFile.getBasicInformation().setDate(LocalDate.parse(attributes.getNamedItem("birthday").getNodeValue()));

        NodeList basicInformationNodeList = basicInformationNode.getChildNodes();

        for (int i = 0; i < basicInformationNodeList.getLength(); i++) {
            Node node = basicInformationNodeList.item(i);
            if (node.getNodeType() != Node.TEXT_NODE) {
                attributes = node.getAttributes();
                personnelFile.getBasicInformation().setFirstName(attributes.getNamedItem("firstName").getNodeValue());
                personnelFile.getBasicInformation().setMiddleName(attributes.getNamedItem("middleName").getNodeValue());
                personnelFile.getBasicInformation().setLastName(attributes.getNamedItem("lastName").getNodeValue());
                break;
            }
        }
    }

    void parseContactInformation(Node contactInformationNode, PersonnelFile personnelFile) {
        NamedNodeMap attributes;

        NodeList contactInformationNodeList = contactInformationNode.getChildNodes();

        for (int i = 0; i < contactInformationNodeList.getLength(); i++) {
            Node node = contactInformationNodeList.item(i);
            if (node.getNodeName().equals("mobilePhone")) {
                personnelFile.getContactInformation().setMobilePhone(node.getFirstChild().getNodeValue());
            }
            if (node.getNodeName().equals("homePhone")) {
                personnelFile.getContactInformation().setHomePhone(node.getFirstChild().getNodeValue());
            }
            if (node.getNodeName().equals("address")) {
                attributes = node.getAttributes();
                personnelFile.getContactInformation().setCountry(attributes.getNamedItem("country").getNodeValue());
                personnelFile.getContactInformation().setCity(attributes.getNamedItem("city").getNodeValue());
                personnelFile.getContactInformation().setStreet(attributes.getNamedItem("street").getNodeValue());
                personnelFile.getContactInformation().setHouse(Integer.valueOf(attributes.getNamedItem("house").getNodeValue()));
                break;
            }
        }
    }

    void parseWorks(Node worksNode, PersonnelFile personnelFile) {
        List<Work> worksList = new ArrayList<>();

        NamedNodeMap attributes;

        NodeList worksNodeList = worksNode.getChildNodes();

        for (int i = 0; i < worksNodeList.getLength(); i++) {
            Node workNode = worksNodeList.item(i);
            if (workNode.getNodeName().equals("work")) {
                attributes = workNode.getAttributes();
                Work work = new Work(
                        attributes.getNamedItem("company").getNodeValue(),
                        attributes.getNamedItem("position").getNodeValue(),
                        Integer.valueOf(attributes.getNamedItem("experience").getNodeValue()));
                worksList.add(work);
            }
        }
        personnelFile.setWorks(worksList);
    }

    Node findPersonNode(NodeList listOfPersons, String passportID) {
        Node person = null;
        for (int i = 0; i < listOfPersons.getLength(); i++) {
            Node tempNode = listOfPersons.item(i);
            if (tempNode.getNodeType() != Node.TEXT_NODE) {
                if (tempNode.getAttributes().getNamedItem("ID").getNodeValue().equals(passportID)) {
                    person = tempNode;
                    break;
                }
            }
        }
        return person;
    }

    @Override
    public void insertPersonnelFileInXML() {

    }

    @Override
    public void removePersonnelFileFromXML() {

    }

    @Override
    public Map<String, String> getMapOfLastNameAndID() {
        Map<String, String> mapOfPersonnelFiles = new HashMap<>();

        String passportID = null;
        String name = null;

        fillMapOfLastNameAndID(personsNode.getChildNodes(), mapOfPersonnelFiles, passportID, name);
        return mapOfPersonnelFiles;
    }

    void fillMapOfLastNameAndID(NodeList nodeList, Map<String, String> mapOfPersonnelFiles, String passportID, String lastName) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() != Node.TEXT_NODE) {
                if (node.getAttributes().getNamedItem("ID") != null) {
                    passportID = node.getAttributes().getNamedItem("ID").getNodeValue();
                }
                if (node.getAttributes().getNamedItem("lastName") != null) {
                    lastName = node.getAttributes().getNamedItem("lastName").getNodeValue();
                    mapOfPersonnelFiles.put(lastName, passportID);
                    return;
                }
                fillMapOfLastNameAndID(node.getChildNodes(), mapOfPersonnelFiles, passportID, lastName);
            }
        }
    }

    public static void main(String[] args) {
        getInstance().getPersonnelFile("AAAAA3HN33666HH");
    }
}
