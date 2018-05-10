package graphics.AuthenticationWindow;

import client.Client;
import Communicate.Message.Request.ClientRequest.AuthenticationRequest;
import Communicate.Message.Response.ServerResponse.AuthenticationResponse;
import Communicate.Message.Response.ServerResponse.ResponseType;
import org.eclipse.swt.SWT;
import server.ArchiveServer;

import java.io.IOException;
import java.net.Socket;

public class AuthenticationWindowModel {
    Client currentClient;
    AuthenticationWindowController authenticationWindowController;

    public AuthenticationWindowModel(AuthenticationWindowController authenticationWindowController) {
        this.authenticationWindowController = authenticationWindowController;
    }

    public class AuthenticationResponseHandler implements Runnable {
        AuthenticationResponse authenticationResponse;

        @Override
        public void run() {
            try {
                while ((authenticationResponse = (AuthenticationResponse) currentClient.getInputStream().readObject()) != null) {
                    if (authenticationResponse.getResponseType() == ResponseType.ERROR) {
                        AuthenticationWindowController.showDialog(authenticationResponse.getMessage(), SWT.ICON_ERROR);
                    } else {
                        /*Thread thread = new Thread(() -> {
                            new ArchiveWindowController(currentClient,
                                    authenticationWindowController.authenticationWindow.display,
                                    authenticationWindowController.authenticationWindow.shell).openMainWindow();
                        });
                        thread.start();*/
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setupConnection() throws IOException {
        Socket clientSocket = new Socket(ArchiveServer.SERVER_IP, ArchiveServer.SERVER_PORT);

        currentClient = new Client(clientSocket);
    }

    public void startListenSocket() {
        Thread thread = new Thread(new AuthenticationResponseHandler());
        thread.start();
    }

    void sendMessage(String login, String password) {
        try {
            currentClient.getOutputStream().writeObject(new AuthenticationRequest(login, password));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
