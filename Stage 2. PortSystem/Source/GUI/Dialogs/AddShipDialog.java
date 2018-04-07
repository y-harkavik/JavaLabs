package GUI.Dialogs;

import objects.Product.Characteristics.Operation;

import javax.swing.*;
import java.awt.*;

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

    public AddShipDialog() {
        super((Dialog) null, "Add ship", true);
        initComponents();
        setComponentsParameters();
        setComponentsOnWindow();
    }

    private void initComponents() {
        String[] operations = {Operation.LOADNIG.toString(), Operation.UNLOADING.toString()};

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
        addButton.addActionListener(e -> {});
    }

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

    public static void main(String args[]) {
        
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddShipDialog().setVisible(true);
            }
        });
    }
}
