package graphics.ArchiveWindow;

import client.Client;

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
        this.currentClient= currentClient;
    }

    public void startListenSocket() {
        Thread thread = new Thread();
        thread.start();
    }
}
