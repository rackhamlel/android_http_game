package com.example.minidevgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.minidevgame.R;


public class InscriptionActivity extends AppCompatActivity {

    private EditText inscription_nom;
    private EditText inscription_mdp;
    private Button inscription_button;

    private DatabaseGame DatabaseGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        // Initialise les vues
        inscription_nom = findViewById(R.id.inscription_nom);
        inscription_mdp = findViewById(R.id.inscription_mdp);
        inscription_button = findViewById(R.id.inscription_button);

        // Initialise le DatabaseHelper
        DatabaseGame = new DatabaseGame(this);

        // Définit un écouteur de clic pour le bouton "Créer un compte"
        inscription_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Récupère les valeurs saisies par l'utilisateur
                    String nom = inscription_nom.getText().toString().trim();
                    String mot_de_passe = inscription_mdp.getText().toString().trim();

                    // Insère l'utilisateur dans la base de données
                    insererUtilisateur(nom, mot_de_passe);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Affiche un message d'erreur en cas d'échec
                    Toast.makeText(InscriptionActivity.this, "Une erreur s'est produite", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void insererUtilisateur(String nom, String mot_de_passe) {
        // Insère l'utilisateur dans la base de données
        long newRowId = DatabaseGame.insererUtilisateur(nom, mot_de_passe);

        // Vérifie le succès de l'insertion
        if (newRowId != -1) {
            // L'utilisateur a été inséré avec succès
            Toast.makeText(this, "Utilisateur créé avec succès", Toast.LENGTH_SHORT).show();
            // Redirige l'utilisateur vers une autre activité, par exemple le menu principal
            AllerMenu();
        } else {
            // Une erreur s'est produite lors de l'insertion de l'utilisateur
            Toast.makeText(this, "Une erreur s'est produite lors de la création de l'utilisateur", Toast.LENGTH_SHORT).show();
        }
    }

    private void AllerMenu() {
        // Crée un Intent pour passer de l'activité actuelle à MenuActivity
        Intent intent = new Intent(InscriptionActivity.this, MenuActivity.class);

        // Démarre l'activité MenuActivity
        startActivity(intent);
    }
}
