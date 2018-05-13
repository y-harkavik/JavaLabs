package Graphics.ArchiveWindow;

import Communicate.Message.Request.Request;
import Communicate.Message.Response.Response;
import Communicate.Message.Response.ServerResponse.ResponseForAdministrator;
import client.Client;
import org.eclipse.swt.widgets.Display;

import java.io.IOException;

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
                        archiveWindowController.archiveWindow.display.asyncExec(() -> {
                            archiveWindowController.makeFieldsEnable(false);

                            int index = archiveWindowController.archiveWindow.tableOfPersonnelFiles.getSelectionIndex();
                            archiveWindowController.setPersonnelFilesInTable(serverResponse.getMapOfPersonnelFiles());
                            archiveWindowController.archiveWindow.tableOfPersonnelFiles.select(index);

                            if (serverResponse.getPersonnelFileOfSpecificMen() != null) {
                                archiveWindowController.setPersonnelFileInformation(serverResponse.getPersonnelFileOfSpecificMen());
                            }
                        });
                    }
                }
            } catch (java.net.SocketException e) {
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    void sendMessage(Request clientRequest) {
        try {
            currentClient.getOutputStream().writeObject(clientRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void closeConnection() {
        try {
            currentClient.getInputStream().close();
            currentClient.getOutputStream().close();
            currentClient.getClientSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
