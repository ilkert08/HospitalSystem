import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.sql.*;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ALTERNATIFRANDEVU extends JPanel {

private JPanel contentPane;

private DatabaseConnection conn;
private JTextField textField;


public ALTERNATIFRANDEVU(DatabaseConnection conn, String alternativeRandevu1, String alternativeRandevu2) {
//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setBounds(100, 100, 347, 117);
contentPane = this;
setLayout(null);
JLabel lblNewLabel = new JLabel("Secili Tarih Doludur.");
lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
lblNewLabel.setBounds(10, 10, 201, 32);
add(lblNewLabel);
JLabel lblAlternatifTarih = new JLabel("Alternatif Tarih:");
lblAlternatifTarih.setFont(new Font("Tahoma", Font.PLAIN, 15));
lblAlternatifTarih.setBounds(10, 52, 114, 32);
add(lblAlternatifTarih);
textField = new JTextField();
textField.setBounds(134, 52, 132, 32);
textField.setText(alternativeRandevu1+" "+alternativeRandevu2);
add(textField);
textField.setColumns(10);
contentPane.setBackground(Color.LIGHT_GRAY);
contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
contentPane.setLayout(null);
String[] str = {"8.00","8.30","9.00","9.30","10.00","10.30","11.00","11.30","12.00","12.30","13.00","13.30","14.00","14.30","15.00",
		"15.30","16.00","16.30"};


}
}
