package com.example.uzivatel.currcon;

/**
 * Created by Uzivatel on 16.02.2018.
 */

public class Mena {

    private double kurz;
    private int mnozstvo;
    private String kod;
    private String krajina;
    private String mena;

    public String toString(){
        return getKrajina()+" - "+getMena()+" - "+getKod()+" - "+getMnozstvo()+" - "+getKurz();

    }

    public double getKurz() {
        return kurz;
    }

    public void setKurz(double kurz) {
        this.kurz = kurz;
    }

    public int getMnozstvo() {
        return mnozstvo;
    }

    public void setMnozstvo(int mnozstvo) {
        this.mnozstvo = mnozstvo;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getKrajina() {
        return krajina;
    }

    public void setKrajina(String krajina) {
        this.krajina = krajina;
    }

    public String getMena() {
        return mena;
    }

    public void setMena(String mena) {
        this.mena = mena;
    }



}
