package com.example.minidevgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.LinearLayout;

public class SuccesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes);
        int userId = getIntent().getIntExtra("userId", -1);

        // Ajout d'un OnClickListener pour le bouton Menu
        Button btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aller au menu lorsque le bouton Menu est cliqué
                allerAuMenu();
            }
        });
        // Récupération de l'instance de la base de données
        DatabaseGame dbHelper = new DatabaseGame(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT s.id, s.nom_realisation, s.description, " +
                        "CASE WHEN us.id_succes IS NOT NULL THEN 1 ELSE 0 END AS status " +
                        "FROM succes s " +
                        "LEFT JOIN utilisateur_succes us ON s.id = us.id_succes AND us.id_user = ?",
                new String[] { String.valueOf(userId) });




        // Vérifier si le curseur contient des données
        if (cursor != null && cursor.moveToFirst()) {
            LinearLayout succesLayout = findViewById(R.id.succesLayout);


            do {
                @SuppressLint("Range") int succesId = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String succesName = cursor.getString(cursor.getColumnIndex("nom_realisation")).toUpperCase();
                @SuppressLint("Range") String succesDescription = cursor.getString(cursor.getColumnIndex("description"));
                @SuppressLint("Range") int succesStatus = cursor.getInt(cursor.getColumnIndex("status"));


                TextView textView = new TextView(this);
                textView.setText(succesName + "\n" + succesDescription + "\n" + (succesStatus == 1 ? "VALIDE" : "NON VALIDE")+"\n \n");
                succesLayout.addView(textView);
            } while (cursor.moveToNext());

            // Fermer le curseur
            cursor.close();
        } else {
            // Si le curseur est vide, afficher un message
            TextView textView = findViewById(R.id.textViewMessage);
            textView.setText("Aucun succès trouvé.");
        }

        // Fermer la base de données
        db.close();
    }
    private void allerAuMenu() {
        int userId = getIntent().getIntExtra("userId", -1);
        Intent intent = new Intent(SuccesActivity.this, MenuActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

}
