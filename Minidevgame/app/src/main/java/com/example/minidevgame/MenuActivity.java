package com.example.minidevgame;
import android.nfc.Tag;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;


public class MenuActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        imageView = findViewById(R.id.imageView);
        try {
            final DatabaseGame databaseGame = new DatabaseGame(this);
            final InputStream inputStream = databaseGame.getImageFromDatabase("100");
            Log.d("PERONNALLOG", databaseGame.getTable() );


            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Rediriger vers une nouvelle activité lorsque l'image est cliquée
                        Intent intent = new Intent(MenuActivity.this, HTTPGameActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void Allerhttpgame() {
    // Créer un Intent pour passer de l'activité actuelle à MenuActivity
    Intent intent = new Intent(MenuActivity.this, HTTPGameActivity.class);

    // Démarrer l'activité MenuActivity
    startActivity(intent);
    }
}
