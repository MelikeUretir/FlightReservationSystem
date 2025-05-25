package com.melike.flightreservationsystem.manager;

import com.melike.flightreservationsystem.model.Ucak;
import com.melike.flightreservationsystem.model.Lokasyon;
import com.melike.flightreservationsystem.model.Ucus;
import com.melike.flightreservationsystem.model.Rezervasyon;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FlightManager {
    private List<Ucus> mevcutUcuslar;
    private List<Rezervasyon> yapilanRezervasyonlar;

    public FlightManager() {
        this.mevcutUcuslar = new ArrayList<>();
        this.yapilanRezervasyonlar = new ArrayList<>();
        Ucuslar();
    }

    public List<Ucus> getMevcutUcuslar() {
        return mevcutUcuslar;
    }

    public List<Rezervasyon> getYapilanRezervasyonlar() {
        return yapilanRezervasyonlar;
    }

    public void ucuslariListele() {
        if (mevcutUcuslar.isEmpty()) {
            System.out.println("Listelenecek uçuş bulunmamaktadır.");
            return;
        }
        System.out.println("\n--- Mevcut Uçuşlar ---");
        for (int i = 0; i < mevcutUcuslar.size(); i++) {
            Ucus ucus = mevcutUcuslar.get(i);
            System.out.println((i + 1) + ". " + ucus);
        }
    }

    public Optional<Ucus> getUcusByNumber(int index) {
        if (index >= 0 && index < mevcutUcuslar.size()) {
            return Optional.of(mevcutUcuslar.get(index));
        }
        return Optional.empty();
    }

    public boolean rezervasyonYap(Ucus ucus, String ad, String soyad, int yas) {
        if (ucus.getBosKoltukSayisi() <= 0) {
            System.out.println("Üzgünüz, bu uçuşta boş koltuk kalmamıştır.");
            return false;
        }

        String koltukNo = "A" + (ucus.getUcak().getKoltukKapasitesi() - ucus.getBosKoltukSayisi() + 1);
        String rezervasyonId = UUID.randomUUID().toString();

        if (ucus.koltukAyir()) {
            Rezervasyon yeniRezervasyon = new Rezervasyon(rezervasyonId, ucus, ad, soyad, yas, koltukNo);
            yapilanRezervasyonlar.add(yeniRezervasyon);
            System.out.println("Rezervasyonunuz başarıyla yapıldı! Rezervasyon ID: " + rezervasyonId + ", Koltuk No: " + koltukNo);
            return true;
        } else {
            System.out.println("Rezervasyon yapılamadı. Bir hata oluştu (Koltuk ayırma başarısız).");
            return false;
        }
    }

    public void rezervasyonlariGoruntule() {
        if (yapilanRezervasyonlar.isEmpty()) {
            System.out.println("Henüz yapılmış bir rezervasyon bulunmamaktadır.");
            return;
        }
        System.out.println("\n--- Yapılan Rezervasyonlar ---");
        for (Rezervasyon rezervasyon : yapilanRezervasyonlar) {
            System.out.println(rezervasyon);
        }
    }

    public Optional<Ucus> findUcusByUcusNumarasi(String ucusNumarasi) {
        return mevcutUcuslar.stream()
                .filter(u -> u.getUcusNumarasi().equals(ucusNumarasi))
                .findFirst();
    }

    private void Ucuslar() {
        Ucak boeing737 = new Ucak("Boeing 737", "Boeing", "SN737-001", 150);
        Ucak airbusA320 = new Ucak("Airbus A320", "Airbus", "SNA320-002", 180);

        Lokasyon istanbul = new Lokasyon("Türkiye", "İstanbul", "İstanbul Havalimanı (IST)", true);
        Lokasyon ankara = new Lokasyon("Türkiye", "Ankara", "Esenboğa Havalimanı (ESB)", true);
        Lokasyon izmir = new Lokasyon("Türkiye", "İzmir", "Adnan Menderes Havalimanı (ADB)", true);

        mevcutUcuslar.add(new Ucus("THY101", istanbul, ankara, LocalDate.of(2025, 7, 10), LocalTime.of(10, 0), boeing737));
        mevcutUcuslar.add(new Ucus("THY102", ankara, istanbul, LocalDate.of(2025, 7, 10), LocalTime.of(14, 30), airbusA320));
        mevcutUcuslar.add(new Ucus("PGS201", izmir, istanbul, LocalDate.of(2025, 7, 11), LocalTime.of(9, 0), boeing737));
        mevcutUcuslar.add(new Ucus("PGS202", istanbul, izmir, LocalDate.of(2025, 7, 11), LocalTime.of(18, 0), airbusA320));

        System.out.println("Uçuşlar yüklendi.");
    }
}