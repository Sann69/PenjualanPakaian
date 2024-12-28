package com.example.penjualanpakaian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuUtamaActivity extends AppCompatActivity {
    Button btnMenuUser, btnMenuProduk, btnMenuTransaksi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_utama);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnMenuUser = (Button) findViewById(R.id.buttonMenuUsr);
        btnMenuProduk = (Button) findViewById(R.id.buttonMenuProduk);
        btnMenuTransaksi = (Button) findViewById(R.id.buttonMenuTransaksi);
        btnMenuUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUtamaActivity.this, MenuUserActivity.class);
                startActivity(intent);
            }
        });
    }
}