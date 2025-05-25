package com.melike.flightreservationsystem.manager;

import com.melike.flightreservationsystem.model.Rezervasyon;
import com.melike.flightreservationsystem.model.Ucus;
import com.melike.flightreservationsystem.model.Lokasyon;
import com.melike.flightreservationsystem.model.Ucak;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataManager {

    private static final String UCUSLAR_CSV = "resources/data/ucuslar.csv";
    private static final String REZERVASYONLAR_CSV = "resources/data/rezervasyonlar.csv";

    public List<Ucus> loadUcuslarFromCsv() {
        List<Ucus> loadedUcuslar = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(UCUSLAR_CSV))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 16) {
                    try {
                        String ucusNumarasi = parts[0];
                        Lokasyon kalkis = new Lokasyon(parts[1], parts[2], parts[3], Boolean.parseBoolean(parts[4]));
                        Lokasyon varis = new Lokasyon(parts[5], parts[6], parts[7], Boolean.parseBoolean(parts[8]));
                        LocalDate tarih = LocalDate.parse(parts[9]);
                        LocalTime saat = LocalTime.parse(parts[10]);
                        Ucak ucak = new Ucak(parts[11], parts[12], parts[13], Integer.parseInt(parts[14]));
                        int bosKoltukSayisi = Integer.parseInt(parts[15]);

                        Ucus loadedUcus = new Ucus(ucusNumarasi, kalkis, varis, tarih, saat, ucak);
                        loadedUcus.setBosKoltukSayisi(bosKoltukSayisi);
                        loadedUcuslar.add(loadedUcus);
                    } catch (NumberFormatException | DateTimeParseException | ArrayIndexOutOfBoundsException e) {
                        System.err.println("Uçuş CSV satırı okunurken format hatası: " + line + " - " + e.getMessage());
                    }
                } else {
                    System.err.println("Geçersiz Uçuş CSV satırı uzunluğu (" + parts.length + " eleman): " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Uçuş verileri yüklenirken hata oluştu: " + e.getMessage());
            return new ArrayList<>();
        }
        System.out.println("Uçuş verileri " + UCUSLAR_CSV + " dosyasından yüklendi.");
        return loadedUcuslar;
    }

    public List<Rezervasyon> loadRezervasyonlarFromCsv(FlightManager flightManager) {
        List<Rezervasyon> loadedRezervasyonlar = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(REZERVASYONLAR_CSV))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    try {
                        String rezervasyonId = parts[0];
                        String ucusNumarasi = parts[1];
                        String ad = parts[2];
                        String soyad = parts[3];
                        int yas = Integer.parseInt(parts[4]);
                        String koltukNo = parts[5];

                        Optional<Ucus> ucusOpt = flightManager.findUcusByUcusNumarasi(ucusNumarasi);
                        if (ucusOpt.isPresent()) {
                            Ucus ucus = ucusOpt.get();
                            Rezervasyon loadedRezervasyon = new Rezervasyon(rezervasyonId, ucus, ad, soyad, yas, koltukNo);
                            loadedRezervasyonlar.add(loadedRezervasyon);
                        } else {
                            System.err.println("Yüklenen rezervasyon için uçuş bulunamadı: " + ucusNumarasi);
                        }

                    } catch (NumberFormatException | DateTimeParseException | ArrayIndexOutOfBoundsException e) {
                        System.err.println("Rezervasyon CSV satırı okunurken format hatası: " + line + " - " + e.getMessage());
                    }
                } else {
                    System.err.println("Geçersiz Rezervasyon CSV satırı uzunluğu (" + parts.length + " eleman): " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Rezervasyon verileri yüklenirken hata oluştu: " + e.getMessage());
            return new ArrayList<>();
        }
        return loadedRezervasyonlar;
    }

    public void saveRezervasyonlarToCsv(List<Rezervasyon> rezervasyonlar) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(REZERVASYONLAR_CSV))) {
            bw.write("RezervasyonID,UcusNumarasi,Ad,Soyad,Yas,KoltukNo,UcusID\n");

            for (Rezervasyon rez : rezervasyonlar) {
                bw.write(rez.toCsvString() + "\n");
            }
            System.out.println("Rezervasyonlar başarıyla kaydedildi.");
        } catch (IOException e) {
            System.err.println("Rezervasyonlar kaydedilirken hata oluştu: " + e.getMessage());
        }
    }
}