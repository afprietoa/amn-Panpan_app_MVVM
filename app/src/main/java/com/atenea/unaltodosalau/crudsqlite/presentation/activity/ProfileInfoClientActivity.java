package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.atenea.unaltodosalau.crudsqlite.R;

public class ProfileInfoClientActivity extends AppCompatActivity {
    private TextView name_client, email_client, gender_client;
    private Button btn_update_info, btn_logout;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private ImageView profile_image_client;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info_client);

        // Inicialización de Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Inicialización de variables
        name_client = findViewById(R.id.user_name_client);
        email_client = findViewById(R.id.user_email_client);
        gender_client = findViewById(R.id.user_gender_client);
        btn_update_info = findViewById(R.id.update_info_button_client);
        btn_logout = findViewById(R.id.btncerrarSesion_client);
        profile_image_client = findViewById(R.id.profile_image_client);

        // Configuración de la barra de navegación
        bottomNavigationView = findViewById(R.id.barnavcliente);
        bottomNavigationView.setSelectedItemId(R.id.navcliente_perfil);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navcliente_inicio) {
                if (!getClass().equals(ClientCategoryListActivity.class)) {
                    startActivity(new Intent(this, ClientCategoryListActivity.class));
                }
                return true;
            } else if (itemId == R.id.navcliente_pedidos) {
                if (!getClass().equals(ClientOrderList.class)) {
                    startActivity(new Intent(this, ClientOrderList.class));
                }
                return true;
            } else if (itemId == R.id.navcliente_ubicacion) {
                if (!getClass().equals(LocationClientActivity.class)) {
                    startActivity(new Intent(this, LocationClientActivity.class));
                }
                return true;
            } else if (itemId == R.id.navcliente_perfil) {
                return true;  // Ya estamos en esta actividad
            } else {
                return false;
            }
        });

        btn_update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileInfoClientActivity.this, ProfileUpdateClientActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /** Evento para cerrar sesión */
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent1 = new Intent(ProfileInfoClientActivity.this, RolesActivity.class);
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
                                    Glide.with(ProfileInfoClientActivity.this)
                                            .load(profileImageUrl)
                                            .into(profile_image_client);
                                }
                            } else {
                                Toast.makeText(ProfileInfoClientActivity.this, "No se encontraron los datos para este usuario", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ProfileInfoClientActivity.this, "Error al obtener datos del usuario: ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
