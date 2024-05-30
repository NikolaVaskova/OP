package org.example.graphic;

import org.example.DBConnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Register extends JFrame {
    JPanel panel;
    JLabel nameLabel, emailLabel, passwordLabel;
    JTextField nameText, emailText;
    JPasswordField passwordText;
    JButton registerButton, cancelButton, showPasswordButton;
    float[] hsb = Color.RGBtoHSB(134, 218, 140, null);
    public Register() throws HeadlessException {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints g = new GridBagConstraints();

        nameLabel = new JLabel("Name:");
            g.gridx = 0;
            g.gridy = 0;
            g.gridwidth = 1;
            g.gridheight = 1;
        panel.add(nameLabel, g);


        emailLabel = new JLabel("Email:");
            g.gridx = 0;
            g.gridy = 1;
            g.gridwidth = 1;
            g.gridheight = 1;
        panel.add(emailLabel, g);


        passwordLabel = new JLabel("Password:");
            g.gridx = 0;
            g.gridy = 2;
            g.gridwidth = 1;
            g.gridheight = 1;
        panel.add(passwordLabel, g);


        nameText = new JTextField();
            g.gridx = 1;
            g.gridy = 0;
            g.gridwidth = 2;
            g.gridheight = 1;
            g.fill = GridBagConstraints.HORIZONTAL;
        nameText.setColumns(20);
        nameText.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        panel.add(nameText, g);


        emailText = new JTextField();
            g.gridx = 1;
            g.gridy = 1;
            g.gridwidth = 2;
            g.gridheight = 1;
            g.fill = GridBagConstraints.HORIZONTAL;
        emailText.setColumns(20);
        emailText.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        panel.add(emailText, g);


        passwordText = new JPasswordField();
            g.gridx = 1;
            g.gridy = 2;
            g.gridwidth = 2;
            g.gridheight = 1;
            g.fill = GridBagConstraints.HORIZONTAL;
        passwordText.setColumns(20);
        passwordText.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        panel.add(passwordText, g);


        registerButton = new JButton("Register");
            g.gridx = 1;
            g.gridy = 3;
            g.gridwidth = 1;
            g.gridheight = 1;
        registerButton.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        panel.add(registerButton, g);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                String email = emailText.getText();

                if (email.indexOf("@savencia.com") != -1) {
                    DBConnect dbConnect = new DBConnect();
                    try {
                        char[] passwordCharArray = passwordText.getPassword();
                        String password = new String(passwordCharArray);
                        dbConnect.addUser(name, email, passwordHash(password));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid email");
                }
                JOptionPane.showMessageDialog(null, "Registration was successful");
            }
        });

        cancelButton = new JButton("Cancel");
            g.gridx = 2;
            g.gridy = 3;
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


        showPasswordButton = new JButton("Show");
            g.gridx = 4;
            g.gridy = 2;
            g.gridwidth = 1;
            g.gridheight = 1;
        showPasswordButton.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        panel.add(showPasswordButton, g);

        showPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (passwordText.getEchoChar() == (char)0) {
                    //pokud je heslo viditelne, skryje se
                    passwordText.setEchoChar('*');
                } else {
                    //pokud je heslo skryte, zviditelni se
                    passwordText.setEchoChar((char)0);
                }
            }
        });
        add(panel);


        setTitle("Register");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private String passwordHash(String password) {
        return password;
    }
}