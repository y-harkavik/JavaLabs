package Graphics.Dialogs;

import Users.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.*;

import static Graphics.Constants.GraphicsConstants.SEGOE_UI_SEMILIGHT;
import static Graphics.Constants.GraphicsConstants.verifyListenerForNumber;
import static Graphics.Constants.GraphicsConstants.verifyListenerForText;

public class AddJobDialog extends Dialog {
    protected Shell shell;
    private Text textCompany;
    private Text textPosition;
    private Text textExperience;
    private Job job = null;
    private Button buttonAddJob;

    public AddJobDialog(Shell shell) {
        super(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CLOSE | SWT.TITLE);
    }

    public Job open() {
        shell = new Shell(getParent(), getStyle());
        Display display = getParent().getDisplay();
        createContents(shell, display);
        initListeners();
        shell.open();
        shell.layout();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
        return job;
    }

    protected void createContents(Shell shell, Display display) {
        Font font12 = new Font(display, SEGOE_UI_SEMILIGHT, 12, SWT.NORMAL);
        Font font10 = new Font(display, SEGOE_UI_SEMILIGHT, 10, SWT.NORMAL);

        shell.setSize(450, 300);
        shell.setText("Adding job");

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

        buttonAddJob = new Button(composite, SWT.NONE);
        buttonAddJob.setText("Add");
        buttonAddJob.setFont(font12);
        buttonAddJob.setBounds(167, 203, 100, 40);
    }

    void initListeners() {
        buttonAddJob.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                job = createJob();
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

    Job createJob() {
        Job job = new Job(
                textCompany.getText(),
                textPosition.getText(),
                textExperience.getText());
        return job;
    }
}
