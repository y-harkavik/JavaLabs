package GUI.Dialogs;

import GUI.MainWindow.PortSystemGUIController;
import RegEx.RegEx;
import objects.Buildings.Port.ShipPort;
import objects.Product.Cargo.Cargo;
import objects.Product.Characteristics.Measure;
import objects.Product.Characteristics.Operation;
import objects.Product.Characteristics.TypeOfProduct;
import objects.Transport.Marine.Ship;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observer;

/**
 * Class that serves to display AddShip dialog frame.
 *
 * @author Yauheni
 * @version 1.0
 */
public class AddShipDialog extends JDialog {
    private JCheckBox clothCheckBox;
    private JComboBox<String> clothComboBox;
    private JTextField clothTextField;
    private JCheckBox coalCheckBox;
    private JComboBox<String> coalComboBox;
    private JTextField coalTextField;
    private JCheckBox goldCheckBox;
    private JComboBox<String> goldComboBox;
    private JTextField goldTextField;
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JCheckBox slavesCheckBox;
    private JComboBox<String> slavesComboBox;
    private JTextField slavesTextField;
    private JCheckBox weedCheckBox;
    private JComboBox<String> weedComboBox;
    private JTextField weedTextField;
    private JCheckBox woodCheckBox;
    private JComboBox<String> woodComboBox;
    private JTextField woodTextField;
    private JButton addButton;
    private PortSystemGUIController controller;
    private Map<String, ShipPort> shipPortList;
    private Observer observer;

    /**
     * Creates addShipDialog by initComponents, setComponentsParameters, setComponentsOnWindow, initListeners.
     *
     * @param controller   Current PortSystemGUIController
     * @param shipPortList Map of ShipPorts.
     * @param observer     Observer above Ship.
     *                     {@link #initComponents() initComponents()}
     *                     {@link #setComponentsParameters() setComponentsParameters()}
     *                     {@link #setComponentsOnWindow() setComponentsOnWindow()}
     *                     {@link #initListeners() initListeners()}
     * @see PortSystemGUIController
     * @see ShipPort
     */
    public AddShipDialog(PortSystemGUIController controller, Map<String, ShipPort> shipPortList, Observer observer) {
        super((Dialog) null, "Add ship", false);
        this.controller = controller;
        this.shipPortList = shipPortList;
        this.observer = observer;

        initComponents();
        initListeners();
        setComponentsParameters();
        setComponentsOnWindow();
    }

    /**
     * Initializes graphical components of AddShipDialog.
     *
     * @see Component
     */
    private void initComponents() {
        String[] operations = {Operation.LOADING.toString(), Operation.UNLOADING.toString()};

        mainPanel = new JPanel();
        nameLabel = new JLabel();
        nameTextField = new JTextField();
        woodCheckBox = new JCheckBox();
        woodComboBox = new JComboBox<>(operations);
        woodTextField = new JTextField();
        weedTextField = new JTextField();
        weedComboBox = new JComboBox<>(operations);
        weedCheckBox = new JCheckBox();
        coalTextField = new JTextField();
        coalComboBox = new JComboBox<>(operations);
        coalCheckBox = new JCheckBox();
        goldTextField = new JTextField();
        goldComboBox = new JComboBox<>(operations);
        goldCheckBox = new JCheckBox();
        slavesTextField = new JTextField();
        slavesComboBox = new JComboBox<>(operations);
        slavesCheckBox = new JCheckBox();
        clothTextField = new JTextField();
        clothComboBox = new JComboBox<>(operations);
        clothCheckBox = new JCheckBox();
        addButton = new JButton();
    }

    /**
     * Set components parameters: font,size and etc.
     */
    private void setComponentsParameters() {
        Font font = new Font("Corbel", 0, 18);

        nameLabel.setFont(new Font("Corbel", 0, 24));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setText("Name");
        nameTextField.setFont(new Font("Corbel", 0, 24));

        woodTextField.setFont(font);
        woodCheckBox.setText("WOOD");

        weedTextField.setFont(font);
        weedCheckBox.setText("WEED");

        coalTextField.setFont(font);
        coalCheckBox.setText("COAl");

        goldTextField.setFont(font);
        goldCheckBox.setText("GOLD");

        slavesTextField.setFont(font);
        slavesCheckBox.setText("SLAVES");

        clothTextField.setFont(font);
        clothCheckBox.setText("CLOTH");

        addButton.setText("ADD");
        addButton.setFont(new Font("Corbel", 0, 48));

    }

    /**
     * Initializes components listeners.
     * <p>
     * {@link #checkInfo() checkInfo()}
     */
    private void initListeners() {
        addButton.addActionListener(e -> {
            List<Cargo> cargoList;
            if ((cargoList = checkInfo()) != null) {
                Ship ship = new Ship(nameTextField.getText(), cargoList, shipPortList);
                ship.addObserver(observer);
                Thread shipThread = new Thread(ship);
                shipThread.setDaemon(true);
                controller.getMainWindow().printAction(ship.getNameShip() + " " + " - в пути" + "\n");
                shipThread.start();
                this.hide();
            }
        });
    }

    /**
     * Set components on AddPortDialog frame.
     */
    private void setComponentsOnWindow() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(woodComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(woodTextField)
                        .addComponent(weedComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(weedTextField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                        .addComponent(coalComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(coalTextField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                        .addComponent(goldComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(goldTextField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                        .addComponent(slavesComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(slavesTextField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                        .addComponent(clothComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clothTextField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(woodCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(weedCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(coalCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(goldCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(slavesCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(clothCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(nameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nameTextField)
                        .addComponent(addButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(nameLabel)
                                .addComponent(nameTextField, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(woodCheckBox)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(woodComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(woodTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(weedCheckBox)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(weedComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(weedTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(coalCheckBox)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(coalComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(coalTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(goldCheckBox)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(goldComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(goldTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slavesCheckBox)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slavesComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slavesTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clothCheckBox)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clothComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clothTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(addButton, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    /**
     * Caused when user typed "Add ship" button. This method checks entered parameters. If all parameters meet the requirements return list of ship cargo.
     *
     * @return If all parameters good returns cargoList, else return null.
     * @see Cargo
     */
    private List<Cargo> checkInfo() {
        String num;
        List<Cargo> cargoList = new LinkedList<>();
        Operation operation;

        if (!RegEx.checkName(nameTextField.getText())) {
            JOptionPane.showMessageDialog(null, "Wrong name", "Error", 2);
            return null;
        }
        if (weedCheckBox.isSelected()) {
            num = weedTextField.getText();
            if (!RegEx.checkNum(num)) {
                JOptionPane.showMessageDialog(null, "Wrong number", "Error", 2);
                return null;
            }
            if (weedComboBox.getSelectedItem().equals(Operation.LOADING.toString())) {
                operation = Operation.LOADING;
            } else operation = Operation.UNLOADING;
            cargoList.add(new Cargo(TypeOfProduct.WEED, Measure.KG, Integer.parseInt(num), operation));
        }
        if (woodCheckBox.isSelected()) {
            num = woodTextField.getText();
            if (!RegEx.checkNum(num)) {
                JOptionPane.showMessageDialog(null, "Wrong number", "Error", 2);
                return null;
            }
            if (woodComboBox.getSelectedItem().equals(Operation.LOADING.toString())) {
                operation = Operation.LOADING;
            } else operation = Operation.UNLOADING;
            cargoList.add(new Cargo(TypeOfProduct.WOOD, Measure.POUND, Integer.parseInt(num), operation));
        }
        if (goldCheckBox.isSelected()) {
            num = goldTextField.getText();
            if (!RegEx.checkNum(num)) {
                JOptionPane.showMessageDialog(null, "Wrong number", "Error", 2);
                return null;
            }
            if (goldComboBox.getSelectedItem().equals(Operation.LOADING.toString())) {
                operation = Operation.LOADING;
            } else operation = Operation.UNLOADING;
            cargoList.add(new Cargo(TypeOfProduct.GOLD, Measure.FUTS, Integer.parseInt(num), operation));
        }
        if (coalCheckBox.isSelected()) {
            num = coalTextField.getText();
            if (!RegEx.checkNum(num)) {
                JOptionPane.showMessageDialog(null, "Wrong number", "Error", 2);
                return null;
            }
            if (coalComboBox.getSelectedItem().equals(Operation.LOADING.toString())) {
                operation = Operation.LOADING;
            } else operation = Operation.UNLOADING;
            cargoList.add(new Cargo(TypeOfProduct.COAL, Measure.KG, Integer.parseInt(num), operation));
        }
        if (clothCheckBox.isSelected()) {
            num = clothTextField.getText();
            if (!RegEx.checkNum(num)) {
                JOptionPane.showMessageDialog(null, "Wrong number", "Error", 2);
                return null;
            }
            if (clothComboBox.getSelectedItem().equals(Operation.LOADING.toString())) {
                operation = Operation.LOADING;
            } else operation = Operation.UNLOADING;
            cargoList.add(new Cargo(TypeOfProduct.CLOTH, Measure.METR, Integer.parseInt(num), operation));
        }
        if (slavesCheckBox.isSelected()) {
            num = slavesTextField.getText();
            if (!RegEx.checkNum(num)) {
                JOptionPane.showMessageDialog(null, "Wrong number", "Error", 2);
                return null;
            }
            if (slavesComboBox.getSelectedItem().equals(Operation.LOADING.toString())) {
                operation = Operation.LOADING;
            } else operation = Operation.UNLOADING;
            cargoList.add(new Cargo(TypeOfProduct.SLAVES, Measure.POUND, Integer.parseInt(num), operation));
        }
        if (cargoList.size() != 0) {
            return cargoList;
        }
        return null;
    }
}
