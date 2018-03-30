package GUI;

import objects.Ship;

import javax.swing.tree.DefaultMutableTreeNode;
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
            addPort("Port1", 3);
            addPort("Port2", 2);
            addPort("Port3", 1);
        });

        mainWindow.portTree.addTreeSelectionListener((event) -> {
            try {
                String child = event.getNewLeadSelectionPath().getLastPathComponent().toString();
                String parent = event.getNewLeadSelectionPath().getParentPath().getLastPathComponent().toString();

                setShipsInTable(parent, child);

            } catch (NullPointerException ex) {

            }
        });
    }

    private void setShipsInTable(String port, String pier) {
        int portIndex = systemModel.checkPort(port);

        if (portIndex < 0) {
            portIndex = systemModel.checkPort(pier);
            if (portIndex > 0) {
                
            }
        }

    }

    void addPort(String name, int numOfPiers) {
        systemModel.addPortInList(name, numOfPiers);
        addPortInTree(name, numOfPiers);
    }

    void addPortInTree(String name, int numOfPiers) {
        DefaultMutableTreeNode port = new DefaultMutableTreeNode(name);
        mainWindow.rootNode.add(port);

        for (int i = 0; i < numOfPiers; i++) {
            DefaultMutableTreeNode pier = new DefaultMutableTreeNode("Pier " + String.valueOf(i + 1));
            port.add(pier);
        }
    }

    public void start() {
        mainWindow = new PortSystemGUI();
        systemModel = new PortSystemModel();

        initializeListeners();
        mainWindow.setVisible(true);
    }
}
