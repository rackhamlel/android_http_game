package com.example.minidevgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // Récupérer le score depuis l'intent
        int score = getIntent().getIntExtra("score", 0);

        // Afficher le score dans un TextView
        TextView textViewScore = findViewById(R.id.textViewScore);
        textViewScore.setText("Votre score : " + score +"/10");

        // Bouton Rejouer
        Button btnRejouer = findViewById(R.id.btnRejouer);
        btnRejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer HTTPGameActivity
                Intent intent = new Intent(ScoreActivity.this, HTTPGameActivity.class);
                startActivity(intent);
                // Terminer ScoreActivity pour éviter de revenir en arrière
                finish();
            }
        });

        // Bouton Retour Menu
        Button btnRetourMenu = findViewById(R.id.btnRetourMenu);
        btnRetourMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer MenuActivity
                int userId = getIntent().getIntExtra("userId", -1);
                Intent intent = new Intent(ScoreActivity.this, MenuActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);

                finish();
            }
        });
    }
}

