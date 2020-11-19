package com.k.kelimekaydedici;

public class TurModel {
    private String isim;
    private String ata;


    public TurModel(String isim, String ata) {
        this.isim = isim;
        this.ata = ata;
    }

    public TurModel() { }

    public TurModel(String isim) {
        this.isim = isim;
    }


    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getAta() {
        return ata;
    }

    public void setAta(String ata) {
        this.ata = ata;
    }
}
