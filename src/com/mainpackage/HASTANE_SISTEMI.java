import java.awt.*;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.toedter.calendar.JDateChooser;

public class HASTANE_SISTEMI extends JFrame{

private JFrame frame;
private DatabaseConnection conn;
private JTextField TC_TEXTFIELD;
private	ArrayList<String[]> Poliklinikler;
private	ArrayList<String []> Doktorlar;
public HASTANE_SISTEMI(DatabaseConnection dbc) {
initialize(dbc);
}


public static void main(String[] args) throws SQLException {

	DatabaseConnection dbc = new DatabaseConnection();//DriverManager.getConnection("jdbc:postgresql:", user,pass);
	final Connection conn;
	conn = dbc.getConnection();
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				HASTANE_SISTEMI window = new HASTANE_SISTEMI(dbc);
				window.frame.setVisible(true);
				window.frame.setResizable(false);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
}




private void initialize(DatabaseConnection conn) {     
DatabaseConnection dbc = conn;
frame = new JFrame();
frame.getContentPane().setBackground(Color.LIGHT_GRAY);
frame.setBackground(Color.DARK_GRAY);
frame.setBounds(100, 100, 431, 358);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.getContentPane().setLayout(null);
frame.setLocationRelativeTo(null);

JLabel RANDEVUKAYIT = new JLabel("RANDEVU KAYIT");
RANDEVUKAYIT.setFont(new Font("Tahoma", Font.PLAIN, 20));
RANDEVUKAYIT.setBounds(38, 53, 202, 28);
frame.getContentPane().add(RANDEVUKAYIT);
JLabel POLIKLINIK = new JLabel("POLIKLINIK");
POLIKLINIK.setFont(new Font("Tahoma", Font.PLAIN, 12));
POLIKLINIK.setBounds(38, 94, 96, 13);
frame.getContentPane().add(POLIKLINIK);
JLabel DOKTOR = new JLabel("DOKTOR");
DOKTOR.setFont(new Font("Tahoma", Font.PLAIN, 12));
DOKTOR.setBounds(38, 123, 96, 13);
frame.getContentPane().add(DOKTOR);
JLabel TARIH = new JLabel("TARIH");
TARIH.setFont(new Font("Tahoma", Font.PLAIN, 12));
TARIH.setBounds(38, 152, 96, 13);
frame.getContentPane().add(TARIH);
JLabel SAAT = new JLabel("SAAT");
SAAT.setFont(new Font("Tahoma", Font.PLAIN, 12));
SAAT.setBounds(38, 180, 96, 13);
frame.getContentPane().add(SAAT);
JLabel TC_NO = new JLabel("TC NO");
TC_NO.setFont(new Font("Tahoma", Font.PLAIN, 12));
TC_NO.setBounds(38, 209, 96, 13);
frame.getContentPane().add(TC_NO);
TC_TEXTFIELD = new JTextField();
TC_TEXTFIELD.setBounds(144, 207, 145, 19);
frame.getContentPane().add(TC_TEXTFIELD);
TC_TEXTFIELD.setColumns(10);
	
Poliklinikler = dbc.getPoliklinikler();

String [] poliklinikId = new String[Poliklinikler.size()];
JComboBox POLIKLINIKSEC = new JComboBox();
for (int i = 0; i < Poliklinikler.size(); i++) {
	String[] temp = (Poliklinikler.get(i));
	poliklinikId[i] = (temp[0]);
	POLIKLINIKSEC.addItem(temp[1]);
}

POLIKLINIKSEC.setBounds(144, 91, 145, 19);
frame.getContentPane().add(POLIKLINIKSEC);


JComboBox DOKTORSEC = new JComboBox();
DOKTORSEC.setBounds(144, 118, 145, 21);

POLIKLINIKSEC.addActionListener (new ActionListener () {
		public void actionPerformed(ActionEvent e) {
			int x = POLIKLINIKSEC.getSelectedIndex();
			x = x+1;
			Doktorlar = dbc.getDoktorlarById(x+"");
			DOKTORSEC.removeAllItems();
			for (int i = 0; i < Doktorlar.size(); i++) {
				String[] temp2 = (Doktorlar.get(i));
				DOKTORSEC.addItem(temp2[1] + " " + temp2[2]);
				DOKTORSEC.repaint();
				frame.repaint();

			}
		}
	});
POLIKLINIKSEC.setSelectedIndex(0);
frame.getContentPane().add(DOKTORSEC);


JDateChooser dateChooser = new JDateChooser();
dateChooser.setBounds(144, 152, 145, 19);
frame.getContentPane().add(dateChooser);
String[] str = {"8.00","8.30","9.00","9.30","10.00","10.30","11.00","11.30","12.00","12.30","13.00","13.30","14.00","14.30","15.00",
		"15.30","16.00","16.30"};
JComboBox timeBox = new JComboBox(str);
	timeBox.setBounds(144, 177, 145, 21);



frame.getContentPane().add(timeBox);

frame.getContentPane().add(timeBox);


	HASTAEKLE hs = new HASTAEKLE(conn);
	hs.setVisible(true);
	hs.setBounds(40,80,200,200);
	



JButton KAYDET_BUTON = new JButton("KAYDET");
KAYDET_BUTON.addMouseListener(new MouseAdapter() {
	@Override

	public void mouseClicked(MouseEvent e) {
		String a = TC_TEXTFIELD.getText().toString();
		String [] b = dbc.getHasta(a);
		String c = b[3];
		System.out.println(b[0] + "  " + b[1]);
		if (a.compareTo(c) != 0){ // BOYLE BIR HASTA YOKSA
			JOptionPane.showMessageDialog(null, "HASTA KAYDI YOK, HASTA KAYIT EKRANI ACILIYOR.");
			JFrame hastaekleWindow = new JFrame("Yeni Hasta");
			hastaekleWindow.setContentPane(hs);
			hastaekleWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			hastaekleWindow.pack();
			hastaekleWindow.setVisible(true);
			hastaekleWindow.setSize(300, 400);
			hastaekleWindow.setLocationRelativeTo(null);
		}
		else {
			
			java.sql.Date randevuDate = new java.sql.Date(dateChooser.getDate().getTime());
			int x = POLIKLINIKSEC.getSelectedIndex();
			x = x+1;
			int y = DOKTORSEC.getSelectedIndex();
			y = y+1; 


			Boolean checkRandevu = dbc.findNewRandevu(randevuDate.toString() , timeBox.getSelectedItem().toString(), y+"", x+"", b[0]);
	        if(checkRandevu){
	            System.out.println("Randevu bulundu, kaydedildi.");
				JOptionPane.showMessageDialog(null, "RANDEVU KAYDEDILDI.");

	        }else{
	            String[] alternativeRandevu = dbc.getAlternativeRandevu(y+"");
	        	ALTERNATIFRANDEVU re = new ALTERNATIFRANDEVU(conn,alternativeRandevu[0],alternativeRandevu[1]);
				re.setVisible(true);
				re.setBounds(40,80,200,200);// Alternatif tarihte 0. g�n buluyor. Var olmayan tarihe alternatif buluyor.
	            JFrame alternatifRandevuWindow = new JFrame("Alternatif Randevu");
	            alternatifRandevuWindow.setContentPane(re);
	            alternatifRandevuWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	            alternatifRandevuWindow.pack();
	            alternatifRandevuWindow.setVisible(true);
	            alternatifRandevuWindow.setSize(300, 300);
	            alternatifRandevuWindow.setLocationRelativeTo(null);
	        } 
			
		}
	}
});
KAYDET_BUTON.setBounds(144, 245, 96, 21);
frame.getContentPane().add(KAYDET_BUTON);


}
}