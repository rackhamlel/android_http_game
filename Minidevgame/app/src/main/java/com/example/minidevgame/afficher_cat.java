package com.example.minidevgame;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.minidevgame.R;

public class afficher_cat extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        imageView = findViewById(R.id.my_image_view);

        // Load the HTTP Cat image
        loadHttpCatImage();
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
        String url = "https://http.cat/201";

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
                    Toast.makeText(afficher_cat.this, "Error loading image", Toast.LENGTH_SHORT).show();
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
}
