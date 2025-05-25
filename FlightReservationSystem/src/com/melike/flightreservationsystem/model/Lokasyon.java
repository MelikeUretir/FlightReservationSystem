package com.melike.flightreservationsystem.model;

import java.io.Serializable;

public class Lokasyon implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ulke;
    private String sehir;
    private String havaalani;
    private boolean aktif;

    public Lokasyon(String ulke, String sehir, String havaalani, boolean aktif) {
        this.ulke = ulke;
        this.sehir = sehir;
        this.havaalani = havaalani;
        this.aktif = aktif;
    }

    public String getUlke() {
        return ulke;
    }

    public String getSehir() {
        return sehir;
    }

    public String getHavaalani() {
        return havaalani;
    }

    public boolean isAktif() {
        return aktif;
    }

    @Override
    public String toString() {
        return sehir + " (" + havaalani + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lokasyon lokasyon = (Lokasyon) o;
        return aktif == lokasyon.aktif &&
                ulke.equals(lokasyon.ulke) &&
                sehir.equals(lokasyon.sehir) &&
                havaalani.equals(lokasyon.havaalani);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(ulke, sehir, havaalani, aktif);
    }
}