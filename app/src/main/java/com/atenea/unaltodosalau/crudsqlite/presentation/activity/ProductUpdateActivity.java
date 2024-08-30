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
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class ProductUpdateActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;
    private EditText etName, etDescription, etPrice;
    private ImageView imgProduct1, imgProduct2;
    private Button btnUpdateProduct;
    private Uri imageUri1, imageUri2;
    private Product product;
    private ActivityResultLauncher<Intent> activityResultLauncher1, activityResultLauncher2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_product_update);

        etName = findViewById(R.id.product_update_name);
        etDescription = findViewById(R.id.product_update_description);
        etPrice = findViewById(R.id.product_update_price);
        imgProduct1 = findViewById(R.id.product_update_image1);
        imgProduct2 = findViewById(R.id.product_update_image2);
        btnUpdateProduct = findViewById(R.id.update_product_button);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");
        if (product != null) {
            etName.setText(product.getName());
            etDescription.setText(product.getDescription());
            etPrice.setText(String.valueOf(product.getPrice()));

            // Handling preloaded images and external images
            try {
                int imageResId1 = Integer.parseInt(product.getImage1());
                imgProduct1.setImageResource(imageResId1);
            } catch (NumberFormatException e) {
                imgProduct1.setImageURI(Uri.parse(product.getImage1()));
            }

            try {
                int imageResId2 = Integer.parseInt(product.getImage2());
                imgProduct2.setImageResource(imageResId2);
            } catch (NumberFormatException e) {
                imgProduct2.setImageURI(Uri.parse(product.getImage2()));
            }
        }

        // Setup for picking image 1
        activityResultLauncher1 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri1 = result.getData().getData();
                        imgProduct1.setImageURI(imageUri1);
                    }
                }
        );

        // Setup for picking image 2
        activityResultLauncher2 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri2 = result.getData().getData();
                        imgProduct2.setImageURI(imageUri2);
                    }
                }
        );

        imgProduct1.setOnClickListener(v -> pickImageFromGallery(activityResultLauncher1));
        imgProduct2.setOnClickListener(v -> pickImageFromGallery(activityResultLauncher2));

        btnUpdateProduct.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String description = etDescription.getText().toString();
            String price = etPrice.getText().toString();
            if (imageUri1 != null && imageUri2 != null && !name.isEmpty() && !description.isEmpty() && !price.isEmpty()) {
                product.setName(name);
                product.setDescription(description);
                product.setPrice(Double.parseDouble(price));
                product.setImage1(imageUri1.toString());
                product.setImage2(imageUri2.toString());
                productViewModel.update(product);
                finish();
            } else {
                Toast.makeText(this, "Please fill all fields and select images", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pickImageFromGallery(ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        launcher.launch(intent);
    }

    private static final int PERMISSION_REQUEST_CODE = 100;

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_MEDIA_IMAGES};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            } else {
                pickImageFromGallery(activityResultLauncher1); // Example for imgProduct1
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            } else {
                pickImageFromGallery(activityResultLauncher1); // Example for imgProduct1
            }
        } else {
            pickImageFromGallery(activityResultLauncher1); // Example for imgProduct1
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImageFromGallery(activityResultLauncher1); // Example for imgProduct1
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
