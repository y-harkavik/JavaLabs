package Graphics.AuthenticationWindow;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import java.io.IOException;

public class AuthenticationWindowController {
    AuthenticationWindowModel authenticationWindowModel;
    AuthenticationWindow authenticationWindow;
    boolean authenticationSuccessful = false;

    public AuthenticationWindowController() {
        authenticationWindowModel = new AuthenticationWindowModel(this);
        authenticationWindow = new AuthenticationWindow();
        initListeners();
    }

    public void openAuthenticationWindow() {
        authenticationWindow.shell.open();
        authenticationWindow.shell.layout();

        try {
            authenticationWindowModel.setupConnection();
            authenticationWindowModel.startListenSocket();
        } catch (IOException e) {
            showDialog("Connection error", SWT.ICON_ERROR);
        }

        while (!authenticationSuccessful) {
            if (!authenticationWindow.display.readAndDispatch()) {
                authenticationWindow.display.sleep();
            }
        }
    }

    void initListeners() {
        authenticationWindow.buttonSignIn.addListener(SWT.Selection, (event) -> {
            String login = authenticationWindow.textPassword.getText();
            String password = authenticationWindow.textLogin.getText();
            if (!login.isEmpty() && !password.isEmpty()) {
                authenticationWindowModel.sendMessage(login, password);
            }
        });
    }

    public static void showDialog(String text, int style) {
        MessageBox messageBox = new MessageBox(new Shell(), style);
        messageBox.setMessage(text);
        messageBox.open();
    }
}
