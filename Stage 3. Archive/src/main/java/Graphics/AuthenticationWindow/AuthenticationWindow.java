package Graphics.AuthenticationWindow;

import Graphics.Constants.GraphicsConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.*;

public class AuthenticationWindow {
    Shell shell;
    Text textLogin;
    Text textPassword;
    Font font16;
    Font font12;
    Button buttonSignIn;
    Display display;

    public AuthenticationWindow() {
        this.display = Display.getDefault();
        this.shell = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN);
        createContents();
    }

    void createContents() {
        shell.setSize(450, 300);
        shell.setText("Sign in");

        font12 = new Font(display, GraphicsConstants.SEGOE_UI_SEMILIGHT, 12, SWT.NORMAL);
        font16 = new Font(display, GraphicsConstants.SEGOE_UI_SEMILIGHT, 16, SWT.NORMAL);

        Composite composite = new Composite(shell, SWT.NONE);
        composite.setBounds(0, 0, 432, 253);

        Label labelLogin = new Label(composite, SWT.NONE);
        labelLogin.setAlignment(SWT.RIGHT);
        labelLogin.setFont(font16);
        labelLogin.setBounds(45, 38, 110, 40);
        labelLogin.setText("Login");

        Label labelPassword = new Label(composite, SWT.NONE);
        labelPassword.setText("Password");
        labelPassword.setFont(font16);
        labelPassword.setAlignment(SWT.RIGHT);
        labelPassword.setBounds(45, 84, 110, 40);

        textLogin = new Text(composite, SWT.BORDER);
        textLogin.setFont(font12);
        textLogin.setBounds(161, 38, 200, 40);

        textPassword = new Text(composite, SWT.BORDER | SWT.PASSWORD);
        textPassword.setFont(font12);
        textPassword.setBounds(161, 84, 200, 40);

        buttonSignIn = new Button(composite, SWT.NONE);
        buttonSignIn.setFont(font16);
        buttonSignIn.setBounds(161, 130, 100, 47);
        buttonSignIn.setText("Sign in");
    }
}
