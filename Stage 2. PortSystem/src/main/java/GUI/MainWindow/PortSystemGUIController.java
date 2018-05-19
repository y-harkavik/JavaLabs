package GUI.MainWindow;

import AuxiliaryClasses.StatisticSaver;
import GUI.Dialogs.AddPortDialog;
import GUI.Dialogs.AddShipDialog;
import objects.Buildings.Port.PortYard;
import objects.Product.Cargo.Cargo;
import objects.Product.Characteristics.Measure;
import objects.Product.Characteristics.Operation;
import objects.Product.Characteristics.TypeOfProduct;
import objects.Transport.Marine.Ship;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controller class for PortSystem window. *
 *
 * @author Yauheni
 * @version 1.0
 */
public class PortSystemGUIController {
    private static PortSystemGUI mainWindow;
    private PortSystemModel systemModel;
    public static int portCount;
    private String child;
    private String parent;
    private AddShipDialog addShipDialog;
    private AddPortDialog addPortDialog;
    private StatisticSaver statisticSaver;
    private Thread statisticSaverThread;

    /**
     * Initializes listeners for GUI components.
     */
    private void initializeListeners() {
        mainWindow.addShipItem.addActionListener(event -> {
            /*List<Ship> ships = new ArrayList<Ship>(Arrays.asList(
                    new Ship(
                            String.valueOf(a++),
                            Arrays.asList
                                    (
                                            new Cargo(TypeOfProduct.CLOTH, Measure.METR, 500, Operation.LOADING),
                                            new Cargo(TypeOfProduct.COAL, Measure.KG, 500, Operation.UNLOADING),
                                            new Cargo(TypeOfProduct.GOLD, Measure.KG, 500, Operation.UNLOADING),
                                            new Cargo(TypeOfProduct.SLAVES, Measure.PEOPLE, 500, Operation.LOADING),
                                            new Cargo(TypeOfProduct.WEED, Measure.KG, 500, Operation.LOADING)
                                    ),
                            systemModel.getMapOfShipPorts()),
                    new Ship(
                            String.valueOf(a++),
                            Arrays.asList
                                    (
                                            new Cargo(TypeOfProduct.CLOTH, Measure.METR, 500, Operation.UNLOADING),
                                            new Cargo(TypeOfProduct.COAL, Measure.KG, 500, Operation.LOADING),
                                            new Cargo(TypeOfProduct.GOLD, Measure.KG, 500, Operation.LOADING),
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
                                            new Cargo(TypeOfProduct.GOLD, Measure.KG, 500, Operation.LOADING),
                                            new Cargo(TypeOfProduct.SLAVES, Measure.PEOPLE, 500, Operation.UNLOADING),
                                            new Cargo(TypeOfProduct.WEED, Measure.KG, 500, Operation.LOADING)
                                    ),
                            systemModel.getMapOfShipPorts())));
            for (Ship ship : ships) {
                ship.addObserver(mainWindow.portTableModel);
                new Thread(ship).start();
            }*/
            addShipDialog.show();
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
        mainWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        mainWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                statisticSaverThread.interrupt();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } finally {
                    System.exit(0);
                }
            }
        });
        statisticSaverThread.start();
    }

    /**
     * Return current PortSystemGUI
     *
     * @return PortSystemGUI
     * @see PortSystemGUI
     */
    public PortSystemGUI getMainWindow() {
        return mainWindow;
    }

    /**
     * Repaint queue ship and current ship tables.
     *
     * @see #repaintShipTable()
     * @see #repaintQueueTable()
     */
    public void repaintTables() {
        repaintShipTable();
        repaintQueueTable();
    }

    /**
     * Repaint ship table.
     *
     * @see #updatePortTable(List)
     */
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

    /**
     * Repaint queue ship table.
     *
     * @see #updateQueueTable(List) (List) ()
     */
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

    /**
     * Caused by repaintQueueTable(), update ship queue table.
     *
     * @param shipList List of ships in queue.
     * @see Ship
     * @see #repaintQueueTable()
     */
    public void updateQueueTable(List<Ship> shipList) {
        mainWindow.queueTableModel.clearTable();
        mainWindow.queueTableModel.addAll(shipList);
    }

    /**
     * Caused by repaintShipTable(), update ship table.
     *
     * @param shipList List of ships in processing.
     * @see #repaintShipTable()
     */
    public void updatePortTable(List<Ship> shipList) {
        mainWindow.portTableModel.clearTable();
        mainWindow.portTableModel.addAll(shipList);
    }

    /**
     * Add port on window and add port in list of ports by causing addPortInList(String, int, PortYard, PortSystemGUIController).
     *
     * @param name       Name of port.
     * @param numOfPiers Count of piers.
     * @param portYard   Port yard.
     * @see PortSystemModel#addPortInList(String, int, PortYard, PortSystemGUIController)
     * @see #addPortInTree(String, int)
     */
    public void addPort(String name, int numOfPiers, PortYard portYard) {
        systemModel.addPortInList(name, numOfPiers, portYard, this);
        addPortInTree(name, numOfPiers);
        ((DefaultTreeModel) mainWindow.portTree.getModel()).reload();
    }

    /**
     * Add port in tree.
     *
     * @param name       Name of port.
     * @param numOfPiers Count of piers.
     */
    void addPortInTree(String name, int numOfPiers) {
        DefaultMutableTreeNode port = new DefaultMutableTreeNode(name);
        mainWindow.rootNode.add(port);

        for (int i = 0; i < numOfPiers; i++) {
            DefaultMutableTreeNode pier = new DefaultMutableTreeNode("Pier " + String.valueOf(i + 1));
            port.add(pier);
        }
    }

    /**
     * Initializes GUI components, start statisticSaver and causes initializeListeners().
     *
     * @see #initializeListeners()
     * @see StatisticSaver
     */
    public void start() {
        mainWindow = new PortSystemGUI();
        systemModel = new PortSystemModel();
        addShipDialog = new AddShipDialog(this, systemModel.getMapOfShipPorts(), mainWindow.portTableModel);
        addPortDialog = new AddPortDialog(this);
        statisticSaver = new StatisticSaver(systemModel.getMapOfShipPorts());
        statisticSaverThread = new Thread(statisticSaver);
        initializeListeners();
        mainWindow.setVisible(true);
    }
}
