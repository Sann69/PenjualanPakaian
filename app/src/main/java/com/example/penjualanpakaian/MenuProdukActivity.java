package com.example.penjualanpakaian;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuProdukActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 100;

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

        // Periksa izin penyimpanan
        if (!checkStoragePermission()) {
            requestStoragePermission();
        } else {
            // Load data produk jika izin sudah diberikan
            loadProdukData();
        }
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

    private boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Izin Storage telah diberikan", Toast.LENGTH_SHORT).show();
                loadProdukData();
            } else {
                Toast.makeText(this, "Izin Storage ditolak", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
