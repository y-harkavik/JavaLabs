package Graphics.AuthenticationWindow;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import java.io.IOException;

public class AuthenticationWindowController {
    AuthenticationWindowModel authenticationWindowModel;
    AuthenticationWindow authenticationWindow;
    boolean authenticationSuccessfull = false;

    public AuthenticationWindowController() {
        authenticationWindowModel = new AuthenticationWindowModel(this);
        authenticationWindow = new AuthenticationWindow();
    }

    public void openAuthenticationWindow() {
        authenticationWindow.shell.open();
        authenticationWindow.shell.layout();

        try {
            authenticationWindowModel.setupConnection();
            authenticationWindowModel.startListenSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!authenticationSuccessfull) {
            if (!authenticationWindow.display.readAndDispatch()) {
                authenticationWindow.display.sleep();
            }
        }
    }

    void initListeners() {
        authenticationWindow.buttonSignIn.addListener(SWT.Selection, (event) -> {
            //authenticationWindowModel.
        });
    }

    public static void showDialog(String text, int style) {
        MessageBox messageBox = new MessageBox(new Shell(), style);
        messageBox.setMessage(text);
        messageBox.open();
    }
}
