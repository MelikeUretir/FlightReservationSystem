package com.melike.flightreservationsystem.model;

import java.io.Serializable;

public class Rezervasyon implements Serializable {
    private static final long serialVersionUID = 1L;
    private String rezervasyonId;
    private Ucus ucus;
    private String ad;
    private String soyad;
    private int yas;
    private String koltukNo;

    public Rezervasyon(String rezervasyonId, Ucus ucus, String ad, String soyad, int yas, String koltukNo) {
        this.rezervasyonId = rezervasyonId;
        this.ucus = ucus;
        this.ad = ad;
        this.soyad = soyad;
        this.yas = yas;
        this.koltukNo = koltukNo;
    }

    public String getRezervasyonId() {
        return rezervasyonId;
    }

    public Ucus getUcus() {
        return ucus;
    }

    public String getAd() {
        return ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public int getYas() {
        return yas;
    }

    public String getKoltukNo() {
        return koltukNo;
    }

    @Override
    public String toString() {
        return "Rezervasyon ID: " + rezervasyonId +
                " | Yolcu: " + ad + " " + soyad + " (Yaş: " + yas + ")" +
                " | Uçuş No: " + ucus.getUcusNumarasi() +
                " | Kalkış: " + ucus.getKalkisLokasyonu().getSehir() +
                " | Varış: " + ucus.getVarisLokasyonu().getSehir() +
                " | Koltuk No: " + koltukNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rezervasyon that = (Rezervasyon) o;
        return rezervasyonId.equals(that.rezervasyonId);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(rezervasyonId);
    }

    public String toCsvString() {
        return String.join(",",
                rezervasyonId,
                ucus.getUcusNumarasi(),
                ad,
                soyad,
                String.valueOf(yas),
                koltukNo
        );
    }
}