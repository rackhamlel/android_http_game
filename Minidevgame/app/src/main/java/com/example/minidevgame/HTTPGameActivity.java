package com.example.minidevgame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;


public class HTTPGameActivity extends AppCompatActivity {
    private TextView textViewNom;
    private EditText editTextChiffre;
    private Button btnValider;
    private DatabaseGame dbHelper;
    private int nombreQuestionsRepondues = 0;
    private int score = 0;
    private TextView afficherNombreQuestionsRepondues;
    private String nomAleatoire;
    private int userId;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_http);
        imageView = findViewById(R.id.my_image_view);
        textViewNom = findViewById(R.id.textViewNom);
        editTextChiffre = findViewById(R.id.editTextChiffre);
        btnValider = findViewById(R.id.btnValider);
        dbHelper = new DatabaseGame(this);
        afficherNombreQuestionsRepondues = findViewById(R.id.afficherNombreQuestionsRepondues);
        userId = getIntent().getIntExtra("userId", -1);

        afficherNomAleatoire();
        loadHttpCatImage();
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validerNumero();
            }
        });

        Button btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allerAuMenu();
            }
        });
    }

    private void afficherNomAleatoire() {
        nomAleatoire = dbHelper.getNomAleatoire(); // Appel BDD
        textViewNom.setText(nomAleatoire); // affichage dans le jeu
    }

    private void validerNumero() {
        String numeroSaisi = editTextChiffre.getText().toString().trim();
        // vérifier si l'utilisateur à rentrer une information
        if (numeroSaisi.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer un chiffre", Toast.LENGTH_SHORT).show();
            return;
        }
        int numeroSaisiInt = Integer.parseInt(numeroSaisi);
        String message; // message pour l'utilisateur
        int numeroLie = dbHelper.recupererNumero(nomAleatoire);
        if (numeroLie != -1 && numeroLie == numeroSaisiInt) { // Réponse correcte
            message = "Numéro correct ! \n " + numeroLie;
            score++;
            Log.d("-----------------id---------------", String.valueOf(userId));
            if (score==1){
                dbHelper.updateSucceSansFaute(userId); // update du succès
            }
        } else { // Réponse Fausse
            message = "Numéro incorrect ! \nLe bon numéro est : " + numeroLie;
        }

        HTTPGameActivityReponse reponse = new HTTPGameActivityReponse(message, numeroSaisiInt, this);
        reponse.show(getSupportFragmentManager(), "CustomDialog"); // appel du pop-up
        nombreQuestionsRepondues++; // Incrémente le nombre de questions répondues
        afficherNombreQuestionsRepondues.setText("Nombre de questions répondues : " + nombreQuestionsRepondues + " / 10");

        // Vérifie si l'utilisateur a répondu à 10 questions
        if (nombreQuestionsRepondues == 10) {
            afficherScore(); // Affiche le score
        }
    }
    private void loadHttpCatImage() {
        // Get the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Création de l'instance ImageLoader
        ImageLoader imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null; // A implémenter si vous souhaitez utiliser un cache personnalisé
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                // A implémenter si vous souhaitez utiliser un cache personnalisé
            }
        });

        // Définition de l'URL de l'image
        int numeroLie = dbHelper.recupererNumero(nomAleatoire);
        String url = "https://http.cat/" + numeroLie;

        // Chargement de l'image
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                // Vérifier si la réponse et le bitmap ne sont pas nuls
                if (response != null && response.getBitmap() != null) {
                    // Image chargée avec succès
                    Bitmap originalBitmap = response.getBitmap();
                    Bitmap croppedBitmap = cropBitmap(originalBitmap);
                    imageView.setImageBitmap(croppedBitmap);
                } else {
                    // Gérer le cas où la réponse ou le bitmap est null
                    //Toast.makeText(HTTPGameActivity.this, "Error loading image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Bitmap cropBitmap(Bitmap originalBitmap) {
        int width = originalBitmap.getWidth(); // Obtenir la largeur de l'image
        int height = originalBitmap.getHeight(); // Obtenir la hauteur de l'image

        int xStart = 70; // Définir le point de départ horizontal du recadrage (en pixels)
        int yStart = 50; // Définir le point de départ vertical du recadrage (en pixels)
        int newWidth = width - 140; // Définir la nouvelle largeur de l'image (largeur d'origine - 60px de chaque côté)
        int newHeight = height - 195; // Définir la nouvelle hauteur de l'image (hauteur d'origine - 40px du haut - 150px du bas)

        // Recadrer l'image
        Bitmap croppedBitmap = Bitmap.createBitmap(originalBitmap, xStart, yStart, newWidth, newHeight);

        return croppedBitmap;
    }



    public void rafraichirJeu() {
        afficherNomAleatoire();
        loadHttpCatImage();
        editTextChiffre.setText("");
    }
    private void afficherScore() {
        Intent intent = new Intent(HTTPGameActivity.this, ScoreActivity.class);
        intent.putExtra("score", score); // Passe le score à ScoreActivity
        startActivity(intent);
    }
    private void allerAuMenu() {
        Intent intent = new Intent(HTTPGameActivity.this, MenuActivity.class);
        startActivity(intent);
    }




}
