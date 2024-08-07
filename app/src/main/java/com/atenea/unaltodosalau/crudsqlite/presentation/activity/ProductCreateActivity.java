package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.ProductViewModel;

public class ProductCreateActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;
    private EditText etName, etDescription, etPrice;
    private ImageView imgProduct1, imgProduct2;
    private Button btnCreateProduct;
    private Uri imageUri1, imageUri2;
    private int categoryId;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_product_create);

        // botón de navegación en el Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_new_product);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent para iniciar ProfileDetailActivity
                Intent intent = new Intent(ProductCreateActivity.this, ProductListActivity.class);
                startActivity(intent);
                finish(); //  finaliza la actividad actual si no secquiere que el usuario vuelva a ella
            }
        });

        etName = findViewById(R.id.product_create_name);
        etDescription = findViewById(R.id.product_create_description);
        etPrice = findViewById(R.id.product_create_price);
        imgProduct1 = findViewById(R.id.product_create_image1);
        imgProduct2 = findViewById(R.id.product_create_image2);
        btnCreateProduct = findViewById(R.id.create_product_button);

        Intent intent = getIntent();
        categoryId = intent.getIntExtra("categoryId", -1);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        imgProduct1.setOnClickListener(v -> {
            Intent intentImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intent);
        });

        imgProduct2.setOnClickListener(v -> {
            Intent intentImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intent);
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
}
