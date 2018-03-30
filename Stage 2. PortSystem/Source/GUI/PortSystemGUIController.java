package GUI;

import objects.Ship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PortSystemGUIController {
    private static PortSystemGUI mainWindow;
    private PortSystemModel systemModel;

    private void initializeListeners() {
        mainWindow.addShip.addActionListener((event)->{
            List<Ship> ships = new ArrayList<Ship>(Arrays.asList(new Ship("1","a",100),new Ship("2","b",100),new Ship("3","c",100)));
            mainWindow.portTableModel.addAll(ships);
        });

        mainWindow.portTree.addTreeSelectionListener((event) -> {
            try {
                String child = event.getNewLeadSelectionPath().getLastPathComponent().toString();
                String parent = event.getNewLeadSelectionPath().getParentPath().getLastPathComponent().toString();

                switch (parent) {
                    case "Ports":

                    case "Port 1":
                        System.out.println(child);
                        break;
                    case "Port 2":
                        System.out.println(child);
                        break;
                    case "Port 3":
                        System.out.println(child);
                }
            } catch (NullPointerException ex) {

            }
        });
    }

    public void start() {
            mainWindow = new PortSystemGUI();
            systemModel = new PortSystemModel();

            initializeListeners();
            mainWindow.setVisible(true);
    }
}
