package com.example.penjualanpakaian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuUtamaActivity extends AppCompatActivity {

    Button btnMenuUser, btnMenuProduk, btnMenuTransaksi, btnExitApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);

        btnMenuUser = findViewById(R.id.buttonMenuUsr);
        btnMenuProduk = findViewById(R.id.buttonMenuProduk);
        btnMenuTransaksi = findViewById(R.id.buttonMenuTransaksi);
        btnExitApp = findViewById(R.id.buttonExitApp);

        btnMenuUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUtamaActivity.this, MenuUserActivity.class);
                startActivity(intent);
            }
        });

        btnMenuProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUtamaActivity.this, MenuProdukActivity.class);
                startActivity(intent);
            }
        });

        btnMenuTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUtamaActivity.this, MenuTransaksiActivity.class);
                startActivity(intent);
            }
        });

        btnExitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }
}
