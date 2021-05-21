package com.mainpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Window1 {
    private JPanel panel1;
    private JLabel formHeaderText;
    private JTextField inputName;
    private JTextField inputSurname;
    private JTextField inputNationalId;
    private JTextField inputBornDate;
    private JLabel textName;
    private JLabel textSurname;
    private JLabel textNationalId;
    private JLabel textBornDate;
    private JButton saveButton;
    private JLabel emptyLabel;
    private boolean check;


    public Window1() {
        formHeaderText.setFont(new Font("Serif", Font.PLAIN, 20));
        check = false;

        DatabaseConnection dbc = new DatabaseConnection();
        Connection mysqlConnection = dbc.getConnection();
        dbc.dbw.getDeneme();

        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!check){
                    check = true;
                    emptyLabel.setText("Tebrikler, buton calisiyor!");
                }else{
                    check = false;
                    emptyLabel.setText("Tebrikler, hala calisiyor!");
                }
            }
        });

    }

    public static void main(String[] args) {
        JFrame jf = new JFrame("Ilk App");
        jf.setContentPane(new Window1().panel1);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
        jf.setSize(300, 300);
        jf.setLocationRelativeTo(null);
    }

}
