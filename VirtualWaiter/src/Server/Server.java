package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        boolean vrati = false;
        boolean dodaj = false;
        ServerSocket serverSocket = null;
        Socket socket = null;
        ObjectInputStream inStream = null;
        try {
            System.out.println("SERVER IS RUNNING:");
            String request;
            while (true) {
                Socket connectionSocket = null;
                if (vrati) {
                    vratiPodatke();
                    vrati = false;
                }

                try {
                    serverSocket = new ServerSocket(1234);
                    socket = serverSocket.accept();
                    inStream = new ObjectInputStream(socket.getInputStream());

                    Packet packet = (Packet) inStream.readObject();

                    if (packet.dodajProizvod) {
                        System.out.println(packet.imeNovogProizvoda);
                        Baza.connect();
                        Baza.dodajProizvod(packet.imeNovogProizvoda, packet.cenaNovogProizvoda, packet.kolicinaNovogProizvoda);
                        Baza.disconnect();
                    } else if (packet.povuciProizvode) {
                        vrati = true;
                    } else if (packet.upisiPazar) {
                        Baza.connect();
                        Baza.upisiPazar(packet.pazar, packet.prodataRoba);
                        Baza.disconnect();
                    }
                    serverSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }


    public static void vratiPodatke() {
        try {
            ServerSocket serverSocket = new ServerSocket(1235);
            Socket connectionSocket = serverSocket.accept();
            DataOutputStream forClient = new DataOutputStream(connectionSocket.getOutputStream());
            final ObjectOutputStream mapOutputStream = new ObjectOutputStream(forClient);
            Baza.connect();
            Packet packet = Baza.ucitajIzBaze();
            mapOutputStream.writeObject(packet);
            Baza.disconnect();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
