package Graphics.ArchiveWindow;

import Graphics.Constants.GraphicsConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import java.awt.*;

import static Graphics.Constants.GraphicsConstants.*;

public class ArchiveWindow {
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
    Text textSearch;
    Label labelFirstName;
    Label labelMiddleName;
    Label labelLastName;
    Label labelBirthDate;
    Combo comboDayOfBirth;
    Combo comboMonthOfBirth;
    Combo comboYearOfBirth;
    Label labelGender;
    Combo comboGender;
    Label labelPassport;
    Label labelBasicInformation;
    Label labelContactInformation;
    Label labelAddress;
    Label labelCity;
    Label labelStreet;
    Label labelHouse;
    Label labelHomeNumber;
    Label labelMobilePhone;
    Button buttonAddWork;
    Table tableOfWorks;
    Button buttonDeleteWork;
    Table tableOfPersonnelFiles;
    Button buttonEditPerson;
    Button buttonSavePerson;
    Button buttonDeletePersonnelFile;
    Display display;
    Font font12;
    Font font10;
    Font font16;
    Shell shell;
    Composite compositeBasicInformation;
    VerifyListener verifyListenerForText;
    VerifyListener verifyListenerForPhone;
    MenuItem addPersonnelFileItem;
    MenuItem changeLawsItem;
    Composite compositeContactInformation;
    Composite compositeWorks;
    Composite compositeArchivesTable;

    public ArchiveWindow(Display display, Shell shell) {
        this.display = display;
        this.shell = shell;
        clearShell();
        initComponents();
        setComponentsSettings();
        setComponentsBounds();
        addListenersForComponents();
    }

    void clearShell() {
        for (Control control : shell.getChildren()) {
            control.dispose();
        }
    }

    void initComponents() {
        verifyListenerForText = verifyEvent -> {
            if (verifyEvent.text.matches("[a-zA-Z]") || verifyEvent.character == '\b' || verifyEvent.character == 0x7f) {
            } else {
                verifyEvent.doit = false;
            }
        };
        verifyListenerForPhone = verifyEvent -> {
            if (verifyEvent.text.matches("[0-9]") || verifyEvent.character == '\b' || verifyEvent.character == 0x7f) {
            } else {
                verifyEvent.doit = false;
            }
        };
        display = Display.getDefault();
        shell = new Shell();

        font12 = new Font(display, SEGOE_UI_SEMILIGHT, 12, SWT.NORMAL);
        font10 = new Font(display, SEGOE_UI_SEMILIGHT, 10, SWT.NORMAL);
        font16 = new Font(display, SEGOE_UI_SEMILIGHT, 16, SWT.NORMAL);

        compositeBasicInformation = new Composite(shell, SWT.NONE);

        labelFirstName = new Label(compositeBasicInformation, SWT.RIGHT);
        labelMiddleName = new Label(compositeBasicInformation, SWT.RIGHT);
        labelLastName = new Label(compositeBasicInformation, SWT.RIGHT);
        labelBirthDate = new Label(compositeBasicInformation, SWT.RIGHT);
        comboDayOfBirth = new Combo(compositeBasicInformation, SWT.READ_ONLY);
        comboMonthOfBirth = new Combo(compositeBasicInformation, SWT.READ_ONLY);
        comboYearOfBirth = new Combo(compositeBasicInformation, SWT.READ_ONLY);
        labelGender = new Label(compositeBasicInformation, SWT.RIGHT);
        comboGender = new Combo(compositeBasicInformation, SWT.READ_ONLY);
        textFirstName = new Text(compositeBasicInformation, SWT.BORDER);
        textMiddleName = new Text(compositeBasicInformation, SWT.BORDER);
        textLastName = new Text(compositeBasicInformation, SWT.BORDER);
        labelPassport = new Label(compositeBasicInformation, SWT.RIGHT);
        textPassport = new Text(compositeBasicInformation, SWT.BORDER);
        labelBasicInformation = new Label(compositeBasicInformation, SWT.CENTER);

        Menu menuBar = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menuBar);

        addPersonnelFileItem = new MenuItem(menuBar, SWT.NONE);
        addPersonnelFileItem.setText("Add personnel file");

        compositeContactInformation = new Composite(shell, SWT.NONE);

        labelContactInformation = new Label(compositeContactInformation, SWT.CENTER);
        labelAddress = new Label(compositeContactInformation, SWT.RIGHT);
        labelCity = new Label(compositeContactInformation, SWT.RIGHT);
        labelStreet = new Label(compositeContactInformation, SWT.RIGHT);
        labelHouse = new Label(compositeContactInformation, SWT.RIGHT);
        labelHomeNumber = new Label(compositeContactInformation, SWT.RIGHT);
        labelMobilePhone = new Label(compositeContactInformation, SWT.RIGHT);
        textCountry = new Text(compositeContactInformation, SWT.BORDER);
        textCity = new Text(compositeContactInformation, SWT.BORDER);
        textStreet = new Text(compositeContactInformation, SWT.BORDER);
        textHouse = new Text(compositeContactInformation, SWT.BORDER);
        textHomePhone = new Text(compositeContactInformation, SWT.BORDER);
        textMobilePhone = new Text(compositeContactInformation, SWT.BORDER);

        compositeWorks = new Composite(shell, SWT.NONE);

        tableOfWorks = new Table(compositeWorks, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
        for (int i = 0; i < 3; i++) {
            TableColumn column = new TableColumn(tableOfWorks, SWT.NONE);
            column.setText(GraphicsConstants.TABLE_HEADERS_OF_WORKS[i]);
        }
        buttonAddWork = new Button(compositeWorks, SWT.NONE);
        buttonDeleteWork = new Button(compositeWorks, SWT.NONE);

        compositeArchivesTable = new Composite(shell, SWT.NONE);

        textSearch = new Text(compositeArchivesTable, SWT.BORDER);

        tableOfPersonnelFiles = new Table(compositeArchivesTable, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);

        buttonEditPerson = new Button(compositeArchivesTable, SWT.NONE);
        buttonSavePerson = new Button(compositeArchivesTable, SWT.NONE);
        buttonDeletePersonnelFile = new Button(compositeArchivesTable, SWT.NONE);
    }

    void setComponentsSettings() {
        shell.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
        shell.setText("Archive");

        labelFirstName.setText("First name");
        labelFirstName.setFont(font12);
        labelMiddleName.setText("Middle name");
        labelMiddleName.setFont(font12);
        labelLastName.setText("Last name");
        labelLastName.setFont(font12);
        labelBirthDate.setText("Birth Date");
        labelBirthDate.setFont(font12);
        labelGender.setText("Gender");
        labelGender.setFont(font12);
        labelPassport.setText("Passport \u2116");
        labelPassport.setFont(font12);
        labelBasicInformation.setText("Basic Information");
        labelBasicInformation.setFont(font16);
        labelContactInformation.setText("Contact Information");
        labelContactInformation.setFont(font16);
        labelAddress.setText("Country");
        labelAddress.setFont(font12);
        labelCity.setText("City");
        labelCity.setFont(font12);
        labelStreet.setText("Street");
        labelStreet.setFont(font12);
        labelHouse.setText("House");
        labelHouse.setFont(font12);
        labelHomeNumber.setText("Home phone");
        labelHomeNumber.setFont(font12);
        labelMobilePhone.setText("Mobile phone");
        labelMobilePhone.setFont(font12);

        comboDayOfBirth.setFont(font10);
        comboDayOfBirth.setItems(MONTH_30_DAYS);
        comboDayOfBirth.setVisibleItemCount(10);
        comboMonthOfBirth.setFont(font10);
        comboMonthOfBirth.setItems(MONTHS);
        comboMonthOfBirth.setVisibleItemCount(10);
        comboYearOfBirth.setFont(font10);
        comboYearOfBirth.setItems(YEARS);
        comboYearOfBirth.setVisibleItemCount(10);
        comboGender.setFont(font10);
        comboGender.setItems(GENDER);

        textFirstName.setFont(font10);
        textMiddleName.setFont(font10);
        textLastName.setFont(font10);
        textPassport.setFont(font10);
        textPassport.setTextLimit(15);
        textCountry.setFont(font10);
        textCity.setFont(font10);
        textStreet.setFont(font10);
        textHouse.setFont(font10);
        textHomePhone.setFont(font10);
        textMobilePhone.setFont(font10);
        textSearch.setFont(font10);

        buttonAddWork.setFont(font12);
        buttonAddWork.setText("Add");
        buttonDeleteWork.setFont(font12);
        buttonDeleteWork.setText("Delete");
        buttonEditPerson.setFont(font12);
        buttonEditPerson.setText("Edit");
        buttonSavePerson.setFont(font12);
        buttonSavePerson.setText("Save");
        buttonDeletePersonnelFile.setText("Delete");
        buttonDeletePersonnelFile.setFont(font12);

        TableColumn column = new TableColumn(tableOfPersonnelFiles, SWT.NONE);
        column.setText(GraphicsConstants.TABLE_HEADERS_OF_PERSONNEL_FILES[0]);
        column.setWidth(100);
        column = new TableColumn(tableOfPersonnelFiles, SWT.NONE);
        column.setText(GraphicsConstants.TABLE_HEADERS_OF_PERSONNEL_FILES[1]);
        column.setWidth(240);

        TableItem tableItem = new TableItem(tableOfPersonnelFiles, SWT.NONE);
        tableItem.setText(new String[]{"MMMMM", "MMMMMMMMMMMMMMM"});
    }

    void addListenersForComponents() {
        textFirstName.addVerifyListener(verifyListenerForText);
        textMiddleName.addVerifyListener(verifyListenerForText);
        textLastName.addVerifyListener(verifyListenerForText);
        textPassport.addVerifyListener(verifyEvent -> {
            if (verifyEvent.text.matches("[a-zA-Z0-9]") || verifyEvent.character == '\b' || verifyEvent.character == 0x7f) {
                verifyEvent.text = verifyEvent.text.toUpperCase();
            } else {
                verifyEvent.doit = false;
            }
        });
        textCountry.addVerifyListener(verifyListenerForText);
        textCity.addVerifyListener(verifyListenerForText);
        textHomePhone.addVerifyListener(verifyListenerForPhone);
        textMobilePhone.addVerifyListener(verifyListenerForPhone);
    }

    void setComponentsBounds() {
        shell.setSize(950, 800);

        compositeBasicInformation.setBounds(0, 0, 576, 220);
        labelFirstName.setBounds(10, 53, 120, 28);
        labelMiddleName.setBounds(10, 87, 120, 26);
        labelLastName.setBounds(10, 119, 120, 26);
        labelBirthDate.setBounds(10, 151, 120, 28);
        comboDayOfBirth.setBounds(136, 151, 56, 28);
        comboMonthOfBirth.setBounds(198, 151, 56, 28);
        comboYearOfBirth.setBounds(260, 151, 97, 28);
        labelGender.setBounds(363, 151, 63, 28);
        comboGender.setBounds(432, 151, 134, 28);
        textFirstName.setBounds(136, 53, 430, 28);
        textMiddleName.setBounds(136, 87, 430, 28);
        textLastName.setBounds(136, 119, 430, 28);
        labelPassport.setBounds(10, 185, 120, 28);
        textPassport.setBounds(136, 185, 430, 28);
        labelBasicInformation.setBounds(10, 10, 556, 37);
        compositeContactInformation.setBounds(0, 226, 576, 255);
        labelContactInformation.setBounds(10, 10, 556, 37);
        labelAddress.setBounds(10, 53, 120, 26);
        labelCity.setBounds(10, 85, 120, 28);
        labelStreet.setBounds(10, 119, 120, 28);
        labelHouse.setBounds(10, 153, 120, 28);
        labelHomeNumber.setBounds(10, 187, 120, 28);
        labelMobilePhone.setBounds(10, 221, 120, 28);
        textCountry.setBounds(136, 53, 430, 28);
        textCity.setBounds(136, 85, 430, 28);
        textStreet.setBounds(136, 119, 430, 28);
        textHouse.setBounds(136, 153, 430, 28);
        textHomePhone.setBounds(136, 187, 430, 28);
        textMobilePhone.setBounds(136, 221, 430, 28);
        compositeWorks.setBounds(0, 487, 576, 231);
        tableOfWorks.setBounds(10, 0, 566, 184);
        buttonAddWork.setBounds(10, 190, 280, 40);
        buttonDeleteWork.setBounds(296, 190, 280, 40);
        compositeArchivesTable.setBounds(582, 0, 350, 718);
        textSearch.setBounds(0, 10, 340, 28);
        tableOfPersonnelFiles.setBounds(0, 44, 340, 627);
        buttonEditPerson.setBounds(0, 677, 107, 40);
        buttonSavePerson.setBounds(116, 677, 107, 40);
        buttonDeletePersonnelFile.setBounds(233, 677, 107, 40);
    }

    public void start() {
        shell.open();
        shell.layout();
        tableOfPersonnelFiles.setLinesVisible(true);
        tableOfPersonnelFiles.setHeaderVisible(true);
        tableOfWorks.setLinesVisible(true);
        tableOfWorks.setHeaderVisible(true);

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new ArchiveWindow(Display.getDefault(), new Shell()).start());
    }
}
