package Graphics.AuthenticationWindow;

import Graphics.Constants.GraphicsDialogs;
import Client.Client;
import Communicate.Message.Request.ClientRequest.AuthenticationRequest;
import Communicate.Message.Response.ServerResponse.AuthenticationResponse;
import Communicate.Message.Response.ServerResponse.ResponseType;
import org.eclipse.swt.SWT;
import Server.ArchiveServer;
import org.eclipse.swt.widgets.Display;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class AuthenticationWindowModel {
    Client currentClient;
    AuthenticationWindowController authenticationWindowController;

    public AuthenticationWindowModel(AuthenticationWindowController authenticationWindowController) {
        this.authenticationWindowController = authenticationWindowController;
    }

    public void closeConnection() throws IOException {
        if (currentClient == null) {
            return;
        }
        currentClient.getInputStream().close();
        currentClient.getOutputStream().close();
        currentClient.getClientSocket().close();
    }

    public class AuthenticationResponseHandler implements Runnable {
        AuthenticationResponse authenticationResponse;

        @Override
        public void run() {
            try {
                while ((authenticationResponse = (AuthenticationResponse) currentClient.getInputStream().readObject()) != null) {
                    if (authenticationResponse.getResponseType() == ResponseType.ERROR) {
                        Display.getDefault().asyncExec(() ->
                                GraphicsDialogs.showDialog(authenticationResponse.getMessage(), SWT.ICON_ERROR));
                    } else {
                        authenticationWindowController.authenticationSuccessful = true;
                        authenticationWindowController.authenticationResponse = authenticationResponse;
                        break;
                    }
                }
            } catch (SocketException ex) {
                Display.getDefault().asyncExec(() -> GraphicsDialogs.showDialog("Connect error", SWT.ICON_ERROR));
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
