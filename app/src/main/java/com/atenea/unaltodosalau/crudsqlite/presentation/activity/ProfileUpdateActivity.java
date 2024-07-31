package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.HashMap;
import java.util.Map;

public class ProfileUpdateActivity extends AppCompatActivity {

    private EditText edtName, edtEmail;
    private TextView TvEmail;
    private Button btnConfirm;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private RadioGroup radioGroupGender;
    private RadioButton selectedGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_update);

        /**Inicialización de Firebase*/
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        /**Inicialización de variables*/
        edtName = findViewById(R.id.edt_profile_update_name);
        edtEmail = findViewById(R.id.edt_profile_update_email);
        btnConfirm = findViewById(R.id.btn_profile_confirm);
        radioGroupGender = findViewById(R.id.profile_radio_group);

        /**Método para obtener los datos del usuario*/
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            fetchUserData(user.getUid());
        }

        /**Evento para actualizar los datos*/
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    actualizarInformacion(user.getUid());
                    Intent intent = new Intent(ProfileUpdateActivity.this, ProfileDetailActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    /**Método para traer y observar los datos del usuario*/
    private void fetchUserData(String userUid) {
        db.collection("Usuarios").document(userUid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                String name = documentSnapshot.getString("name");
                                String email = documentSnapshot.getString("email");
                                String phone = documentSnapshot.getString("Telefono");
                                String gender = documentSnapshot.getString("gender");

                                edtName.setText(name);
                                edtEmail.setText(email);
                                setGenderRadioButton(gender);
                            } else {
                                Toast.makeText(ProfileUpdateActivity.this, "Datos no encontrados", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ProfileUpdateActivity.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**Método para seleccionar el RadioButton del género*/
    private void setGenderRadioButton(String gender) {
        if (gender != null) {
            if (gender.equals("Masculino")) {
                radioGroupGender.check(R.id.profile_radio_male);
            } else if (gender.equals("Femenino")) {
                radioGroupGender.check(R.id.profile_radio_female);
            } else if (gender.equals("Otro")) {
                radioGroupGender.check(R.id.profile_radio_other);
            }
        }
    }

    /**Método para actualizar la información*/
    private void actualizarInformacion(String userId) {
        String nuevoNombre = edtName.getText().toString().trim();
        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        selectedGender = findViewById(selectedId);
        String nuevoGenero = selectedGender.getText().toString();

        if (nuevoNombre.isEmpty() || nuevoGenero.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        /**Actualizar datos en FireStore*/
        Map<String, Object> updatedDatos = new HashMap<>();
        updatedDatos.put("name", nuevoNombre);
        updatedDatos.put("gender", nuevoGenero);

        db.collection("Usuarios").document(userId).update(updatedDatos)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileUpdateActivity.this, "Usuario actualizado exitosamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProfileUpdateActivity.this, "Error al actualizar el perfil en Firestore", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}