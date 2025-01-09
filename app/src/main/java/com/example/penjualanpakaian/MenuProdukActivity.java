package com.example.penjualanpakaian;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuProdukActivity extends AppCompatActivity {
    GridView gridProduk;
    Button btnTambahProduk, btnKembali;
    DatabaseHelperProduk databaseHelperProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_produk);

        databaseHelperProduk = new DatabaseHelperProduk(this);

        gridProduk = findViewById(R.id.gridViewData);
        btnTambahProduk = findViewById(R.id.buttonTambahProduk);
        btnKembali = findViewById(R.id.buttonKembali);

        btnTambahProduk.setOnClickListener(v -> {
            Intent intent = new Intent(MenuProdukActivity.this, ManageDataProduk.class);
            startActivity(intent);
        });

        btnKembali.setOnClickListener(v -> {
            Intent intent = new Intent(MenuProdukActivity.this, MenuUtamaActivity.class);
            startActivity(intent);
            finish();
        });

        loadProdukData();
    }

    private void loadProdukData() {
        ArrayList<HashMap<String, String>> produkList = databaseHelperProduk.getAllProduk();
        ProdukAdapter adapter = new ProdukAdapter(this, produkList);
        gridProduk.setAdapter(adapter);

        gridProduk.setOnItemClickListener((parent, view, position, id) -> {
            HashMap<String, String> selectedProduk = produkList.get(position);
            Intent intent = new Intent(MenuProdukActivity.this, ManageDataProduk.class);
            intent.putExtra("id", selectedProduk.get("id"));
            intent.putExtra("mode", "update");
            startActivity(intent);
        });
    }
}
