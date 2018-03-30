package GUI;


import objects.Ship;

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
    JTextArea logTextArea;
    JTree portTree;

    DefaultMutableTreeNode rootNode;

    ShipTableModel portTableModel;
    ShipTableModel queueTableModel;

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
        queueTableModel = new ShipTableModel();

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
/*

        DefaultMutableTreeNode firstPort = new DefaultMutableTreeNode("Port 1");
        DefaultMutableTreeNode secondPort = new DefaultMutableTreeNode("Port 2");
        DefaultMutableTreeNode thirdPort = new DefaultMutableTreeNode("Port 3");

        DefaultMutableTreeNode firstPier = new DefaultMutableTreeNode("Pier 1");
        DefaultMutableTreeNode secondPier = new DefaultMutableTreeNode("Pier 2");
        DefaultMutableTreeNode thirdPier = new DefaultMutableTreeNode("Pier 3");

        DefaultMutableTreeNode fourthPier = new DefaultMutableTreeNode("Pier 1");
        DefaultMutableTreeNode fifthPier = new DefaultMutableTreeNode("Pier 2");

        DefaultMutableTreeNode sixthPier = new DefaultMutableTreeNode("Pier 1");


        rootNode.add(firstPort);
        rootNode.add(secondPort);
        rootNode.add(thirdPort);

        firstPort.add(firstPier);
        firstPort.add(secondPier);
        firstPort.add(thirdPier);

        secondPort.add(fourthPier);
        secondPort.add(fifthPier);

        thirdPort.add(sixthPier);
*/


        ((DefaultTreeModel) portTree.getModel()).setRoot(rootNode);
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(() -> {
            new PortSystemGUI().setVisible(true);
        });
    }

    class ShipTableModel extends AbstractTableModel implements Observer {
        final String[] headers = {"Ship", "Good", "Count", "Progress"};
        final Class[] columnClasses = {String.class, String.class, Integer.class, JProgressBar.class};
        final Vector data = new Vector();

        public void addAll(List<Ship> shipsList) {
            for (Ship ship : shipsList) {
                addShip(ship);
            }
        }

        public void clearTable() {
            data.removeAllElements();
        }

        public void addShip(Ship addingShip) {
            data.addElement(addingShip);
            addingShip.addObserver(this);
            fireTableRowsInserted(data.size() - 1, data.size() - 1);
        }

        @Override
        public void update(Observable o, Object arg) {
            int index = data.indexOf(o);
            if (index != -1)
                fireTableRowsUpdated(index, index);
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
            if (columnIndex == 0) return ship.getNameShip();
            else if (columnIndex == 1) return ship.getCargo();
            else if (columnIndex == 2) return (Integer) ship.getWeight();
            else if (columnIndex == 3) return (Float) ship.getProgress();
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

            setValue((int) ((Float) value).floatValue());

            return this;
        }
    }
}
