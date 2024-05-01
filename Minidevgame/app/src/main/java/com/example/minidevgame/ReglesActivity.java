package com.example.minidevgame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReglesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regles_http);

        // Récupérer le TextView pour afficher les règles
        TextView reglesTextView = findViewById(R.id.regles_text_view);

        // Définir le texte des règles (remplacez par vos règles réelles)
        String regles = "Règles du jeu : Code HTTP et Chat\n\n" +
                "Objectif : Remplir correctement les champs avec le code HTTP correspondant.\n" +
                "Déroulement du jeu :\n" +
                "1. Au début de chaque niveau, un texte s'affiche à l'écran. Ce texte est un code HTTP.\n" +
                "   - Exemple : \"404 Not Found\"\n" +
                "   - L'image d'un chat est également affichée pour vous guider.\n" +
                "2. Le joueur dispose d'un champ de texte où il doit entrer le code HTTP correspondant à celui affiché à l'écran.\n" +
                "3. Le joueur dispose d'une série de 10 codes HTTP possibles parmi lesquels choisir.\n" +
                "4. Une fois que le joueur a rempli tous les champs avec les codes HTTP correspondants, il soumet ses réponses.\n" +
                "5. Le jeu vérifie les réponses du joueur :\n" +
                "   - Si toutes les réponses sont correctes, un message de félicitations s'affiche à la fin du niveau.\n" +
                "   - Si une ou plusieurs réponses sont incorrectes, un message d'erreur indique au joueur les champs qu'il doit corriger.\n" +
                "6. Le joueur peut passer au niveau suivant ou quitter le jeu.\n" +
                "Conseils :\n" +
                "- Utilisez l'image du chat comme indice pour associer le code HTTP à la situation appropriée.\n" +
                "- Assurez-vous de comprendre la signification de chaque code HTTP pour choisir la réponse correcte.\n";
        reglesTextView.setText(regles);

        // Récupérer le bouton "Retour"
        Button retourButton = findViewById(R.id.retour_button);

        // Définir l'action du bouton "Retour"
        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ajoutez ici le code pour retourner à l'activité précédente ou au menu principal
                finish(); // Ferme cette activité pour retourner à l'activité précédente
            }
        });
    }
}
