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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.CategoryViewModel;

public class CategoryUpdateActivity extends AppCompatActivity {
    private CategoryViewModel categoryViewModel;
    private EditText etName, etDescription;
    private ImageView imgCategory;
    private Button btnUpdateCategory;
    private Uri imageUri;
    private Category category;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_category_update);

        etName = findViewById(R.id.category_update_name);
        etDescription = findViewById(R.id.category_update_description);
        imgCategory = findViewById(R.id.category_update_image);
        btnUpdateCategory = findViewById(R.id.update_category_button);

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        Intent intent = getIntent();
        category = (Category) intent.getSerializableExtra("category");
        if (category != null) {
            etName.setText(category.getName());
            etDescription.setText(category.getDescription());
            imageUri = Uri.parse(category.getImage());
            imgCategory.setImageURI(imageUri);
        }

        imgCategory.setOnClickListener(v -> {
            Intent intentImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intent);
        });

        btnUpdateCategory.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String description = etDescription.getText().toString();
            if (imageUri != null && !name.isEmpty() && !description.isEmpty()) {
                category.setName(name);
                category.setDescription(description);
                category.setImage(imageUri.toString());
                categoryViewModel.update(category);
                finish();
            } else {
                Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imgCategory.setImageURI(imageUri);
        }
    }
}