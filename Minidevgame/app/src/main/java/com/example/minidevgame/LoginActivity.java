package com.example.minidevgame;

import static com.example.minidevgame.DatabaseGame.hashMotDePasse;

import android.annotation.SuppressLint;
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
    private int utilisateurID;
    private int userId;
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

            @Override
            public void onClick(View v) {
                try {
                    // Récupérer les valeurs saisies par l'utilisateur
                    String nom = connection_nom.getText().toString().trim();
                    String mot_de_passe = connection_mdp.getText().toString().trim();
                    String motDePasseHache = hashMotDePasse(mot_de_passe);
                    // Effectuer la vérification de connexion
                    if (verifierConnexion(nom, motDePasseHache)) {
                        userId = RecupererID();
                        AllerMenu(userId);
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

                    allerInscription();
                }catch (Exception e) {
                    e.printStackTrace();
                    // Affichez un message d'erreur
                    Toast.makeText(LoginActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("Range")
    private boolean verifierConnexion(String nom, String mot_de_passe) {
        DatabaseGame databaseHelper = new DatabaseGame(this);
        Cursor cursor = null;
        try {

            cursor = databaseHelper.getReadableDatabase().query("utilisateur", new String[] {"id"}, "nom=? AND mot_de_passe=?", new String[] {nom, mot_de_passe}, null, null, null);

            if (cursor.moveToFirst()) {
                // Récupérer l'ID de l'utilisateur à partir du curseur
                utilisateurID = cursor.getInt(cursor.getColumnIndex("id"));
                return true; // Retourner true si la connexion est réussie
            } else {
                return false; // Retourner false si aucun utilisateur correspondant n'est trouvé
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public int RecupererID(){

        return utilisateurID;
    }

    private void AllerMenu(int userId) {
        // Créer un Intent pour passer de l'activité actuelle à MenuActivity
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        intent.putExtra("userId", userId); // Faire circuler l'ID de l'utilsateur
        // Démarrer l'activité MenuActivity
        startActivity(intent);
    }



    private void allerInscription() {
        // Créer un Intent pour passer de l'activité actuelle à MenuActivity
        Intent intent = new Intent(LoginActivity.this, InscriptionActivity.class);

        // Démarrer l'activité MenuActivity
        startActivity(intent);
    }
}

