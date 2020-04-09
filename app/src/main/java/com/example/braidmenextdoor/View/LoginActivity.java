package com.example.braidmenextdoor.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    /* Déclaration XML */
    EditText adresseEmail_txt, password_txt ;
    Button valid ;
    TextView redirectionInscription;

    public static final String TAG = "YOUR-TAG-NAME";

    /* Instance de Firebase */
    private FirebaseAuth mAuth;
    /* Déclaration observable pour vérifier si l'user est déjà connecté */
    private FirebaseAuth.AuthStateListener mAuthStateListener ;

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

        /* Vérification si déjà connecté */
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseuser = mAuth.getCurrentUser();
                if(mFirebaseuser != null){
                    Intent redirectionMenu = new Intent(LoginActivity.this,MenuActivity.class);
                    startActivity(redirectionMenu);
                }
            }
        };


        /* Bouton Valider la connexion */
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* On met nos valeurs dans des données*/
                String email = adresseEmail_txt.getText().toString().trim();
                final String password = password_txt.getText().toString().trim();

                /* Vérification EMAIL */
                if(TextUtils.isEmpty(email)){
                    adresseEmail_txt.setError("Le champ email est vide");
                    return ;
                }
                if (!validEmail(email)) {
                    adresseEmail_txt.setError("Le format de l'email n'est pas valide");
                    return;
                }

                /* Vérification PASSWORD */
                if(TextUtils.isEmpty(password)){
                    password_txt.setError("Le champ mot de passe est vide");
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
                            try
                            {
                                throw task.getException();
                            }
                            // if user enters wrong password.
                            catch (FirebaseAuthInvalidCredentialsException wrongPassword)
                            {
                                Toast.makeText(LoginActivity.this, "Le mot de passe n'est pas correct" ,Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onComplete: wrong_password");
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(LoginActivity.this, "Connexion échoué - Vérifier vos identifiants" ,Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onComplete: " + e.getMessage());
                            }
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

    /* Après la méthode onCreate, on ajoute un observable */
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }
}
