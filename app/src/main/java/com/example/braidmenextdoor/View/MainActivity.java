package com.example.braidmenextdoor.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.braidmenextdoor.R;

public class MainActivity extends AppCompatActivity {

    Button btn_inscription, btn_dejaInscrit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Souhaite la bienvenu sur l'application */
        Toast.makeText(this, "Bienvenu sur BraidMeNextDoor ", Toast.LENGTH_SHORT).show();


        /* Récupération des id via le xml */
        btn_inscription = findViewById(R.id.BoutonInscrire_ActivityMain);
        btn_dejaInscrit = findViewById(R.id.BoutonDejaInscrit_ActivityMain);


        /* Bouton Inscription */
        btn_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegistrationActivityIntent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(RegistrationActivityIntent);
            }
        });

        /* Bouton Connexion */
        btn_dejaInscrit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLoginActivityIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(toLoginActivityIntent);
            }
        });

    }


}
