package com.example.braidmenextdoor.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.braidmenextdoor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText adresseEmail_txt, password_txt ;
    Button valid ;
    TextView redirectionInscription;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_log_in);


        /* Récupération des id via le xml */
        adresseEmail_txt = findViewById(R.id.AdresseEmail_ToLoginActivity);
        password_txt = findViewById(R.id.MotDePasse_ToLoginActivity);
        valid = findViewById(R.id.BoutonValidationConnect);
        redirectionInscription = findViewById(R.id.TextPasDeCompte);

        /* Initialise l'authentification */
        mAuth = FirebaseAuth.getInstance();


        /* Bouton Valider la connexion */
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* On met nos valeurs dans des données*/
                String email = adresseEmail_txt.getText().toString().trim();
                String password = password_txt.getText().toString().trim();

                /* Vérification EMAIL */
                if(TextUtils.isEmpty(email)){
                    adresseEmail_txt.setError("Champ email vide");
                    return ;
                }
                if (!validEmail(email)) {
                    adresseEmail_txt.setError("Le format de l'email n'est pas valide");
                    return;
                }

                /* Vérification PASSWORD */
                if(TextUtils.isEmpty(password)){
                    password_txt.setError("Champ password vide");
                    return ;
                }
                if(password.length()<6){
                    password_txt.setError("Ce champ nécessite 6 caratères au minimum");
                    return ;
                }

                // Authentification l'utilisateur
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Connecté réussi avec succes." ,Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                            /* Redirection */
                            Intent ValidConnectionActivityIntent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(ValidConnectionActivityIntent);
                        }else {
                            Toast.makeText(LoginActivity.this, "Connection échoué.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            private boolean validEmail(String email) {
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                return pattern.matcher(email).matches();
            }
        });

        /* Lien vers la page d'inscription */
        redirectionInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegistrationIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(RegistrationIntent);
            }
        });





    }
}
