package com.company;

import java.io.PrintWriter;
import java.util.ArrayList;


public class Salon {
    ArrayList<ArrayList<Integer>> czyjeZapisy= new ArrayList<ArrayList<Integer>>();
    ArrayList<PrintWriter> powiadomieniaDoWszystkich = null;
    ArrayList<String> zapisy = new ArrayList<String>();


    Salon(ArrayList<PrintWriter> powiadomieniaDoWszystkich){
        this.powiadomieniaDoWszystkich = powiadomieniaDoWszystkich;
        for (int i = 0; i<9; i++){
            zapisy.add("1"+ i + ":00 wolne miejsce");

        }
    }


    //public void setZapisy(ArrayList<String> zapisy) {
  //      this.zapisy = zapisy;
//    }

    String get(int i){
        return zapisy.get(i);
    }

    void set(int a, String b){
        zapisy.set(a,b);
    }

    void add(String b){
        zapisy.add(b);
    }

    void newWriter(PrintWriter out){
        powiadomieniaDoWszystkich.add(out);
    }

    void  newIndex(ArrayList<Integer> people) {
        czyjeZapisy.add(people);
    }


//
//public void setPowiadomienia(PrintWriter powiadomienie) {
//        this.powiadomieniaDoWszystkich.add(powiadomienie);
//    }

    public ArrayList<String> getZapisy() {
        return zapisy;
    }



    void wyslijDoMnie(PrintWriter out1){
        int liczba = this.powiadomieniaDoWszystkich.indexOf(out1);
        out1.println("Witaj w salonie fryzjerskim 'Rachu Ciachu'");
        out1.println("Dziesiejsze godziny przyjęc to:");
        for (int j = 0; j < this.zapisy.size(); j++) {
            if (this.czyjeZapisy.get(liczba).contains(j)) {
                out1.println(this.zapisy.get(j) + " przez Ciebie");
            } else {
                out1.println(zapisy.get(j));
            }
        }
        out1.println("Aby wybrać godzinę, która Cie interesuje wybierz numer od 10 do 18: ");



    }

    void wyslijDoWszystkich(){
        boolean czyZajete = false;
        for (int i = 0; i<powiadomieniaDoWszystkich.size(); i++){
            PrintWriter out = powiadomieniaDoWszystkich.get(i);
            out.println("Witaj w salonie fryzjerskim 'Rachu Ciachu'/nDziesiejsze godziny przyjęc to: ");
            for(int k = 0; k < zapisy.size(); k++){
                if (czyjeZapisy.get(i).contains(k)){
                    czyZajete = true;
                    out.println(zapisy.get(k) + " przez Ciebie" );
                } else{
                    out.println(zapisy.get(k));
                }
            }
            out.println("Witaj w salonie fryzjerskim 'Rachu Ciachu'/nDziesiejsze godziny przyjęc to: ");
            out.println("GODZINY PRZYJEC");
            out.println("Aby wybrać godzinę, która Cie interesuje wybierz numer od 10 do 18: ");
            if(czyZajete == true){
                out.println("Możesz odwałac wizyte wpisujac 'odwalaj'");
                czyZajete = false;
            }


        }
    }

    boolean anulujRezerwacje(ArrayList<Integer> zapis, int odwolaj){
        int doOdwolania=czyjeZapisy.indexOf(zapis);
        if(czyjeZapisy.get(doOdwolania).contains(odwolaj-10)){
            int czyje = czyjeZapisy.get(doOdwolania).indexOf(odwolaj -10);
            czyjeZapisy.get(doOdwolania).remove(czyje);
            zapisy.set(doOdwolania - 10 , " 1" + (odwolaj - 10) + ":00 wolne");
            return true;
        }else{
            return false;
        }
    }

}
