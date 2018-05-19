package GUI.Dialogs;

import GUI.MainWindow.PortSystemGUIController;
import RegEx.RegEx;
import objects.Buildings.Port.PortYard;
import objects.Product.Characteristics.TypeOfProduct;

import javax.swing.*;
import java.awt.*;

/**
 * Class that serves to display AddPort dialog frame.
 *
 * @author Yauheni
 * @version 1.0
 */
public class AddPortDialog extends JDialog {
    private JCheckBox clothCheckBox;
    private JTextField clothTextField;
    private JTextField coalTextField;
    private JCheckBox goldCheckBox;
    private JTextField goldTextField;
    private JButton addPortButton;
    private JPanel mainPanel;
    private JLabel numOfPiersLabel;
    private JTextField numOfPiersTextField;
    private JCheckBox slavesCheckBox;
    private JTextField slavesTextField;
    private JCheckBox weedCheckBox;
    private JTextField weedTextField;
    private JCheckBox coalCheckBox;
    private JCheckBox woodCheckBox;
    private JTextField woodTextField;
    private PortSystemGUIController controller;

    /**
     * Creates addPortDialog by initComponents, setComponentsParameters, setComponentsOnWindow.
     *
     * @param controller Our PortSystemGUIController variable
     * @see PortSystemGUIController
     * @see #initComponents()
     */
    public AddPortDialog(PortSystemGUIController controller) {
        this.controller = controller;
        initComponents();
        setComponentsParameters();
        setComponentsOnWindow();
    }

    /**
     * Initializes graphical components of AddPortDialog.
     *
     * @see Component
     */
    private void initComponents() {
        mainPanel = new JPanel();
        numOfPiersLabel = new JLabel();
        numOfPiersTextField = new JTextField();
        coalCheckBox = new JCheckBox();
        woodTextField = new JTextField();
        woodCheckBox = new JCheckBox();
        weedTextField = new JTextField();
        weedCheckBox = new JCheckBox();
        coalTextField = new JTextField();
        goldCheckBox = new JCheckBox();
        goldTextField = new JTextField();
        slavesCheckBox = new JCheckBox();
        slavesTextField = new JTextField();
        clothCheckBox = new JCheckBox();
        clothTextField = new JTextField();
        addPortButton = new JButton();
    }

    /**
     * Set components parameters: font,size and etc.
     */
    private void setComponentsParameters() {
        Font font = new Font("Corbel", 0, 18);

        numOfPiersLabel.setFont(new Font("Corbel", 0, 18));
        numOfPiersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numOfPiersLabel.setText("Number of piers");
        numOfPiersTextField.setFont(font);

        coalCheckBox.setText("COAL");
        coalTextField.setFont(font);

        woodCheckBox.setText("WOOD");
        woodTextField.setFont(font);

        weedCheckBox.setText("WEED");
        weedTextField.setFont(font);

        goldCheckBox.setText("GOLD");
        goldTextField.setFont(font);

        slavesCheckBox.setText("SLAVES");
        slavesTextField.setFont(font);

        clothCheckBox.setText("CLOTH");
        clothTextField.setFont(font);

        addPortButton.setFont(new Font("Corbel", 0, 24));
        addPortButton.setText("ADD");
        addPortButton.addActionListener(e -> {
            createPort();
            this.hide();
        });
    }

    /**
     * Caused when user typed "Add port" button. This method checks entered parameters. If all parameters meet the requirements caused addPort method in PortSystemGUIController.
     * <p>
     * {@link PortSystemGUIController#addPort(String, int, PortYard) addPort(String, int, PortYard)}
     *
     * @see PortSystemGUIController
     * @see PortYard
     */
    private void createPort() {
        int flag = 0;
        int numOfPiers;
        String num;
        PortYard portYard = new PortYard();

        num = numOfPiersTextField.getText();
        if (!RegEx.checkNumOfPiers(num)) {
            JOptionPane.showMessageDialog(null, "Wrong name", "Error", 2);
            return;
        }

        numOfPiers = Integer.parseInt(num);

        if (weedCheckBox.isSelected()) {
            num = weedTextField.getText();
            if (!RegEx.checkNum(num)) {
                JOptionPane.showMessageDialog(null, "Wrong number", "Error", 2);
                return;
            }
            portYard.addProduct(TypeOfProduct.WEED, Integer.parseInt(num));
            flag++;
        }
        if (woodCheckBox.isSelected()) {
            num = woodTextField.getText();
            if (!RegEx.checkNum(num)) {
                JOptionPane.showMessageDialog(null, "Wrong number", "Error", 2);
                return;
            }
            portYard.addProduct(TypeOfProduct.WOOD, Integer.parseInt(num));
            flag++;
        }
        if (goldCheckBox.isSelected()) {
            num = goldTextField.getText();
            if (!RegEx.checkNum(num)) {
                JOptionPane.showMessageDialog(null, "Wrong number", "Error", 2);
                return;
            }
            portYard.addProduct(TypeOfProduct.GOLD, Integer.parseInt(num));
            flag++;
        }
        if (coalCheckBox.isSelected()) {
            num = coalTextField.getText();
            if (!RegEx.checkNum(num)) {
                JOptionPane.showMessageDialog(null, "Wrong number", "Error", 2);
                return;
            }
            portYard.addProduct(TypeOfProduct.COAL, Integer.parseInt(num));
            flag++;
        }
        if (clothCheckBox.isSelected()) {
            num = clothTextField.getText();
            if (!RegEx.checkNum(num)) {
                JOptionPane.showMessageDialog(null, "Wrong number", "Error", 2);
                return;
            }
            portYard.addProduct(TypeOfProduct.CLOTH, Integer.parseInt(num));
            flag++;
        }
        if (slavesCheckBox.isSelected()) {
            num = slavesTextField.getText();
            if (!RegEx.checkNum(num)) {
                JOptionPane.showMessageDialog(null, "Wrong number", "Error", 2);
                return;
            }
            portYard.addProduct(TypeOfProduct.SLAVES, Integer.parseInt(num));
            flag++;
        }
        if (flag != 0) {
            controller.addPort("Port " + ++PortSystemGUIController.portCount, numOfPiers, portYard);
            return;
        }
        JOptionPane.showMessageDialog(null, "Nothing selected", "Error", 2);
        return;
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
                        .addComponent(numOfPiersLabel, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                        .addComponent(numOfPiersTextField)
                        .addComponent(coalCheckBox, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(woodTextField)
                        .addComponent(woodCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(weedTextField)
                        .addComponent(weedCheckBox, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(coalTextField)
                        .addComponent(goldCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(goldTextField)
                        .addComponent(slavesCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(slavesTextField)
                        .addComponent(clothCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clothTextField)
                        .addComponent(addPortButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(numOfPiersLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(numOfPiersTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(woodCheckBox)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(woodTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(weedCheckBox)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(weedTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(coalCheckBox)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(coalTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(goldCheckBox)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(goldTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slavesCheckBox)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slavesTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clothCheckBox)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clothTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(addPortButton, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
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
}

