package com.example.penjualanpakaian;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditDataTransaksiActivity extends AppCompatActivity {

    private EditText etProduk, etHarga, etQty;
    private TextView tvTotal;
    private Button btnSimpan, btnHapus;
    private DatabaseHelperTransaksi databaseHelper;
    private DataTransaksi datatransaksi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_transaksi);


        etProduk = findViewById(R.id.editTextText5);
        etHarga = findViewById(R.id.editTextText6);
        etQty = findViewById(R.id.editTextText7);
        tvTotal = findViewById(R.id.textTotal);
        btnSimpan = findViewById(R.id.button3);
        btnHapus = findViewById(R.id.button4);


        databaseHelper = new DatabaseHelperTransaksi(this);


        int transaksiId = getIntent().getIntExtra("transaksiId", -1);


        datatransaksi = getTransaksiById(transaksiId);

        if (datatransaksi != null) {

            etProduk.setText(datatransaksi.getProduk());
            etHarga.setText(String.valueOf(datatransaksi.getHarga()));
            etQty.setText(String.valueOf(datatransaksi.getQty()));
            hitungTotal();
        }


        etHarga.setOnFocusChangeListener((v, hasFocus) -> hitungTotal());
        etQty.setOnFocusChangeListener((v, hasFocus) -> hitungTotal());


        btnSimpan.setOnClickListener(v -> {
            String produk = etProduk.getText().toString();
            String hargaStr = etHarga.getText().toString();
            String qtyStr = etQty.getText().toString();

            if (produk.isEmpty() || hargaStr.isEmpty() || qtyStr.isEmpty()) {
                Toast.makeText(EditDataTransaksiActivity.this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double harga = Double.parseDouble(hargaStr);
                int qty = Integer.parseInt(qtyStr);
                double total = harga * qty;


                datatransaksi.setProduk(produk);
                datatransaksi.setHarga(harga);
                datatransaksi.setQty(qty);
                datatransaksi.setTotal(total);


                boolean isUpdated = databaseHelper.updateTransaksi(datatransaksi);

                if (isUpdated) {
                    Toast.makeText(EditDataTransaksiActivity.this, "Transaksi berhasil diperbarui", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditDataTransaksiActivity.this, "Gagal memperbarui transaksi", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(EditDataTransaksiActivity.this, "Masukkan angka yang valid untuk harga dan qty", Toast.LENGTH_SHORT).show();
            }
        });


        btnHapus.setOnClickListener(v -> new AlertDialog.Builder(EditDataTransaksiActivity.this)
                .setTitle("Hapus Transaksi")
                .setMessage("Apakah Anda yakin ingin menghapus transaksi ini?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    boolean isDeleted = databaseHelper.deleteTransaksi(datatransaksi.getId());

                    if (isDeleted) {
                        Toast.makeText(EditDataTransaksiActivity.this, "Transaksi berhasil dihapus", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditDataTransaksiActivity.this, "Gagal menghapus transaksi", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Tidak", null)
                .show());
    }


    private DataTransaksi getTransaksiById(int id) {
        return databaseHelper.getTransaksiById(id);
    }


    private void hitungTotal() {
        String hargaStr = etHarga.getText().toString();
        String qtyStr = etQty.getText().toString();

        if (!hargaStr.isEmpty() && !qtyStr.isEmpty()) {
            try {
                double harga = Double.parseDouble(hargaStr);
                int qty = Integer.parseInt(qtyStr);
                double total = harga * qty;
                tvTotal.setText("Total: " + total);
            } catch (NumberFormatException e) {
                tvTotal.setText("Total: 0");
            }
        } else {
            tvTotal.setText("Total: 0");
        }
    }
}
