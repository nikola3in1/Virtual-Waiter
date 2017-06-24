package Client;

import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Sto extends Button {
    int brStola = -1;
    int racun;
    boolean initPorudzbina = false;
    HashMap<String, Integer> porudzbina;
    static ArrayList<Integer> x;
    static ArrayList<Integer> y;

    public Sto() {

        getStylesheets().add(getClass().getResource("./button.css").toExternalForm());
    }

    public Sto(String text) {
        super(text);
        getStylesheets().add(getClass().getResource("./button.css").toExternalForm());
    }

    public Sto(String text, Node graphic) {
        super(text, graphic);
        getStylesheets().add(getClass().getResource("Client/button.css").toExternalForm());
    }

    public static int getX(int i) {
        return x.get(i);
    }

    public static int getY(int i) {
        return y.get(i);
    }

    public static void init() {
        x = new ArrayList<>(50);
        y = new ArrayList<>(50);
        x.add(12);
        x.add(73);
        x.add(184);
        x.add(12);
        x.add(70);
        x.add(184);
        x.add(12);
        x.add(73);
        x.add(182);
        x.add(12);
        x.add(67);
        x.add(178);
        x.add(12);
        x.add(85);
        x.add(142);
        x.add(12);
        x.add(85);
        x.add(142);
        x.add(12);
        x.add(85);
        x.add(142);
        x.add(40);
        x.add(99);
        x.add(169);
        x.add(364);
        x.add(419);
        x.add(364);
        x.add(419);
        x.add(364);
        x.add(419);
        x.add(562);
        x.add(576);
        x.add(633);
        x.add(576);
        x.add(633);
        x.add(576);
        x.add(633);
        x.add(759);//38
        x.add(759);
        x.add(813);
        x.add(813);
        x.add(932);
        x.add(269);
        x.add(402);
        x.add(266);
        x.add(399);

        y.add(41);
        y.add(44);
        y.add(59);
        y.add(105);
        y.add(112);
        y.add(126);
        y.add(209);
        y.add(204);
        y.add(204);
        y.add(280);
        y.add(274);
        y.add(275);
        y.add(455);
        y.add(442);
        y.add(442);
        y.add(530);
        y.add(511);
        y.add(511);
        y.add(603);
        y.add(586);
        y.add(586);
        y.add(656);
        y.add(656);
        y.add(656);
        y.add(479);
        y.add(477);
        y.add(551);
        y.add(551);
        y.add(629);
        y.add(629);
        y.add(475);
        y.add(529);
        y.add(529);
        y.add(584);
        y.add(584);
        y.add(640);
        y.add(640);
        y.add(455);//445
        y.add(505);
        y.add(431);
        y.add(551);
        y.add(536);
        y.add(50);
        y.add(50);
        y.add(211);
        y.add(211);


    }

    public HashMap<String, Integer> getPorudzbina() {
        return porudzbina;
    }

    public boolean isInitPorudzbina() {
        return initPorudzbina;
    }

    public boolean dodajPorudzbinu(String proizvod, Integer kvantitet) {
        Map.Entry temp = Magacin.dostupan(proizvod, kvantitet);
        if (temp != null) { // dostupan
            int tempSmanjiKvantitet = Magacin.smanjiKvanititet(temp, kvantitet);
            if (tempSmanjiKvantitet == 0) {
                upisiPorudzbinu(proizvod, kvantitet);
                return false;
            } else if (tempSmanjiKvantitet > 0) {
                upisiPorudzbinu(proizvod, kvantitet);
            }
        } else if (Magacin.dostupan(proizvod, 1) != null) {
            System.out.println("Nije dostupan u toj kolicini");
            return false;
        }
        return true;
    }



    void upisiPorudzbinu(String proizvod, Integer kvantitet) {
        if (initPorudzbina) {
            if (porudzbina.containsKey(proizvod)) {
                for (Map.Entry<String, Integer> entry : porudzbina.entrySet()) {
                    if (entry.getKey().compareTo(proizvod) == 0) {
                        entry.setValue(entry.getValue() + kvantitet);
                    }
                }
            } else {
                porudzbina.put(proizvod, kvantitet);
            }
        } else {
            porudzbina = new HashMap<String, Integer>();
            initPorudzbina = true;
            if (porudzbina.containsKey(proizvod)) {
                for (Map.Entry<String, Integer> entry : porudzbina.entrySet()) {
                    if (entry.getKey().compareTo(proizvod) == 0) {
                        entry.setValue(entry.getValue() + kvantitet);
                    }
                }
            } else {
                porudzbina.put(proizvod, kvantitet);
            }
        }
    }

    int ispraviPorudzbinu(String proizvod) {

        for (Map.Entry<String, Integer> entry : porudzbina.entrySet()) {
            if (entry.getKey().compareTo(proizvod) == 0) {
                if (entry.getValue() > 0) {
                    entry.setValue(entry.getValue() - 1);
                    Magacin.povecajKvantitet(entry.getKey());
                    if (entry.getValue() == 0) {
                        //Magacin.povecajKvantitet(entry,entry.getValue()+1);
                        porudzbina.entrySet().remove(entry);
                    }
                    return entry.getValue();
                } else if (entry.getValue() < 1) {
                    Magacin.povecajKvantitet(entry.getKey());
                    porudzbina.entrySet().remove(entry);
                }
            }
        }
        return -1;
    }

    int naplatiDin() {
        int cena = 0;
        for (Map.Entry<String, Integer> entry : porudzbina.entrySet()) {
            cena += Magacin.proizvodi.get(entry.getKey()) * entry.getValue();

        }
        Pazar.proknjizi(porudzbina, cena);
        porudzbina.clear();
        System.out.println(cena);
        return cena;
    }

    double naplatiEur() {
        double kurs= Evro.getKurs();
        System.out.println(kurs);

        int cena = 0;
        for (Map.Entry<String, Integer> entry : porudzbina.entrySet()) {
            cena += Magacin.proizvodi.get(entry.getKey()) * entry.getValue();
        }
        Pazar.proknjizi(porudzbina, cena);
        porudzbina.clear();
        return cena / kurs;
    }

}
