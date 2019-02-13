package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{

    Socket socket= null;

    Client(Socket socket){
        this.socket=socket;
    }
    public static void main(String[] args) throws IOException {
        final String host= "127.0.0.1";
        final int port= 6666;


        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Podaj numer portu klienta:");
            int clientPortNumber = Integer.parseInt(in.readLine());
            Socket clientSocket = new Socket(host, port);
            ServerSocket serverSocket = new ServerSocket(clientPortNumber);
            PrintWriter outt = new PrintWriter(clientSocket.getOutputStream(), true);
            // out.println("ip klienta: " + host + "port klienta: " + port);
            outt.println(clientPortNumber);
            String text = null;
            (new Thread(new Client(serverSocket.accept()))).start();
            while (true) {
                if ((text = in.readLine()) != null) {
                    // jak cos wpisalismy i klikniemy enter to to sie wysle
                    outt.println(text);
                }

            }

             } catch (ConnectException | BindException| NumberFormatException e){
                System.out.println("Problem z połączeniem");
             }
    }

    public void run(){
        try{


            String text;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true){
            if ((text = bufferedReader.readLine()) != null){
                System.out.println(text);
            }

        }
        }catch (IOException e){

        }
    }

}
