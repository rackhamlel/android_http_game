package com.example.minidevgame;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DatabaseGame extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bdd_gamedev.db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseGame instance;

    // Méthode pour obtenir une instance unique de DatabaseGame
    public static synchronized DatabaseGame getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseGame(context.getApplicationContext());
        }
        return instance;
    }
    public DatabaseGame(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

            db.execSQL("CREATE TABLE succes (id INTEGER PRIMARY KEY AUTOINCREMENT, nom_realisation TEXT NOT NULL, description TEXT NOT NULL, status INTEGER NOT NULL)");
            db.execSQL("INSERT INTO succes (nom_realisation, description, status) VALUES \n" +
                    " ('Sans faute', 'Réussir une série sans aucune faute.', 0),\n" +
                    " ('Est de 10!', 'Faire 10 séries.',0),\n" +
                    " ('Omniscience', 'Faire 10 séries sans fautes à la suite.',0);");

            db.execSQL("CREATE TABLE jeux (id INTEGER PRIMARY KEY AUTOINCREMENT, nom_jeu TEXT NOT NULL)");

            db.execSQL("CREATE TABLE score (id INTEGER PRIMARY KEY AUTOINCREMENT, score TEXT NOT NULL, id_jeu INTEGER NOT NULL, id_user INTEGER NOT NULL, FOREIGN KEY (id_jeu) REFERENCES jeux(id_jeu), FOREIGN KEY (id_user) REFERENCES utilisateur(id_user))");

            db.execSQL("CREATE TABLE num_http (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT NOT NULL, numero INTEGER NOT NULL)");
            db.execSQL("INSERT INTO num_http (numero, nom) VALUES\n" +
                    "    (100, 'Continue'),\n" +
                    "    (101, 'Switching Protocols'),\n" +
                    "    (102, 'Processing'),\n" +
                    "    (103, 'Early Hints'),\n" +
                    "    (200, 'OK'),\n" +
                    "    (201, 'Created'),\n" +
                    "    (202, 'Accepted'),\n" +
                    "    (203, 'Non-Authoritative Information'),\n" +
                    "    (204, 'No Content'),\n" +
                    "    (205, 'Reset Content'),\n" +
                    "    (206, 'Partial Content'),\n" +
                    "    (207, 'Multi-Status'),\n" +
                    "    (208, 'Already Reported'),\n" +
                    "    (226, 'IM Used'),\n" +
                    "    (300, 'Multiple Choices'),\n" +
                    "    (301, 'Moved Permanently'),\n" +
                    "    (302, 'Found'),\n" +
                    "    (303, 'See Other'),\n" +
                    "    (304, 'Not Modified'),\n" +
                    "    (305, 'Use Proxy'),\n" +
                    "    (307, 'Temporary Redirect'),\n" +
                    "    (308, 'Permanent Redirect'),\n" +
                    "    (400, 'Bad Request'),\n" +
                    "    (401, 'Unauthorized'),\n" +
                    "    (402, 'Payment Required'),\n" +
                    "    (403, 'Forbidden'),\n" +
                    "    (404, 'Not Found'),\n" +
                    "    (405, 'Method Not Allowed'),\n" +
                    "    (406, 'Not Acceptable'),\n" +
                    "    (407, 'Proxy Authentication Required'),\n" +
                    "    (408, 'Request Timeout'),\n" +
                    "    (409, 'Conflict'),\n" +
                    "    (410, 'Gone'),\n" +
                    "    (411, 'Length Required'),\n" +
                    "    (412, 'Precondition Failed'),\n" +
                    "    (413, 'Payload Too Large'),\n" +
                    "    (414, 'URI Too Long'),\n" +
                    "    (415, 'Unsupported Media Type'),\n" +
                    "    (416, 'Range Not Satisfiable'),\n" +
                    "    (417, 'Expectation Failed'),\n" +
                    "    (418, 'I''m a teapot'),\n" +
                    "    (421, 'Misdirected Request'),\n" +
                    "    (422, 'Unprocessable Entity'),\n" +
                    "    (423, 'Locked'),\n" +
                    "    (424, 'Failed Dependency'),\n" +
                    "    (425, 'Too Early'),\n" +
                    "    (426, 'Upgrade Required'),\n" +
                    "    (428, 'Precondition Required'),\n" +
                    "    (429, 'Too Many Requests'),\n" +
                    "    (431, 'Request Header Fields Too Large'),\n" +
                    "    (451, 'Unavailable For Legal Reasons'),\n" +
                    "    (500, 'Internal Server Error'),\n" +
                    "    (501, 'Not Implemented'),\n" +
                    "    (502, 'Bad Gateway'),\n" +
                    "    (503, 'Service Unavailable'),\n" +
                    "    (504, 'Gateway Timeout'),\n" +
                    "    (505, 'HTTP Version Not Supported'),\n" +
                    "    (506, 'Variant Also Negotiates'),\n" +
                    "    (507, 'Insufficient Storage'),\n" +
                    "    (508, 'Loop Detected'),\n" +
                    "    (510, 'Not Extended'),\n" +
                    "    (511, 'Network Authentication Required');\n");

            db.execSQL("CREATE TABLE utilisateur_succes (id_user INTEGER NOT NULL, id_succes INTEGER NOT NULL, id_utilisateur_succes INTEGER PRIMARY KEY AUTOINCREMENT, date_debloquer DATE, FOREIGN KEY (id_user) REFERENCES utilisateur(id_user), FOREIGN KEY (id_succes) REFERENCES succes(id_succes))");

            db.execSQL("CREATE TABLE lookup (id_jeu INTEGER NOT NULL, id_http INTEGER NOT NULL, PRIMARY KEY (id_jeu, id_http), FOREIGN KEY (id_jeu) REFERENCES jeux(id_jeu), FOREIGN KEY (id_http) REFERENCES num_http(id_http))");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Gérer les mises à jour de la structure de la base de données (si nécessaire)
    }
    @SuppressLint("Range")
    public String getRandomName() {
        String randomName = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nom FROM num_http ORDER BY RANDOM() LIMIT 1", null);
        if (cursor != null && cursor.moveToFirst()) {
            randomName = cursor.getString(cursor.getColumnIndex("nom"));
            cursor.close();
        }
        return randomName;
    }

    @SuppressLint("Range")
    public int recupererNumero(String nomAleatoire) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT numero FROM num_http WHERE nom = ?", new String[]{nomAleatoire});
        int numeroLie = -1; // Valeur par défaut si aucun numéro n'est trouvé
        if (cursor != null && cursor.moveToFirst()) {
            numeroLie = cursor.getInt(cursor.getColumnIndex("numero"));
        }
        if (cursor != null) {
            cursor.close();
        }
        return numeroLie;
    }


    private static int currentUserId = -1; // Variable pour stocker l'ID de l'utilisateur connecté

    // Méthode pour stocker l'ID de l'utilisateur connecté
    public void setCurrentUserId(int userId) {
        currentUserId = userId;
    }



    public void updateSucceSansFaute(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Vérifier si l'entrée existe déjà
        String query = "SELECT COUNT(*) FROM utilisateur_succes WHERE id_user = ? AND id_succes = ?";
        String[] selectionArgs = { String.valueOf(userId), "1" }; // "1" est l'identifiant du succès
        Cursor cursor = db.rawQuery(query, selectionArgs);

        // Si l'entrée n'existe pas, l'insérer
        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();

            if (count == 0) {
                // Insérer une nouvelle entrée avec la date de déblocage actuelle
                String insertQuery = "INSERT INTO utilisateur_succes (id_user, id_succes, date_debloquer) VALUES (?, ?, CURRENT_TIMESTAMP)";
                String[] insertArgs = { String.valueOf(userId), "1" }; // "1" est l'identifiant du succès
                db.execSQL(insertQuery, insertArgs);
            }
        }

        db.close();
    }
    // Hachage de mot de passe
    public static String hashMotDePasse(String password) {
        try {
            // instance de MessageDigest avec l'algorithme SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = password.getBytes("UTF-8"); // Convertit le mot de passe en tableau d'octets
            byte[] hash = digest.digest(bytes); // Calcule le hachage des octets du mot de passe
            return Base64.encodeToString(hash, Base64.NO_WRAP); // Encode le hachage en base64 et retour en chaine de caractères
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }




}

