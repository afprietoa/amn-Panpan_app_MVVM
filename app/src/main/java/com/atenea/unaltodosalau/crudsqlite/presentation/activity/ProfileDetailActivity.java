package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileDetailActivity extends AppCompatActivity {
    private TextView name_client, email_client, gender_client;
    private Button btn_update_info, btn_logout;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private ImageView imagen_perfil;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_info);

        /** Inicialización de Firebase */
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        /** Inicialización de variables */
        name_client = findViewById(R.id.user_name);
        email_client = findViewById(R.id.user_email);
        gender_client = findViewById(R.id.user_gender);
        btn_update_info = findViewById(R.id.update_info_button);
        btn_logout = findViewById(R.id.btncerrarSesion);
        imagen_perfil = findViewById(R.id.profile_image);
        bottomNavigationView = findViewById(R.id.barnavadm); // Asegúrate de usar el ID correcto

        btn_update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileDetailActivity.this, ProfileUpdateActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /** Evento para cerrar sesión */
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent1 = new Intent(ProfileDetailActivity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            fetchUserData(user.getUid());
        }
    }

    /** Método para traer los datos del usuario */
    private void fetchUserData(String uid) {
        db.collection("Users").document(uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                String Name = documentSnapshot.getString("name");
                                String Email = documentSnapshot.getString("email");
                                String Gender = documentSnapshot.getString("gender");
                                String profileImageUrl = documentSnapshot.getString("profileImageUrl");

                                name_client.setText(Name);
                                email_client.setText(Email);
                                gender_client.setText(Gender);

                                if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                                    Glide.with(ProfileDetailActivity.this).load(profileImageUrl).into(imagen_perfil);
                                }
                            } else {
                                Toast.makeText(ProfileDetailActivity.this, "No se encontraron los datos para este usuario", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ProfileDetailActivity.this, "Error al obtener datos del usuario: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }

                        // Configuración de BottomNavigationView
                        if (bottomNavigationView != null) {
                            bottomNavigationView.setSelectedItemId(R.id.nav_profileadm);  // Ajusta según la actividad actual
                            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                                int itemId = item.getItemId();

                                Intent intent;
                                if (itemId == R.id.nav_homeadm) {
                                    if (!getClass().getSimpleName().equals("CategoryListActivity")) {
                                        intent = new Intent(ProfileDetailActivity.this, CategoryListActivity.class);
                                        startActivity(intent);
                                        return true;
                                    }
                                } else if (itemId == R.id.nav_ordersadm) {
                                    if (!getClass().getSimpleName().equals("AdminOrdersList")) {
                                        intent = new Intent(ProfileDetailActivity.this, AdminOrdersList.class);
                                        startActivity(intent);
                                        return true;
                                    }
                                } else if (itemId == R.id.nav_estadisticas) {
                                    if (!getClass().getSimpleName().equals("ChartsNotFound")) {
                                        intent = new Intent(ProfileDetailActivity.this, ChartsNotFound.class);
                                        startActivity(intent);
                                        return true;
                                    }
                                } else if (itemId == R.id.nav_profileadm) {
                                    return true;  // Ya estamos en esta actividad
                                }
                                return false;
                            });
                        }
                    }
                });
    }
}
