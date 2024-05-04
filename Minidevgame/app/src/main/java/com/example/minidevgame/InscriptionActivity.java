package com.example.minidevgame;

import static com.example.minidevgame.DatabaseGame.hashMotDePasse;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.minidevgame.R;


public class InscriptionActivity extends AppCompatActivity {

    private EditText inscription_nom;
    private EditText inscription_mdp;
    private Button inscription_button;
    private Button retour_button;

    private DatabaseGame DatabaseGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        // Initialise les vues
        inscription_nom = findViewById(R.id.inscription_nom);
        inscription_mdp = findViewById(R.id.inscription_mdp);
        inscription_button = findViewById(R.id.inscription_button);
        retour_button = findViewById(R.id.retour_button);

        // Initialise le DatabaseHelper
        DatabaseGame = new DatabaseGame(this);


        inscription_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Récupère les valeurs saisies par l'utilisateur
                    String nom = inscription_nom.getText().toString().trim();
                    String mot_de_passe = inscription_mdp.getText().toString().trim();
                    boolean testmdp = estMotDePasseSecurise(mot_de_passe);
                    if (testmdp == true) {
                        String motDePasseHache = hashMotDePasse(mot_de_passe); // Hash du mot de passse
                        // Insère l'utilisateur dans la base de données
                        insererUtilisateur(nom, motDePasseHache);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Affiche un message d'erreur en cas d'échec
                    Toast.makeText(InscriptionActivity.this, "Une erreur s'est produite", Toast.LENGTH_SHORT).show();
                }
            }
        });
        retour_button.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v) {
              BtnRetour();
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
            BtnRetour();
        } else {
            // Une erreur s'est produite lors de l'insertion de l'utilisateur
            Toast.makeText(this, "Une erreur s'est produite lors de la création de l'utilisateur", Toast.LENGTH_SHORT).show();
        }
    }


    private void BtnRetour() {
        // Crée un Intent pour passer de l'activité actuelle à MenuActivity
        Intent intent = new Intent(InscriptionActivity.this, LoginActivity.class);

        // Démarre l'activité MenuActivity
        startActivity(intent);
    }
    public void afficherMessageErreur(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Erreur");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Vous pouvez ajouter des actions supplémentaires ici si nécessaire
            }
        });
        builder.show();
    }

    public boolean estMotDePasseSecurise(String motDePasse) {
        // Vérifie si le mot de passe contient au moins 8 caractères
        if (motDePasse.length() < 8) {
            afficherMessageErreur("Le mot de passe doit contenir au moins 8 caractères.");
            return false;
        }

        // Vérifie si le mot de passe contient au moins une lettre majuscule
        if (!motDePasse.matches(".*[A-Z].*")) {
            afficherMessageErreur("Le mot de passe doit contenir au moins une lettre majuscule.");
            return false;
        }

        // Vérifie si le mot de passe contient au moins une lettre minuscule
        if (!motDePasse.matches(".*[a-z].*")) {
            afficherMessageErreur("Le mot de passe doit contenir au moins une lettre minuscule.");
            return false;
        }

        // Vérifie si le mot de passe contient au moins un chiffre
        if (!motDePasse.matches(".*\\d.*")) {
            afficherMessageErreur("Le mot de passe doit contenir au moins un chiffre.");
            return false;
        }

        // Vérifie si le mot de passe contient au moins un caractère spécial
        if (!motDePasse.matches(".*[@#$%^&+=/!()_{}|;:',.?~].*")) {
            afficherMessageErreur("Le mot de passe doit contenir au moins un caractère spécial.");
            return false;
        }

        // Si toutes les conditions sont satisfaites, le mot de passe est considéré comme sécurisé
        return true;
    }

}
