package Graphics.ArchiveWindow;

import Communicate.Message.Request.Request;
import Communicate.Message.Response.Response;
import Communicate.Message.Response.ServerResponse.AuthenticationResponse;
import Communicate.Message.Response.ServerResponse.ResponseForAdministrator;
import Communicate.Message.Response.ServerResponse.ResponseForUser;
import Communicate.Message.Response.ServerResponse.ResponseType;
import client.Client;
import Users.Account;

import java.io.IOException;
import java.util.Map;

public class ArchiveWindowModel {
    ArchiveWindowController archiveWindowController;
    Client currentClient;

    public ArchiveWindowModel(ArchiveWindowController archiveWindowController,
                              Client currentClient) {
        this.archiveWindowController = archiveWindowController;
        this.currentClient = currentClient;
    }

    public void startListenSocket() {
        Thread thread = new Thread(new ResponseListener());
        thread.start();
    }

    class ResponseListener implements Runnable {
        Response serverResponse;

        @Override
        public void run() {
            try {
                while ((serverResponse = (Response) currentClient.getInputStream().readObject()) != null) {
                    if (serverResponse instanceof ResponseForAdministrator) {
                        //serverResponse = (ResponseForAdministrator) serverResponse;
                        archiveWindowController.makeFieldsNotEditable();
                        archiveWindowController.setPersonnelFilesInTable(serverResponse.getlistOfPersonnelFiles());
                        if (serverResponse.getpersonnelFileOfSpecificMen() != null) {
                            archiveWindowController.setPersonnelFileInformation(serverResponse.getpersonnelFileOfSpecificMen());
                        }
                    }
                }
            } catch (Exception e) {

            }
        }

        void handleResponseForUser() {

        }
    }

    void sendMessage(Request clientRequest) {
        try {
            currentClient.getOutputStream().writeObject(clientRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
