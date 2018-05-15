package Graphics.Dialogs;

import Graphics.Constants.GraphicsConstants;
import Users.PersonnelFile;
import Users.Work;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static Graphics.Constants.GraphicsConstants.SEGOE_UI_SEMILIGHT;

public class AddPersonnelFileDialog extends Dialog {
    Shell shell;
    Text textFirstName;
    Text textMiddleName;
    Text textLastName;
    Text textPassport;
    Text textCountry;
    Text textCity;
    Text textStreet;
    Text textHouse;
    Text textHomePhone;
    Text textMobilePhone;
    Combo comboDayOfBirth;
    Combo comboMonthOfBirth;
    Combo comboYearOfBirth;
    Combo comboGender;
    private Button buttonSavePersonnelFile;
    private Button buttonDeleteWork;
    private Button buttonAddWork;
    private Table tableOfWorks;
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

        shell.setModified(true);
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

        Label label_13 = new Label(composite_1, SWT.CENTER);
        label_13.setText("Basic Information");
        label_13.setFont(font16);
        label_13.setBounds(10, 10, 556, 37);

        Composite composite_2 = new Composite(shell, SWT.NONE);
        composite_2.setBounds(0, 487, 572, 231);

        tableOfWorks = new Table(composite_2, SWT.BORDER);
        tableOfWorks.setBounds(10, 0, 552, 184);
        for (int i = 0; i < 3; i++) {
            TableColumn column1 = new TableColumn(tableOfWorks, SWT.NONE);
            column1.setText(GraphicsConstants.TABLE_HEADERS_OF_WORKS[i]);
            column1.setWidth(200);
        }

        tableOfWorks.setLinesVisible(true);
        tableOfWorks.setHeaderVisible(true);

        buttonAddWork = new Button(composite_2, SWT.NONE);
        buttonAddWork.setText("Add");
        buttonAddWork.setFont(font12);
        buttonAddWork.setBounds(10, 190, 280, 40);

        buttonDeleteWork = new Button(composite_2, SWT.NONE);
        buttonDeleteWork.setText("Delete");
        buttonDeleteWork.setFont(font12);
        buttonDeleteWork.setBounds(296, 190, 266, 40);

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
        comboMonthOfBirth.addModifyListener(modifyEvent -> {
            int month = Integer.parseInt(comboMonthOfBirth.getText());
            if (month == 2) {
                comboDayOfBirth.removeAll();
                comboDayOfBirth.setItems(GraphicsConstants.MONTH_28_DAYS);
            }
            if (month % 2 == 0) {
                comboDayOfBirth.removeAll();
                comboDayOfBirth.setItems(GraphicsConstants.MONTH_30_DAYS);
            }
            if (month % 2 == 1) {
                comboDayOfBirth.removeAll();
                comboDayOfBirth.setItems(GraphicsConstants.MONTH_31_DAYS);
            }
        });
        textFirstName.addVerifyListener(GraphicsConstants.verifyListenerForText);
        textMiddleName.addVerifyListener(GraphicsConstants.verifyListenerForText);
        textLastName.addVerifyListener(GraphicsConstants.verifyListenerForText);
        textPassport.addVerifyListener(GraphicsConstants.verifyListenerForPassport);
        textCountry.addVerifyListener(GraphicsConstants.verifyListenerForText);
        textCity.addVerifyListener(GraphicsConstants.verifyListenerForText);
        textHomePhone.addVerifyListener(GraphicsConstants.verifyListenerForPhone);
        textHomePhone.setTextLimit(15);
        textMobilePhone.addVerifyListener(GraphicsConstants.verifyListenerForPhone);
        textMobilePhone.setTextLimit(15);
        textHouse.addVerifyListener(GraphicsConstants.verifyListenerForPhone);
    }

    PersonnelFile createPersonnelFile() {
        LocalDate localDate = LocalDate.of(
                Integer.parseInt(comboYearOfBirth.getText()),
                Integer.parseInt(comboMonthOfBirth.getText()),
                Integer.parseInt(comboDayOfBirth.getText()));

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
                Integer.parseInt(textHouse.getText()),
                textMobilePhone.getText(),
                textHomePhone.getText());

        List<Work> workList = new ArrayList<>();
        fillWorkList(workList);
        personnelFile.setWorks(workList);
        return personnelFile;
    }

    void fillWorkList(List<Work> works) {
        for (int i = 0; i < tableOfWorks.getItemCount(); i++) {
            TableItem workItem = tableOfWorks.getItem(i);
            Work work = new Work(workItem.getText(0), workItem.getText(1), Integer.valueOf(workItem.getText(2)));
            works.add(work);
        }
    }
}
