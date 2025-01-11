package com.example.penjualanpakaian;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class DataProduk implements Parcelable {
    private String idProduk, namaProduk, deskripsiProduk, gambarProduk;
    private double hargaProduk;
    private int stokProduk;

    // Constructor
    public DataProduk(String idProduk, String namaProduk, double hargaProduk, int stokProduk, String deskripsiProduk, String gambarProduk) {
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.stokProduk = stokProduk;
        this.deskripsiProduk = deskripsiProduk;
        this.gambarProduk = gambarProduk;
    }

    // implementasi Parcelable
    protected DataProduk(Parcel in) {
        idProduk = in.readString();
        namaProduk = in.readString();
        hargaProduk = in.readDouble();
        stokProduk = in.readInt();
        deskripsiProduk = in.readString();
        gambarProduk = in.readString();
    }

    public static final Creator<DataProduk> CREATOR = new Creator<DataProduk>() {
        @Override
        public DataProduk createFromParcel(Parcel in) {
            return new DataProduk(in);
        }

        @Override
        public DataProduk[] newArray(int size) {
            return new DataProduk[size];
        }
    };

    // Getters dan Setters
    public String getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public double getHargaProduk() {
        return hargaProduk;
    }

    public void setHargaProduk(double hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public int getStokProduk() {
        return stokProduk;
    }

    public void setStokProduk(int stokProduk) {
        this.stokProduk = stokProduk;
    }

    public String getDeskripsiProduk() {
        return deskripsiProduk;
    }

    public void setDeskripsiProduk(String deskripsiProduk) {
        this.deskripsiProduk = deskripsiProduk;
    }

    public String getGambarProduk() {
        return gambarProduk;
    }

    public void setGambarProduk(String gambarProduk) {
        this.gambarProduk = gambarProduk;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(idProduk);
        dest.writeString(namaProduk);
        dest.writeDouble(hargaProduk);
        dest.writeInt(stokProduk);
        dest.writeString(deskripsiProduk);
        dest.writeString(gambarProduk);
    }
}
