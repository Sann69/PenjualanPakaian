package com.example.penjualanpakaian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button btnLogin;

    DatabaseHelper db = new DatabaseHelper(MainActivity.this);

    private byte counter = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        username = findViewById(R.id.editTextUsrLogin);
        password = findViewById(R.id.editTextPassLogin);
        btnLogin = findViewById(R.id.buttonLogin);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Validate(username.getText().toString(), password.getText().toString());

                String enteredUsername = username.getText().toString();
                String enteredPassword = password.getText().toString();

                // Validate login credentials using database
                Validate(enteredUsername, enteredPassword, db);
            }
        });
    }
    private void Validate(String Username, String Password, DatabaseHelper db){
        // Verify credentials from database
        boolean isValid = db.checkUserCredentials(Username, Password);

        if (isValid) {
            // If valid, proceed to the next activity
            Intent intent = new Intent(MainActivity.this, MenuUtamaActivity.class);
            startActivity(intent);
        } else {
            // Decrement counter for failed attempts
            counter--;
            Toast.makeText(MainActivity.this, "Toleransi jumlah kesalahan : " + counter, Toast.LENGTH_SHORT).show();

            // Disable the login button after 3 failed attempts
            if (counter == 0) {
                btnLogin.setEnabled(false);
            }
        }
    }
}

