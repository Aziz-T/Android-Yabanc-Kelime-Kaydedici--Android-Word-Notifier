package com.k.kelimekaydedici;

public class KelimeModel {
    private String ata, kelime,anlami;

    public KelimeModel(){}
    public KelimeModel(String kelime, String anlami) {
        this.kelime = kelime;
        this.anlami = anlami;
    }

    public KelimeModel(String ata, String kelime, String anlami) {
        this.ata = ata;
        this.kelime = kelime;
        this.anlami = anlami;
    }

    public String getAta() {
        return ata;
    }

    public void setAta(String ata) {
        this.ata = ata;
    }

    public String getKelime() {
        return kelime;
    }

    public void setKelime(String kelime) {
        this.kelime = kelime;
    }

    public String getAnlami() {
        return anlami;
    }

    public void setAnlami(String anlami) {
        this.anlami = anlami;
    }
}
