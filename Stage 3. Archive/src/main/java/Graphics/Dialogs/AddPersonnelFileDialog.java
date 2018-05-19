package Graphics.Dialogs;

import Graphics.Constants.GraphicsConstants;
import Users.PersonnelFile;
import Users.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static Graphics.Constants.GraphicsConstants.SEGOE_UI_SEMILIGHT;

public class AddPersonnelFileDialog extends Dialog {
    private Shell shell;
    private Text textFirstName;
    private Text textMiddleName;
    private Text textLastName;
    private Text textPassport;
    private Text textCountry;
    private Text textCity;
    private Text textStreet;
    private Text textHouse;
    private Text textHomePhone;
    private Text textMobilePhone;
    private Combo comboDayOfBirth;
    private Combo comboMonthOfBirth;
    private Combo comboYearOfBirth;
    private Combo comboGender;
    private Button buttonSavePersonnelFile;
    private Button buttonDeleteJob;
    private Button buttonAddJob;
    private Table tableOfJobs;
    private PersonnelFile personnelFile = null;

    public AddPersonnelFileDialog(Shell shell) {
        super(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CLOSE | SWT.TITLE);
    }

    public PersonnelFile open() {
        shell = new Shell(getParent(), getStyle());
        Display display = getParent().getDisplay();
        createContents(shell, display);
        initListeners();
        shell.open();
        shell.layout();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
        return personnelFile;
    }

    protected void createContents(Shell shell, Display display) {
        Font font12 = new Font(display, SEGOE_UI_SEMILIGHT, 12, SWT.NORMAL);
        Font font10 = new Font(display, SEGOE_UI_SEMILIGHT, 10, SWT.NORMAL);
        Font font16 = new Font(display, SEGOE_UI_SEMILIGHT, 16, SWT.NORMAL);

        shell.setSize(589, 817);
        shell.setText("Add personnel file");

        Composite composite = new Composite(shell, SWT.NONE);
        composite.setBounds(0, 226, 572, 255);

        Label label = new Label(composite, SWT.CENTER);
        label.setText("Contact Information");
        label.setFont(font16);
        label.setBounds(10, 10, 556, 37);

        Label label_1 = new Label(composite, SWT.RIGHT);
        label_1.setText("Country");
        label_1.setFont(font12);
        label_1.setBounds(10, 53, 120, 26);

        Label label_2 = new Label(composite, SWT.RIGHT);
        label_2.setText("City");
        label_2.setFont(font12);
        label_2.setBounds(10, 85, 120, 28);

        Label label_3 = new Label(composite, SWT.RIGHT);
        label_3.setText("Street");
        label_3.setFont(font12);
        label_3.setBounds(10, 119, 120, 28);

        Label label_4 = new Label(composite, SWT.RIGHT);
        label_4.setText("House");
        label_4.setFont(font12);
        label_4.setBounds(10, 153, 120, 28);

        Label label_5 = new Label(composite, SWT.RIGHT);
        label_5.setText("Home phone");
        label_5.setFont(font12);
        label_5.setBounds(10, 187, 120, 28);

        Label label_6 = new Label(composite, SWT.RIGHT);
        label_6.setText("Mobile phone");
        label_6.setFont(font12);
        label_6.setBounds(10, 221, 120, 28);

        textCountry = new Text(composite, SWT.BORDER);
        textCountry.setFont(font10);
        textCountry.setBounds(136, 53, 426, 28);

        textCity = new Text(composite, SWT.BORDER);
        textCity.setFont(font10);
        textCity.setBounds(136, 85, 426, 28);

        textStreet = new Text(composite, SWT.BORDER);
        textStreet.setFont(font10);
        textStreet.setBounds(136, 119, 426, 28);

        textHouse = new Text(composite, SWT.BORDER);
        textHouse.setFont(font10);
        textHouse.setBounds(136, 153, 426, 28);

        textHomePhone = new Text(composite, SWT.BORDER);
        textHomePhone.setFont(font10);
        textHomePhone.setBounds(136, 187, 426, 28);

        textMobilePhone = new Text(composite, SWT.BORDER);
        textMobilePhone.setFont(font10);
        textMobilePhone.setBounds(136, 221, 426, 28);

        Composite composite_1 = new Composite(shell, SWT.NONE);
        composite_1.setBounds(0, 0, 572, 220);

        Label label_7 = new Label(composite_1, SWT.RIGHT);
        label_7.setText("First name");
        label_7.setFont(font12);
        label_7.setBounds(10, 53, 120, 28);

        Label label_8 = new Label(composite_1, SWT.RIGHT);
        label_8.setText("Middle name");
        label_8.setFont(font12);
        label_8.setBounds(10, 87, 120, 26);

        Label label_9 = new Label(composite_1, SWT.RIGHT);
        label_9.setText("Last name");
        label_9.setFont(font12);
        label_9.setBounds(10, 119, 120, 26);

        Label label_10 = new Label(composite_1, SWT.RIGHT);
        label_10.setText("Birth Date");
        label_10.setFont(font12);
        label_10.setBounds(10, 151, 120, 28);

        comboDayOfBirth = new Combo(composite_1, SWT.READ_ONLY);
        comboDayOfBirth.setFont(font10);
        comboDayOfBirth.setBounds(136, 151, 56, 31);
        comboDayOfBirth.setItems(GraphicsConstants.MONTH_31_DAYS);
        comboDayOfBirth.setTextLimit(10);

        comboMonthOfBirth = new Combo(composite_1, SWT.READ_ONLY);
        comboMonthOfBirth.setFont(font10);
        comboMonthOfBirth.setBounds(198, 151, 56, 31);
        comboMonthOfBirth.setItems(GraphicsConstants.MONTHS);
        comboMonthOfBirth.setTextLimit(10);

        comboYearOfBirth = new Combo(composite_1, SWT.READ_ONLY);
        comboYearOfBirth.setFont(font10);
        comboYearOfBirth.setBounds(260, 151, 97, 31);
        comboYearOfBirth.setItems(GraphicsConstants.YEARS);
        comboYearOfBirth.setTextLimit(10);

        Label label_11 = new Label(composite_1, SWT.RIGHT);
        label_11.setText("Gender");
        label_11.setFont(font12);
        label_11.setBounds(363, 151, 63, 28);

        comboGender = new Combo(composite_1, SWT.READ_ONLY);
        comboGender.setItems(new String[]{"Female", "Male"});
        comboGender.setFont(font10);
        comboGender.setBounds(432, 151, 130, 31);

        textFirstName = new Text(composite_1, SWT.BORDER);
        textFirstName.setFont(font10);
        textFirstName.setBounds(136, 53, 426, 28);

        textMiddleName = new Text(composite_1, SWT.BORDER);
        textMiddleName.setFont(font10);
        textMiddleName.setBounds(136, 87, 426, 28);

        textLastName = new Text(composite_1, SWT.BORDER);
        textLastName.setFont(font10);
        textLastName.setBounds(136, 119, 426, 28);

        Label label_12 = new Label(composite_1, SWT.RIGHT);
        label_12.setText("Passport \u2116");
        label_12.setFont(font12);
        label_12.setBounds(10, 185, 120, 28);

        textPassport = new Text(composite_1, SWT.BORDER);
        textPassport.setFont(font10);
        textPassport.setBounds(136, 185, 426, 28);
        textPassport.setTextLimit(15);

        Label label_13 = new Label(composite_1, SWT.CENTER);
        label_13.setText("Basic Information");
        label_13.setFont(font16);
        label_13.setBounds(10, 10, 556, 37);

        Composite composite_2 = new Composite(shell, SWT.NONE);
        composite_2.setBounds(0, 487, 572, 231);

        tableOfJobs = new Table(composite_2, SWT.BORDER);
        tableOfJobs.setBounds(10, 0, 552, 184);
        for (int i = 0; i < 3; i++) {
            TableColumn column1 = new TableColumn(tableOfJobs, SWT.NONE);
            column1.setText(GraphicsConstants.TABLE_HEADERS_OF_WORKS[i]);
            column1.setWidth(200);
        }

        tableOfJobs.setLinesVisible(true);
        tableOfJobs.setHeaderVisible(true);

        buttonAddJob = new Button(composite_2, SWT.NONE);
        buttonAddJob.setText("Add");
        buttonAddJob.setFont(font12);
        buttonAddJob.setBounds(10, 190, 280, 40);

        buttonDeleteJob = new Button(composite_2, SWT.NONE);
        buttonDeleteJob.setText("Delete");
        buttonDeleteJob.setFont(font12);
        buttonDeleteJob.setBounds(296, 190, 266, 40);

        buttonSavePersonnelFile = new Button(shell, SWT.NONE);
        buttonSavePersonnelFile.setText("Save personnel file");
        buttonSavePersonnelFile.setFont(font12);
        buttonSavePersonnelFile.setBounds(10, 724, 551, 40);
    }

    void initListeners() {
        buttonSavePersonnelFile.addListener(SWT.Selection, event -> {
            personnelFile = createPersonnelFile();
            shell.dispose();
        });
        buttonAddJob.addSelectionListener(new SelectionListener() {
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
        buttonDeleteJob.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                int rowIndex = tableOfJobs.getSelectionIndex();
                if (rowIndex != -1) {
                    tableOfJobs.remove(rowIndex);
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });
        comboMonthOfBirth.addModifyListener(modifyEvent -> {
            int month = Integer.parseInt(comboMonthOfBirth.getText());

            if (month == 2) {
                comboDayOfBirth.removeAll();
                comboDayOfBirth.setItems(GraphicsConstants.MONTH_28_DAYS);
                return;
            }
            if (GraphicsConstants.NUM_OF_MONTHS_THAT_HAS_31_DAYS.contains(month)) {
                comboDayOfBirth.removeAll();
                comboDayOfBirth.setItems(GraphicsConstants.MONTH_31_DAYS);

                return;
            } else {
                comboDayOfBirth.removeAll();
                comboDayOfBirth.setItems(GraphicsConstants.MONTH_30_DAYS);
            }
        });
        textFirstName.addVerifyListener(GraphicsConstants.verifyListenerForText);
        textMiddleName.addVerifyListener(GraphicsConstants.verifyListenerForText);
        textLastName.addVerifyListener(GraphicsConstants.verifyListenerForText);
        textPassport.addVerifyListener(GraphicsConstants.verifyListenerForPassport);
        textCountry.addVerifyListener(GraphicsConstants.verifyListenerForText);
        textCity.addVerifyListener(GraphicsConstants.verifyListenerForText);
        textHomePhone.addVerifyListener(GraphicsConstants.verifyListenerForNumber);
        textHomePhone.setTextLimit(15);
        textMobilePhone.addVerifyListener(GraphicsConstants.verifyListenerForNumber);
        textMobilePhone.setTextLimit(15);
        textHouse.addVerifyListener(GraphicsConstants.verifyListenerForNumber);
    }

    PersonnelFile createPersonnelFile() {
        String localDate = comboYearOfBirth.getText() + '-' + comboMonthOfBirth.getText() + '-' + comboDayOfBirth.getText();

        PersonnelFile personnelFile = new PersonnelFile(
                textFirstName.getText(),
                textMiddleName.getText(),
                textLastName.getText(),
                comboGender.getText(),
                localDate,
                textPassport.getText(),
                textCountry.getText(),
                textCity.getText(),
                textStreet.getText(),
                textHouse.getText(),
                textMobilePhone.getText(),
                textHomePhone.getText());

        List<Job> jobList = new ArrayList<>();
        fillJobList(jobList);
        personnelFile.setJobs(jobList);
        return personnelFile;
    }

    void fillJobList(List<Job> jobs) {
        for (int i = 0; i < tableOfJobs.getItemCount(); i++) {
            TableItem jobItem = tableOfJobs.getItem(i);
            Job job = new Job(jobItem.getText(0), jobItem.getText(1), jobItem.getText(2));
            jobs.add(job);
        }
    }

    void fillJobTable(Job job) {
        TableItem tableItem = new TableItem(tableOfJobs, SWT.NONE);
        tableItem.setText(0, job.getCompany());
        tableItem.setText(1, job.getPosition());
        tableItem.setText(2, job.getExperience());
    }
}
