package com.example.minidevgame;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private DatabaseGame mDBHelper;

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
        mDBHelper = new DatabaseGame(this);

        try {
            mDBHelper.copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les erreurs lors de la copie de la base de données
        }

        // Maintenant que la base de données est copiée, vous pouvez l'ouvrir et accéder à vos données
        SQLiteDatabase db = mDBHelper.openDatabase();

        // Exemple de requête pour accéder aux données
        Cursor cursor = db.rawQuery("SELECT * FROM your_table", null);
        // Utilisez le curseur pour accéder aux données...

        // N'oubliez pas de fermer la base de données et le curseur lorsque vous avez fini avec eux
        cursor.close();
        db.close();

    }
}