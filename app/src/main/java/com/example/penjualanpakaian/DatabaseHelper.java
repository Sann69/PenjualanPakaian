package com.example.penjualanpakaian;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Queue;

public class DatabaseHelper extends SQLiteOpenHelper {
//   variable create database
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME = "Toko.db";

//    create table user and it's attributes
    private static final String TABLE_USER = "Pengguna";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAMA = "nama";
    private static final String COLUMN_UMUR = "umur";
    private static final String COLUMN_TELEPON= "telepon";
    private static final String COLUMN_JABATAN = "jabatan";
    private static final String COLUMN_PATH_USER = "path";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_TEST_USER = "testUser";

    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS "+TABLE_USER
            +"("
            + COLUMN_ID+ " TEXT PRIMARY KEY, "
            + COLUMN_NAMA+" TEXT, "
            +COLUMN_UMUR+" INTEGER, "
            +COLUMN_TELEPON+ " TEXT, "
            +COLUMN_JABATAN+ " TEXT, "
            +COLUMN_PATH_USER+ " TEXT, "
            +COLUMN_PASSWORD+ " TEXT "
            +")";

//    TODO: MAKE ANOTHER TABLE HERE


//    access object from SQLiteDatabase
    SQLiteDatabase db;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
////        create ur table here
//        // TODO: ADD ANOTHER TABLE HERE
//        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
//        db = sqLiteDatabase;
//
//    }

    //usr.pass
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);

        // Tambahkan user default
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, "admin");
        values.put(COLUMN_NAMA, "admin");
        values.put(COLUMN_PASSWORD, "admin123");

        sqLiteDatabase.insert(TABLE_USER, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//      change database version here
        String query;
        if (i>1){
//            add all tables here
//            TODO: ADD ANOTHER TABLE HERE
            query="ALTER TABLE "+TABLE_USER+" ADD COLUMN "+COLUMN_TEST_USER+" TEXT ;";
            db.execSQL(query);
        }
    }

//    user table
    public int getuserCountData(){
        int result = 0;

        db=getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER,new String[]{COLUMN_ID}, null, null, null, null, COLUMN_ID+" ASC ");
        result = cursor.getCount();

        db.close();

        return result;
    }

//    check if data exist to make sure data not duplicated
    public DataUser getExistDataUser(Context context, String key){
        DataUser usr = null;

        try {
            String query = "SELECT * FROM " + TABLE_USER + " WHERE upper(" + COLUMN_ID + ") = '" + key.toUpperCase()+"'";
            db = getReadableDatabase();

            Cursor cursor = db.rawQuery(query, null);

            if (cursor.getCount() > 0){
                cursor.moveToFirst();

                usr = new DataUser(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_UMUR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEPON)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JABATAN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATH_USER)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))

                );
            }

            }catch (Exception e){
            Toast.makeText(context, e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        db.close();
        return usr;
    }

//    transfer data user from sqlite to arrayList
    public ArrayList<DataUser> transfertoArrayList(Context context){
        ArrayList<DataUser> arrUser = null;

        try {
            db = getReadableDatabase();

            Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_ID, COLUMN_NAMA, COLUMN_UMUR, COLUMN_TELEPON, COLUMN_JABATAN, COLUMN_PATH_USER, COLUMN_PASSWORD}, null, null,null, null, COLUMN_ID + " ASC");

            if (cursor.getCount() > 0){
                arrUser = new ArrayList<DataUser>();

                cursor.moveToFirst();

                do {
                    arrUser.add(new DataUser(
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_UMUR)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEPON)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JABATAN)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATH_USER)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
                    ));

                }while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
        db.close();
        return arrUser;
    }

//    insert data user
    public boolean insertDataUser(Context context,DataUser dataUser){
        boolean benar = false;

        try{
            db = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, dataUser.getId());
            values.put(COLUMN_NAMA, dataUser.getNama());
            values.put(COLUMN_UMUR, dataUser.getUmur());
            values.put(COLUMN_TELEPON, dataUser.getTelepon());
            values.put(COLUMN_JABATAN, dataUser.getJabatan());
            values.put(COLUMN_PATH_USER, dataUser.getPath());
            values.put(COLUMN_PASSWORD, dataUser.getPassword());

            long result = db.insert(TABLE_USER, null, values);

            if (result > -1){
                benar = true;
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        db.close();
        return benar;
    }

//    update data user
    public boolean updateDataUser(Context context, DataUser data){
        boolean benar = false;

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, data.getId());
            values.put(COLUMN_NAMA, data.getNama());
            values.put(COLUMN_UMUR, data.getUmur());
            values.put(COLUMN_TELEPON, data.getTelepon());
            values.put(COLUMN_JABATAN, data.getJabatan());
            values.put(COLUMN_PATH_USER, data.getPath());
            values.put(COLUMN_PASSWORD, data.getPassword());

            db = getWritableDatabase();
            db.update(TABLE_USER,values,COLUMN_ID+"=?",new String[]{data.getId()});

            db.close();

            benar = true;
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return benar;
    }

//    delete data user
    public int deleteDataUser(Context context, String id){
        int jmlDelete= -1;

        try {
            db = getWritableDatabase();

            jmlDelete = db.delete(TABLE_USER, COLUMN_ID+"=?", new String[]{id});
            db.close();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return jmlDelete;
    }
//    checked username and password login

    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_NAMA + " = ? AND " + COLUMN_PASSWORD + " = ?",
                new String[]{username, password}
        );

        boolean result = cursor.getCount() > 0; // True if user exists
        cursor.close();
        db.close();
        return result;
    }



}
