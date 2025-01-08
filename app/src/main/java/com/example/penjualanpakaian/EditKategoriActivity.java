package com.example.penjualanpakaian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditKategoriActivity extends AppCompatActivity {

    private DatabaseHelperKategori databaseHelper;
    private EditText editTextTambahKategori;
    private Button buttonAddDataKategori, buttonHapusKategori, buttonViewKategori;
    private String originalCategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kategori);

        databaseHelper = new DatabaseHelperKategori(this);
        editTextTambahKategori = findViewById(R.id.editTextTambahKategori);
        buttonAddDataKategori = findViewById(R.id.buttonAddDataKategori);
        buttonHapusKategori = findViewById(R.id.buttonHapusKategori);
        buttonViewKategori = findViewById(R.id.buttonViewKategori);

        // Get the original category name from the intent
        originalCategoryName = getIntent().getStringExtra("category_name");
        editTextTambahKategori.setText(originalCategoryName);

        buttonAddDataKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCategoryName = editTextTambahKategori.getText().toString();
                databaseHelper.updateCategory(originalCategoryName, newCategoryName);
                Toast.makeText(EditKategoriActivity.this, "Kategori diperbarui", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        buttonHapusKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteCategory(originalCategoryName);
                Toast.makeText(EditKategoriActivity.this, "Kategori dihapus", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        buttonViewKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditKategoriActivity.this, MenuProdukActivity.class);
                startActivity(intent);
            }
        });
    }
}