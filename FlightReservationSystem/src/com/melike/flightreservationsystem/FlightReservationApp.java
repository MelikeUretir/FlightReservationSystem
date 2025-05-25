package com.melike.flightreservationsystem;

import com.melike.flightreservationsystem.manager.DataManager;
import com.melike.flightreservationsystem.manager.FlightManager;
import com.melike.flightreservationsystem.model.Rezervasyon;
import com.melike.flightreservationsystem.model.Ucus;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Optional;

public class FlightReservationApp {

    private static FlightManager flightManager = new FlightManager();
    private static DataManager dataManager = new DataManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        yukleVerileri();

        int choice;
        do {
            displayMenu();
            choice = getUserChoice();

            switch (choice) {
                case 1:
                    flightManager.ucuslariListele();
                    break;
                case 2:
                    rezervasyonYap();
                    break;
                case 3:
                    flightManager.rezervasyonlariGoruntule();
                    break;
                case 0:
                    System.out.println("Uygulama sonlandırılıyor. İyi günler!");
                    break;
                default:
                    System.out.println("Geçersiz seçim. Lütfen tekrar deneyin.");
            }
        } while (choice != 0);

        kaydetVerileri();
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n--- Uçak Bilet Rezervasyon Sistemi ---");
        System.out.println("1. Uçuşları Listele");
        System.out.println("2. Rezervasyon Yap");
        System.out.println("3. Rezervasyonları Görüntüle");
        System.out.println("0. Çıkış");
        System.out.print("Seçiminiz: ");
    }

    private static int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Geçersiz giriş. Lütfen bir sayı girin.");
            scanner.next();
            return -1;
        }
    }

    private static void rezervasyonYap() {
        flightManager.ucuslariListele();

        System.out.print("Rezervasyon yapmak istediğiniz uçuşun numarasını girin: ");
        int ucusIndex = -1;
        try {
            ucusIndex = scanner.nextInt() - 1;
        } catch (InputMismatchException e) {
            System.out.println("Geçersiz giriş. Lütfen bir sayı girin.");
            scanner.next();
            return;
        }
        scanner.nextLine();

        Optional<Ucus> secilenUcusOpt = flightManager.getUcusByNumber(ucusIndex);

        if (secilenUcusOpt.isPresent()) {
            Ucus secilenUcus = secilenUcusOpt.get();
            System.out.print("Adınız: ");
            String ad = scanner.nextLine();
            System.out.print("Soyadınız: ");
            String soyad = scanner.nextLine();
            System.out.print("Yaşınız: ");
            int yas = -1;
            try {
                yas = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Geçersiz yaş girişi. Lütfen bir sayı girin.");
                scanner.next();
                return;
            }
            scanner.nextLine();

            flightManager.rezervasyonYap(secilenUcus, ad, soyad, yas);
        } else {
            System.out.println("Geçersiz uçuş numarası.");
        }
    }

    private static void kaydetVerileri() {
        dataManager.saveRezervasyonlarToCsv(flightManager.getYapilanRezervasyonlar());
        System.out.println("Rezervasyon verileri kaydedildi.");
    }

    private static void yukleVerileri() {
        List<Rezervasyon> yuklenenRezervasyonlar = dataManager.loadRezervasyonlarFromCsv(flightManager);
        if (yuklenenRezervasyonlar != null && !yuklenenRezervasyonlar.isEmpty()) {
            for (Rezervasyon r : yuklenenRezervasyonlar) {
                flightManager.getYapilanRezervasyonlar().add(r);
            }
            System.out.println("Önceki rezervasyonlar yüklendi.");
        } else {
            System.out.println("Yüklenecek eski rezervasyon bulunamadı.");
        }
    }
}