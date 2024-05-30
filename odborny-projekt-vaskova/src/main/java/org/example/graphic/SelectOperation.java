package org.example.graphic;

import org.example.DBConnect;
import org.example.DeviceOperations;
import org.example.Names;
import org.example.UsersOperations;
import org.example.logic.ChecklistItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SelectOperation extends JFrame {
    static String lastUsedTable;
    private static String name;
    JPanel panel;
    JLabel operationLabel, nameLabel;
    JTextField nameUsDvText;
    JComboBox nameComboBox, operationComboBox;
    JButton nextButton;
    float[] hsb = Color.RGBtoHSB(134, 218, 140, null);
    public SelectOperation() throws HeadlessException {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints g = new GridBagConstraints();

        operationLabel = new JLabel("Operation:");
            g.gridx = 0;
            g.gridy = 1;
            g.gridwidth = 1;
            g.gridheight = 1;
        panel.add(operationLabel, g);


        nameLabel =  new JLabel("Name:");
            g.gridx = 0;
            g.gridy = 0;
            g.gridwidth = 1;
            g.gridheight = 1;
        panel.add(nameLabel, g);


        nameUsDvText = new JTextField();
            g.gridx = 1;
            g.gridy = 0;
            g.gridwidth = 2;
            g.gridheight = 1;
            g.fill = GridBagConstraints.HORIZONTAL;
        nameUsDvText.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        nameUsDvText.setColumns(10);
        panel.add(nameUsDvText, g);


        nameComboBox = new JComboBox();
            g.gridx = 3;
            g.gridy = 0;
            g.gridwidth = 2;
            g.gridheight = 1;
            g.fill = GridBagConstraints.HORIZONTAL;
        panel.add(nameComboBox, g);

        for (Names name : Names.values()) {
            nameComboBox.addItem(name.toString());
        }
        nameComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    operationComboBox.removeAllItems();
                    if (nameComboBox.getSelectedItem().equals(Names.PERSON.toString())) {
                        for (UsersOperations operation : UsersOperations.values()) {
                            operationComboBox.addItem(operation.toString());
                        }
                    } else if (nameComboBox.getSelectedItem().equals(Names.DEVICE.toString())) {
                        for (DeviceOperations operation : DeviceOperations.values()) {
                            operationComboBox.addItem(operation.toString());
                        }
                    }
                }
            }
        });


        operationComboBox = new JComboBox();
            g.gridx = 2;
            g.gridy = 1;
            g.gridwidth = 3;
            g.gridheight = 1;
            g.fill = GridBagConstraints.HORIZONTAL;
        panel.add(operationComboBox, g);


        nextButton = new JButton("Next");
            g.gridx = 2;
            g.gridy = 2;
            g.gridwidth = 1;
            g.gridheight = 1;
        nextButton.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        panel.add(nextButton, g);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = nameUsDvText.getText();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in the name");
                    return;
                }
                if (operationComboBox.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Please select an operation");
                    return;
                }
                String selectedOperation = operationComboBox.getSelectedItem().toString();
                lastUsedTable = selectedOperation;
                name = nameUsDvText.getText();
                DBConnect dbConnect = new DBConnect();
                java.util.List<ChecklistItem> items = dbConnect.getItemsFromTable(selectedOperation);
                Checklist checklist = new Checklist(items);
            }
        });
        add(panel);


        setTitle("Select Operation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static String getUsDvName() {
        return name;
    }
    public static String getLastUsedTable() {
        return lastUsedTable;
    }
}