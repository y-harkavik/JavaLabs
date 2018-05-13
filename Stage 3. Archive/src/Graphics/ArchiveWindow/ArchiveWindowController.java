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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArchiveWindowController {
    ArchiveWindow archiveWindow;
    ArchiveWindowModel archiveWindowModel;
    String previousPassportID;

    public ArchiveWindowController(Client currentClient,
                                   Map<String, String> mapOfPersonnelFiles,
                                   Map<String, Account> mapOfUsers,
                                   List<Laws> lawsList) {
        archiveWindow = new ArchiveWindow(Display.getCurrent(), new Shell());
        archiveWindowModel = new ArchiveWindowModel(this, currentClient);
        setConstraints(lawsList);
        initListeners();
       setPersonnelFilesInTable(mapOfPersonnelFiles);
        makeFieldsEnable(false);
    }

    public void openMainWindow() {
        archiveWindow.shell.open();
        archiveWindow.shell.layout();

        setTablesHeadersVisible(true);

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
                String passportID = ((TableItem) selectionEvent.item).getText(1);
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
            addListeners();
            makeFieldsEnable(true);
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
        archiveWindow.shell.addListener(SWT.Close, event -> {
            archiveWindowModel.closeConnection();
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
        removeListeners();
        setBasicInformation(personnelFile.getBasicInformation());
        setContactInformation(personnelFile.getContactInformation());
        setWorksInTable(personnelFile.getWorks());
    }

    void removeListeners() {
        archiveWindow.textFirstName.removeVerifyListener(archiveWindow.verifyListenerForText);
        archiveWindow.textMiddleName.removeVerifyListener(archiveWindow.verifyListenerForText);
        archiveWindow.textLastName.removeVerifyListener(archiveWindow.verifyListenerForText);
        archiveWindow.textPassport.removeVerifyListener(archiveWindow.verifyListenerForPassport);
        archiveWindow.textCountry.removeVerifyListener(archiveWindow.verifyListenerForText);
        archiveWindow.textCity.removeVerifyListener(archiveWindow.verifyListenerForText);
        archiveWindow.textHomePhone.removeVerifyListener(archiveWindow.verifyListenerForPhone);
        archiveWindow.textMobilePhone.removeVerifyListener(archiveWindow.verifyListenerForPhone);
    }

    void addListeners() {
        archiveWindow.textFirstName.addVerifyListener(archiveWindow.verifyListenerForText);
        archiveWindow.textMiddleName.addVerifyListener(archiveWindow.verifyListenerForText);
        archiveWindow.textLastName.addVerifyListener(archiveWindow.verifyListenerForText);
        archiveWindow.textPassport.addVerifyListener(archiveWindow.verifyListenerForPassport);
        archiveWindow.textCountry.addVerifyListener(archiveWindow.verifyListenerForText);
        archiveWindow.textCity.addVerifyListener(archiveWindow.verifyListenerForText);
        archiveWindow.textHomePhone.addVerifyListener(archiveWindow.verifyListenerForPhone);
        archiveWindow.textMobilePhone.addVerifyListener(archiveWindow.verifyListenerForPhone);
    }

    void setBasicInformation(PersonnelFile.BasicInformation basicInformation) {
        archiveWindow.textFirstName.setText(basicInformation.getFirstName());
        archiveWindow.textMiddleName.setText(basicInformation.getMiddleName());
        archiveWindow.textLastName.setText(basicInformation.getLastName());

        archiveWindow.comboGender.setText(basicInformation.getGender());

        String[] date = basicInformation.getDate().toString().split("-");
        archiveWindow.comboYearOfBirth.setText(date[0]);
        archiveWindow.comboMonthOfBirth.setText(date[1]);
        archiveWindow.comboDayOfBirth.setText(date[2]);

        archiveWindow.textPassport.setText(basicInformation.getPassport());
    }

    void setContactInformation(PersonnelFile.ContactInformation contactInformation) {
        archiveWindow.textCountry.setText(contactInformation.getCountry());
        archiveWindow.textCity.setText(contactInformation.getCity());
        archiveWindow.textStreet.setText(contactInformation.getStreet());
        archiveWindow.textHouse.setText(contactInformation.getHouse().toString());
        archiveWindow.textHomePhone.setText(contactInformation.getHomePhone());
        archiveWindow.textMobilePhone.setText(contactInformation.getMobilePhone());
    }

    void setPersonnelFilesInTable(Map<String, String> personnelFiles) {
        clearPersonnelFilesTable();
        if (personnelFiles != null) {
            personnelFiles.forEach(this::fillPersonnelFileTable);
        }
    }

    void fillPersonnelFileTable(String name, String passportID) {
        TableItem tableItem = new TableItem(archiveWindow.tableOfPersonnelFiles, SWT.NONE);
        tableItem.setText(0, name);
        tableItem.setText(1, passportID);
    }

    void setWorksInTable(List<Work> works) {
        clearWorksTable();
        works.forEach(this::fillWorksTable);
    }

    void fillWorksTable(Work work) {
        TableItem tableItem = new TableItem(archiveWindow.tableOfWorks, SWT.NONE);
        tableItem.setText(0, work.getCompany());
        tableItem.setText(1, work.getPosition());
        tableItem.setText(2, work.getExperience().toString());
    }

    void clearPersonnelFilesTable() {
        int index = archiveWindow.tableOfPersonnelFiles.getSelectionIndex();
        archiveWindow.tableOfPersonnelFiles.select(index);
        archiveWindow.tableOfPersonnelFiles.removeAll();
    }

    void clearWorksTable() {
        archiveWindow.tableOfWorks.removeAll();
    }

    void makeFieldsEnable(boolean enable) {
        previousPassportID = archiveWindow.textPassport.getText();

        archiveWindow.textFirstName.setEnabled(enable);
        archiveWindow.textMiddleName.setEnabled(enable);
        archiveWindow.textLastName.setEnabled(enable);

        archiveWindow.comboDayOfBirth.setEnabled(enable);
        archiveWindow.comboMonthOfBirth.setEnabled(enable);
        archiveWindow.comboYearOfBirth.setEnabled(enable);
        archiveWindow.comboGender.setEnabled(enable);

        archiveWindow.textPassport.setEnabled(enable);

        archiveWindow.textCountry.setEnabled(enable);
        archiveWindow.textCity.setEnabled(enable);
        archiveWindow.textStreet.setEnabled(enable);
        archiveWindow.textHouse.setEnabled(enable);
        archiveWindow.textHomePhone.setEnabled(enable);
        archiveWindow.textMobilePhone.setEnabled(enable);

        archiveWindow.buttonAddWork.setEnabled(enable);
        archiveWindow.buttonDeleteWork.setEnabled(enable);
    }

    void setTablesHeadersVisible(boolean visible) {
        archiveWindow.tableOfPersonnelFiles.setLinesVisible(visible);
        archiveWindow.tableOfPersonnelFiles.setHeaderVisible(visible);
        archiveWindow.tableOfWorks.setLinesVisible(visible);
        archiveWindow.tableOfWorks.setHeaderVisible(visible);
    }

    public static void main(String[] args) {
    }
}
