package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MuliThreadedServer implements Runnable {
    String host;
    Salon salon;
    Socket csocket;
    ArrayList<Integer> godzinyPrzyjec = null;

    MuliThreadedServer(Socket csocket, Salon salon){
        this.csocket = csocket;
        this.salon = salon;
        this.godzinyPrzyjec = new ArrayList<Integer>();
    }

    public static void main(String[] args) throws IOException {
        ArrayList<PrintWriter> powiadomieniaOdWszystkich = new ArrayList<PrintWriter>();
        Salon salon = new Salon(powiadomieniaOdWszystkich);

        final int portNumber = 6666;
       try {
           ServerSocket ssok = new ServerSocket(portNumber);
           ssok.setReuseAddress(true);
           System.out.println("Serwer działa i czeka na polecenia.");
           while (true) {
               System.out.println("Połączono");
               (new Thread(new MuliThreadedServer(ssok.accept(), salon))).start();
           }
       }catch (BindException e){
           System.out.println("Port jest juz zajety spróbuj inny");
       }
    }

    public void run(){
        BufferedReader in = null;
        PrintWriter out = null;
        String ipClient = "127.0.0.1";
        try {
            in = new BufferedReader(new InputStreamReader(csocket.getInputStream()));

            int clientPort = Integer.parseInt(in.readLine());
            System.out.println("Incoming connection! Ip: " + ipClient + " Port to bind back: " + clientPort);
            Socket toClient = new Socket(ipClient, clientPort);
            out = new PrintWriter(toClient.getOutputStream(), true);


            salon.newWriter(out);
            salon.newIndex(godzinyPrzyjec);
            salon.wyslijDoMnie(out);
            String line;
            while (true){
                if((line = in.readLine()) != null){
                    if(line.equals("odwolaj")){
                        out.println("Ktora godzine chcesz odwolac?");
                        if ((line = in.readLine()) != null){
                            try{
                                int godzinaDoOdwolania = Integer.parseInt(line);
                                boolean odwolana = salon.anulujRezerwacje(godzinyPrzyjec,godzinaDoOdwolania);
                                if(odwolana ){
                                    out.println("Wizyta o" + godzinaDoOdwolania + " zostala odwolana");
                                    salon.wyslijDoWszystkich();
                                } else {
                                    out.println("O tej godzinie nie masz wyznaczonej wizyty");
                                }
                            }catch (NumberFormatException e){
                                salon.wyslijDoMnie(out);
                                out.println("niepoprawnie wpisana godzina");
                            }
                        }

                    }else{
                        try{
                            int godzina = Integer.parseInt(line)-10;
                            if(godzina < 0 || godzina > 8 ) {
                                out.println("Salon nie paracuje w takich godzinach");
                            } else if (salon.get(godzina).toLowerCase().contains("wolne")){
                                salon.set(godzina,"1" + godzina + " zajete");
                                godzinyPrzyjec.add(godzina);
                                salon.wyslijDoWszystkich();
                            } else {
                                out.println("ta godzina jest juz zajeta");
                            }
                        } catch (NumberFormatException e){
                            out.println("format godziny nie pasuje wpisz od 10 do 18 lub zle wpisales slowo'odwolaj' ");
                        }
                    }
                }

            }
        } catch (IOException e) {

        }
    }
}
