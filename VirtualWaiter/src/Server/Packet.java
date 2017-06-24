package Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nikola3in1 on 5.6.17..
 */
public class Packet implements Serializable {
    public String getProizvodObj() {
        return proizvodObj;
    }
    ArrayList<String> proizvodi;
    ArrayList<Integer> cena;
    ArrayList<Integer> kolicina;
    public String imeNovogProizvoda;
    public int cenaNovogProizvoda, kolicinaNovogProizvoda;
   public boolean povuciProizvode;
    public boolean dodajProizvod;
    public boolean upisiPazar;
    public int pazar;
    HashMap<String,Integer> prodataRoba;

    public void setProdataRoba(HashMap<String, Integer> prodataRoba) {
        this.prodataRoba = prodataRoba;
    }

    public void setProizvodObj(String proizvodObj) {
        this.proizvodObj = proizvodObj;
    }

    public Integer getCenaObj() {
        return cenaObj;
    }

    public void setCenaObj(Integer cenaObj) {
        this.cenaObj = cenaObj;
    }

    public Integer getKolicinaObj() {
        return kolicinaObj;
    }

    public void setKolicinaObj(Integer kolicinaObj) {
        this.kolicinaObj = kolicinaObj;
    }

    private String proizvodObj;
    private Integer cenaObj,kolicinaObj;

    public ArrayList<String> getProizvodi() {
        return proizvodi;
    }

    public void setProizvodi(ArrayList<String> proizvodi) {
        this.proizvodi = proizvodi;
    }

    public ArrayList<Integer> getCena() {
        return cena;
    }

    public void setCena(ArrayList<Integer> cena) {
        this.cena = cena;
    }

    public ArrayList<Integer> getKolicina() {
        return kolicina;
    }

    public void setKolicina(ArrayList<Integer> kolicina) {
        this.kolicina = kolicina;
    }




}
