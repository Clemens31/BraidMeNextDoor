package com.example.braidmenextdoor.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.braidmenextdoor.Model.Bean.TypeCoiffureBean;
import com.example.braidmenextdoor.R;
import com.example.braidmenextdoor.View.Adapter.LineMenuAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    /* Déclaration des Data */
    private ArrayList<TypeCoiffureBean> typeCoiffureBeanArrayList;
    private LineMenuAdapter lineMenuAdapter;

    /* Déclaration Composant graphique*/
    private RecyclerView rv;
    ImageView deconnexion ;

    /* Instance de Firebase */
    private FirebaseAuth mAuth;
    /* Déclaration observable pour vérifier si l'user est déjà connecté */
    private FirebaseAuth.AuthStateListener mAuthStateListener ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        /* Création de la liste */
        typeCoiffureBeanArrayList = new ArrayList<TypeCoiffureBean>();
        /* Son remplissage */
        createItem();

        /* Instanciation de l'adapteur */
        lineMenuAdapter = new LineMenuAdapter(typeCoiffureBeanArrayList);

        /* Récupère le composant graphique du XML */
        deconnexion = findViewById(R.id.deconnexionCompte);
        rv = findViewById(R.id.rv);
        rv.setAdapter(lineMenuAdapter);

        /* Type d'affichage  */
        rv.setLayoutManager(new LinearLayoutManager(this));

        /* Vérification si déjà connecté */
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseuser = mAuth.getCurrentUser();
                if(mFirebaseuser == null){
                    Toast.makeText(MenuActivity.this, "Vous n'êtes pas connecté." ,Toast.LENGTH_SHORT).show();
                    Intent redirectionMenu = new Intent(MenuActivity.this,LoginActivity.class);
                    startActivity(redirectionMenu);
                }
            }
        };

        /* Clic que la déconnexion */
        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Déconnection */
                FirebaseAuth.getInstance().signOut();
                /* Redirection */
                Intent RedirectionMenuPrincipal = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(RedirectionMenuPrincipal);
            }
        });

    }



    /* Ajout de données */
    public void createItem(){
        TypeCoiffureBean typeCoiffureBean = new TypeCoiffureBean("Lisse",R.drawable.chev1);
        typeCoiffureBeanArrayList.add(typeCoiffureBean);

        TypeCoiffureBean typeCoiffureBean1 = new TypeCoiffureBean("Pas Lisse",R.drawable.chev2);
        typeCoiffureBeanArrayList.add(typeCoiffureBean1);

        TypeCoiffureBean typeCoiffureBean2 = new TypeCoiffureBean("crepu",R.drawable.chev3);
        typeCoiffureBeanArrayList.add(typeCoiffureBean2);

        TypeCoiffureBean typeCoiffureBean3 = new TypeCoiffureBean("frise",R.drawable.chev4);
        typeCoiffureBeanArrayList.add(typeCoiffureBean3);

    }




}
