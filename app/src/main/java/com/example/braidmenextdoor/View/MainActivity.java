package com.example.braidmenextdoor.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.braidmenextdoor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    /* Déclaration XML */
    Button btn_inscription, btn_dejaInscrit;

    /* Instance de Firebase */
    private FirebaseAuth mAuth;
    /* Déclaration observable pour vérifier si l'user est déjà connecté */
    private FirebaseAuth.AuthStateListener mAuthStateListener ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Souhaite la bienvenu sur l'application */
        Toast.makeText(this, "Bienvenu sur BraidMeNextDoor ", Toast.LENGTH_SHORT).show();

        /* Récupération des id via le xml */
        btn_inscription = findViewById(R.id.BoutonInscrire_ActivityMain);
        btn_dejaInscrit = findViewById(R.id.BoutonDejaInscrit_ActivityMain);

        /* Initialise l'authentification */
        mAuth = FirebaseAuth.getInstance();


        /* Vérification si déjà connecté */
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseuser = mAuth.getCurrentUser();
                if(mFirebaseuser != null){
                    Intent redirectionMenu = new Intent(MainActivity.this,MenuActivity.class);
                    startActivity(redirectionMenu);
                }
            }
        };


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

    /* Après la méthode onCreate, on ajoute un observable */
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

}
