package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private EditText name, email, password;
    private Button signUp;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private TextView txtLogin;
    private RadioGroup radioGroup;
    private RadioButton selectedGender;
    private ImageButton atrasButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);

        // Inicialización del botón "Atrás"
        atrasButton = findViewById(R.id.imageBtn_signup_screen);

        // Configuración del clic del botón "Atrás"
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUpActivity.this, RolesActivity.class);
                startActivity(intent);
                finish(); // Finaliza esta actividad si se desea que el usuario vuelva a ella
            }
        });


        /**Inicio de Firebase Authentication y Firestore*/

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        /** Vinculacion de elementos de la interfaz*/
        name = findViewById(R.id.edt_signup_name);
        email = findViewById(R.id.edt_signup_email);
        password = findViewById(R.id.edt_signup_password);
        signUp = findViewById(R.id.btn_signup_register);
        txtLogin = findViewById(R.id.txt_login);
        radioGroup = findViewById(R.id.signup_fragment_radio_group);

        /**En caso de no estar registrado*/

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        /** Obtener datos del usuario*/

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();

            }
        });



    }
    /**Obtención de datos y verificacion de campos*/

    private void registerUser() {
        String Name= name.getText().toString().trim();
        String Email= email.getText().toString().trim();
        String Password= password.getText().toString().trim();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        String Role = getIntent().getStringExtra("role");


        if (Name.isEmpty() || Email.isEmpty() || Password.isEmpty()){
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length()<6){
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Role == null) {
            Toast.makeText(this, "El tipo de usuario es nulo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedId == -1) {
            Toast.makeText(this, "Por favor, selecciona tu género", Toast.LENGTH_SHORT).show();
            return;
        }

        selectedGender = findViewById(selectedId);
        /** Creacion de usuario en Firebase Authentication y Firestore*/

        // Creacion de usuario en Firebase Authentication y Firestore
        auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Usuario registrado exitosamente en Firebase Authentication");
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("name", Name);
                        userData.put("email", Email);
                        userData.put("password", Password);
                        userData.put("gender", selectedGender.getText().toString());
                        userData.put("role", Role);

                        db.collection("Users")
                                .document(user.getUid())
                                .set(userData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "Usuario registrado exitosamente en Firestore");
                                        Toast.makeText(SignUpActivity.this, "Usuario Registrado con Exito", Toast.LENGTH_SHORT).show();
                                        name.setText("");
                                        email.setText("");
                                        password.setText("");

                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Log.e(TAG, "Error al registrar usuario en Firestore", e);
                                    Toast.makeText(SignUpActivity.this, "Error al registrar usuario en Firestore", Toast.LENGTH_SHORT).show();
                                });
                    }
                } else {
                    Log.e(TAG, "Error al registrar usuario en Firebase Authentication", task.getException());
                    Toast.makeText(SignUpActivity.this, "Error al registrar al usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
