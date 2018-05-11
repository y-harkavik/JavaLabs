package Graphics.ArchiveWindow;

import Communicate.Message.Response.Response;
import Communicate.Message.Response.ServerResponse.AuthenticationResponse;
import Communicate.Message.Response.ServerResponse.ResponseForAdministrator;
import Communicate.Message.Response.ServerResponse.ResponseForUser;
import Communicate.Message.Response.ServerResponse.ResponseType;
import client.Client;
import Users.Account;

import java.util.Map;

public class ArchiveWindowModel {
    Map<String, Account> mapOfUsers;
    Map<String, Integer> mapOfPersonnelFiles;
    ArchiveWindowController archiveWindowController;
    Client currentClient;

    public ArchiveWindowModel(ArchiveWindowController archiveWindowController,
                              Map<String, Integer> mapOfPersonnelFiles,
                              Map<String, Account> mapOfUsers,
                              Client currentClient) {
        this.mapOfPersonnelFiles = mapOfPersonnelFiles;
        this.archiveWindowController = archiveWindowController;
        this.mapOfUsers = mapOfUsers;
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
                    if (serverResponse instanceof ResponseForUser) {

                    }
                    if (serverResponse instanceof ResponseForAdministrator) {

                    }
                }
            } catch (Exception e) {

            }
        }

        void handleResponseForUser() {

        }
    }
}
