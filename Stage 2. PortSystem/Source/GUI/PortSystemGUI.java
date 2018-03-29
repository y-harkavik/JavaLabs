package GUI;


import objects.Ship;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
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
    JMenuItem addShip;
    JPanel mainJPanel;
    JScrollPane portTreeJScrollPane;
    JScrollPane shipsTableJScrollPane;
    JScrollPane logTextAreaJScrollPane;
    JTable shipsTable;
    JTextArea logTextArea;
    JTree portTree;
    ShipTableModel tableModel;
    unloadingShipProgressBar progressBarRenderer;

    /**
     * Creates new form PortSystemGUI
     */

    public PortSystemGUI() {
        initComponents();
        setComponents();
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
                                .addComponent(shipsTableJScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(logTextAreaJScrollPane, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
        );

        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(portTreeJScrollPane)
                        .addComponent(shipsTableJScrollPane, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
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
                        .addComponent(mainJPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setJMenuBar(appMenuBar);

        pack();
    }

    private void initComponents() {
        tableModel = new ShipTableModel();

        progressBarRenderer = new unloadingShipProgressBar(0, 100);

        mainJPanel = new JPanel();

        portTree = new JTree();
        portTreeJScrollPane = new JScrollPane(portTree);

        shipsTable = new JTable(tableModel);
        shipsTableJScrollPane = new JScrollPane(shipsTable);

        logTextArea = new JTextArea();
        logTextAreaJScrollPane = new JScrollPane(logTextArea);

        appMenuBar = new JMenuBar();
        appMenu = new JMenu();
        addShip = new JMenuItem();

        progressBarRenderer.setStringPainted(true);

        shipsTable.setDefaultRenderer(JProgressBar.class, progressBarRenderer);

        shipsTable.setRowHeight((int) progressBarRenderer.getPreferredSize().getHeight());

        portTree.setFont(new Font("Corbel", 0, 13));
        appMenuBar.setFont(new Font("Corbel", 0, 15));

        logTextArea.setEditable(false);

        appMenu.setText("Settings");
        addShip.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_MASK));
        addShip.setText("Add ship");
        appMenu.add(addShip);
        appMenuBar.add(appMenu);
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
            for(Ship ship: shipsList) {
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
