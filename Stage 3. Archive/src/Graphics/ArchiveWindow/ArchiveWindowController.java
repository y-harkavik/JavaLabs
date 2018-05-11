package Graphics.ArchiveWindow;

import Communicate.Message.Request.ClientRequest.AdministratorRequest;
import Communicate.Message.Request.ClientRequest.RequestType;
import Users.PersonnelFile;
import Users.Work;
import client.Client;
import Law.Laws;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import Users.Account;
import org.eclipse.swt.widgets.TableItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ArchiveWindowController {
    ArchiveWindow archiveWindow;
    ArchiveWindowModel archiveWindowModel;
    String previousPassportID;

    public ArchiveWindowController(Client currentClient,
                                   Display display,
                                   Shell shell,
                                   Map<String, Integer> mapOfPersonnelFiles,
                                   Map<String, Account> mapOfUsers,
                                   List<Laws> lawsList) {
        archiveWindow = new ArchiveWindow(display, shell);
        archiveWindowModel = new ArchiveWindowModel(this, currentClient);
        setConstraints(lawsList);
        initListeners();
        setPersonnelFilesInTable(mapOfPersonnelFiles);
    }

    public void openMainWindow() {
        archiveWindow.shell.open();
        archiveWindow.shell.layout();
        archiveWindowModel.startListenSocket();

        while (!archiveWindow.shell.isDisposed()) {
            if (!archiveWindow.display.readAndDispatch()) {
                archiveWindow.display.sleep();
            }
        }
    }

    public void setConstraints(List<Laws> laws) {
        if (!laws.contains(Laws.ADMINISTRATOR)) {
            archiveWindow.changeLawsItem.setEnabled(false);
        } else {
            return;
        }
        if (!laws.contains(Laws.READ)) {

        }
        if (!laws.contains(Laws.CREATE)) {
            archiveWindow.addPersonnelFileItem.setEnabled(false);
        }
        if (!laws.contains(Laws.DELETE)) {
            archiveWindow.buttonDeletePersonnelFile.setEnabled(false);
        }
        if (!laws.contains(Laws.UPDATE)) {
            archiveWindow.buttonEditPerson.setEnabled(false);
            archiveWindow.buttonSavePerson.setEnabled(false);
        }
    }

    void initListeners() {
        archiveWindow.tableOfPersonnelFiles.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                String passportID = archiveWindow.tableOfPersonnelFiles.getItem(selectionEvent.detail).getText();
                archiveWindowModel.sendMessage(new AdministratorRequest(RequestType.GET_PERSONNEL_FILE,
                        null,
                        passportID,
                        null));
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });
        archiveWindow.buttonEditPerson.addListener(SWT.Selection, event -> {
            makeFieldsEditable();
        });
        archiveWindow.buttonDeletePersonnelFile.addListener(SWT.Selection, event -> {
            int rowIndex = archiveWindow.tableOfPersonnelFiles.getSelectionIndex();
            if (rowIndex != -1) {
                archiveWindowModel.sendMessage(new AdministratorRequest(RequestType.DELETE,
                        null,
                        archiveWindow.tableOfPersonnelFiles.getItem(rowIndex).getText(1),
                        null));
            }
        });
        archiveWindow.buttonSavePerson.addListener(SWT.Selection, event -> {
            if (archiveWindow.textFirstName.getEditable()) {
                archiveWindowModel.sendMessage(
                        new AdministratorRequest(RequestType.UPDATE,
                                createPersonnelFiles(),
                                previousPassportID,
                                null));
            }
        });
    }

    PersonnelFile createPersonnelFiles() {
        LocalDate localDate = LocalDate.of(
                Integer.parseInt(archiveWindow.comboYearOfBirth.getText()),
                Integer.parseInt(archiveWindow.comboMonthOfBirth.getText()),
                Integer.parseInt(archiveWindow.comboDayOfBirth.getText()));

        PersonnelFile personnelFile = new PersonnelFile(
                archiveWindow.textFirstName.getText(),
                archiveWindow.textMiddleName.getText(),
                archiveWindow.textLastName.getText(),
                archiveWindow.comboGender.getText(),
                localDate,
                archiveWindow.textPassport.getText(),
                archiveWindow.textCountry.getText(),
                archiveWindow.textCity.getText(),
                archiveWindow.textStreet.getText(),
                Integer.parseInt(archiveWindow.textHouse.getText()),
                archiveWindow.textMobilePhone.getText(),
                archiveWindow.textHomePhone.getText());
        return personnelFile;
    }

    void setPersonnelFileInformation(PersonnelFile personnelFile) {
        setBasicInformation(personnelFile.getBasicInformation());
        setContactInformation(personnelFile.getContactInformation());
        setWorksInTable(personnelFile.getWorks());
    }

    void setBasicInformation(PersonnelFile.BasicInformation basicInformation) {
        archiveWindow.textFirstName.setText(basicInformation.getFirstName());
        archiveWindow.textMiddleName.setText(basicInformation.getMiddleName());
        archiveWindow.textLastName.setText(basicInformation.getLastName());

        String[] date = basicInformation.getDate().toString().split("-");
        archiveWindow.comboDayOfBirth.setText(date[2]);
        archiveWindow.comboMonthOfBirth.setText(date[1]);
        archiveWindow.comboYearOfBirth.setText(date[0]);

        archiveWindow.textPassport.setText(basicInformation.getPassport().toString());
    }

    void setContactInformation(PersonnelFile.ContactInformation contactInformation) {
        archiveWindow.textCountry.setText(contactInformation.getCountry());
        archiveWindow.textCity.setText(contactInformation.getCity());
        archiveWindow.textStreet.setText(contactInformation.getStreet());
        archiveWindow.textHouse.setText(contactInformation.getHouse().toString());
        archiveWindow.textHomePhone.setText(contactInformation.getHomePhone());
        archiveWindow.textMobilePhone.setText(contactInformation.getMobilePhone());
    }

    void setPersonnelFilesInTable(Map<String, Integer> personnelFiles) {
        clearPersonnelFilesTable();
        String[] names = (String[]) personnelFiles.keySet().toArray();
        Integer[] passportIDs = (Integer[]) personnelFiles.values().toArray();
        for (int i = 0; i < personnelFiles.size(); i++) {
            TableItem tableItem = new TableItem(archiveWindow.tableOfPersonnelFiles, SWT.NONE);
            tableItem.setText(0, names[i]);
            tableItem.setText(1, passportIDs[i].toString());
        }
    }

    void setWorksInTable(List<Work> works) {
        clearWorksTable();
        for (Work work : works) {
            TableItem tableItem = new TableItem(archiveWindow.tableOfWorks, SWT.NONE);
            tableItem.setText(0, work.getCompany());
            tableItem.setText(1, work.getPosition());
            tableItem.setText(2, work.getExperience().toString());
        }
    }

    void clearPersonnelFilesTable() {
        archiveWindow.tableOfPersonnelFiles.clearAll();
    }

    void clearWorksTable() {
        archiveWindow.tableOfWorks.clearAll();
    }

    void makeFieldsEditable() {
        previousPassportID = archiveWindow.textPassport.getText();

        archiveWindow.textFirstName.setEditable(true);
        archiveWindow.textMiddleName.setEditable(true);
        archiveWindow.textLastName.setEditable(true);

        archiveWindow.comboDayOfBirth.setEnabled(true);
        archiveWindow.comboMonthOfBirth.setEnabled(true);
        archiveWindow.comboYearOfBirth.setEnabled(true);

        archiveWindow.textPassport.setEditable(true);

        archiveWindow.textCountry.setEditable(true);
        archiveWindow.textCity.setEditable(true);
        archiveWindow.textStreet.setEditable(true);
        archiveWindow.textHouse.setEditable(true);
        archiveWindow.textHomePhone.setEditable(true);
        archiveWindow.textMobilePhone.setEditable(true);

        archiveWindow.buttonAddWork.setEnabled(true);
        archiveWindow.buttonDeleteWork.setEnabled(true);
        archiveWindow.buttonSavePerson.setEnabled(true);
    }

    void makeFieldsNotEditable() {
        archiveWindow.textFirstName.setEditable(false);
        archiveWindow.textMiddleName.setEditable(false);
        archiveWindow.textLastName.setEditable(false);

        archiveWindow.comboDayOfBirth.setEnabled(false);
        archiveWindow.comboMonthOfBirth.setEnabled(false);
        archiveWindow.comboYearOfBirth.setEnabled(false);

        archiveWindow.textPassport.setEditable(false);

        archiveWindow.textCountry.setEditable(false);
        archiveWindow.textCity.setEditable(false);
        archiveWindow.textStreet.setEditable(false);
        archiveWindow.textHouse.setEditable(false);
        archiveWindow.textHomePhone.setEditable(false);
        archiveWindow.textMobilePhone.setEditable(false);

        archiveWindow.buttonAddWork.setEnabled(false);
        archiveWindow.buttonDeleteWork.setEnabled(false);
        archiveWindow.buttonSavePerson.setEnabled(false);
    }

    public static void main(String[] args) {
    }
}
