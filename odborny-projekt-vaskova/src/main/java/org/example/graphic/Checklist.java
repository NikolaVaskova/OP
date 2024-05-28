package org.example.graphic;

import org.example.logic.ChecklistItem;

import org.example.logic.NewChecklist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Checklist extends JFrame {
    private org.example.logic.NewChecklist newChecklist;
    private Map<ChecklistItem, JCheckBox> itemCheckBoxMap;
    JPanel panel;
    JCheckBox checkBox;
    JButton sendButton, exportButton, editButton, cancelButton;

    public Checklist(java.util.List<ChecklistItem> items) throws HeadlessException {
        this.newChecklist = new NewChecklist();
        for (ChecklistItem item : items) {
            this.newChecklist.addItem(item);
            }

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints g = new GridBagConstraints();

        this.itemCheckBoxMap = new HashMap<>();

        for (ChecklistItem item : newChecklist.getItems()) {
            checkBox = new JCheckBox(item.getLabel());
            this.itemCheckBoxMap.put(item, checkBox);
            g.gridx = 0;
            g.gridy = GridBagConstraints.RELATIVE;
            g.gridwidth = 1;
            g.gridheight = 1;
            g.anchor = GridBagConstraints.WEST;
            panel.add(checkBox, g);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout()); // vytvori specialni panel pro buttony


        sendButton = new JButton("Create message");
        sendButton.setBackground(Color.getHSBColor(192,237,251));
        buttonPanel.add(sendButton);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = SelectOperation.getUsDvName();
                String userName = Login.getUserName();
                String lastUsedTable = SelectOperation.getLastUsedTable();
                StringBuilder doneItems = new StringBuilder();
                StringBuilder notDoneItems = new StringBuilder();

                for (Map.Entry<ChecklistItem, JCheckBox> entry : itemCheckBoxMap.entrySet()) {
                    ChecklistItem item = entry.getKey();
                    JCheckBox checkBox = entry.getValue();
                    item.setDone(checkBox.isSelected());

                    if (item.isDone()) {
                        doneItems.append(item.getLabel()).append("\n");
                    } else {
                        notDoneItems.append(item.getLabel()).append("\n");
                    }
                }
                String message = "ON THIS DEVICE/PERSON " + name + " WERE DONE:\n" + doneItems.toString() + "\n" +
                        "WEREN'T DONE:\n" + notDoneItems.toString() + "\n" +
                        "BY " + userName;

                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String fileName = lastUsedTable + "_" + date + ".txt";

                try {
                    PrintWriter writer = new PrintWriter(fileName, "UTF-8");
                    writer.println(message);
                    writer.close();
                } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
            }
        });

        exportButton = new JButton("Export");
        exportButton.setBackground(Color.getHSBColor(192,237,251));
        buttonPanel.add(exportButton);

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    java.io.File selectedFile = fileChooser.getSelectedFile();
                    System.out.println(selectedFile.getName());
                    //
                }
            }
        });


        editButton = new JButton("Edit");
        editButton.setBackground(Color.getHSBColor(192,237,251));
        buttonPanel.add(editButton);

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditChecklist newWindow = new EditChecklist();
                newWindow.setVisible(true);
            }
        });


        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.getHSBColor(192,237,251));
        buttonPanel.add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });


        JScrollPane scrollPane = new JScrollPane(panel);

        setLayout(new BorderLayout()); // nastaví layout na BorderLayout
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);


        setTitle("Checklist");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,400);
        setVisible(true);
    }
}