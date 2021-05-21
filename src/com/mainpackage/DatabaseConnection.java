package com.mainpackage;

import javax.xml.crypto.Data;
import java.sql.*;

public class DatabaseConnection {


    private static Connection con;
    DatabaseWorks dbw;

    public DatabaseConnection() {
        dbw = new DatabaseWorks();
    }

    public Connection getConnection() {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "password1");
            System.out.println("Baglanti basarili!");
        } catch (SQLException e) {
            System.out.println("Exception, Mysql baglantisi basarisiz.");
            System.out.println(e);
        }
        return con;
    }


    public class DatabaseWorks{

        public void addNewPatient(){
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(String.format("SELECT * from ogrenci where tcno = '%s'", "11111111"));
            } catch (SQLException e) {
                System.out.println(e);
                //warningText.setText("Hata oluştu.");
                //warningText.setVisible(true); //Eklenememe hatasi}
            }
        }
        public void getDeneme(){
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(String.format("select * from sakila.actor where last_name > \"a\" and last_name < \"c\""));
                while (rs.next()) {
                    String tempStr = rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3);
                    System.out.println(tempStr);
                }

            } catch (SQLException e) {
                System.out.println(e);
                //warningText.setText("Hata oluştu.");
                //warningText.setVisible(true); //Eklenememe hatasi}
            }
        }

    }


}
