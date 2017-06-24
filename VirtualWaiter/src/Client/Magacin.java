package Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class Magacin {
    ///Mapa proizvoda key ImeProizvoda, value Cena
    static HashMap<Map.Entry,Integer> stanje = new HashMap<>();
    static HashMap<String,Integer> proizvodi = new HashMap<>();
    static boolean ucitani = false;

    public Magacin() {
    }

    public static void ucitajProizvode(ArrayList proizvodiLista, ArrayList cene, ArrayList kolicine){
        HashMap<String,Integer> tempProizvodi= new HashMap<String,Integer>();
        HashMap<String,Integer> tempKolicine = new HashMap<String,Integer>();

        for (int i = 0; i< proizvodiLista.size() ; i++){
            tempProizvodi.put(proizvodiLista.get(i).toString(),Integer.parseInt(cene.get(i).toString()));
            tempKolicine.put(proizvodiLista.get(i).toString(),Integer.parseInt(kolicine.get(i).toString()));
        }




            for (Map.Entry<String, Integer> entry : tempProizvodi.entrySet()) {
                for (Map.Entry<String, Integer> entry1 : tempKolicine.entrySet()){
                    if(entry.getKey().compareTo(entry1.getKey())==0){
                        stanje.put(entry,entry1.getValue());
                    }
                }
            }
        proizvodi=tempProizvodi;
    }

    static ObservableList<String> dostupniProizvodi(){
        ArrayList<String> lista= new ArrayList<>();
        for (Map.Entry<Map.Entry, Integer> entry : stanje.entrySet()) {
            Map.Entry pair = entry.getKey();
            String ime = pair.getKey().toString();
            String cena = pair.getValue().toString();
            Integer kvantitet = entry.getValue();

            if(kvantitet >0){
                lista.add(ime);
            }
        }
        Collections.sort(lista);
        ObservableList<String> oLista = FXCollections.observableArrayList(lista);
        return oLista;

    }

    static Map.Entry dostupan(String naziv, Integer kvantitet){
        for (Map.Entry<Map.Entry, Integer> entry : stanje.entrySet()) {
            Map.Entry pair = entry.getKey();
            String ime = pair.getKey().toString();
            Integer stanjeKvant = entry.getValue();

            if(ime.compareTo(naziv) == 0){
                if(stanjeKvant-kvantitet >=0){
                    System.out.println("novo stanje "+(stanjeKvant-kvantitet));
                    return entry;
                }
            }
        }
        return null;
    }
    static int smanjiKvanititet(Map.Entry<Map.Entry,Integer> entry , int kvantitet){
        entry.setValue(entry.getValue()-kvantitet);
        return entry.getValue();
    }
    static void povecajKvantitet(String nazivProizvoda){
        for(Iterator<Map.Entry<Map.Entry, Integer>> it = stanje.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Map.Entry, Integer> entry = it.next();
            Map.Entry pair = entry.getKey();
            String ime = pair.getKey().toString();
            Integer stanjeKvant = entry.getValue();
            if(ime.compareTo(nazivProizvoda)==0){
                entry.setValue(stanjeKvant+1);
            }
        }
    }

}
