package com.example.penjualanpakaian;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelperTransaksi extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "penjualanpakaian.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TRANSAKSI = "transaksi";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRODUK = "produk";
    private static final String COLUMN_HARGA = "harga";
    private static final String COLUMN_QTY = "qty";
    private static final String COLUMN_TOTAL = "total";


    private static final String CREATE_TABLE_TRANSAKSI = "CREATE TABLE " + TABLE_TRANSAKSI + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PRODUK + " TEXT NOT NULL, " +
            COLUMN_HARGA + " REAL NOT NULL, " +
            COLUMN_QTY + " INTEGER NOT NULL, " +
            COLUMN_TOTAL + " REAL NOT NULL)";

    public DatabaseHelperTransaksi(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TRANSAKSI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSAKSI);
        onCreate(db);
    }


    public boolean insertTransaksi(String produk, double harga, int qty, double total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUK, produk);
        contentValues.put(COLUMN_HARGA, harga);
        contentValues.put(COLUMN_QTY, qty);
        contentValues.put(COLUMN_TOTAL, total);

        long result = db.insert(TABLE_TRANSAKSI, null, contentValues);
        db.close();
        return result != -1;
    }


    public boolean updateTransaksi(DataTransaksi transaksi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUK, transaksi.getProduk());
        contentValues.put(COLUMN_HARGA, transaksi.getHarga());
        contentValues.put(COLUMN_QTY, transaksi.getQty());
        contentValues.put(COLUMN_TOTAL, transaksi.getTotal());

        int result = db.update(TABLE_TRANSAKSI, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(transaksi.getId())});
        return result > 0;
    }



    public boolean deleteTransaksi(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_TRANSAKSI, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }


    public ArrayList<DataTransaksi> getAllTransaksi() {
        ArrayList<DataTransaksi> transaksiList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRANSAKSI, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int transaksiId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String produk = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUK));
                double harga = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_HARGA));
                int qty = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QTY));
                double total = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TOTAL));

                DataTransaksi transaksi = new DataTransaksi(transaksiId, produk, harga, qty, total);
                transaksiList.add(transaksi);
            }
            cursor.close();
        }
        return transaksiList;
    }



    public DataTransaksi getTransaksiById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRANSAKSI + " WHERE " +
                COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int transaksiId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String produk = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUK));
                double harga = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_HARGA));
                int qty = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QTY));
                double total = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TOTAL));

                DataTransaksi transaksi = new DataTransaksi(transaksiId, produk, harga, qty, total);
                cursor.close();
                return transaksi;
            }
            cursor.close();
        }
        return null;
    }

}
