import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {
    DatabaseConnection dbc = new DatabaseConnection();

    private MockData data = new MockData();

    /*
    Projede 3 farklı test yazılmıştır. Testler yeni hasta ekleme, hasta bilgilerini veritabanından çekme,
    doktor bilgilerini çekme, poliklinik bilgilerini çekme fonksiyonlarının sınanması için yazılmıştır. Bu sayede
    girilen tarih bilgilerinin formatı, tckimlik numarasının formatı, ad soyad bilgilerinin formatı,
    veritabanından çekilen poliklinik sayısının kontrolü gibi işlemler test edilebilmiştir.

     */


    @Test
    void addNewHasta() {
        ArrayList<String[]> hastaList = data.newHastaList; //İlk 2 true, kalanı false.
        for (int i = 0; i < hastaList.size() ; i++) {
            String[] hasta = hastaList.get(i);
            if(i<2){
                assertTrue(dbc.addNewHasta(hasta[0], hasta[1], hasta[2], hasta[3]), i + ". dizi testi gecemedi.");
            }else{
                assertFalse(dbc.addNewHasta(hasta[0], hasta[1], hasta[2], hasta[3]), i + ". dizi testi gecemedi.");
            }
        }
    }

    @Test
    void getHasta() {

        ArrayList<String> hastaList = data.getHastaList;
        for (int i = 0; i < hastaList.size() ; i++) {  //İlk 3 true, kalanı false.
            if(i<3){
                assertFalse(dbc.getHasta(hastaList.get(i))[0].equals(""), i + ". dizi testi gecemedi.");
            }else{
                assertTrue(dbc.getHasta(hastaList.get(i))[0].equals(""), i + ". dizi testi gecemedi.");
            }
        }

    }

    @Test
    void getDoktorlarById() {

        ArrayList<String[]> doktorList = data.getDoktorlarByIdList;

        for (int i = 0; i < doktorList.size() ; i++) {
            String[] doktorlar = doktorList.get(i);
            int index = i + 1;
            if(i ==0){
                ArrayList<String[]> lst = dbc.getDoktorlarById("" + index);
                assertEquals("1", lst.get(0)[0], i + ". dizi testi gecemedi.");
                assertEquals("11", lst.get(1)[0], i + ". dizi testi gecemedi.");

            }else if(i == 1){
                ArrayList<String[]> lst = dbc.getDoktorlarById("" + index);
                assertEquals("2", lst.get(0)[0], i + ". dizi testi gecemedi.");
            }
            else if(i == 2){
                ArrayList<String[]> lst = dbc.getDoktorlarById("" + index);
                assertEquals("3", lst.get(0)[0], i + ". dizi testi gecemedi.");
            }
            else if(i == 3){
                ArrayList<String[]> lst = dbc.getDoktorlarById("" + index);
                assertEquals("4", lst.get(0)[0], i + ". dizi testi gecemedi.");
            }
            else if(i == 4){
                ArrayList<String[]> lst = dbc.getDoktorlarById("" + index);
                assertEquals("5", lst.get(0)[0], i + ". dizi testi gecemedi.");
            }
        }


    }


    @Test
    void getPoliklinikler() {
        int counter = dbc.getPoliklinikler().size();
        assertTrue(counter > 0 ,"Testi gecemedi.");
    }







    private class MockData{
        ArrayList<String[]> newHastaList;
        ArrayList<String> getHastaList;
        ArrayList<String[]> getDoktorlarByIdList;
        public MockData(){
            newHastaList = new ArrayList<>();
            getHastaList = new ArrayList<>();
            getDoktorlarByIdList = new ArrayList<>();

            newHastaList.add(new String[]{"Ali", "Veli", "100000", "2020-05-30"});
            newHastaList.add(new String[]{"Selami", "Veli", "150000", "2020-11-30"});
            newHastaList.add(new String[]{"Ali", "V", "100000", "2020-05-30"});
            newHastaList.add(new String[]{"Ali", "Veli", "10", "2020-05-30"});
            newHastaList.add(new String[]{"A", "Veli", "100", "2020-05-30"});
            newHastaList.add(new String[]{"Ali", "Veli", "100000", "xxxx-05-30"});
            newHastaList.add(new String[]{"Ali", "Veli", "100000", "2020-yy-30"});
            newHastaList.add(new String[]{"Ali", "Veli", "10x000", "2020-05-z"});

            getHastaList.add("200000");
            getHastaList.add("200001");
            getHastaList.add("200049");
            getHastaList.add("xxxxx");
            getHastaList.add("1xx000");
            getHastaList.add("00");
            getHastaList.add("100");

            getDoktorlarByIdList.add(new String[]{"1", "11"});
            getDoktorlarByIdList.add(new String[]{"2"});
            getDoktorlarByIdList.add(new String[]{"3"});
            getDoktorlarByIdList.add(new String[]{"4"});
            getDoktorlarByIdList.add(new String[]{"5"});

        }
    }



}