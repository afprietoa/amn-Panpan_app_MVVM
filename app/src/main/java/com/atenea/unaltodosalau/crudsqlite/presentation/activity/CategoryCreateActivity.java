package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.CategoryViewModel;
import com.bumptech.glide.Glide;

public class CategoryCreateActivity extends AppCompatActivity {
    private CategoryViewModel categoryViewModel;
    private EditText etName, etDescription;
    private ImageView imgCategory;
    private Button btnCreateCategory;
    private Uri imageUri;
    private ActivityResultLauncher<Intent> activityResultLauncher;

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
                        Glide.with(this).load(imageUri).into(imgCategory);
                    }
                }
        );

        imgCategory.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intent);
        });

        btnCreateCategory.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String description = etDescription.getText().toString();
            if (imageUri != null && !name.isEmpty() && !description.isEmpty()) {
                Category category = new Category();
                category.setName(name);
                category.setDescription(description);
                category.setImage(imageUri.toString());
                categoryViewModel.insert(category);
                finish();
            } else {
                Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }