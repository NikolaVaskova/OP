package org.example.graphic;

import org.example.logic.ChecklistItem;
import org.example.logic.NewChecklist;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemsID extends JFrame {
    private org.example.logic.NewChecklist newChecklist;
    JPanel panel;
    JTable table;
    JButton cancelButton;

    public ItemsID(java.util.List<ChecklistItem> items) throws HeadlessException {
        this.newChecklist = new NewChecklist();
        for (ChecklistItem item : items) {
            this.newChecklist.addItem(item);
        }
        panel = new JPanel();
        panel.setLayout(new BorderLayout());


        // definuje nazev sloupcu
        String[] columnNames = {"ID", "Item"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        // naplni tabulku
        for (ChecklistItem item : newChecklist.getItems()) {
            Object[] row = {item.getId(), item.getLabel()};
            model.addRow(row);
        }
        table = new JTable(model);

        table.getColumnModel().getColumn(0).setPreferredWidth(10);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);


        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.getHSBColor(192,237,251));
        panel.add(cancelButton, BorderLayout.SOUTH);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });


        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);


        setTitle("IDs");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}