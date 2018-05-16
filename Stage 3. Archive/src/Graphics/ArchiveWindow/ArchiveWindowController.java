package Graphics.ArchiveWindow;

import Communicate.Message.Request.ClientRequest.AdministratorRequest;
import Communicate.Message.Request.ClientRequest.RequestType;
import Graphics.Constants.GraphicsConstants;
import Graphics.Dialogs.AddPersonnelFileDialog;
import Graphics.Dialogs.AddJobDialog;
import Graphics.Dialogs.ChangeLawsDialog;
import Users.PersonnelFile;
import Users.Job;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArchiveWindowController {
    ArchiveWindow archiveWindow;
    ArchiveWindowModel archiveWindowModel;
    List<Account> accountList;
    String previousPassportID;

    public ArchiveWindowController(Client currentClient,
                                   Map<String, String> mapOfPersonnelFiles,
                                   List<Account> accountList,
                                   List<Laws> lawsList) {
        archiveWindow = new ArchiveWindow(Display.getCurrent(), new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN));
        archiveWindowModel = new ArchiveWindowModel(this, currentClient);
        setConstraints(lawsList);
        initListeners();
        setPersonnelFilesInTable(mapOfPersonnelFiles);
        makeFieldsEnable(false);
        this.accountList = accountList;
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
            if (archiveWindow.tableOfPersonnelFiles.getSelectionIndex() != -1) {
                addVerifyListeners();
                makeFieldsEnable(true);
            }
        });
        archiveWindow.buttonDeletePersonnelFile.addListener(SWT.Selection, event -> {
            int rowIndex = archiveWindow.tableOfPersonnelFiles.getSelectionIndex();
            if (rowIndex != -1) {
                archiveWindowModel.sendMessage(new AdministratorRequest(RequestType.DELETE,
                        null,
                        archiveWindow.tableOfPersonnelFiles.getItem(rowIndex).getText(1),
                        null));
                clearPersonnelFileInformation();
                clearJobsTable();
            }
        });
        archiveWindow.buttonSavePerson.addListener(SWT.Selection, event -> {
            if (archiveWindow.textFirstName.getEnabled()) {
                archiveWindowModel.sendMessage(
                        new AdministratorRequest(RequestType.UPDATE,
                                createPersonnelFiles(),
                                previousPassportID,
                                null));
            }
        });
        archiveWindow.shell.addListener(SWT.Close, event -> {
            archiveWindowModel.sendMessage(new AdministratorRequest(RequestType.DISCONNECT,
                    null,
                    null,
                    null));
            archiveWindowModel.closeConnection();
        });
        archiveWindow.addPersonnelFileItem.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Display.getCurrent().asyncExec(() -> {
                    PersonnelFile personnelFile = new AddPersonnelFileDialog(new Shell()).open();
                    if (personnelFile != null) {
                        archiveWindowModel.sendMessage(new AdministratorRequest(RequestType.ADD,
                                personnelFile,
                                null,
                                null));
                    }
                });

            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });
        archiveWindow.buttonAddJob.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Display.getCurrent().asyncExec(() -> {
                    Job job = new AddJobDialog(new Shell()).open();
                    if (job != null) {
                        fillJobTable(job);
                    }
                });
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });
        archiveWindow.buttonDeleteJob.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                int rowIndex = archiveWindow.tableOfJobs.getSelectionIndex();
                if (rowIndex != -1) {
                    archiveWindow.tableOfJobs.remove(rowIndex);
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });
        archiveWindow.changeLawsItem.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                Display.getDefault().asyncExec(() -> {
                    accountList = new ChangeLawsDialog(new Shell()).open(accountList);
                    archiveWindowModel.sendMessage(new AdministratorRequest(
                            RequestType.CHANGE_LAWS,
                            null,
                            null,
                            accountList));
                });
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

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

        List<Job> jobList = new ArrayList<>();
        fillJobList(jobList);
        personnelFile.setJobs(jobList);
        return personnelFile;
    }

    void fillJobList(List<Job> jobs) {
        for (int i = 0; i < archiveWindow.tableOfJobs.getItemCount(); i++) {
            TableItem jobItem = archiveWindow.tableOfJobs.getItem(i);
            Job job = new Job(jobItem.getText(0), jobItem.getText(1), Integer.valueOf(jobItem.getText(2)));
            jobs.add(job);
        }
    }

    void setPersonnelFileInformation(PersonnelFile personnelFile) {
        removeVerifyListeners();
        setBasicInformation(personnelFile.getBasicInformation());
        setContactInformation(personnelFile.getContactInformation());
        setJobsInTable(personnelFile.getJobs());
    }

    void removeVerifyListeners() {
        archiveWindow.textFirstName.removeVerifyListener(GraphicsConstants.verifyListenerForText);
        archiveWindow.textMiddleName.removeVerifyListener(GraphicsConstants.verifyListenerForText);
        archiveWindow.textLastName.removeVerifyListener(GraphicsConstants.verifyListenerForText);
        archiveWindow.textPassport.removeVerifyListener(GraphicsConstants.verifyListenerForPassport);
        archiveWindow.textCountry.removeVerifyListener(GraphicsConstants.verifyListenerForText);
        archiveWindow.textCity.removeVerifyListener(GraphicsConstants.verifyListenerForText);
        archiveWindow.textHomePhone.removeVerifyListener(GraphicsConstants.verifyListenerForNumber);
        archiveWindow.textMobilePhone.removeVerifyListener(GraphicsConstants.verifyListenerForNumber);
        archiveWindow.textHouse.removeVerifyListener(GraphicsConstants.verifyListenerForNumber);
    }

    void addVerifyListeners() {
        archiveWindow.textFirstName.addVerifyListener(GraphicsConstants.verifyListenerForText);
        archiveWindow.textMiddleName.addVerifyListener(GraphicsConstants.verifyListenerForText);
        archiveWindow.textLastName.addVerifyListener(GraphicsConstants.verifyListenerForText);
        archiveWindow.textPassport.addVerifyListener(GraphicsConstants.verifyListenerForPassport);
        archiveWindow.textCountry.addVerifyListener(GraphicsConstants.verifyListenerForText);
        archiveWindow.textCity.addVerifyListener(GraphicsConstants.verifyListenerForText);
        archiveWindow.textHomePhone.addVerifyListener(GraphicsConstants.verifyListenerForNumber);
        archiveWindow.textHomePhone.setTextLimit(15);
        archiveWindow.textMobilePhone.addVerifyListener(GraphicsConstants.verifyListenerForNumber);
        archiveWindow.textMobilePhone.setTextLimit(15);
        archiveWindow.textHouse.addVerifyListener(GraphicsConstants.verifyListenerForNumber);
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

    void fillPersonnelFileTable(String passportID, String name) {
        TableItem tableItem = new TableItem(archiveWindow.tableOfPersonnelFiles, SWT.NONE);
        tableItem.setText(0, name);
        tableItem.setText(1, passportID);
    }

    void setJobsInTable(List<Job> jobs) {
        clearJobsTable();
        jobs.forEach(this::fillJobTable);
    }

    void fillJobTable(Job job) {
        TableItem tableItem = new TableItem(archiveWindow.tableOfJobs, SWT.NONE);
        tableItem.setText(0, job.getCompany());
        tableItem.setText(1, job.getPosition());
        tableItem.setText(2, job.getExperience().toString());
    }

    void clearPersonnelFilesTable() {
        int index = archiveWindow.tableOfPersonnelFiles.getSelectionIndex();
        archiveWindow.tableOfPersonnelFiles.select(index);
        archiveWindow.tableOfPersonnelFiles.removeAll();
    }

    void clearJobsTable() {
        archiveWindow.tableOfJobs.removeAll();
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

        archiveWindow.buttonAddJob.setEnabled(enable);
        archiveWindow.buttonDeleteJob.setEnabled(enable);
    }

    void setTablesHeadersVisible(boolean visible) {
        archiveWindow.tableOfPersonnelFiles.setLinesVisible(visible);
        archiveWindow.tableOfPersonnelFiles.setHeaderVisible(visible);
        archiveWindow.tableOfJobs.setLinesVisible(visible);
        archiveWindow.tableOfJobs.setHeaderVisible(visible);
    }

    void clearPersonnelFileInformation() {
        previousPassportID = archiveWindow.textPassport.getText();

        archiveWindow.textFirstName.setText("");
        archiveWindow.textMiddleName.setText("");
        archiveWindow.textLastName.setText("");

        archiveWindow.comboDayOfBirth.setText("");
        archiveWindow.comboMonthOfBirth.setText("");
        archiveWindow.comboYearOfBirth.setText("");
        archiveWindow.comboGender.setText("");

        archiveWindow.textPassport.setText("");

        archiveWindow.textCountry.setText("");
        archiveWindow.textCity.setText("");
        archiveWindow.textStreet.setText("");
        archiveWindow.textHouse.setText("");
        archiveWindow.textHomePhone.setText("");
        archiveWindow.textMobilePhone.setText("");
    }

    public static void main(String[] args) {
    }
}
