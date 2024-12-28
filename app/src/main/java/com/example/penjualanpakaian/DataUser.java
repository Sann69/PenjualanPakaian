package com.example.penjualanpakaian;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class DataUser implements Parcelable {
    private String id, nama, jabatan, path, telepon, password;
    private int  umur;

    public DataUser(String id, String nama, Integer umur, String telepon, String jabatan, String path, String password){
        this.id = id;
        this.nama = nama;
        this.umur = umur;
        this.telepon = telepon;
        this.jabatan = jabatan;
        this.path = path;
        this.password = password;

    }
    protected DataUser(Parcel in) {
        id = in.readString();
        nama = in.readString();
        umur = in.readInt();
        telepon = in.readString();
        jabatan = in.readString();
        path = in.readString();
        password = in.readString();

    }

    public static final Creator<DataUser> CREATOR = new Creator<DataUser>() {
        @Override
        public DataUser createFromParcel(Parcel in) {
            return new DataUser(in);
        }

        @Override
        public DataUser[] newArray(int size) {
            return new DataUser[size];
        }
    };

    public String getNama() {

        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nama);
        dest.writeInt(umur);
        dest.writeString(telepon);
        dest.writeString(jabatan);
        dest.writeString(path);
        dest.writeString(password);

    }
}
