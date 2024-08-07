package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ProfileUpdateClientActivity extends AppCompatActivity {

    private EditText edtName, edtEmail;
    private TextView TvEmail;
    private Button btnConfirm;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private RadioGroup radioGroupGender;
    private RadioButton selectedGender;
    private ImageView foto_perfil;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 22;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_update_client);

        // botón de navegación en el Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_update_profile_client);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent para iniciar ProfileInfoClientActivity
                Intent intent = new Intent(ProfileUpdateClientActivity.this, ProfileInfoClientActivity.class);
                startActivity(intent);
                finish(); // finaliza la actividad actual si no se quiere que el usuario vuelva a ella
            }
        });

        /**Inicialización de Firebase*/
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        /**Inicialización de variables*/
        edtName = findViewById(R.id.edt_profile_update_name_client);
        edtEmail = findViewById(R.id.edt_profile_update_email_client);
        btnConfirm = findViewById(R.id.btn_profile_confirm_client);
        radioGroupGender = findViewById(R.id.profile_radio_group_client);
        foto_perfil = findViewById(R.id.profile_image_client);

        /**Método para obtener los datos del usuario*/
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            fetchUserData(user.getUid());
        }

        /**Evento para seleccionar la foto de perfil*/
        foto_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        /**Evento para actualizar los datos*/
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    actualizarInformacion(user.getUid());
                }
            }
        });
    }

    /**Método para seleccionar la imagen*/
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona tu imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            foto_perfil.setImageURI(filePath);
        }
    }

    /**Método para subir la imagen*/
    private void uploadImage(final String userID) {
        if (filePath != null) {
            final StorageReference ref = storageReference.child("images/" + userID);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    db.collection("Users").document(userID).update("profileImageUrl", imageUrl);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileUpdateClientActivity.this, "Error al subir la foto", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    /**Método para traer y observar los datos del usuario*/
    private void fetchUserData(String userUid) {
        db.collection("Users").document(userUid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                String name = documentSnapshot.getString("name");
                                String email = documentSnapshot.getString("email");
                                String gender = documentSnapshot.getString("gender");
                                String profileImageUrl = documentSnapshot.getString("profileImageUrl");

                                if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                                    Glide.with(ProfileUpdateClientActivity.this).load(profileImageUrl).into(foto_perfil);
                                }
                                edtName.setText(name);
                                edtEmail.setText(email);
                                setGenderRadioButton(gender);
                            } else {
                                Toast.makeText(ProfileUpdateClientActivity.this, "Datos no encontrados", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ProfileUpdateClientActivity.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**Método para seleccionar el RadioButton del género*/
    private void setGenderRadioButton(String gender) {
        if (gender != null) {
            if (gender.equals("Masculino")) {
                radioGroupGender.check(R.id.profile_radio_male_client);
            } else if (gender.equals("Femenino")) {
                radioGroupGender.check(R.id.profile_radio_female_client);
            } else if (gender.equals("Otro")) {
                radioGroupGender.check(R.id.profile_radio_other_client);
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

        db.collection("Users").document(userId).update(updatedDatos)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (filePath != null) {
                                uploadImage(userId);
                            }
                            Toast.makeText(ProfileUpdateClientActivity.this, "Usuario actualizado exitosamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ProfileUpdateClientActivity.this, ProfileInfoClientActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ProfileUpdateClientActivity.this, "Error al actualizar el perfil en Firestore", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
