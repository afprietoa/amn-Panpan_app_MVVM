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

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.ProductViewModel;

public class ProductUpdateActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;
    private EditText etName, etDescription, etPrice;
    private ImageView imgProduct1, imgProduct2;
    private Button btnUpdateProduct;
    private Uri imageUri1, imageUri2;
    private Product product;
    private ActivityResultLauncher<Intent> activityResultLauncher;

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

        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");
        if (product != null) {
            etName.setText(product.getName());
            etDescription.setText(product.getDescription());
            etPrice.setText(String.valueOf(product.getPrice()));
            imageUri1 = Uri.parse(product.getImage1());
            imageUri2 = Uri.parse(product.getImage2());
            imgProduct1.setImageURI(imageUri1);
            imgProduct2.setImageURI(imageUri2);
        }

        imgProduct1.setOnClickListener(v -> {
            Intent intentImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intent);
        });

        imgProduct2.setOnClickListener(v -> {
            Intent intentImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intent);
        });

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
}
