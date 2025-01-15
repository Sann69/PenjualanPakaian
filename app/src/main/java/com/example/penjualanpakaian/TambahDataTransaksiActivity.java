package com.example.penjualanpakaian;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TambahDataTransaksiActivity extends AppCompatActivity {

    private Spinner selectProduct;
    private EditText etProduk, etHarga, etQty;
    private TextView tvTotal;
    private Button btnSimpan;
    private DatabaseHelperTransaksi databaseHelper;

    private DatabaseHelperProduk databaseHelperProduk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_transaksi);


//        etProduk = findViewById(R.id.editTextText);
        selectProduct = findViewById(R.id.selectProduct);
        etHarga = findViewById(R.id.editTextText2);
        etQty = findViewById(R.id.editTextText3);
        tvTotal = findViewById(R.id.textTotal);
        btnSimpan = findViewById(R.id.buttonSimpan);

        databaseHelper = new DatabaseHelperTransaksi(this);
        databaseHelperProduk = new DatabaseHelperProduk(this);

        ArrayList<HashMap<String, String>> produkList = databaseHelperProduk.getAllProduk();

        List<String> namaProdukList = new ArrayList<>();
        for (HashMap<String, String> produk : produkList){
            namaProdukList.add(produk.get("nama"));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, namaProdukList);

        selectProduct.setAdapter(adapter);

//        set onItemSelectedListener utk spinner
        selectProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String harga = produkList.get(position).get("harga");

                etHarga.setText(harga);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        etHarga.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                hitungTotal();
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {

            }
        });

        etQty.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                hitungTotal();
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {

            }
        });


        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String produk = etProduk.getText().toString();
                String hargaStr = etHarga.getText().toString();
                String qtyStr = etQty.getText().toString();

                if (produk.isEmpty() || hargaStr.isEmpty() || qtyStr.isEmpty()) {
                    Toast.makeText(TambahDataTransaksiActivity.this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int qty = Integer.parseInt(qtyStr);
                    double harga = Double.parseDouble(hargaStr);
                    double total = qty * harga;

                    boolean isInserted = databaseHelper.insertTransaksi(produk, harga, qty, total);

                    if (isInserted) {
                        Toast.makeText(TambahDataTransaksiActivity.this, "Transaksi berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(TambahDataTransaksiActivity.this, "Gagal menambahkan transaksi", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(TambahDataTransaksiActivity.this, "Masukkan angka yang valid untuk harga dan qty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void hitungTotal() {
        String hargaStr = etHarga.getText().toString();
        String qtyStr = etQty.getText().toString();

        if (!hargaStr.isEmpty() && !qtyStr.isEmpty()) {
            try {
                double harga = Double.parseDouble(hargaStr);
                int qty = Integer.parseInt(qtyStr);
                double total = harga * qty;
                tvTotal.setText("Total: Rp " + total);
            } catch (NumberFormatException e) {
                tvTotal.setText("Total: Rp 0");
            }
        } else {
            tvTotal.setText("Total: Rp 0");
        }
    }
}
