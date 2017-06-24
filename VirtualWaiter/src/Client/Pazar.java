package Client;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Pazar {
    public static HashMap<String, Integer> getProdataRoba() {
        return prodataRoba;
    }

    public static int getPazarDin() {
        return pazarDin;
    }

    static HashMap<String,Integer> prodataRoba;
    static int pazarDin;
    static int pazarEur;
    static boolean pazarInit =false;
    static void proknjizi(Map<String ,Integer> prodatiProizvodi, int vrednost){
        System.out.println("PAZAR:");
        if(pazarInit){
            prodataRoba.putAll(prodatiProizvodi);
            pazarDin+=vrednost;
        }else{
            init();
            prodataRoba.putAll(prodatiProizvodi);
            pazarDin+=vrednost;
        }
        for (Map.Entry<String, Integer >entry: prodataRoba.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
        System.out.println("ZARADA:"+pazarDin);
    }
    static void init(){
        prodataRoba=new HashMap<String,Integer>();
        pazarInit = true;
    }
    static void stampaj(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date datum = new Date();
        String fileName=dateFormat.format(datum)+".txt";
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
            writer.write(dateFormat.format(datum)+" ");
            writer.write("Zarada:"+pazarDin+"\n");
            for (Map.Entry<String, Integer> entry : prodataRoba.entrySet()) {
                writer.write(entry.getKey()+ " kolicina:" + entry.getValue()+"\n");
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
