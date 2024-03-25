package com.example.minidevgame;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class DatabaseGame extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bdd_gamedev.sqlite";
    private static final int DATABASE_VERSION = 1;

    public DatabaseGame(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public String getTable()
    {
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT name FROM sqlite_master WHERE type='table';";
        try
        {
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null && cursor.moveToFirst())
            {
                do {
                    @SuppressLint("Range") String tableName = cursor.getString(cursor.getColumnIndex("name"));
                    System.out.println("Table name: " +tableName);
                } while (cursor.moveToNext());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }

        return selectQuery;
    }

    public long insererUtilisateur(String nom, String motDePasse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("mot_de_passe", motDePasse);
        long newRowId = db.insert("utilisateur", null, values);
        db.close();
        return newRowId;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE utilisateur (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT NOT NULL, mot_de_passe TEXT NOT NULL)");
            db.execSQL("CREATE TABLE image (id INTEGER PRIMARY KEY AUTOINCREMENT, image_blob BLOB NOT NULL UNIQUE, nom TEXT)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Gérer les mises à jour de la structure de la base de données (si nécessaire)
    }
    public InputStream getImageFromDatabase(String imageName) {
        InputStream inputStream = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT image_blob FROM image WHERE nom = ?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{imageName});
            if (cursor != null && cursor.moveToFirst()) {
                @SuppressLint("Range") byte[] imageBlob = cursor.getBlob(cursor.getColumnIndex("image"));
                if (imageBlob != null) {
                    inputStream = new ByteArrayInputStream(imageBlob);
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

}

