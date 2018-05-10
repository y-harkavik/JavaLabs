package Graphics.ArchiveWindow;

import client.Client;
import Communicate.Message.Request.Request;
import Law.Laws;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import Users.Account;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ArchiveWindowController {
    ArchiveWindow archiveWindow;
    ArchiveWindowModel archiveWindowModel;
    boolean flagSetupLaws;


    public ArchiveWindowController(Client currentClient,
                                   Display display,
                                   Shell shell,
                                   Map<String, Integer> mapOfPersonnelFiles,
                                   Map<String, Account> mapOfUsers) {
        archiveWindow = new ArchiveWindow(display, shell);
        archiveWindowModel = new ArchiveWindowModel(this, mapOfPersonnelFiles, mapOfUsers, currentClient);
    }

    public void openMainWindow() {
        archiveWindow.shell.open();
        archiveWindow.shell.layout();
        archiveWindowModel.startListenSocket();

        while (!archiveWindow.shell.isDisposed()) {
            if (!archiveWindow.display.readAndDispatch()) {
                archiveWindow.display.sleep();
            }
        }
    }

    /*class IncomingReader implements Runnable {
        Response serverResponse;

        @Override
        public void run() {
            try {
                while ((serverResponse = (Response) currentClient.getInputStream().readObject()) != null) {
                    if (serverResponse instanceof ResponseForUser) {

                    }
                    if (serverResponse instanceof ResponseForAdministrator) {

                    }
                    if (serverResponse instanceof AuthenticationResponse) {
                        checkAuthenticationResponse((AuthenticationResponse) serverResponse);

                        continue;
                    }
                }
            } catch (Exception e) {

            }
        }

        void handleResponseForUser() {

        }

        void checkAuthenticationResponse(AuthenticationResponse authenticationResponse) {
            if (authenticationResponse.getResponseType() == ResponseType.GOOD) {
                Thread thread = new Thread(() -> {
                    openMainWindow(authenticationResponse.getUserLaws());
                });
            } else {
                showDialog(authenticationResponse.getMessage());
            }
        }
    }*/

    public void setConstraints(List<Laws> laws) {
        flagSetupLaws = true;

        if (!laws.contains(Laws.ADMINISTRATOR)) {
            archiveWindow.changeLawsItem.setEnabled(false);
            return;
        }
        if (!laws.contains(Laws.READ)) {

        }
        if (!laws.contains(Laws.CREATE)) {
            archiveWindow.addPersonnelFileItem.setEnabled(false);
        }
        if (!laws.contains(Laws.DELETE)) {
            archiveWindow.buttonDeletePersonnelFile.setEnabled(false);
        }
        if (!laws.contains(Laws.UPDATE)) {
            archiveWindow.buttonEditPerson.setEnabled(false);
            archiveWindow.buttonSavePerson.setEnabled(false);
        }
    }

    void openMainWindow(List<Laws> lawsList) {
        setConstraints(lawsList);
        archiveWindow.shell.open();
        archiveWindow.shell.layout();

        while (!archiveWindow.shell.isDisposed()) {
            if (!archiveWindow.display.readAndDispatch()) {
                archiveWindow.display.sleep();
            }
        }
    }

    void sendMessage(Client client, Request clientRequest) {
        try {
            client.getOutputStream().writeObject(clientRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showDialog(String message) {

    }

    void initListeners() {

    }

    public static void main(String[] args) {
    }
}
