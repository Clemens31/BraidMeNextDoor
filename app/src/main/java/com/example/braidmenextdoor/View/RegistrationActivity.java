package com.example.braidmenextdoor.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.braidmenextdoor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    /* XML */
    EditText userTxt, emailTxt, passwordTxt ;
    Button btn_ValidInscription;

    /* Instance de Firebase */
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        /* Récupération des id via le xml */
        userTxt = findViewById(R.id.User_RegistrationActivity);
        emailTxt = findViewById(R.id.Email_RegistrationActivity);
        passwordTxt = findViewById(R.id.Password_RegistrationActivity);
        btn_ValidInscription = findViewById(R.id.BoutonValidationInscription);

        /* Initialise l'authentification */
        mAuth = FirebaseAuth.getInstance();

        /* Redirige si l'on est connecté
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MenuActivity.class));
            finish();
        } */

        /* Bouton Valider Inscription */
        btn_ValidInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* On met nos valeurs dans des données*/
                final String user = userTxt.getText().toString().trim();
                String email = emailTxt.getText().toString().trim();
                String password = passwordTxt.getText().toString().trim();

                /* Vérification USER */
                if(TextUtils.isEmpty(user)){
                    userTxt.setError("Champ utilisateur vide");
                    return ;
                }
                if(user.length()<=3 || user.length()>15){
                    userTxt.setError("Le champ utilisateur doit avoir entre 3 et 12 caractères");
                    return ;
                }

                /* Vérification EMAIL */
                if(TextUtils.isEmpty(email)){
                    emailTxt.setError("Champ email vide");
                    return ;
                }
                if (!validEmail(email)) {
                    emailTxt.setError("Le format de l'email n'est pas valide");
                    return;
                }

                /* Vérification PASSWORD */
                if(TextUtils.isEmpty(password)){
                    passwordTxt.setError("Champ password vide");
                    return ;
                }
                if(password.length()<6){
                    passwordTxt.setError("Ce champ nécessite 6 caratères au minimum");
                    return ;
                }


                /* Envoi en base des données pour vérification */
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(RegistrationActivity.this, "Authentication réussi. Bonjour " + user ,Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegistrationActivity.this, "Authentication échouée.",Toast.LENGTH_SHORT).show();
                                }
                            }
                });
            }

            private boolean validEmail(String email) {
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                return pattern.matcher(email).matches();
            }


        });

    }
}
