package com.example.penjualanpakaian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MenuKategoriActivity extends AppCompatActivity {

    private DatabaseHelperKategori databaseHelper;
    private GridView gridViewData;
    private Button buttonTambahKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_kategori);

        databaseHelper = new DatabaseHelperKategori(this);
        gridViewData = findViewById(R.id.gridViewData);
        buttonTambahKategori = findViewById(R.id.buttonTambahKategori);

        loadCategories();

        buttonTambahKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuKategoriActivity.this, ManageDataKategori.class);
                startActivity(intent);
            }
        });

        gridViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(MenuKategoriActivity.this, EditKategoriActivity.class);
                intent.putExtra("category_name", selectedCategory);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCategories(); // Memuat ulang kategori setiap kali aktivitas ini muncul
    }

    private void loadCategories() {
        List<String> categories = databaseHelper.getAllCategories();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        gridViewData.setAdapter(adapter);
    }
}