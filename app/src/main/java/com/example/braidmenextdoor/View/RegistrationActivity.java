package com.example.braidmenextdoor.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    /* Déclaration XML */
    EditText userTxt, emailTxt, passwordTxt ;
    Button btn_ValidInscription;

    /* Pour les TAG */
    public static final String TAG = "YOUR-TAG-NAME";

    /* Instance de Firebase */
    private FirebaseAuth mAuth;
    /* Déclaration observable pour vérifier si l'user est déjà connecté */
    private FirebaseAuth.AuthStateListener mAuthStateListener ;

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

        /* Vérification si déjà connecté */
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseuser = mAuth.getCurrentUser();
                if(mFirebaseuser != null){
                    Intent redirectionMenu = new Intent(RegistrationActivity.this,MenuActivity.class);
                    startActivity(redirectionMenu);
                }
            }
        };


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
                    userTxt.setError("Le champ utilisateur est vide");
                    return ;
                }
                if(user.length()<3 || user.length()>15){
                    userTxt.setError("Le champ utilisateur nécessite entre 3 et 12 caractères");
                    return ;
                }

                /* Vérification EMAIL */
                if(TextUtils.isEmpty(email)){
                    emailTxt.setError("Le champ email est vide");
                    return ;
                }
                if (!validEmail(email)) {
                    emailTxt.setError("Le format de l'email n'est pas valide");
                    return;
                }

                /* Vérification PASSWORD */
                if(TextUtils.isEmpty(password)){
                    passwordTxt.setError("Le champ mot de passe est vide");
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
                                    try
                                    {
                                        throw task.getException();
                                    }
                                    // if user enters wrong email.
                                    catch (FirebaseAuthUserCollisionException invalidEmail)
                                    {
                                        Toast.makeText(RegistrationActivity.this, "L'email est déjà utilisé",Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "onComplete: invalid_email");
                                    }
                                    catch (Exception e)
                                    {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegistrationActivity.this, "Authentication échouée.",Toast.LENGTH_SHORT).show();
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

    }

    /* Après la méthode onCreate, on ajoute un observable */
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }


}
