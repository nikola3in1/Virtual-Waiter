package Server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Baza {
    private static java.sql.Connection con = null;
    private static String url = "jdbc:mysql://localhost:3306/Restoran";
    private static String user = "root";
    private static String pass = "n14031997";
    private static String query = "";

    public static void connect()
    {
        System.out.println("connect");
        try
        {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex)
        {
            Logger.getLogger(Baza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void disconnect()
    {
        try
        {
            con.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(Baza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static void dodajProizvod(String proizvod, int cena , int kolicina){
        String temp = "'"+proizvod+"'";
        try
        {
                Statement st = con.createStatement();
                query = "SELECT ime_proizvoda, cena, kolicina " +
                        "FROM `Proizvodi` WHERE ime_proizvoda=" + temp;
                ResultSet rs = st.executeQuery(query);

                if(rs.next()){
                    Statement st1 = (Statement) con.createStatement();
                    st.execute("UPDATE Proizvodi SET kolicina = kolicina +"+kolicina);
                    st1.close();
                    con.close();
                }else{
                    Statement st1 = (Statement) con.createStatement();
                st.execute("INSERT INTO Proizvodi (ime_proizvoda,cena,kolicina) "
                        + "" + "VALUES ('" + proizvod
                        +"','" + cena+ "','" + kolicina+"')");
                st1.close();
                con.close();
                }
    } catch (SQLException e) {
            e.printStackTrace();
        }}

    public static void upisiPazar(int zarada, HashMap<String,Integer> prodatiProizvodi){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date datum = new Date();
        try
        {
            Statement st = (Statement) con.createStatement();
            st.execute("INSERT INTO Popis (datum,pazar) "
                    + "" + "VALUES ('"+ dateFormat.format(datum)+ "','" + zarada+"')");
            st.close();

            for (Map.Entry<String, Integer> entry : prodatiProizvodi.entrySet()) {
                Statement st1 = (Statement) con.createStatement();
                st1.execute("INSERT INTO Prodati_proizvodi (datum_prodaje,ime_proizvoda,kolicina) "+ "VALUES ('"
                        + dateFormat.format(datum)+ "','" + entry.getKey()+ "','" + entry.getValue()+"')");
                st1.close();
            }
            con.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Packet ucitajIzBaze(){
        Packet temp = new Packet();
        ArrayList<String> proizvodi= new ArrayList<>();
        ArrayList<Integer> cene= new ArrayList<>();
        ArrayList<Integer> kolicine= new ArrayList<>();
        try
        {
            Statement st = con.createStatement();
            query = "SELECT ime_proizvoda, cena, kolicina " +
                    "FROM `Proizvodi`";
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                String proizvod= rs.getString("ime_proizvoda");
                Integer cena = rs.getInt("cena");
                Integer kolicina= rs.getInt("kolicina");
                proizvodi.add(proizvod);
                cene.add(cena);
                kolicine.add(kolicina);
            }
            temp.setCena(cene);
            temp.setKolicina(kolicine);
            temp.setProizvodi(proizvodi);
            } catch (SQLException ex)
        {
            Logger.getLogger(Baza.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }
}
