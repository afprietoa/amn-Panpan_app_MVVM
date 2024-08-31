package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.ProductViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class ProductCreateActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;
    private EditText etName, etDescription, etPrice;
    private ImageView imgProduct1, imgProduct2;
    private Button btnCreateProduct;
    private Uri imageUri1, imageUri2;
    private int categoryId;
    private ActivityResultLauncher<Intent> activityResultLauncher1;
    private ActivityResultLauncher<Intent> activityResultLauncher2;
    private static final int PERMISSION_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_product_create);

        etName = findViewById(R.id.product_create_name);
        etDescription = findViewById(R.id.product_create_description);
        etPrice = findViewById(R.id.product_create_price);
        imgProduct1 = findViewById(R.id.product_create_image1);
        imgProduct2 = findViewById(R.id.product_create_image2);
        btnCreateProduct = findViewById(R.id.create_product_button);

        Intent intent = getIntent();
        categoryId = intent.getIntExtra("categoryId", -1);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        activityResultLauncher1 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri1 = result.getData().getData();
                        imgProduct1.setImageURI(imageUri1);
                    }
                }
        );

        activityResultLauncher2 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri2 = result.getData().getData();
                        imgProduct2.setImageURI(imageUri2);
                    }
                }
        );

        imgProduct1.setOnClickListener(v -> {
            // Use ImagePicker for the first image
            ImagePicker.with(this)
                    .galleryOnly()    // Only gallery images
                    .crop()           // Optionally, you can add cropping
                    .createIntent(intentImage -> {
                        activityResultLauncher1.launch(intentImage);
                        return null; // Return null to prevent automatic launching
                    });
        });

        imgProduct2.setOnClickListener(v -> {
            // Use ImagePicker for the second image
            ImagePicker.with(this)
                    .galleryOnly()    // Only gallery images
                    .crop()           // Optionally, you can add cropping
                    .createIntent(intentImage -> {
                        activityResultLauncher2.launch(intentImage);
                        return null; // Return null to prevent automatic launching
                    });
        });
        btnCreateProduct.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String description = etDescription.getText().toString();
            String price = etPrice.getText().toString();
            if (imageUri1 != null && imageUri2 != null && !name.isEmpty() && !description.isEmpty() && !price.isEmpty()) {
                Product product = new Product();
                product.setName(name);
                product.setDescription(description);
                product.setPrice(Double.parseDouble(price));
                product.setImage1(imageUri1.toString());
                product.setImage2(imageUri2.toString());
                product.setIdCategory(categoryId);
                productViewModel.insert(product);
                finish();
            } else {
                Toast.makeText(this, "Please fill all fields and select images", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            imageUri1 = data.getData();
            imgProduct1.setImageURI(imageUri1);
        }
        if (requestCode == 102 && resultCode == RESULT_OK && data != null) {
            imageUri2 = data.getData();
            imgProduct2.setImageURI(imageUri2);
        }
    }


    private void requestStoragePermission(int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_MEDIA_IMAGES};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            } else {
                pickImageFromGallery(requestCode);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            } else {
                pickImageFromGallery(requestCode);
            }
        } else {
            pickImageFromGallery(requestCode);
        }
    }

    private void pickImageFromGallery(int requestCode) {
        ImagePicker.with(this)
                .galleryOnly()    // Solo seleccionar imágenes de la galería
                .crop()           // Opcional: permite recortar la imagen seleccionada
                .createIntent(intent -> {
                    if (requestCode == 1) {
                        activityResultLauncher1.launch(intent); // Para la primera imagen
                    } else if (requestCode == 2) {
                        activityResultLauncher2.launch(intent); // Para la segunda imagen
                    }
                    return null; // Retorna null para evitar que se lance automáticamente
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImageFromGallery(requestCode);
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}