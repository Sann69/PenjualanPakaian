package com.example.penjualanpakaian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MenuTransaksiActivity extends AppCompatActivity {

    private Button btnTambahTransaksi;
    private GridView gridViewData;
    private DatabaseHelperTransaksi databaseHelper;
    private ArrayList<DataTransaksi> transaksiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_transaksi);


        btnTambahTransaksi = findViewById(R.id.buttonTambahTransaksi);
        gridViewData = findViewById(R.id.gridViewData);


        databaseHelper = new DatabaseHelperTransaksi(this);


        loadDataTransaksi();


        btnTambahTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTransaksiActivity.this, TambahDataTransaksiActivity.class);
                startActivity(intent);
            }
        });


        gridViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataTransaksi transaksi = transaksiList.get(position);


                Intent intent = new Intent(MenuTransaksiActivity.this, EditDataTransaksiActivity.class);
                intent.putExtra("transaksiId", transaksi.getId());
                startActivity(intent);
            }
        });
    }


    private void loadDataTransaksi() {
        transaksiList = databaseHelper.getAllTransaksi();

        if (transaksiList != null && !transaksiList.isEmpty()) {
            ArrayList<String> produkList = new ArrayList<>();
            for (DataTransaksi transaksi : transaksiList) {
                produkList.add(transaksi.getProduk());
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, produkList);
            gridViewData.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Tidak ada data transaksi", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataTransaksi();
    }


    public void onKembaliClicked(View view) {
        finish();
    }
}
