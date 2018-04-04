package GUI;


import objects.Ship;
import objects.ShipStatus;

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
    public JTextArea logTextArea;
    JTree portTree;

    DefaultMutableTreeNode rootNode;

    ShipTableModel portTableModel;
    ShipQueueTableModel queueTableModel;

    unloadingShipProgressBar progressBarRenderer;

    /**
     * Creates new form PortSystemGUI
     */

    public PortSystemGUI() {
        initComponents();
        setComponents();
        createTree();
    }

    private void setComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 550));
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
                        .addComponent(portTreeJScrollPane, GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(shipsTableJScrollPane, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addComponent(queueTableJScrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addComponent(logTextAreaJScrollPane)
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mainJPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(mainJPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        ));

        setJMenuBar(appMenuBar);

        pack();
    }

    private void initComponents() {
        portTableModel = new ShipTableModel();
        queueTableModel = new ShipQueueTableModel();

        progressBarRenderer = new unloadingShipProgressBar(0, 100);

        mainJPanel = new JPanel();

        portTree = new JTree();
        portTreeJScrollPane = new JScrollPane(portTree);

        shipsTable = new JTable(portTableModel);
        shipsTableJScrollPane = new JScrollPane(shipsTable);

        queueTable = new JTable(queueTableModel);
        queueTableJScrollPane = new JScrollPane(queueTable);

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

    private void createTree() {
        portTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        rootNode = new DefaultMutableTreeNode("Ports");
        ((DefaultTreeModel) portTree.getModel()).setRoot(rootNode);
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(() -> {
            new PortSystemGUI().setVisible(true);
        });
    }

    class ShipQueueTableModel extends AbstractTableModel {
        final String[] headers = {"Ship", "Good","Operation" ,"Count"};
        final Class[] columnClasses = {String.class, String.class, ShipStatus.class, Long.class};
        final Vector<Ship> shipQueue = new Vector<>();

        synchronized public void addAll(List<Ship> shipsList) {
            shipQueue.addAll(shipsList);
            fireTableDataChanged();
        }

        synchronized public void clearTable() {
            shipQueue.removeAllElements();
        }

        synchronized public void addShip(Ship addingShip) {
            shipQueue.addElement(addingShip);
            fireTableRowsInserted(shipQueue.size() - 1, shipQueue.size() - 1);
        }

        @Override
        public int getRowCount() {
            return shipQueue.size();
        }

        @Override
        public int getColumnCount() {
            return headers.length;
        }

        @Override
        public Class getColumnClass(int c) {
            return columnClasses[c];
        }

        @Override
        public String getColumnName(int col) {
            return headers[col];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Ship ship = (Ship) shipQueue.elementAt(rowIndex);
            if (columnIndex == 0) return ship.getNameShip();
            else if (columnIndex == 1) return ship.getShipTypeOfProduct().getType();
            else if(columnIndex == 2) return ship.getStatus().getStatus();
            else if (columnIndex == 3) return (Long) ship.getShipTypeOfProduct().getCount();
            else return null;
        }
    }
    class ShipTableModel extends AbstractTableModel implements Observer {
        final String[] headers = {"Ship", "Good","Operation" ,"Count", "Progress"};
        final Class[] columnClasses = {String.class, String.class, ShipStatus.class, Long.class, JProgressBar.class};
        final Vector data = new Vector();

        public void addAll(List<Ship> shipsList) {
            data.addAll(shipsList);
            fireTableDataChanged();
        }

        public void clearTable() {
            data.removeAllElements();
        }

        public void addShip(Ship addingShip) {
            /*if(addingShip==null) {
                fireTableDataChanged();
                return;
            }*/
            if(addingShip==null) {
                fireTableDataChanged();
                return;
            }
            data.addElement(addingShip);
            fireTableRowsInserted(data.size() - 1, data.size() - 1);
        }

        @Override
        public void update(Observable o, Object arg) {
            //int index = data.indexOf(o);
            //if (index != -1)
            //fireTableRowsUpdated(index, index);
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return headers.length;
        }

        @Override
        public Class getColumnClass(int c) {
            return columnClasses[c];
        }

        @Override
        public String getColumnName(int col) {
            return headers[col];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Ship ship = (Ship) data.elementAt(rowIndex);
            if(ship == null) return null;
            if (columnIndex == 0) return ship.getNameShip();
            else if (columnIndex == 1) return ship.getShipTypeOfProduct().getType();
            else if(columnIndex == 2) return ship.getStatus().getStatus();
            else if (columnIndex == 3) return (Long) ship.getShipTypeOfProduct().getCount();
            else if (columnIndex == 4) return (Float) ship.getProgress();
            else return null;
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
            if(value == null) return this;
            setValue((int) ((Float) value).floatValue());

            return this;
        }
    }
}
