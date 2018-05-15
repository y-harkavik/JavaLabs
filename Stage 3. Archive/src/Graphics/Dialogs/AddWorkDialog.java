package Graphics.Dialogs;

import Users.Work;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.*;

import static Graphics.Constants.GraphicsConstants.SEGOE_UI_SEMILIGHT;
import static Graphics.Constants.GraphicsConstants.verifyListenerForNumber;
import static Graphics.Constants.GraphicsConstants.verifyListenerForText;

public class AddWorkDialog extends Dialog {
    protected Shell shell;
    private Text textCompany;
    private Text textPosition;
    private Text textExperience;
    private Work work = null;
    private Button buttonAddWork;

    public AddWorkDialog(Shell shell) {
        super(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CLOSE | SWT.TITLE);
    }

    public Work open() {
        shell = new Shell(getParent(), getStyle());
        Display display = getParent().getDisplay();
        createContents(shell, display);
        initListeners();
        shell.open();
        shell.layout();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
        return work;
    }

    protected void createContents(Shell shell, Display display) {
        Font font12 = new Font(display, SEGOE_UI_SEMILIGHT, 12, SWT.NORMAL);
        Font font10 = new Font(display, SEGOE_UI_SEMILIGHT, 10, SWT.NORMAL);

        shell.setSize(450, 300);
        shell.setText("Adding work");

        Composite composite = new Composite(shell, SWT.NONE);
        composite.setBounds(0, 0, 432, 253);

        Label lblCompany = new Label(composite, SWT.RIGHT);
        lblCompany.setText("Company");
        lblCompany.setFont(font12);
        lblCompany.setBounds(10, 10, 110, 28);

        textCompany = new Text(composite, SWT.BORDER);
        textCompany.setFont(font10);
        textCompany.setBounds(126, 10, 296, 28);

        Label lblPosition = new Label(composite, SWT.RIGHT);
        lblPosition.setText("Position");
        lblPosition.setFont(font12);
        lblPosition.setBounds(10, 47, 110, 28);

        Label lblExperience = new Label(composite, SWT.RIGHT);
        lblExperience.setText("Experience");
        lblExperience.setFont(font12);
        lblExperience.setBounds(10, 81, 110, 28);

        textPosition = new Text(composite, SWT.BORDER);
        textPosition.setFont(font10);
        textPosition.setBounds(126, 44, 296, 28);

        textExperience = new Text(composite, SWT.BORDER);
        textExperience.setFont(font10);
        textExperience.setBounds(126, 81, 296, 28);
        textExperience.setTextLimit(3);

        buttonAddWork = new Button(composite, SWT.NONE);
        buttonAddWork.setText("Add");
        buttonAddWork.setFont(font12);
        buttonAddWork.setBounds(167, 203, 100, 40);
    }

    void initListeners() {
        buttonAddWork.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                work = createWork();
                shell.dispose();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });
        textCompany.addVerifyListener(verifyListenerForText);
        textPosition.addVerifyListener(verifyListenerForText);
        textExperience.addVerifyListener(verifyListenerForNumber);
    }

    Work createWork() {
        Work work = new Work(
                textCompany.getText(),
                textPosition.getText(),
                Integer.valueOf(textExperience.getText()));
        return work;
    }
}
