package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity  extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView btnRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        /**Inicio de Firebase Authentication */
        auth = FirebaseAuth.getInstance();


        /** Vinculacion de elementos de la interfaz*/


        edtEmail = findViewById(R.id.edt_login_email);
        edtPassword=findViewById(R.id.edt_login_password);
        btnRegister = findViewById(R.id.btn_register);
        btnLogin = findViewById(R.id.btn_login);

        /** Intencion de los botones*/
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginUser();
            }
        });


    }
    /** Metodo para logear al usuario dependiendo de su tipo de usuario*/

    private void LoginUser() {

        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        /** Inicio de  sesión con Firebase Authentication*/
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {

                        fetchUserData(user.getUid());
                    } else {
                        Toast.makeText(LoginActivity.this, "Error al obtener el usuario actual", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(LoginActivity.this, "Error en la autenticación: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchUserData(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        /**Accede a la colección 'Usuarios' y al documento correspondiente al ID del usuario*/

        db.collection("Users").document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                String nombre = document.getString("name");
                                String role = document.getString("role");

                                Toast.makeText(LoginActivity.this,"Inicio de Sesión Exitoso", Toast.LENGTH_LONG).show();

                                /** Redirigimos segun el tipo de usuario*/

                                switch (role) {
                                    case "Administrador":
                                        startActivity(new Intent(LoginActivity.this, CategoryListActivity.class));
                                        break;
                                    case "Cliente":
                                        startActivity(new Intent(LoginActivity.this, ClientCategoryListActivity.class));
                                        break;
                                    default:
                                        Toast.makeText(LoginActivity.this, "Tipo de usuario no reconocido", Toast.LENGTH_SHORT).show();
                                        break;
                                }

                                finish();

                            } else {
                                Toast.makeText(LoginActivity.this, "No se encontraron datos para este usuario", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Error al obtener datos del usuario ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}