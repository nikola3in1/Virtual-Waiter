package Client;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
public class Evro implements Runnable {
    private Document doc;
    private Elements tekst;
    public static double kurs;

    @Override
    public void run() {
        try {
            doc = Jsoup.connect("http://www.nbs.rs/static/nbs_site/gen/cirilica/30/kurs/IndikativniKurs.htm").get();
            dodaj();
        } catch (IOException ex) {
            System.out.println("Greska!");
        }
    }

    public static double getKurs() {
        return kurs;
    }

    public void dodaj() {
        tekst = doc.select("body > table > tbody > tr:nth-child(3) >td:nth-child(1)");
        kurs = Double.parseDouble(tekst.text().replace(",", "."));
    }

}
