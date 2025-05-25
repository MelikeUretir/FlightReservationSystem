package com.melike.flightreservationsystem.model;

import java.io.Serializable;

public class Ucak implements Serializable {
    private static final long serialVersionUID = 1L;
    private String model;
    private String marka;
    private String seriNo;
    private int koltukKapasitesi;

    public Ucak(String model, String marka, String seriNo, int koltukKapasitesi) {
        this.model = model;
        this.marka = marka;
        this.seriNo = seriNo;
        this.koltukKapasitesi = koltukKapasitesi;
    }

    public String getModel() {
        return model;
    }

    public String getMarka() {
        return marka;
    }

    public String getSeriNo() {
        return seriNo;
    }

    public int getKoltukKapasitesi() {
        return koltukKapasitesi;
    }

    @Override
    public String toString() {
        return "Uçak [Model=" + model + ", Marka=" + marka + ", SeriNo=" + seriNo + ", KoltukKapasitesi=" + koltukKapasitesi + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ucak ucak = (Ucak) o;
        return koltukKapasitesi == ucak.koltukKapasitesi &&
                model.equals(ucak.model) &&
                marka.equals(ucak.marka) &&
                seriNo.equals(ucak.seriNo);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(model, marka, seriNo, koltukKapasitesi);
    }
}