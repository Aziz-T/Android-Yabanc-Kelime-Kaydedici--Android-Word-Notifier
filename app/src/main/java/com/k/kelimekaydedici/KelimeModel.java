package com.k.kelimekaydedici;

public class KelimeModel {
   private String kelime, cevirisi;

    public KelimeModel(String kelime, String cevirisi) {
        this.kelime = kelime;
        this.cevirisi = cevirisi;
    }

    public String getKelime() {
        return kelime;
    }

    public void setKelime(String kelime) {
        this.kelime = kelime;
    }

    public String getCevirisi() {
        return cevirisi;
    }

    public void setCevirisi(String cevirisi) {
        this.cevirisi = cevirisi;
    }
}
