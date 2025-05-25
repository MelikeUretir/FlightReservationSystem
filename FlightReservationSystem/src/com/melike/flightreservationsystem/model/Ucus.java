package com.melike.flightreservationsystem.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Ucus implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ucusNumarasi;
    private Lokasyon kalkisLokasyonu;
    private Lokasyon varisLokasyonu;
    private LocalDate ucusTarihi;
    private LocalTime kalkisSaati;
    private Ucak ucak;
    private int bosKoltukSayisi;

    public Ucus(String ucusNumarasi, Lokasyon kalkisLokasyonu, Lokasyon varisLokasyonu,
                LocalDate ucusTarihi, LocalTime kalkisSaati, Ucak ucak) {
        this.ucusNumarasi = ucusNumarasi;
        this.kalkisLokasyonu = kalkisLokasyonu;
        this.varisLokasyonu = varisLokasyonu;
        this.ucusTarihi = ucusTarihi;
        this.kalkisSaati = kalkisSaati;
        this.ucak = ucak;
        this.bosKoltukSayisi = ucak.getKoltukKapasitesi();
    }

    public String getUcusNumarasi() {
        return ucusNumarasi;
    }

    public Lokasyon getKalkisLokasyonu() {
        return kalkisLokasyonu;
    }

    public Lokasyon getVarisLokasyonu() {
        return varisLokasyonu;
    }

    public LocalDate getUcusTarihi() {
        return ucusTarihi;
    }

    public LocalTime getKalkisSaati() {
        return kalkisSaati;
    }

    public Ucak getUcak() {
        return ucak;
    }

    public int getBosKoltukSayisi() {
        return bosKoltukSayisi;
    }

    public boolean koltukAyir() {
        if (bosKoltukSayisi > 0) {
            bosKoltukSayisi--;
            return true;
        }
        return false;
    }

    public void koltukIptalEt() {
        if (bosKoltukSayisi < ucak.getKoltukKapasitesi()) {
            bosKoltukSayisi++;
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return "Uçuş No: " + ucusNumarasi +
                " | Kalkış: " + kalkisLokasyonu +
                " | Varış: " + varisLokasyonu +
                " | Tarih: " + ucusTarihi.format(dateFormatter) +
                " | Saat: " + kalkisSaati.format(timeFormatter) +
                " | Uçak: " + ucak.getModel() +
                " | Boş Koltuk: " + bosKoltukSayisi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ucus ucus = (Ucus) o;
        return ucusNumarasi.equals(ucus.ucusNumarasi);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(ucusNumarasi);
    }

    public String toCsvString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

        return String.join(",",
                ucusNumarasi,
                kalkisLokasyonu.getUlke(), kalkisLokasyonu.getSehir(), kalkisLokasyonu.getHavaalani(), String.valueOf(kalkisLokasyonu.isAktif()),
                varisLokasyonu.getUlke(), varisLokasyonu.getSehir(), varisLokasyonu.getHavaalani(), String.valueOf(varisLokasyonu.isAktif()),
                ucusTarihi.format(dateFormatter),
                kalkisSaati.format(timeFormatter),
                ucak.getModel(), ucak.getMarka(), ucak.getSeriNo(), String.valueOf(ucak.getKoltukKapasitesi()),
                String.valueOf(bosKoltukSayisi)
        );
    }

    public void setBosKoltukSayisi(int bosKoltukSayisi) {
        this.bosKoltukSayisi = bosKoltukSayisi;
    }

    public static Ucus fromCsvString(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length != 15) {
            System.err.println("Geçersiz Uçuş CSV satırı formatı: " + csvLine);
            return null;
        }

        try {
            String ucusNumarasi = parts[0];
            Lokasyon kalkis = new Lokasyon(parts[1], parts[2], parts[3], Boolean.parseBoolean(parts[4]));
            Lokasyon varis = new Lokasyon(parts[5], parts[6], parts[7], Boolean.parseBoolean(parts[8]));
            LocalDate tarih = LocalDate.parse(parts[9], DateTimeFormatter.ISO_LOCAL_DATE);
            LocalTime saat = LocalTime.parse(parts[10], DateTimeFormatter.ISO_LOCAL_TIME);
            Ucak ucak = new Ucak(parts[11], parts[12], parts[13], Integer.parseInt(parts[14]));
            int bosKoltukSayisi = Integer.parseInt(parts[15]);

            Ucus loadedUcus = new Ucus(ucusNumarasi, kalkis, varis, tarih, saat, ucak);
            loadedUcus.bosKoltukSayisi = bosKoltukSayisi;
            return loadedUcus;

        } catch (Exception e) {
            System.err.println("Uçuş CSV satırı işlenirken hata oluştu: " + csvLine + " - " + e.getMessage());
            return null;
        }
    }
}