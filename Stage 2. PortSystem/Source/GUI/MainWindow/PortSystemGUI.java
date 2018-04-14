package GUI.MainWindow;


import GUI.GUIConstants.TableConstants;
import objects.Transport.Marine.Ship;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import java.util.List;

/**
 * Class that serves to display main frame. *
 *
 * @author Yauheni
 * @version 1.0
 */
public class PortSystemGUI extends JFrame {

    JMenu appMenu;
    JMenuBar appMenuBar;
    JMenuItem addShipItem;
    JMenuItem addPortItem;
    JPanel mainJPanel;
    JScrollPane portTreeJScrollPane;
    JScrollPane shipsTableJScrollPane;
    JScrollPane logTextAreaJScrollPane;
    JScrollPane queueTableJScrollPane;
    JTable shipsTable;
    JTable queueTable;
    JTextArea logTextArea;
    JTree portTree;

    DefaultMutableTreeNode rootNode;

    ShipTableModel portTableModel;
    ShipQueueTableModel queueTableModel;

    unloadingShipProgressBar progressBarRenderer;

    /**
     * Shows ship state.
     *
     * @param text Text, that shows in log area.
     */
    synchronized public void printAction(String text) {
        logTextArea.append(text);
    }

    /**
     * Creates PortSystem window by initComponents(), setComponents(), createTree().
     * {@link #initComponents() initComponents()}
     * {@link #setComponents() setComponents()}
     * {@link #createTree() createTree()}
     */
    public PortSystemGUI() {
        initComponents();
        setComponents();
        createTree();
    }

    /**
     * Set components on PortSystem frame.
     */
    private void setComponents() {
        setResizable(false);

        GroupLayout groupLayout = new GroupLayout(mainJPanel);
        mainJPanel.setLayout(groupLayout);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(portTreeJScrollPane, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(shipsTableJScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(queueTableJScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addComponent(logTextAreaJScrollPane, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(shipsTableJScrollPane, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addComponent(queueTableJScrollPane, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                        .addComponent(logTextAreaJScrollPane)
                        .addComponent(portTreeJScrollPane)
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        setJMenuBar(appMenuBar);

        pack();
    }

    /**
     * Initializes components on PortSystem frame.
     */
    private void initComponents() {
        portTableModel = new ShipTableModel();
        queueTableModel = new ShipQueueTableModel();

        progressBarRenderer = new unloadingShipProgressBar(0, 100);

        mainJPanel = new JPanel();

        portTree = new JTree();
        portTreeJScrollPane = new JScrollPane(portTree);

        shipsTable = new JTable(portTableModel);
        shipsTableJScrollPane = new JScrollPane(shipsTable);
        shipsTable.getTableHeader().setReorderingAllowed(false);

        queueTable = new JTable(queueTableModel);
        queueTableJScrollPane = new JScrollPane(queueTable);
        queueTable.getTableHeader().setReorderingAllowed(false);

        logTextArea = new JTextArea();
        logTextAreaJScrollPane = new JScrollPane(logTextArea);

        appMenuBar = new JMenuBar();
        appMenu = new JMenu();
        addShipItem = new JMenuItem();
        addPortItem = new JMenuItem();

        progressBarRenderer.setStringPainted(true);

        shipsTable.setDefaultRenderer(JProgressBar.class, progressBarRenderer);

        shipsTable.setRowHeight((int) progressBarRenderer.getPreferredSize().getHeight());

        portTree.setFont(new Font("Corbel", 0, 13));
        appMenuBar.setFont(new Font("Corbel", 0, 15));

        logTextArea.setEditable(false);

        appMenu.setText("Settings");
        addShipItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_MASK));
        addShipItem.setText("Add ship");
        addPortItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK));
        addPortItem.setText("Add port");
        appMenu.add(addShipItem);
        appMenu.add(addPortItem);
        appMenuBar.add(appMenu);
    }

    /**
     * Creates tree for PortSystem window.
     */
    private void createTree() {
        portTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        rootNode = new DefaultMutableTreeNode("Ports");
        ((DefaultTreeModel) portTree.getModel()).setRoot(rootNode);
    }

    class ShipQueueTableModel extends AbstractTableModel {
        final Vector<Ship> listOfShipsInQueue = new Vector<>();

        public void addAll(List<Ship> shipsList) {
            listOfShipsInQueue.addAll(shipsList);
            fireTableDataChanged();
        }

        public void clearTable() {
            listOfShipsInQueue.removeAllElements();
        }

        @Override
        public int getRowCount() {
            return listOfShipsInQueue.size();
        }

        @Override
        public int getColumnCount() {
            return TableConstants.QUEUE_TABLE_HEADERS.length;
        }

        @Override
        public Class getColumnClass(int c) {
            return TableConstants.QUEUE_TABLE_COLUMN_CLASSES[c];
        }

        @Override
        public String getColumnName(int col) {
            return TableConstants.QUEUE_TABLE_HEADERS[col];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                Ship ship = (Ship) listOfShipsInQueue.elementAt(rowIndex);
                if (columnIndex == 0) return ship.getNameShip();
                else if (columnIndex == 1)
                    return ship.getCurrentCargo().getCargoParameters().getTypeOfProduct().getType()
                            + " "
                            + ship.getCurrentCargo().getCargoParameters().getCargoMeasure().getMeasure();
                else if (columnIndex == 2)
                    return ship.getStatus().getStatus();
                else if (columnIndex == 3)
                    return (Integer) ship.getCurrentCargo().getCargoParameters().getCargoCount();
                else
                    return null;
            } catch (ArrayIndexOutOfBoundsException ex) {
                return null;
            }
        }
    }

    class ShipTableModel extends AbstractTableModel implements Observer {
        final Vector shipList = new Vector();

        public void addAll(List<Ship> shipsList) {
            shipList.addAll(shipsList);
            fireTableDataChanged();
        }

        public void clearTable() {
            shipList.removeAllElements();
        }

        public void addShip(Ship addingShip) {
            if (addingShip == null) {
                fireTableDataChanged();
                return;
            }
            shipList.addElement(addingShip);
            fireTableRowsInserted(shipList.size() - 1, shipList.size() - 1);
        }

        @Override
        public void update(Observable o, Object arg) {
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return shipList.size();
        }

        @Override
        public int getColumnCount() {
            return TableConstants.SHIP_TABLE_HEADERS.length;
        }

        @Override
        public Class getColumnClass(int c) {
            return TableConstants.SHIP_TABLE_COLUMN_CLASSES[c];
        }

        @Override
        public String getColumnName(int col) {
            return TableConstants.SHIP_TABLE_HEADERS[col];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                Ship ship = (Ship) shipList.elementAt(rowIndex);
                if (ship == null) return null;
                if (columnIndex == 0) return ship.getNameShip();
                else if (columnIndex == 1)
                    return ship.getCurrentCargo().getCargoParameters().getTypeOfProduct().getType()
                            + " "
                            + ship.getCurrentCargo().getCargoParameters().getCargoMeasure().getMeasure();
                else if (columnIndex == 2) return ship.getStatus().getStatus();
                else if (columnIndex == 3) return (Integer) ship.getCurrentCargo().getCargoParameters().getCargoCount();
                else if (columnIndex == 4) return (Float) ship.getProgress();
                else return null;
            } catch (ArrayIndexOutOfBoundsException e) {
                return null;
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }

    class unloadingShipProgressBar extends JProgressBar implements TableCellRenderer {

        public unloadingShipProgressBar(int min, int max) {
            super(min, max);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            if (value == null) return this;
            setValue((int) ((Float) value).floatValue());

            return this;
        }
    }
}
