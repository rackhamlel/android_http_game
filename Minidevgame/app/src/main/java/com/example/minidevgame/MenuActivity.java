package com.example.minidevgame;
import android.view.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Déplacez la déclaration du bouton ici pour qu'il soit initialisé après le setContentView()
        Button btnJouer = findViewById(R.id.btnJouer);
        Button btnSucces = findViewById(R.id.btnSucces);
        Button btnRegles = findViewById(R.id.btnRegles);
        btnJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllerHTTPGame();
            }
        });
        btnSucces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllerSucces();
            }
        });
        btnRegles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllerRegles();
            }
        });
    }

    private void AllerHTTPGame() {
        // Créez un Intent pour passer de l'activité actuelle à HTTPGameActivity
        int userId = getIntent().getIntExtra("userId", -1);
        Intent intent = new Intent(MenuActivity.this, HTTPGameActivity.class);
        intent.putExtra("userId", userId);
        // Démarrer l'activité HTTPGameActivity
        startActivity(intent);
    }
    private void AllerSucces() {
        // Créez un Intent pour passer de l'activité actuelle à HTTPGameActivity
        int userId = getIntent().getIntExtra("userId", -1);
        Intent intent = new Intent(MenuActivity.this, SuccesActivity.class);
        intent.putExtra("userId", userId);
        // Démarrer l'activité HTTPGameActivity
        startActivity(intent);
    }
    private void AllerRegles() {
        // Créez un Intent pour passer de l'activité actuelle à HTTPGameActivity
        int userId = getIntent().getIntExtra("userId", -1);
        Intent intent = new Intent(MenuActivity.this, ReglesActivity.class);
        intent.putExtra("userId", userId);
        // Démarrer l'activité HTTPGameActivity
        startActivity(intent);
    }
}

