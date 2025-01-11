package com.example.penjualanpakaian;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MenuUserActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_READIMAGE = 1001;

    GridView gridUser;
    Button addUser;
    SearchView searchUser;
    ArrayList<DataUser> data_user = new ArrayList<>();
    DataUser tempData;

    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gridUser = findViewById(R.id.gridViewData);
        addUser = findViewById(R.id.buttonTambahUser);
        searchUser = findViewById(R.id.searchUser);




        // Cek dan minta izin akses gambar
        showPermission();

        // Tombol tambah user
        addUser.setOnClickListener(v -> {
            Intent intent = new Intent(MenuUserActivity.this, ManageDataUser.class);
            intent.putExtra("Mode", "Insert");
            startActivity(intent);
        });

        // Load data dari database
        if (databaseHelper.getuserCountData() > 0) {
            data_user = databaseHelper.transfertoArrayList(getApplicationContext());
            if (data_user.size() > 0) {
                setAdapterGrid();
            }
        }

        searchUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchUsers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchUsers(newText);
                return false;
            }


        });


    }

    private void searchUsers(String query){
        Cursor cursor = databaseHelper.searchUser(query);

        if (cursor != null && cursor.getCount() > 0) {
            data_user.clear();

            while (cursor.moveToNext()){
                String id =  cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String nama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
                int umur = cursor.getInt(cursor.getColumnIndexOrThrow("umur"));
                String telepon =  cursor.getString(cursor.getColumnIndexOrThrow("telepon"));
                String jabatan =  cursor.getString(cursor.getColumnIndexOrThrow("jabatan"));
                String path =  cursor.getString(cursor.getColumnIndexOrThrow("path"));
                String password =  cursor.getString(cursor.getColumnIndexOrThrow("password"));
                DataUser user = new DataUser(id,nama,umur,telepon,jabatan,path,password);
                data_user.add(user);

            }

            setAdapterGrid();

        }else{
            gridUser.setAdapter(null);
        }

        if (cursor != null){
            cursor.close();

        }
    }
    class myListAdapter extends ArrayAdapter<DataUser> {
        public myListAdapter() {
            super(MenuUserActivity.this, R.layout.data_user, data_user);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.data_user, parent, false);
            }

            DataUser myUsr = data_user.get(position);

            ImageView imv = convertView.findViewById(R.id.imageViewUser);
            TextView tvNama = convertView.findViewById(R.id.textViewNamaUser);
            TextView tvJabatan = convertView.findViewById(R.id.textViewJabatanUser);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;
            Bitmap bMap = BitmapFactory.decodeFile(myUsr.getPath(), options);
            imv.setImageBitmap(bMap);
            tvNama.setText(myUsr.getNama());
            tvJabatan.setText(myUsr.getJabatan());
            return convertView;
        }
    }

    private void setAdapterGrid() {
        gridUser.setOnItemClickListener((adapterView, view, i, l) -> {
            tempData = data_user.get(i);
            Intent intent = new Intent(MenuUserActivity.this, ManageDataUser.class);
            intent.putExtra("data", tempData);
            intent.putExtra("Mode", "updatedelete");
            startActivity(intent);
        });
        ArrayAdapter<DataUser> adapter = new myListAdapter();
        gridUser.setAdapter(adapter);
    }

    // Metode untuk meminta izin akses gambar
    public void showPermission() {
        String kindPermission;
        if (Build.VERSION.SDK_INT >= 33) {
            kindPermission = Manifest.permission.READ_MEDIA_IMAGES;
        } else {
            kindPermission = Manifest.permission.READ_EXTERNAL_STORAGE;
        }

        if (ContextCompat.checkSelfPermission(this, kindPermission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, kindPermission)) {
                showExplanation("Permission Needed", "Application needs permission to access images.", kindPermission, REQUEST_PERMISSION_READIMAGE);
            } else {
                requestPermission(kindPermission, REQUEST_PERMISSION_READIMAGE);
            }
        } else {
            Toast.makeText(this, "Permission Read Image Granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this, new String[]{permissionName}, permissionRequestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0) {
            if (requestCode == REQUEST_PERMISSION_READIMAGE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void showExplanation(String title, String message, String permission, int permissionCode) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> requestPermission(permission, permissionCode))
                .create()
                .show();
    }
}
