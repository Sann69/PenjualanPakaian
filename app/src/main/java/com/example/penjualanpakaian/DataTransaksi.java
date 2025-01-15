package com.example.penjualanpakaian;

public class DataTransaksi {
    private int id;
    private String produk;
    private double harga;
    private int qty;
    private double total;


    public DataTransaksi(int id, String produk, double harga, int qty, double total) {
        this.id = id;
        this.produk = produk;
        this.harga = harga;
        this.qty = qty;
        this.total = total;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduk() {
        return produk;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
