package org.example.graphic;

import org.example.DBConnect;
import org.example.logic.ChecklistItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class EditChecklist extends JFrame {
    JPanel panel;
    JLabel addLabel, removeLabel;
    JTextField addText, removeText;
    JButton saveButton, cancelButton, infoAddButton, infoRemoveButton, idButton;
    float[] hsb = Color.RGBtoHSB(134, 218, 140, null);
    public EditChecklist() throws HeadlessException {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints g = new GridBagConstraints();

        addLabel = new JLabel("Add:");
            g.gridx = 0;
            g.gridy = 0;
            g.gridwidth = 1;
            g.gridheight = 1;
        panel.add(addLabel, g);


        removeLabel = new JLabel("Remove:");
            g.gridx = 0;
            g.gridy = 1;
            g.gridwidth = 1;
            g.gridheight = 1;
        panel.add(removeLabel, g);


        addText = new JTextField();
            g.gridx = 1;
            g.gridy = 0;
            g.gridwidth = 2;
            g.gridheight = 1;
            g.fill = GridBagConstraints.HORIZONTAL;
        addText.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        addText.setColumns(20);
        panel.add(addText, g);


        removeText = new JTextField();
            g.gridx = 1;
            g.gridy = 1;
            g.gridwidth = 2;
            g.gridheight = 1;
            g.fill = GridBagConstraints.HORIZONTAL;
        removeText.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        removeText.setColumns(20);
        panel.add(removeText, g);


        infoAddButton = new JButton("?");
            g.gridx = 4;
            g.gridy = 0;
            g.gridwidth = 1;
            g.gridheight = 1;
        infoAddButton.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        panel.add(infoAddButton, g);

        infoAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Enter the text you want to add to the checklist.");
            }
        });


        infoRemoveButton = new JButton("?");
            g.gridx = 4;
            g.gridy = 1;
            g.gridwidth = 1;
            g.gridheight = 1;
        infoRemoveButton.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        panel.add(infoRemoveButton, g);

        infoRemoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Enter the ID of the item you want to remove from the checklist.");
            }
        });


        idButton = new JButton("ID");
            g.gridx = 4;
            g.gridy = 2;
            g.gridwidth = 1;
            g.gridheight = 1;
        idButton.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        panel.add(idButton, g);

        idButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBConnect dbConnect = new DBConnect();
                java.util.List<ChecklistItem> items = dbConnect.getItemsFromTable(SelectOperation.getLastUsedTable());
                ItemsID itemsID = new ItemsID(items);
            }
        });


        saveButton = new JButton("Save");
            g.gridx = 1;
            g.gridy = 2;
            g.gridwidth = 1;
            g.gridheight = 1;
        saveButton.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        panel.add(saveButton, g);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBConnect dbConnect = new DBConnect();
                String itemToAdd = addText.getText();
                String itemToRemove = removeText.getText();

                if (!itemToAdd.isEmpty()) {
                    String lastUsedTable = SelectOperation.getLastUsedTable();
                    dbConnect.addItemInTable(lastUsedTable, itemToAdd);
                }

                if (!itemToRemove.isEmpty()) {
                    try {
                        int idToRemove = Integer.parseInt(itemToRemove);
                        String lastUsedTable = SelectOperation.getLastUsedTable();
                        dbConnect.removeItemById(lastUsedTable, idToRemove);
                    } catch (NumberFormatException ex) {
                        System.out.println("Invalid ID format: " + itemToRemove);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the window?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    setVisible(false);
                }
            }
        });



            cancelButton = new JButton("Cancel");
            g.gridx = 2;
            g.gridy = 2;
            g.gridwidth = 1;
            g.gridheight = 1;
        cancelButton.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        panel.add(cancelButton, g);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the window?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    setVisible(false);
                }
            }
        });
        add(panel);


        setTitle("Edit");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}