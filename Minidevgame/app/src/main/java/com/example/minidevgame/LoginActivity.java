package com.example.minidevgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText connection_nom;
    private EditText connection_mdp;
    private Button connection_button;
    private Button connection_cree_compte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Récupérer les références des éléments de l'interface utilisateur
        connection_nom = findViewById(R.id.connection_nom);
        connection_mdp = findViewById(R.id.connection_mdp);
        connection_button = findViewById(R.id.connection_button);
        connection_cree_compte = findViewById(R.id.connection_cree_compte);

        // Ajouter un écouteur de clic au bouton de connexion
        connection_button.setOnClickListener(new View.OnClickListener() {
            String nom = connection_nom.getText().toString().trim();
            String mot_de_passe = connection_mdp.getText().toString().trim();

            @Override
            public void onClick(View v) {
                try {
                    // Récupérer les valeurs saisies par l'utilisateur
                    String nom = connection_nom.getText().toString().trim();
                    String mot_de_passe = connection_mdp.getText().toString().trim();

                    // Effectuer la vérification de connexion
                    if (verifierConnexion(nom, mot_de_passe)) {
                        AllerMenu();
                    } else {
                        Toast.makeText(LoginActivity.this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Ajouter un écouteur de clic au bouton de création de compte
        connection_cree_compte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Ajouter la logique de création de compte ici
                    // par exemple, rediriger vers l'écran de création de compte
                    allerInscription();
                }catch (Exception e) {
                    e.printStackTrace();
                    // Affichez un message d'erreur
                    Toast.makeText(LoginActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean verifierConnexion(String nom, String mot_de_passe) {
        DatabaseGame databaseHelper = new DatabaseGame(this);
        Cursor cursor = null;
        try {

            cursor = databaseHelper.getReadableDatabase().query("utilisateur", new String[] {"id"}, "nom=? AND mot_de_passe=?", new String[] {nom, mot_de_passe}, null, null, null);

            return cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    // Méthode pour rediriger vers la page suivante (à personnaliser)
    private void AllerMenu() {
        // Créer un Intent pour passer de l'activité actuelle à MenuActivity
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);

        // Démarrer l'activité MenuActivity
        startActivity(intent);
    }


    // Méthode pour rediriger vers l'écran de création de compte (à personnaliser)
    private void allerInscription() {
        // Créer un Intent pour passer de l'activité actuelle à MenuActivity
        Intent intent = new Intent(LoginActivity.this, InscriptionActivity.class);

        // Démarrer l'activité MenuActivity
        startActivity(intent);
    }
}

