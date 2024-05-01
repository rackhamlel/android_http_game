package com.example.minidevgame;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class HTTPGameActivityReponse extends DialogFragment {

    private String message;
    private int numero;
    private HTTPGameActivity activity;

    public HTTPGameActivityReponse(String message, int numero, HTTPGameActivity activity) {
        this.message = message;
        this.numero = numero;
        this.activity = activity;
    }
    public HTTPGameActivityReponse() {
        // You can leave this empty if you don't need to initialize anything here.
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_game_http_reponse, null);

        TextView textViewMessage = view.findViewById(R.id.textViewMessage);
        Button btnSuivant = view.findViewById(R.id.btnSuivant);

        textViewMessage.setText(message);

        btnSuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mettez ici le code à exécuter lorsque le bouton "Suivant" est cliqué
                activity.rafraichirJeu(); // Appel de la méthode dans l'activité principale
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }
}

