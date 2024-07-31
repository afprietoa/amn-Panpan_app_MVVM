package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileDetailActivity extends AppCompatActivity {
    private TextView name_client, email_client, gender_client;
    private Button btn_update_info, btn_logout;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_info);

        /** Inicializacion de firebase*/

        auth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();

        /** Inicializacion de variables*/

        name_client = findViewById(R.id.user_name);
        email_client = findViewById(R.id.user_email);
        gender_client = findViewById(R.id.user_gender);
        btn_update_info = findViewById(R.id.update_info_button);
        btn_logout = findViewById(R.id.btncerrarSesion);


        btn_update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileDetailActivity.this, ProfileUpdateActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /** Evento para cerrar sesion*/
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent1 = new Intent(ProfileDetailActivity.this, ProfileUpdateActivity.class);
                startActivity(intent1);
                finish();
            }
        });




        FirebaseUser user = auth.getCurrentUser();
        if (user != null){
            fetchUserData(user.getUid());
        }

    }


    /**Metodo para Traer los datos del usuario*/

    private void fetchUserData(String uid) {
        db.collection("Usuarios").document(uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()){
                                String Name = documentSnapshot.getString("name");
                                String Email = documentSnapshot.getString("email");
                                String Gender = documentSnapshot.getString("genre");

                                name_client.setText(Name);
                                email_client.setText(Email);
                                gender_client.setText(Gender);

                            }else {
                                Toast.makeText(ProfileDetailActivity.this, "No se encontraron los datos para este usuario", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(ProfileDetailActivity.this, "Error al obtener datos del usuario: ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}