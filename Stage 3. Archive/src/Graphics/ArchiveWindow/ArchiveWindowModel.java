package Graphics.ArchiveWindow;

import Communicate.Message.Request.Request;
import Communicate.Message.Response.Response;
import Communicate.Message.Response.ServerResponse.ResponseType;
import Graphics.Constants.GraphicsDialogs;
import client.Client;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import java.io.IOException;
import java.util.Set;

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
                    archiveWindowController.archiveWindow.display.asyncExec(() -> {
                        archiveWindowController.makeFieldsEnable(false);

                        archiveWindowController.setPersonnelFilesInTable(serverResponse.getMapOfPersonnelFiles());

                        if (serverResponse.getPersonnelFileOfSpecificMen() != null) {
                            archiveWindowController.setPersonnelFileInformation(serverResponse.getPersonnelFileOfSpecificMen());
                            archiveWindowController.archiveWindow.tableOfPersonnelFiles.select(indexOf(serverResponse.getMapOfPersonnelFiles().keySet(), serverResponse.getPersonnelFileOfSpecificMen().getBasicInformation().getPassport()));
                        }
                        if (serverResponse.getResponseType() == ResponseType.ERROR) {
                            GraphicsDialogs.showDialog(serverResponse.getMessage(), SWT.ICON_ERROR);
                        }
                    });
                }
            } catch (java.net.SocketException e) {
                Display.getDefault().asyncExec(() -> {
                    GraphicsDialogs.showDialog("Connect error. Maybe the server is unavailable.", SWT.ICON_ERROR);
                });
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

    int indexOf(Set<String> setOfPassportID, String passportID) {
        int index = 0;
        for (String ID : setOfPassportID) {
            if (ID.equals(passportID)) {
                break;
            }
            index++;
        }
        return index;
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
