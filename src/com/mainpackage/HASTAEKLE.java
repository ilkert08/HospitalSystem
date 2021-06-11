import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import com.toedter.calendar.JDateChooser;

public class HASTAEKLE extends JPanel {

    private  JPanel contentPane;
    private JLabel showTicLabel;

    private DatabaseConnection conn;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_4;

    public HASTAEKLE(DatabaseConnection conn) {
        DatabaseConnection dbc = conn;
        setBounds(100, 100, 334, 416);
        contentPane = this;
        contentPane.setBackground(Color.LIGHT_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        showTicLabel = new JLabel("HASTA KAYIT");
        showTicLabel.setIcon(new ImageIcon(""));
        showTicLabel.setFont(new Font("Klee", Font.PLAIN, 20));
        showTicLabel.setBounds(6, 39, 192, 71);
        contentPane.add(showTicLabel);


        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon(""));
        lblNewLabel_1.setBounds(399, 0, 100, 218);
        contentPane.add(lblNewLabel_1);
        JLabel lblNewLabel_2 = new JLabel("HASTA ADI");
        lblNewLabel_2.setBounds(6, 103, 79, 22);
        contentPane.add(lblNewLabel_2);
        textField = new JTextField();
        textField.setBounds(102, 105, 96, 19);
        contentPane.add(textField);
        textField.setColumns(10);
        textField_1 = new JTextField();
        textField_1.setBounds(102, 137, 96, 19);
        contentPane.add(textField_1);
        textField_1.setColumns(10);
        JLabel lblNewLabel_2_1 = new JLabel("HASTA SOYADI");
        lblNewLabel_2_1.setBounds(6, 135, 79, 22);
        contentPane.add(lblNewLabel_2_1);
        JLabel lblNewLabel_2_4 = new JLabel("TC NO");
        lblNewLabel_2_4.setBounds(6, 164, 79, 22);
        contentPane.add(lblNewLabel_2_4);
        textField_4 = new JTextField();
        textField_4.setBounds(102, 169, 96, 19);
        contentPane.add(textField_4);
        textField_4.setColumns(10);


        JLabel lblDoktorId = new JLabel("DOGUM TARIHI");
        lblDoktorId.setBounds(6, 196, 79, 21);
        contentPane.add(lblDoktorId);
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setBounds(102, 198, 96, 19);
        contentPane.add(dateChooser);
        JButton btnNewButton = new JButton("KAYDET");

        JPanel thisPanel = this;
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                java.sql.Date birthDate = new java.sql.Date(dateChooser.getDate().getTime());
                String [] kontrol = dbc.getHasta(textField_4.getText().toString());
                if (kontrol[0] == "" ) {
                    Boolean check = dbc.addNewHasta(textField.getText().toString(), textField_1.getText().toString(), textField_4.getText().toString(),birthDate.toString() );

                    if(!check){
                        JOptionPane.showMessageDialog(null, "Hasta bilgilerinde hata var.");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "HASTA KAYDI EKLENDI");
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(thisPanel);
                        frame.dispose();
                    }
                }


            }
        });
        btnNewButton.setBounds(102, 249, 96, 21);
        contentPane.add(btnNewButton);
    }






    


        //ArrayList<String[]> poliklinikler= dbc.dbw.getPoliklinikler(); //T�m poliklinikler.
        //dbc.dbw.getDoktorlarById(poliklinikler.get(1)[0]); // 1 id'li poliklinikteki doktorlar.
        //dbc.dbw.getHasta("200001");
        //dbc.dbw.addNewHasta("Yusuf", "Ziya", "200050", "1996-5-12");

        /*
        Boolean checkRandevu = dbc.dbw.findNewRandevu("2021-05-27", "11.30", "1", "1", "1");
        if(checkRandevu){
            System.out.println("Randevu bulundu, kaydedildi.");
        }else{
            String[] alternativeRandevu = dbc.dbw.getAlternativeRandevu("1");
        } */


        //dbc.dbw.getHasta("200050");
        //dbc.dbw.addNewHasta("Salih", "U�an", "200049", "1980-04-24");



        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //dbc.dbw.getPoliklinikler(); //ilk bu �a�r�lacak. poliklinikler a��l�r listeye kaydedilecek. Arraylist doner.
        // Bunlar a��l�r listede g�sterilecek.

        //dbc.dbw.getDoktorlarById("1"); //Se�ilen poliklinikteki doktorlar� poliklinikId'ye gore dondurur. Arraylist doner.
        // Bunlar a��l�r listede g�sterilecek.


        // dbc.dbw.getHasta("11111111");//Randevu kaydetme ekran�nda kaydete bas�nca ilk olarak hastan�n kayd� kontrol edilir.
        //Bu method tc kimlik numaras�na g�re hasta bilgilerini ceker ve bir ARRAY dondurur. Hasta id'sini iceren ARRAY[0] = "" ise
        //Bu hasta sistemde kay�tl� de�il demektir. Bu durumda hasta kay�t penceresi a��l�r ve hasta kay�t edilir.

        //dbc.dbw.addNewHasta("ad", "soyad", "tckimlikno", "dogum tarihi"); //Hasta kay�t penceresi a��l�p bilgiler girildikten sonra
        //Bu fonksiyon �a��r�l�r ve i�ine parametreler verilir. Fonksiyon i�inde gelen parametrelere g�re hastay� veritaban�na kaydeder.


        //dbc.dbw.findNewRandevu("2021-05-27", "8.30", "1", "1", "1"); //Verilen tarihteki randevunun uygun olup olmad���n� kontrol eder.
        //E�er randevu uygunsa randevuyu veritaban�na kaydeder ve TRUE dondurur. Bu durumda program sona erdirilebilir.
        //E�er uygun randevu yoksa FALSE dondurur. bu durumda veritaban�na bir �ey kaydedilmez.
        // Alternatif randevu i�in getAlternativeRandevu() methodu �a��r�lmal�d�r.

        //dbc.dbw.getAlternativeRandevu("doctorid");  Bulunan alternatif randevu bilgilerini(tarih ve saat) i�eren bir String dizisi d�nd�r�r.
        // E�er 20 g�n i�inde alternatif randevu bulunamazsa String dizisi ["", ""] �eklinde iki adet bo� string d�nd�r�r.
        //Bo� string donmesi durumunda randevu bulunamad���na dair mesaj g�sterilip program sonland�r�labilir.
        //E�er fonksiyon ge�erli bir  tarih ve saat d�nd�r�rse d�nd�r�len yeni tarih ve saat aray�zde g�revli taraf�ndan yeniden girilir.
        //Yeni tarih ve saat girildikten sonra art�k ge�erli randevu tarihi girildi�i i�in randevu sisteme kay�t edilecektir.


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //dbc.dbw.getRandevular(); //Uygun randevular� bulmak i�in di�er fonksiyonlar i�erisinde kullan�l�r. Aray�zde kullan�m� yok.

        //dbc.dbw.addNewRandevu(); //Uygun randevunun kaydedildi�i fonksiyon. Di�er fonksiyonlar i�erisinde kaydedici olarak kullan�l�r.
        //Aray�zde bir kullan�m� yok.


        //dbc.dbw.getDoktorlar(); //T�m doktorlar�n bilgilerinin bulundu�u bir arraylist d�nd�r�r. �u anda bir kullan�m� yok.

        //dbc.dbw.getPoliklinik("poliklinikid"); //Poliklinik id'ye gore poliklini�in ad�n�(bran��n�) d�nd�r�r. �u anda bir kullan�m� yok.

        //dbc.dbw.getDoktor("Tc kimlik no"); //Tc kimlik no'ya g�re tek bir doktoru geri d�nd�r�r. �u anda bir kullan�m� yok.

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}