package com.example.penjualanpakaian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ManageDataKategori extends AppCompatActivity {

    private EditText editTextTambahKategori; // Declare EditText for category input
    private DatabaseHelperKategori databaseHelper; // Declare DatabaseHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_data_kategori); // Set the content view

        // Initialize views
        editTextTambahKategori = findViewById(R.id.editTextTambahKategori);
        Button buttonAddDataKategori = findViewById(R.id.buttonAddDataKategori);
        databaseHelper = new DatabaseHelperKategori(this); // Initialize DatabaseHelper

        // Set up button click listener
        buttonAddDataKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = editTextTambahKategori.getText().toString().trim(); // Get input text
                if (!category.isEmpty()) {
                    databaseHelper.addCategory(category); // Add category to database
                    Toast.makeText(ManageDataKategori.this, "Kategori ditambahkan", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ManageDataKategori.this, MenuKategoriActivity.class);
                    startActivity(intent); // Navigate back to MenuKategoriActivity
                    finish(); // Close this activity
                } else {
                    Toast.makeText(ManageDataKategori.this, "Nama kategori tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}