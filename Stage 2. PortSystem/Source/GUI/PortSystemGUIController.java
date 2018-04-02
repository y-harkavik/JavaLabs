package GUI;

import objects.Ship;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PortSystemGUIController {
    private static PortSystemGUI mainWindow;
    private PortSystemModel systemModel;
    private int portCount;
    private String child;
    private String parent;

    private void initializeListeners() {
        mainWindow.addShipItem.addActionListener((event) -> {
            List<Ship> ships = new ArrayList<Ship>(Arrays.asList(new Ship("1", "a", 100), new Ship("2", "b", 100), new Ship("3", "c", 100)));
            for (Ship ship : ships) {
                ship.addObserver(mainWindow.portTableModel);
            }
            systemModel.getListOfShipPorts().get(portCount - 1).getListOfPiers().get(1).setListOfShips(ships);
            checkedSelectedElement(parent, child);
        });

        mainWindow.addPortItem.addActionListener(e -> {
            addPort("Port" + String.valueOf(portCount++ + 1), 3);
        });

        mainWindow.portTree.addTreeSelectionListener((event) -> {
            try {
                child = event.getNewLeadSelectionPath().getLastPathComponent().toString();
                parent = event.getNewLeadSelectionPath().getParentPath().getLastPathComponent().toString();

                checkedSelectedElement(parent, child);

            } catch (NullPointerException ex) {
                if (child.equals("Ports")) {
                    mainWindow.portTableModel.clearTable();
                    parent = null;
                    mainWindow.portTableModel.addAll(systemModel.getAllShipsList());
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        });

        new Thread(()->{
            while(true) {
                mainWindow.logTextArea.append(systemModel.getShipsLog().toString());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void checkedSelectedElement(String port, String pier) {
        boolean containsPort = systemModel.checkPort(port);

        if (!containsPort) {
            containsPort = systemModel.checkPort(pier);
            if (containsPort) {
                updateTable(systemModel.getListOfShipsInPort(pier));
                return;
            } else {
                updateTable(systemModel.getAllShipsList());
                return;
            }
        }
        updateTable(systemModel.getListOfShipsInPier(port, pier));
    }

    public void updateTable(List<Ship> shipList) {
        mainWindow.portTableModel.clearTable();
        mainWindow.portTableModel.addAll(shipList);
    }

    void addPort(String name, int numOfPiers) {
        systemModel.addPortInList(name, numOfPiers);
        addPortInTree(name, numOfPiers);
        ((DefaultTreeModel) mainWindow.portTree.getModel()).reload();
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
