package com.example.penjualanpakaian;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ProdukAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> produkList;
    private LayoutInflater inflater;

    public ProdukAdapter(Context context, ArrayList<HashMap<String, String>> produkList) {
        this.context = context;
        this.produkList = produkList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return produkList.size();
    }

    @Override
    public Object getItem(int position) {
        return produkList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_data_produk, parent, false);
        }

        // Ambil data produk dari list
        HashMap<String, String> produk = produkList.get(position);

        // Bind komponen pada layout
        ImageView imageViewProduk = convertView.findViewById(R.id.imageViewProduk);
        TextView textViewNama = convertView.findViewById(R.id.textViewNamaProduk);
        TextView textViewHarga = convertView.findViewById(R.id.textViewHargaProduk);
        TextView textViewStok = convertView.findViewById(R.id.textViewStokProduk);

        // Set data ke komponen
        textViewNama.setText(produk.get("nama"));
        textViewHarga.setText("Harga: Rp " + produk.get("harga"));
        textViewStok.setText("Stok: " + produk.get("stok"));

        // Load gambar dari path
        String gambarPath = produk.get("gambar");
        if (gambarPath != null && !gambarPath.isEmpty()) {
            imageViewProduk.setImageURI(android.net.Uri.parse(gambarPath));
        } else {
            imageViewProduk.setImageResource(R.drawable.ic_launcher_background);
        }

        return convertView;
    }
}
