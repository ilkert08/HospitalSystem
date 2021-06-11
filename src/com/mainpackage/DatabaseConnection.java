import com.mysql.cj.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {


    private static Connection con;
    public DatabaseConnection() {
        getConnection();
    }

    public Connection getConnection() {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalschema?verifyServerCertificate=false&useSSL=false&serverTimezone=UTC", "root", "password1");
            //System.out.println("Baglanti basarili!");
        } catch (SQLException e) {
            System.out.println("Exception, Mysql baglantisi basarisiz.");
            System.out.println(e);
        }
        return con;
    }


    public boolean addNewHasta(String ad, String soyad, String tckimlik, String dogumtarihi){

        try{
            int num = Integer.parseInt(tckimlik); //Tckimlik numeric mi kontrolu.
        } catch (NumberFormatException e) {
            return false;
        }

        if(ad.length() < 2 || soyad.length() < 2 || Integer.parseInt(tckimlik) < Math.pow(10, 5)){
            return false;
        }


        String[] splittedDate = dogumtarihi.split("-");

        for (int i = 0; i < splittedDate.length; i++) {
            try{
                int num = Integer.parseInt(splittedDate[i]); //Tarih numeric mi kontrolu.
            } catch (NumberFormatException e) {
                return false;
            }
        }

        if(Integer.parseInt(splittedDate[0]) < Math.pow(10, 3) ||
                Integer.parseInt(splittedDate[1]) < 1 ||
                Integer.parseInt(splittedDate[1]) > 12 ||
                Integer.parseInt(splittedDate[2]) < 1 ||
                Integer.parseInt(splittedDate[2]) > 31){
            return  false;
        }



        try {
            Statement stmt = con.createStatement();
            String sqlStatement = String.format("insert into hospitalschema.hastalar(ad, soyad, tckimlikno, dogumtarihi)" +
                    " values('%s', '%s', '%s', \"%s\");", ad, soyad, tckimlik, dogumtarihi);
            System.out.println(sqlStatement);
            stmt.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }


    public String[] getHasta(String tcKimlikNo){

        String hastaArr[] = new String[5];
        hastaArr[0] = "";
        hastaArr[1] = "";
        hastaArr[2] = "";
        hastaArr[3] = "";
        hastaArr[4] = "";

        try{
            int num = Integer.parseInt(tcKimlikNo); //Tckimlik numeric mi kontrolu.
        } catch (NumberFormatException e) {
            return hastaArr;
        }

        if(Integer.parseInt(tcKimlikNo) < Math.pow(10, 5)){
            return hastaArr;
        }



        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("select * from hospitalschema.hastalar where tckimlikno = %s", tcKimlikNo));
            while (rs.next()) {
                String tempStr = rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) +
                        " " + rs.getString(4) + " " + rs.getString(5);
                //System.out.println(tempStr);

            hastaArr[0] = rs.getString(1);
            hastaArr[1] = rs.getString(2);
            hastaArr[2] = rs.getString(3);
            hastaArr[3] = rs.getString(4);
            hastaArr[4] = rs.getString(5);


            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        for (int i = 0; i < hastaArr.length; i++) {
           // System.out.println(hastaArr[i]);
        }

        return hastaArr;
    }



    public ArrayList<String>[] getRandevular(){
        ArrayList<String>[] lists = new ArrayList[3];
        ArrayList<String> tarihler = new ArrayList<>();
        ArrayList<String> saatler = new ArrayList<>();
        ArrayList<String> doktorlar = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("select * from hospitalschema.randevular"));
            while (rs.next()) {
                String tempStr = rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3);
                tarihler.add(rs.getString(2));
                saatler.add(rs.getString(3));
                doktorlar.add(rs.getString(4));
                //System.out.println(tempStr);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        lists[0] = tarihler;
        lists[1] = saatler;
        lists[2] = doktorlar;
        return (lists);
    }


    public void addNewRandevu(String date, String time, String doktorid, String poliklinikid, String hastaid){
        try {
            Statement stmt = con.createStatement();
            String sqlStatement = String.format("insert into hospitalschema.randevular(tarih, saat, doktorid, poliklinikid, hastaid)" +
                    " values('%s', \"%s\", %s, %s, %s);", date, time, doktorid, poliklinikid, hastaid);
            //System.out.println(sqlStatement);
            stmt.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }




    public Boolean findNewRandevu(String requestedDate, String requestedTime, String requestedDoctorId, String requestedPoliclinicId, String patientId){
        ArrayList<String>[] randevular = getRandevular();
        ArrayList<String> tarihler = randevular[0];
        ArrayList<String> saatler = randevular[1];
        ArrayList<String> doktorlar = randevular[2];

        Boolean isDateEmpty = true;

        for(int i=0; i < tarihler.size(); i++){
            if(requestedDate.equals(tarihler.get(i)) && requestedTime.equals(saatler.get(i))  && requestedDoctorId.equals(doktorlar.get(i))){
                isDateEmpty = false;
                break;
            }
        }
        if(isDateEmpty){
            System.out.println("Randevu baþarýyla kaydedildi.");
            addNewRandevu(requestedDate, requestedTime, requestedDoctorId, requestedPoliclinicId, patientId); //TODO
        }
        return  isDateEmpty;
    }

    public String[] getAlternativeRandevu(String requestedDoctorId){
        ArrayList<String>[] randevular = getRandevular();
        ArrayList<String> tarihler = randevular[0];
        ArrayList<String> saatler = randevular[1];
        ArrayList<String> doktorlar = randevular[2];
        String alternativeRandevu[] = new String[2];

        alternativeRandevu[0] = "";
        alternativeRandevu[1] = "";


        long millis=System.currentTimeMillis();
        java.sql.Date currentDate = new java.sql.Date(millis);


        String tempTime = "8.00";
        String tempDate = "";
        String tempTime1 = "8.00";
        String tempTime2 = "8.30";

        long tempMillis = millis;
        Boolean loopBreaker = false;
        Boolean isFound = true;
        int counter = 0;
        int dayLimit = 20; //20 gün kadar randevu arar.

        while(true){
            if(counter++ > dayLimit){
                break;
            }

            tempMillis = tempMillis + 24*60*60*1000;
            java.sql.Date nextDate = new java.sql.Date(tempMillis);
            System.out.println();
            System.out.println();
            for (int i = 8; i <= 16; i++) {
                isFound = true;
                tempTime1 = i + ".00";
                tempTime2 = i + ".30";
                for (int j = 0; j < tarihler.size() ; j++) {
                    if(tarihler.get(j).equals(nextDate.toString()) && saatler.get(j).equals(tempTime1) && doktorlar.get(j).equals(requestedDoctorId)){
                        isFound = false;
                        break;
                    }
                }
                if(isFound){
                    tempTime = tempTime1;
                    loopBreaker = true;
                    break;
                }


                isFound = true;
                for (int j = 0; j < tarihler.size() ; j++) {
                    if(tarihler.get(j).equals(nextDate.toString()) && saatler.get(j).equals(tempTime2) && doktorlar.get(j).equals(requestedDoctorId)){
                        isFound = false;
                        break;
                    }
                }
                if(isFound){
                    tempTime = tempTime2;
                    loopBreaker = true;
                    break;
                }
            }
            if(loopBreaker){
                tempDate = nextDate.toString();
                break;
            }
        }

        alternativeRandevu[0] = tempDate;
        alternativeRandevu[1] = tempTime;
        System.out.println("Döndürülen alternatif randevu: " + alternativeRandevu[0] + " - " + alternativeRandevu[1]);
        return  alternativeRandevu;
    }



    public ArrayList<String[]> getPoliklinikler(){
        ArrayList<String[]> poliklinikList = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("select * from hospitalschema.poliklinkler"));
            while (rs.next()) {
                String poliklinikArr[] = new String[2];
                String tempStr = rs.getString(1) + " " + rs.getString(2);
                //System.out.println(tempStr);

                poliklinikArr[0] = rs.getString(1);
                poliklinikArr[1] = rs.getString(2);
                poliklinikList.add(poliklinikArr);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        /*
        for (int i = 0; i < poliklinikList.size(); i++) {
            System.out.println(poliklinikList.get(i)[0]);
            System.out.println(poliklinikList.get(i)[1]);
            System.out.println();
        }*/

        return poliklinikList;
    }

    public String[] getPoliklinik(String poliklinikId){
        String poliklinikArr[] = new String[2];
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("select * from hospitalschema.doktorlar where poliklinkler = %s", poliklinikId));

            while (rs.next()) {
                String tempStr = rs.getString(1) + " " + rs.getString(2);
                //System.out.println(tempStr);

                poliklinikArr[0] = rs.getString(1);
                poliklinikArr[1] = rs.getString(2);

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return poliklinikArr;
    }





    public ArrayList<String[]> getDoktorlar(){
        ArrayList<String[]> doktorList = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("select * from hospitalschema.doktorlar"));
            while (rs.next()) {
                String doktorArr[] = new String[6];
                String tempStr = rs.getString(1) + " " + rs.getString(2) + " " +
                        rs.getString(3)+ " " + rs.getString(4) + " " +
                        rs.getString(5)+ " " + rs.getString(6);
                System.out.println(tempStr);

                doktorArr[0] = rs.getString(1);
                doktorArr[1] = rs.getString(2);
                doktorArr[2] = rs.getString(3);
                doktorArr[3] = rs.getString(4);
                doktorArr[4] = rs.getString(5);
                doktorArr[5] = rs.getString(6);
                doktorList.add(doktorArr);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        /*
        for (int i = 0; i < doktorList.size() ; i++) {
            System.out.println(doktorList.get(i)[0]);
            System.out.println(doktorList.get(i)[1]);
            System.out.println(doktorList.get(i)[2]);
            System.out.println(doktorList.get(i)[3]);
            System.out.println(doktorList.get(i)[4]);
            System.out.println(doktorList.get(i)[5]);
        } */

        return doktorList;
    }

    public ArrayList<String[]> getDoktorlarById(String poliklinikId){
        ArrayList<String[]> doktorList = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("select * from hospitalschema.doktorlar where poliklinikid = %s", poliklinikId));
            while (rs.next()) {
                String doktorArr[] = new String[6];
                String tempStr = rs.getString(1) + " " + rs.getString(2) + " " +
                        rs.getString(3)+ " " + rs.getString(4) + " " +
                        rs.getString(5)+ " " + rs.getString(6);
                System.out.println(tempStr);

                doktorArr[0] = rs.getString(1);
                doktorArr[1] = rs.getString(2);
                doktorArr[2] = rs.getString(3);
                doktorArr[3] = rs.getString(4);
                doktorArr[4] = rs.getString(5);
                doktorArr[5] = rs.getString(6);
                doktorList.add(doktorArr);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        /*
        for (int i = 0; i < doktorList.size() ; i++) {
            System.out.println(doktorList.get(i)[0]);
            System.out.println(doktorList.get(i)[1]);
            System.out.println(doktorList.get(i)[2]);
            System.out.println(doktorList.get(i)[3]);
            System.out.println(doktorList.get(i)[4]);
            System.out.println(doktorList.get(i)[5]);
        } */

        return doktorList;
    }



    public String[] getDoktor(String tcKimlikNo){
        String doktorArr[] = new String[6];
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("select * from hospitalschema.doktorlar where tckimlikno = %s", tcKimlikNo));

            while (rs.next()) {
                String tempStr = rs.getString(1) + " " + rs.getString(2) + " " +
                        rs.getString(3)+ " " + rs.getString(4) + " " +
                        rs.getString(5)+ " " + rs.getString(6);
                System.out.println(tempStr);

                doktorArr[0] = rs.getString(1);
                doktorArr[1] = rs.getString(2);
                doktorArr[2] = rs.getString(3);
                doktorArr[3] = rs.getString(4);
                doktorArr[4] = rs.getString(5);
                doktorArr[5] = rs.getString(6);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return doktorArr;
    }

}
