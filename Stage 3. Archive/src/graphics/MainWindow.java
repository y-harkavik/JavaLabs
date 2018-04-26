package graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import java.awt.*;

public class MainWindow {
    List personsList;
    Shell shell;
    Display display;
    Composite composite;
    Label nameLabel;
    Label surnameLabel;
    Label homePhoneLabel;
    Label mobilePhoneLabel;
    StyledText mobilePhoneText;
    List jobsList;
    Label descriptionLabel;
    StyledText descriptionText;
    Button addButton;
    Button removeButton;
    StyledText homePhoneText;
    StyledText nameText;
    StyledText surnameText;
    Button editButton;
    Button saveButton;
    StyledText searchText;
    Menu menuBar;
    MenuItem addPersonnelFileItem;
    Font font14Arial;
    Font font12Arial;

    private void initComponents() {
        display = Display.getDefault();
        shell = new Shell();

        composite = new Composite(shell, SWT.NONE);

        jobsList = new List(composite, SWT.BORDER);
        personsList = new List(shell, SWT.BORDER);

        nameLabel = new Label(composite, SWT.NONE);
        surnameLabel = new Label(composite, SWT.NONE);
        homePhoneLabel = new Label(composite, SWT.NONE);
        mobilePhoneLabel = new Label(composite, SWT.NONE);
        descriptionLabel = new Label(composite, SWT.NONE);

        addButton = new Button(composite, SWT.NONE);
        removeButton = new Button(composite, SWT.NONE);
        editButton = new Button(composite, SWT.NONE);
        saveButton = new Button(composite, SWT.NONE);

        searchText = new StyledText(shell, SWT.BORDER);
        mobilePhoneText = new StyledText(composite, SWT.BORDER);
        surnameText = new StyledText(composite, SWT.BORDER);
        homePhoneText = new StyledText(composite, SWT.BORDER);
        descriptionText = new StyledText(composite, SWT.BORDER);
        nameText = new StyledText(composite, SWT.BORDER);

        menuBar = new Menu(shell, SWT.BAR);

        addPersonnelFileItem = new MenuItem(menuBar, SWT.NONE);

        font14Arial = new Font(display, "Arial", 14, SWT.NORMAL);
        font12Arial = new Font(display, "Arial", 12, SWT.NORMAL);
    }

    private void setupComponents() {
        setupShell();
        setupComposite();
        setupLists();
        setupButtons();
        setupLabels();
        setupTexts();
        setupMenuBar();
    }

    private void setupLabels() {
        nameLabel.setFont(font14Arial);
        nameLabel.setAlignment(SWT.CENTER);
        nameLabel.setBounds(0, 10, 100, 26);
        nameLabel.setText("Name");

        surnameLabel.setText("Surname");
        surnameLabel.setFont(font14Arial);
        surnameLabel.setAlignment(SWT.CENTER);
        surnameLabel.setBounds(0, 42, 100, 26);

        homePhoneLabel.setText("Home phone");
        homePhoneLabel.setFont(font14Arial);
        homePhoneLabel.setAlignment(SWT.CENTER);
        homePhoneLabel.setBounds(0, 74, 100, 55);

        mobilePhoneLabel.setText("Mobile phone");
        mobilePhoneLabel.setFont(font14Arial);
        mobilePhoneLabel.setAlignment(SWT.CENTER);
        mobilePhoneLabel.setBounds(0, 135, 100, 55);

        descriptionLabel.setText("Description");
        descriptionLabel.setFont(font14Arial);
        descriptionLabel.setAlignment(SWT.CENTER);
        descriptionLabel.setBounds(0, 402, 566, 26);
    }

    private void setupTexts() {
        mobilePhoneText.setEditable(false);
        mobilePhoneText.setFont(font12Arial);
        mobilePhoneText.setBounds(106, 135, 460, 30);

        descriptionText.setEditable(false);
        descriptionText.setFont(font12Arial);
        descriptionText.setBounds(0, 434, 566, 170);

        homePhoneText.setEditable(false);
        homePhoneText.setFont(font12Arial);
        homePhoneText.setBounds(106, 73, 460, 30);

        nameText.setEditable(false);
        nameText.setFont(font12Arial);
        nameText.setBounds(106, 6, 460, 30);

        surnameText.setEditable(false);
        surnameText.setFont(font12Arial);
        surnameText.setBounds(106, 38, 460, 30);

        searchText.setEditable(false);
        searchText.setFont(font12Arial);
        searchText.setBounds(10, 10, 340, 40);
    }

    private void setupButtons() {
        addButton.setBounds(506, 196, 60, 30);
        addButton.setText("Add");

        removeButton.setBounds(506, 232, 60, 30);
        removeButton.setText("Remove");

        editButton.setBounds(0, 610, 283, 34);
        editButton.setText("Edit");

        saveButton.setBounds(289, 610, 277, 34);
        saveButton.setText("Save");
    }

    private void setupLists() {
        personsList.setFont(font12Arial);
        personsList.setBounds(10, 55, 340, 589);

        jobsList.setFont(new Font(display, "Arial", 12, SWT.NORMAL));
        jobsList.setBounds(0, 196, 500, 200);
    }

    private void setupShell() {
        shell.setSize(950, 720);
        shell.setText("Archive");
    }

    private void setupMenuBar() {
        shell.setMenuBar(menuBar);
        addPersonnelFileItem.setText("Add personnel file");
    }

    private void setupComposite() {
        composite.setBounds(356, 0, 576, 644);
    }

    public MainWindow() {
        initComponents();
        setupComponents();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new MainWindow();
        });
    }

}
