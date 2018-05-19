package Graphics.Dialogs;

import Law.Laws;
import Users.Account;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Graphics.Constants.GraphicsConstants.SEGOE_UI_SEMILIGHT;

public class ChangeLawsDialog extends Dialog {
    protected Shell shell;
    private Table tableOfAccounts;
    private Map<String, List<Laws>> accountMap;
    private Button buttonSave;
    private Button buttonCreate;
    private Button buttonDelete;
    private Button buttonUpdate;

    public ChangeLawsDialog(Shell shell) {
        super(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CLOSE | SWT.TITLE);
    }

    public Map<String, List<Laws>> open(Map<String, List<Laws>> accountMap) {
        this.accountMap = new HashMap<>(accountMap);
        shell = new Shell(getParent(), getStyle());
        Display display = getParent().getDisplay();
        createContents(shell, display);
        initListeners();
        this.accountMap.keySet().forEach(this::fillTable);
        shell.open();
        shell.layout();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
        return this.accountMap;
    }

    protected void createContents(Shell shell, Display display) {
        Font font12 = new Font(display, SEGOE_UI_SEMILIGHT, 12, SWT.NORMAL);

        shell.setSize(450, 750);
        shell.setText("Account laws");

        Composite composite = new Composite(shell, SWT.NONE);
        composite.setBounds(0, 0, 432, 703);

        tableOfAccounts = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE);
        tableOfAccounts.setBounds(10, 10, 240, 683);
        TableColumn tableColumn = new TableColumn(tableOfAccounts, SWT.NONE);
        tableColumn.setText("Account");
        tableColumn.setWidth(236);
        tableOfAccounts.setHeaderVisible(true);
        tableOfAccounts.setLinesVisible(true);

        buttonUpdate = new Button(composite, SWT.CHECK);
        buttonUpdate.setAlignment(SWT.CENTER);
        buttonUpdate.setFont(font12);
        buttonUpdate.setBounds(256, 44, 166, 35);
        buttonUpdate.setText("Update");

        buttonDelete = new Button(composite, SWT.CHECK);
        buttonDelete.setText("Delete");
        buttonDelete.setFont(font12);
        buttonDelete.setAlignment(SWT.CENTER);
        buttonDelete.setBounds(256, 85, 166, 35);

        buttonCreate = new Button(composite, SWT.CHECK);
        buttonCreate.setText("Create");
        buttonCreate.setFont(font12);
        buttonCreate.setAlignment(SWT.CENTER);
        buttonCreate.setBounds(256, 126, 166, 35);

        Label lblLaws = new Label(composite, SWT.RIGHT);
        lblLaws.setAlignment(SWT.CENTER);
        lblLaws.setText("Laws");
        lblLaws.setFont(font12);
        lblLaws.setBounds(256, 10, 166, 28);

        buttonSave = new Button(composite, SWT.NONE);
        buttonSave.setText("Save");
        buttonSave.setFont(font12);
        buttonSave.setBounds(256, 167, 166, 40);

    }

    void fillTable(String name) {
        TableItem tableItem = new TableItem(tableOfAccounts, SWT.NONE);
        tableItem.setText(0, name);
    }

    void initListeners() {
        tableOfAccounts.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                int index = tableOfAccounts.getSelectionIndex();
                if (index != -1) {
                    clearCurrentLaws();
                    setCurrentLaws(accountMap.get(tableOfAccounts.getItem(index).getText()));
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });
        buttonSave.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                int index = tableOfAccounts.getSelectionIndex();
                if (index != -1) {
                    accountMap.put(tableOfAccounts.getItem(index).getText(), getSelectedLaws());
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });
    }

    List<Laws> getSelectedLaws() {
        List<Laws> lawsList = new ArrayList<>();
        if (buttonCreate.getSelection()) {
            lawsList.add(Laws.CREATE);
        }
        if (buttonDelete.getSelection()) {
            lawsList.add(Laws.DELETE);
        }
        if (buttonUpdate.getSelection()) {
            lawsList.add(Laws.UPDATE);
        }
        return lawsList;
    }

    void setCurrentLaws(List<Laws> laws) {
        if (laws.contains(Laws.UPDATE)) {
            buttonUpdate.setSelection(true);
        }
        if (laws.contains(Laws.DELETE)) {
            buttonDelete.setSelection(true);
        }
        if (laws.contains(Laws.CREATE)) {
            buttonCreate.setSelection(true);
        }
    }

    void clearCurrentLaws() {
        buttonUpdate.setSelection(false);
        buttonDelete.setSelection(false);
        buttonCreate.setSelection(false);
    }
}
