package GUI;

import objects.Ship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PortSystemGUIController {
    private static PortSystemGUI mainWindow;
    private PortSystemModel systemModel;

    private void initializeListeners() {
        mainWindow.addShipItem.addActionListener((event) -> {
            List<Ship> ships = new ArrayList<Ship>(Arrays.asList(new Ship("1", "a", 100), new Ship("2", "b", 100), new Ship("3", "c", 100)));
            mainWindow.portTableModel.addAll(ships);
        });

        mainWindow.addPortItem.addActionListener(e -> {
            addPort("Port1",3);
            addPort("Port2",2);
            addPort("Port3",1);
        });

        mainWindow.portTree.addTreeSelectionListener((event) -> {
            try {
                String child = event.getNewLeadSelectionPath().getLastPathComponent().toString();
                String parent = event.getNewLeadSelectionPath().getParentPath().getLastPathComponent().toString();


            } catch (NullPointerException ex) {

            }
        });
    }

    private int checkPort(String portName {
        switch (portName) {
            case "Ports":
                return -1;
            case "Port 1":
                return 1;
            case "Port 2":
                System.out.println(child);
                break;
            case "Port 3":
                System.out.println(child);
        }
    }

    private int checkPier(String pierName) {
        return 0;
    }

    void addPort(String name,int numOfPiers) {
        systemModel.addPortInList(name,numOfPiers);
        addPortInTree();
    }

    void addPortInTree() {

    }

    public void start() {
        mainWindow = new PortSystemGUI();
        systemModel = new PortSystemModel();

        initializeListeners();
        mainWindow.setVisible(true);
    }
}
