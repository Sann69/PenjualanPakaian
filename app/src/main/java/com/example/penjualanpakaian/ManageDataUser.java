package com.example.penjualanpakaian;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ManageDataUser extends AppCompatActivity {
    ImageView imv;
    Button btnInputFile, btnSimpan, btEdit, btDelete, btView;
    EditText etId, etNama, etUmur, etTelp, etJabatan, etpass;
    DataUser data = null;
    String mode, path;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_data_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imv = (ImageView) findViewById(R.id.imageViewTambahUser);
        btnInputFile = (Button) findViewById(R.id.buttonInputTambahUsr);
        btnSimpan = (Button) findViewById(R.id.buttonSimpanTambahUsr);
        btEdit = (Button) findViewById(R.id.buttonEditTambahUsr);
        btDelete = (Button) findViewById(R.id.buttonHapusTambahUsr);
        etId = (EditText) findViewById(R.id.editTextIdTambahUsr);
        etNama = (EditText) findViewById(R.id.editTextNamaTambahUsr);
        etUmur = (EditText) findViewById(R.id.editTextUmurTambahUsr);
        etTelp = (EditText) findViewById(R.id.editTextTelpTambahUsr);
        etJabatan = (EditText) findViewById(R.id.editTextJabatanTambahUsr);
        etpass = (EditText) findViewById(R.id.editTextPassTambahUsr);
        btView = (Button) findViewById(R.id.buttonViewDatausr);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAlert("insert");
            }
        });

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAlert("update");
            }
        });
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAlert("delete");
            }
        });
        btnInputFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myPath = Environment.getExternalStorageState()+"/"+"Pictures"+"/";
//                set path uri
                Uri uri=Uri.parse(myPath);
//                buat intent untuk mengarahkan pemiliham image ke path yang sudah diset
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                filter file dengan type image
                intent.setDataAndType(uri, "image/*");
//                menampilkan activity untuk action pick
                activityResultLauncher.launch(intent);
            }
        });

        btView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageDataUser.this,MenuUserActivity.class);
                startActivity(intent);
            }
        });

//        refresh activity
        refreshActivity();
//        get intent from  menu user activity
        Intent intent = getIntent();
        mode = intent.getStringExtra("Mode");

//        TODO :create enable/disable button
        if (mode.equalsIgnoreCase("Insert")){
            btnSimpan.setEnabled(true);
            btEdit.setEnabled(false);
            btDelete.setEnabled(false);
        } else{
            btDelete.setEnabled(true);
            btEdit.setEnabled(true);
            btnSimpan.setEnabled(false);

//            get data
            data = intent.getParcelableExtra("data");
            etId.setEnabled(false);

            Bitmap bMap = BitmapFactory.decodeFile(data.getPath().toString());
            imv.setImageBitmap(bMap);

            path=data.getPath();

            etId.setText(data.getId());
            etNama.setText(data.getNama());
            etUmur.setText(String.valueOf(data.getUmur()));
            etTelp.setText(String.valueOf(data.getTelepon()));
            etJabatan.setText(data.getJabatan());
            etpass.setText(data.getPassword());
        }




    }

    // clear all activity and path value
    public void refreshActivity(){
//        TODO: CHANGE IMV IMAGE TO PHOTO PROFILE SAMPLE
        imv.setImageResource(R.drawable.ic_launcher_background);
        etId.setText("");
        etNama.setText("");
        etUmur.setText("");
        etTelp.setText("");
        etJabatan.setText("");
        path="";
        etpass.setText("");
    }

    public String getRealPath(Context context, Uri contentUri){
        Cursor cursor = null;
        try {
//            mendapatdata dari media data image
            String[] data_media_uri={MediaStore.Images.Media.DATA};

            cursor=context.getContentResolver().query(contentUri,data_media_uri,null,null,null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);

        }finally {

        }
    }
    //    method to get file path while choosing image with action pick
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
//                mendapatkan path uri dari result
                    Uri data = result.getData().getData();
//                menampilkan image
                    imv.setImageURI(data);
//                nilai path sesuai dng path sebenaarnya yang nanti akan disimpan di sqlite karena berbeda dengan path dari uri
                    path = getRealPath(getApplicationContext(),data);
                }
            }
    );

    private void showDialogAlert(String Mode){
        int buttonpic=-1;
        String title="", message="";

//        TODO: CHANGE ICON ACTION
        switch (Mode){
            case "insert":
                title = "Do you sure save data?";
                message = "Click yes to save data";
                buttonpic=R.drawable.ic_launcher_foreground;
                break;
            case "update":
                title = "Do you sure update data?";
                message= "Click yes to update data";
                buttonpic=R.drawable.ic_launcher_foreground;
                break;
            case "delete":
                title = "Do you sure delete data?";
                message="Click yes to delete data";
                buttonpic=R.drawable.ic_launcher_foreground;
                break;
        }

//        build sesuai rancangan diatas
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(message).setIcon(buttonpic).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
//                proses insert update delete akan dieksekusi sesuai mode
                        switch (Mode){
                            case "insert":
//                        cek data ada
                                DataUser usr = databaseHelper.getExistDataUser(
                                        getApplicationContext(),
                                        etId.getText().toString());

//                                cek apakah data kosong
                                if (usr==null){
                                    DataUser dtUser = new DataUser(
                                            etId.getText().toString(),
                                            etNama.getText().toString(),
                                            Integer.parseInt(etUmur.getText().toString()),
                                            etTelp.getText().toString(),
                                            etJabatan.getText().toString(),
                                            path,
                                            etpass.getText().toString()

                                    );
//                            eksekusi proses insert
                                    boolean benarInsert = databaseHelper.insertDataUser(
                                            getApplicationContext(),dtUser
                                    );
//                            tampilkan pesan sesuai nilai benarInsert
                                    if (benarInsert){
                                        Toast.makeText(ManageDataUser.this,"Insert Success",Toast.LENGTH_SHORT).show();
                                        refreshActivity();
                                    }else {
                                        Toast.makeText(ManageDataUser.this, "Insert Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
//                            pesan untuk menampilkan nim sudah dipakai
                                    Toast.makeText(ManageDataUser.this,"Id owned by "+usr.getNama(),Toast.LENGTH_LONG).show();
                                }
                                break;
                            case "update":
                                DataUser myUser = new DataUser(
                                        etId.getText().toString(),
                                        etNama.getText().toString(),
                                        Integer.parseInt(etUmur.getText().toString()),
                                        etTelp.getText().toString(),
                                        etJabatan.getText().toString(),
                                        path,
                                        etpass.getText().toString()
                                );

                                boolean benarUpdate = databaseHelper.updateDataUser(getApplicationContext(), myUser);
                                if (benarUpdate){
                                    Toast.makeText(ManageDataUser.this, "Update Success", Toast.LENGTH_SHORT).show();
                                    refreshActivity();
                                }else {
                                    Toast.makeText(ManageDataUser.this, "Update Field", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case "delete":
                                int benarDelete=databaseHelper.deleteDataUser(getApplicationContext(), etId.getText().toString());
                                if (benarDelete>0){
                                    Toast.makeText(ManageDataUser.this,"Delete successs", Toast.LENGTH_SHORT).show();
                                    refreshActivity();
                                }else {
                                    Toast.makeText(ManageDataUser.this,"Delete failed", Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }
}

