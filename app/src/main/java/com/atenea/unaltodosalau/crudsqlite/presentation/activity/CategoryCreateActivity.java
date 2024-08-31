package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.CategoryViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class CategoryCreateActivity extends AppCompatActivity {
    private CategoryViewModel categoryViewModel;
    private EditText etName, etDescription;
    private ImageView imgCategory;
    private Button btnCreateCategory;
    private Uri imageUri;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_category_create);

        etName = findViewById(R.id.category_create_name);
        etDescription = findViewById(R.id.category_create_description);
        imgCategory = findViewById(R.id.category_create_image);
        btnCreateCategory = findViewById(R.id.create_category_button);

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        imgCategory.setImageURI(imageUri); // Set the selected image
                    }
                }
        );

        imgCategory.setOnClickListener(v -> {
            // Use ImagePicker to pick an image from the gallery
            ImagePicker.with(this)
                    .galleryOnly()    // Only gallery images
                    .crop()           // Optionally, you can add cropping
                    .createIntent(intent -> {
                        activityResultLauncher.launch(intent);
                        return null; // Return null to prevent automatic launching
                    });
        });

        btnCreateCategory.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String description = etDescription.getText().toString();
            if (imageUri != null && !name.isEmpty() && !description.isEmpty()) {
                Category category = new Category();
                category.setName(name);
                category.setDescription(description);
                category.setImage(imageUri.toString()); // Store the image URI as a string
                categoryViewModel.insert(category);
                finish();
            } else {
                Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_MEDIA_IMAGES};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            } else {
                pickImageFromGallery();
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            } else {
                pickImageFromGallery();
            }
        } else {
            pickImageFromGallery();
        }
    }

    private void pickImageFromGallery() {
        // Use ImagePicker to pick an image from the gallery
        ImagePicker.with(this)
                .galleryOnly()    // Only gallery images
                .crop()           // Optionally, you can add cropping
                .createIntent(intent -> {
                    activityResultLauncher.launch(intent);
                    return null; // Return null to prevent automatic launching
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImageFromGallery();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


}