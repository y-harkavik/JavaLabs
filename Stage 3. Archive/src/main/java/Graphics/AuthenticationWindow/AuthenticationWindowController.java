package Graphics.AuthenticationWindow;

import Communicate.Message.Response.ServerResponse.AuthenticationResponse;
import Client.Client;
import org.eclipse.swt.SWT;

import java.io.IOException;

import static Graphics.Constants.GraphicsDialogs.showDialog;

public class AuthenticationWindowController {
    AuthenticationWindowModel authenticationWindowModel;
    AuthenticationWindow authenticationWindow;
    AuthenticationResponse authenticationResponse;
    boolean authenticationSuccessful = false;

    public AuthenticationWindowController() {
        authenticationWindowModel = new AuthenticationWindowModel(this);
        authenticationWindow = new AuthenticationWindow();
        initListeners();
    }

    public void connectToServer() {
        try {
            authenticationWindowModel.setupConnection();
            authenticationWindowModel.startListenSocket();
        } catch (IOException e) {
            showDialog("Connection error", SWT.ICON_ERROR);
        }
    }

    public AuthenticationResponse openAuthenticationWindow() {
        authenticationWindow.shell.open();
        authenticationWindow.shell.layout();

        connectToServer();

        while (true) {
            if (!authenticationWindow.display.readAndDispatch()) {
                authenticationWindow.display.sleep();
            }
            if (authenticationSuccessful) {
                authenticationWindow.shell.close();
                break;
            }
            if (authenticationWindow.shell.isDisposed()) {
                try {
                    authenticationWindowModel.closeConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return authenticationResponse;
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

    public Client getCurrentClient() {
        return authenticationWindowModel.currentClient;
    }
}
