package com.example.penjualanpakaian;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelperProduk extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TokoProduk.db";

    private static final String TABLE_PRODUK = "Produk";
    private static final String COLUMN_PRODUK_ID = "id_produk";
    private static final String COLUMN_PRODUK_NAMA = "nama_produk";
    private static final String COLUMN_PRODUK_HARGA = "harga";
    private static final String COLUMN_PRODUK_STOK = "stok";
    private static final String COLUMN_PRODUK_DESKRIPSI = "deskripsi";
    private static final String COLUMN_PRODUK_GAMBAR = "gambar_produk";

    private static final String CREATE_TABLE_PRODUK = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUK + " ("
            + COLUMN_PRODUK_ID + " TEXT PRIMARY KEY, "
            + COLUMN_PRODUK_NAMA + " TEXT, "
            + COLUMN_PRODUK_HARGA + " REAL, "
            + COLUMN_PRODUK_STOK + " INTEGER, "
            + COLUMN_PRODUK_DESKRIPSI + " TEXT, "
            + COLUMN_PRODUK_GAMBAR + " TEXT)";

    public DatabaseHelperProduk(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PRODUK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUK);
            onCreate(db);
        }
    }

    public boolean insertProduk(String id, String nama, double harga, int stok, String deskripsi, String gambarPath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUK_ID, id);
        values.put(COLUMN_PRODUK_NAMA, nama);
        values.put(COLUMN_PRODUK_HARGA, harga);
        values.put(COLUMN_PRODUK_STOK, stok);
        values.put(COLUMN_PRODUK_DESKRIPSI, deskripsi);
        values.put(COLUMN_PRODUK_GAMBAR, gambarPath);
        long result = db.insert(TABLE_PRODUK, null, values);
        return result != -1;
    }

    public ArrayList<HashMap<String, String>> getAllProduk() {
        ArrayList<HashMap<String, String>> produkList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUK, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> produk = new HashMap<>();
                produk.put("id", cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUK_ID)));
                produk.put("nama", cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUK_NAMA)));
                produk.put("harga", String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRODUK_HARGA))));
                produk.put("stok", String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRODUK_STOK))));
                produk.put("deskripsi", cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUK_DESKRIPSI)));
                produk.put("gambar", cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUK_GAMBAR)));
                produkList.add(produk);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return produkList;
    }

    public boolean updateProduk(String id, String nama, double harga, int stok, String deskripsi, String gambarPath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUK_NAMA, nama);
        values.put(COLUMN_PRODUK_HARGA, harga);
        values.put(COLUMN_PRODUK_STOK, stok);
        values.put(COLUMN_PRODUK_DESKRIPSI, deskripsi);
        values.put(COLUMN_PRODUK_GAMBAR, gambarPath);

        int rowsAffected = db.update(TABLE_PRODUK, values, COLUMN_PRODUK_ID + "=?", new String[]{id});
        return rowsAffected > 0;
    }

    public boolean deleteProduk(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_PRODUK, COLUMN_PRODUK_ID + "=?", new String[]{id});
        return rowsDeleted > 0;
    }
}