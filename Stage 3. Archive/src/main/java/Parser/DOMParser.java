package Parser;

import Communicate.Message.Request.ClientRequest.RequestType;
import Users.PersonnelFile;
import Users.Job;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DOMParser implements Parser {
    private static volatile DOMParser instance = null;
    private static Transformer transformer;

    private Document documentOfPersonnelFiles;
    private static DOMSource domSourcePersons;
    private static StreamResult outputStreamForPersons;
    private Node personsRootNode;

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
            domSourcePersons = new DOMSource(documentOfPersonnelFiles);
            outputStreamForPersons = new StreamResult(new File("persons.xml"));
            personsRootNode = documentOfPersonnelFiles.getDocumentElement();

            transformer = TransformerFactory.newInstance().newTransformer();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PersonnelFile getPersonnelFile(String passportID) {
        PersonnelFile personnelFile = new PersonnelFile();
        personnelFile.getBasicInformation().setPassport(passportID);

        Node personNode = findPersonNode(passportID);
        parsePersonNode(personNode, personnelFile);
        return personnelFile;
    }

    @Override
    public String insertPersonnelFileInXML(PersonnelFile personnelFile, String previousPassportID) {
        /*String report = checkPersonnelFile(personnelFile, previousPassportID, RequestType.ADD);
        if (report == null) {
            Element personnelFileElement = createPersonnelFileElement(personnelFile);
            personsRootNode.appendChild(personnelFileElement);
        }
        return report;*/
        return checkPersonnelFile(personnelFile, previousPassportID, RequestType.ADD);
    }

    public String checkPersonnelFile(PersonnelFile personnelFile, String previousPassportID, RequestType requestType) {
        Node oldPersonNode = findPersonNode(previousPassportID);
        Element elementPersonnelFile = createPersonnelFileElement(personnelFile);
        String report = null;
        switch (requestType) {
            case UPDATE:
                personsRootNode.replaceChild(elementPersonnelFile, oldPersonNode);
                saveInFile();
                report = XMLValidator.getInstance().validateXML();
                if (report != null) {
                    personsRootNode.replaceChild(oldPersonNode, elementPersonnelFile);
                    saveInFile();
                }
                break;
            case ADD:
                personsRootNode.appendChild(elementPersonnelFile);
                saveInFile();
                report = XMLValidator.getInstance().validateXML();
                if (report != null) {
                    personsRootNode.removeChild(elementPersonnelFile);
                    saveInFile();
                }
                break;
        }

        return report;
    }

    @Override
    public void removePersonnelFileFromXML(String passportID) {
        Node deletingPersonNode = findPersonNode(passportID);
        deletingPersonNode.getParentNode().removeChild(deletingPersonNode);
    }

    @Override
    public void saveInFile() {
        try {
            transformer.transform(domSourcePersons, outputStreamForPersons);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String updatePersonInXML(PersonnelFile updatedPersonnelFile, String oldPassportID) {
        /*Element personnelFileElement = createPersonnelFileElement(updatedPersonnelFile);
        Node replacingPersonNode = findPersonNode(oldPassportID);
        replacingPersonNode.getParentNode().replaceChild(personnelFileElement, replacingPersonNode);*/
        return checkPersonnelFile(updatedPersonnelFile, oldPassportID, RequestType.UPDATE);
    }

    @Override
    public Map<String, String> getMapOfLastNameAndID() {
        Map<String, String> mapOfPersonnelFiles = new HashMap<>();

        String passportID = null;
        String name = null;

        fillMapOfLastNameAndID(personsRootNode.getChildNodes(), mapOfPersonnelFiles, passportID, name);
        return mapOfPersonnelFiles;
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
            if (node.getNodeName().equals("jobs")) {
                parseJobs(node, personnelFile);
            }
        }
    }

    void parseBasicInformation(Node basicInformationNode, PersonnelFile personnelFile) {
        NamedNodeMap attributes = basicInformationNode.getAttributes();

        personnelFile.getBasicInformation().setGender(attributes.getNamedItem("sex").getNodeValue());
        personnelFile.getBasicInformation().setDate(attributes.getNamedItem("birthday").getNodeValue());

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
                personnelFile.getContactInformation().setHouse(attributes.getNamedItem("house").getNodeValue());
                break;
            }
        }
    }

    void parseJobs(Node jobsNode, PersonnelFile personnelFile) {
        List<Job> jobsList = new ArrayList<>();

        NamedNodeMap attributes;

        NodeList jobsNodeList = jobsNode.getChildNodes();

        for (int i = 0; i < jobsNodeList.getLength(); i++) {
            Node jobNode = jobsNodeList.item(i);
            if (jobNode.getNodeName().equals("job")) {
                attributes = jobNode.getAttributes();
                Job job = new Job(
                        attributes.getNamedItem("company").getNodeValue(),
                        attributes.getNamedItem("position").getNodeValue(),
                        attributes.getNamedItem("experience").getNodeValue());
                jobsList.add(job);
            }
        }
        personnelFile.setJobs(jobsList);
    }

    Node findPersonNode(String passportID) {
        Node person = null;
        NodeList listOfPersons = personsRootNode.getChildNodes();
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

    Element createPersonnelFileElement(PersonnelFile personnelFile) {
        Element personnelFileElement = createPersonElement(personnelFile.getBasicInformation().getPassport());
        Element basicInformationElement = createBasicInformationElement(personnelFile.getBasicInformation());
        Element contactInformation = createContactInformationElement(personnelFile.getContactInformation());
        Element jobsElement = createJobsElement(personnelFile.getJobs());

        personnelFileElement.appendChild(basicInformationElement);
        personnelFileElement.appendChild(contactInformation);
        personnelFileElement.appendChild(jobsElement);

        return personnelFileElement;
    }

    Element createBasicInformationElement(PersonnelFile.BasicInformation basicInformation) {
        Element basicInformationElement = documentOfPersonnelFiles.createElement("basicInformation");
        basicInformationElement.setAttribute("sex", basicInformation.getGender());
        basicInformationElement.setAttribute("birthday", basicInformation.getDate());

        Element name = documentOfPersonnelFiles.createElement("name");
        name.setAttribute("firstName", basicInformation.getFirstName());
        name.setAttribute("middleName", basicInformation.getMiddleName());
        name.setAttribute("lastName", basicInformation.getLastName());
        basicInformationElement.appendChild(name);

        return basicInformationElement;
    }

    Element createContactInformationElement(PersonnelFile.ContactInformation contactInformation) {
        Element contactInformationElement = documentOfPersonnelFiles.createElement("contactInformation");

        Element mobilePhoneElement = documentOfPersonnelFiles.createElement("mobilePhone");
        mobilePhoneElement.setTextContent(contactInformation.getMobilePhone());

        Element homePhoneElement = documentOfPersonnelFiles.createElement("homePhone");
        homePhoneElement.setTextContent(contactInformation.getHomePhone());

        Element addressElement = documentOfPersonnelFiles.createElement("address");
        addressElement.setAttribute("country", contactInformation.getCountry());
        addressElement.setAttribute("city", contactInformation.getCity());
        addressElement.setAttribute("street", contactInformation.getStreet());
        addressElement.setAttribute("house", contactInformation.getHouse().toString());

        contactInformationElement.appendChild(mobilePhoneElement);
        contactInformationElement.appendChild(homePhoneElement);
        contactInformationElement.appendChild(addressElement);

        return contactInformationElement;
    }

    Element createPersonElement(String passportID) {
        Element personnelFileElement = documentOfPersonnelFiles.createElement("person");
        personnelFileElement.setAttribute("ID", passportID);

        return personnelFileElement;
    }

    Element createJobsElement(List<Job> jobList) {
        Element jobsElement = documentOfPersonnelFiles.createElement("jobs");

        for (Job job : jobList) {
            Element jobElement = documentOfPersonnelFiles.createElement("job");
            jobElement.setAttribute("company", job.getCompany());
            jobElement.setAttribute("position", job.getPosition());
            jobElement.setAttribute("experience", job.getExperience().toString());
            jobsElement.appendChild(jobElement);
        }
        return jobsElement;
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
                    mapOfPersonnelFiles.put(passportID, lastName);
                    return;
                }
                fillMapOfLastNameAndID(node.getChildNodes(), mapOfPersonnelFiles, passportID, lastName);
            }
        }
    }

    public static void main(String[] args) {
    }
}
