package GUI.MainWindow;

import GUI.Dialogs.AddPortDialog;
import GUI.Dialogs.AddShipDialog;
import objects.Buildings.Port.PortYard;
import objects.Product.Cargo.Cargo;
import objects.Product.Characteristics.Measure;
import objects.Product.Characteristics.Operation;
import objects.Product.Characteristics.TypeOfProduct;
import objects.Transport.Marine.Ship;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PortSystemGUIController {
    private static PortSystemGUI mainWindow;
    private PortSystemModel systemModel;
    public static int portCount;
    private String child;
    private String parent;
    int a;
    private AddShipDialog addShipDialog;
    private AddPortDialog addPortDialog;

    private void initializeListeners() {
        mainWindow.addShipItem.addActionListener(event -> {
            List<Ship> ships = new ArrayList<Ship>(Arrays.asList(
                    new Ship(
                            String.valueOf(a++),
                            Arrays.asList
                                    (
                                            new Cargo(TypeOfProduct.CLOTH, Measure.METR, 500, Operation.LOADNIG),
                                            new Cargo(TypeOfProduct.COAL, Measure.KG, 500, Operation.UNLOADING),
                                            new Cargo(TypeOfProduct.GOLD, Measure.KG, 500, Operation.UNLOADING),
                                            new Cargo(TypeOfProduct.SLAVES, Measure.PEOPLE, 500, Operation.LOADNIG),
                                            new Cargo(TypeOfProduct.WEED, Measure.KG, 500, Operation.LOADNIG)
                                    ),
                            systemModel.getMapOfShipPorts()),
                    new Ship(
                            String.valueOf(a++),
                            Arrays.asList
                                    (
                                            new Cargo(TypeOfProduct.CLOTH, Measure.METR, 500, Operation.UNLOADING),
                                            new Cargo(TypeOfProduct.COAL, Measure.KG, 500, Operation.LOADNIG),
                                            new Cargo(TypeOfProduct.GOLD, Measure.KG, 500, Operation.LOADNIG),
                                            new Cargo(TypeOfProduct.SLAVES, Measure.PEOPLE, 500, Operation.UNLOADING),
                                            new Cargo(TypeOfProduct.WEED, Measure.KG, 500, Operation.UNLOADING)
                                    ),
                            systemModel.getMapOfShipPorts()),
                    new Ship(
                            String.valueOf(a++),
                            Arrays.asList
                                    (
                                            new Cargo(TypeOfProduct.CLOTH, Measure.METR, 500, Operation.UNLOADING),
                                            new Cargo(TypeOfProduct.COAL, Measure.KG, 500, Operation.UNLOADING),
                                            new Cargo(TypeOfProduct.GOLD, Measure.KG, 500, Operation.LOADNIG),
                                            new Cargo(TypeOfProduct.SLAVES, Measure.PEOPLE, 500, Operation.UNLOADING),
                                            new Cargo(TypeOfProduct.WEED, Measure.KG, 500, Operation.LOADNIG)
                                    ),
                            systemModel.getMapOfShipPorts())));
            for (Ship ship : ships) {
                ship.addObserver(mainWindow.portTableModel);
                new Thread(ship).start();
            }
            //addShipDialog.show();
        });
        mainWindow.addPortItem.addActionListener(e -> {
           // addPort("Port" + String.valueOf(portCount++ + 1), 3);
            addPortDialog.show();
        });
        mainWindow.portTree.addTreeSelectionListener(event -> {
            try {
                child = event.getNewLeadSelectionPath().getLastPathComponent().toString();
                parent = event.getNewLeadSelectionPath().getParentPath().getLastPathComponent().toString();

                repaintTables();
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
    }

    public PortSystemGUI getMainWindow() {
        return mainWindow;
    }

    public void repaintTables() {
        repaintShipTable();
        repaintQueueTable();
    }

    public void repaintShipTable() {
        boolean containsPort = systemModel.checkPort(this.parent);

        if (!containsPort) {
            containsPort = systemModel.checkPort(this.child);
            if (containsPort) {
                updatePortTable(systemModel.getListOfShipsInPort(this.child));
                return;
            } else {
                updatePortTable(systemModel.getAllShipsList());
                return;
            }
        }
        mainWindow.portTableModel.clearTable();
        mainWindow.portTableModel.addShip(systemModel.getShipInPier(this.parent, this.child));
    }

    public void repaintQueueTable() {
        if (systemModel.checkPort(this.parent)) {
            updateQueueTable(systemModel.getMapOfShipPorts().get(this.parent).getListOfShipsInQueue());
            return;
        } else if (systemModel.checkPort(this.child)) {
            updateQueueTable(systemModel.getMapOfShipPorts().get(this.child).getListOfShipsInQueue());
            return;
        }
        updateQueueTable(systemModel.getAllShipsInQueue());
    }

    public void updateQueueTable(List<Ship> shipList) {
        mainWindow.queueTableModel.clearTable();
        mainWindow.queueTableModel.addAll(shipList);
    }

    public void updatePortTable(List<Ship> shipList) {
        mainWindow.portTableModel.clearTable();
        mainWindow.portTableModel.addAll(shipList);
    }

    public void addPort(String name, int numOfPiers, PortYard portYard) {
        systemModel.addPortInList(name, numOfPiers, portYard,this);
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
        addShipDialog = new AddShipDialog(this,systemModel.getMapOfShipPorts(),mainWindow.portTableModel);
        addPortDialog = new AddPortDialog(this);
        initializeListeners();
        mainWindow.setVisible(true);
    }
}
