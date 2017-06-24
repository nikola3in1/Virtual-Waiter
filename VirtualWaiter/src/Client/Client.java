package Client;

import Server.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

public class Client {

    public static void posaljiRequestZaUcitavanje(){
        Socket socket = null;
        ObjectOutputStream outputStream = null;
        boolean isConnected = false;
        while (!isConnected) {
            try {
                socket = new Socket("localHost", 1234);
                System.out.println("Connected");
                    isConnected = true;
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                final Packet packet = new Packet();
                packet.povuciProizvode=true;
                outputStream.writeObject(packet);
                outputStream.close();
                socket.close();
            } catch (SocketException se) {
                se.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void ucitajIzBaze(){
        boolean isConnected = false;
        while (!isConnected) {
        try {
            Socket clientSocket = new Socket("localhost",1235);
            isConnected = true;
            ObjectInputStream inStream = null;
            inStream = new ObjectInputStream(clientSocket.getInputStream());
            Packet packet = (Packet) inStream.readObject();
            Magacin.ucitajProizvode(packet.getProizvodi(),packet.getCena(),packet.getKolicina());
            clientSocket.close();
            break;
        } catch (ConnectException e){
//            ucitajIzBaze();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        }
    }
    public static void dodajToBaza(String naziv, Integer cena, Integer kolicina){
         Socket socket = null;
         ObjectInputStream inputStream = null;
         ObjectOutputStream outputStream = null;
         boolean isConnected = false;
        while (!isConnected) {
            try {
                socket = new Socket("localHost", 1234);
                isConnected = true;
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                final Packet packet = new Packet();
                packet.dodajProizvod=true;
                packet.imeNovogProizvoda = naziv;
                packet.cenaNovogProizvoda = cena;
                packet.kolicinaNovogProizvoda = kolicina;
                outputStream.writeObject(packet);
                socket.close();

            } catch (SocketException se) {
                se.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void upisiPopis(HashMap<String,Integer> proizvodi, Integer zarada){
        Socket socket = null;
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        boolean isConnected = false;
        while (!isConnected) {
            try {
                socket = new Socket("localHost", 1234);
                isConnected = true;
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                final Packet packet = new Packet();
                packet.upisiPazar=true;
                packet.pazar=zarada;
                packet.setProdataRoba(proizvodi);
                outputStream.writeObject(packet);
                socket.close();

            } catch (SocketException se) {
                se.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    }

