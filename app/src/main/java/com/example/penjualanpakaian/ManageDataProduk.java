package com.example.penjualanpakaian;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class ManageDataProduk extends AppCompatActivity {

    private EditText editTextId, editTextNama, editTextHarga, editTextStok, editTextDeskripsi;
    private Button btnSimpan, btnEdit, btnHapus, btnInputGambarProduk, btnViewDataProduk;
    private ImageView imageViewTambahProduk;
    private String gambarPath = "";

    private static final int PICK_IMAGE_REQUEST = 1;

    private DatabaseHelperProduk databaseHelperProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_data_produk);

        databaseHelperProduk = new DatabaseHelperProduk(this);

        editTextId = findViewById(R.id.editTextIdTambahProduk);
        editTextNama = findViewById(R.id.editTextNamaTambahProduk);
        editTextHarga = findViewById(R.id.editTextHargaTambahProduk);
        editTextStok = findViewById(R.id.editTextStokTambahProduk);
        editTextDeskripsi = findViewById(R.id.editTextDeskripsiTambahProduk);
        imageViewTambahProduk = findViewById(R.id.imageViewTambahProduk);

        btnSimpan = findViewById(R.id.buttonSimpanTambahProduk);
        btnEdit = findViewById(R.id.buttonEditTambahProduk);
        btnHapus = findViewById(R.id.buttonHapusProduk);
        btnInputGambarProduk = findViewById(R.id.buttonInputGambarProduk);
        btnViewDataProduk = findViewById(R.id.buttonViewDataProduk);

        String mode = getIntent().getStringExtra("mode");
        if (mode != null && mode.equals("update")) {
            String id = getIntent().getStringExtra("id");
            loadProdukData(id);
        }

        btnInputGambarProduk.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        btnSimpan.setOnClickListener(v -> {
            String id = editTextId.getText().toString();
            String nama = editTextNama.getText().toString();
            double harga = Double.parseDouble(editTextHarga.getText().toString());
            int stok = Integer.parseInt(editTextStok.getText().toString());
            String deskripsi = editTextDeskripsi.getText().toString();

            boolean success = databaseHelperProduk.insertProduk(id, nama, harga, stok, deskripsi, gambarPath);
            if (success) {
                Toast.makeText(this, "Produk berhasil ditambahkan", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Gagal menambahkan produk", Toast.LENGTH_SHORT).show();
            }
        });

        btnEdit.setOnClickListener(v -> {
            String id = editTextId.getText().toString();
            String nama = editTextNama.getText().toString();
            double harga = Double.parseDouble(editTextHarga.getText().toString());
            int stok = Integer.parseInt(editTextStok.getText().toString());
            String deskripsi = editTextDeskripsi.getText().toString();

            boolean success = databaseHelperProduk.updateProduk(id, nama, harga, stok, deskripsi, gambarPath);
            if (success) {
                Toast.makeText(this, "Produk berhasil diperbarui", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Gagal memperbarui produk", Toast.LENGTH_SHORT).show();
            }
        });

        btnHapus.setOnClickListener(v -> {
            String id = editTextId.getText().toString();

            boolean success = databaseHelperProduk.deleteProduk(id);
            if (success) {
                Toast.makeText(this, "Produk berhasil dihapus", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Gagal menghapus produk", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener tombol View Data
        btnViewDataProduk.setOnClickListener(v -> {
            Intent intent = new Intent(ManageDataProduk.this, MenuProdukActivity.class);
            startActivity(intent);
        });
    }

    private void loadProdukData(String id) {
        for (HashMap<String, String> produk : databaseHelperProduk.getAllProduk()) {
            if (produk.get("id").equals(id)) {
                editTextId.setText(produk.get("id"));
                editTextNama.setText(produk.get("nama"));
                editTextHarga.setText(produk.get("harga"));
                editTextStok.setText(produk.get("stok"));
                editTextDeskripsi.setText(produk.get("deskripsi"));

                gambarPath = produk.get("gambar");
                if (!gambarPath.isEmpty()) {
                    imageViewTambahProduk.setImageURI(Uri.parse(gambarPath));
                }

                editTextId.setEnabled(false);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imageViewTambahProduk.setImageURI(selectedImage);
            gambarPath = getRealPathFromURI(selectedImage);
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return "";
    }
}
