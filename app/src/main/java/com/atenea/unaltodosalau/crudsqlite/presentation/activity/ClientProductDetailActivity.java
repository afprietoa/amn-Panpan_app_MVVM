package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;
import com.atenea.unaltodosalau.crudsqlite.domain.model.ShoppingBagProduct;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.ClientProductDetailViewModel;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class ClientProductDetailActivity extends AppCompatActivity {
    private ClientProductDetailViewModel viewModel;
    private TextView productName, productDescription, productPrice, productQuantity;
    private ImageView productImage;
    private Button addToCartButton;
    private ImageButton incrementButton, decrementButton;
    private Product product;
    private int quantity = 1;

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_product_detail);

        // Request storage permission for handling gallery images
        requestStoragePermission();

        productName = findViewById(R.id.text_pricipal_product_detail_client);
        productDescription = findViewById(R.id.text_product_description_detail_client);
        productPrice = findViewById(R.id.product_price_detail_client);
        productQuantity = findViewById(R.id.quantity_detail_client);
        productImage = findViewById(R.id.img_product_detail_client);
        addToCartButton = findViewById(R.id.add_to_cart_button_detail_client);
        incrementButton = findViewById(R.id.increment_button_detail_client);
        decrementButton = findViewById(R.id.decrement_button_detail_client);

        viewModel = new ViewModelProvider(this).get(ClientProductDetailViewModel.class);

        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");

        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productPrice.setText(String.valueOf(product.getPrice()));

        String imageString = product.getImage1();

        if (imageString != null) {
            try {
                // Try to interpret the string as a drawable resource ID
                int imageResId = Integer.parseInt(imageString);
                productImage.setImageResource(imageResId);
            } catch (NumberFormatException e) {
                // If it's not a number, try to load it as a URI from the gallery
                Uri imageUri = Uri.parse(imageString);
                if (imageUri != null && imageUri.getScheme() != null) {
                    ImagePicker.with(this)
                            .galleryOnly()         // Only allow picking images from the gallery
                            .createIntent(intentPicker -> {
                                productImage.setImageURI(imageUri);
                                return null;  // Returning null to avoid launching the intent immediately
                            });
                } else {
                    productImage.setImageResource(R.drawable.error_image);
                }
            }
        } else {
            productImage.setImageResource(R.drawable.error_image); // Default image if there's no image
        }

        incrementButton.setOnClickListener(v -> {
            quantity++;
            productQuantity.setText(String.valueOf(quantity));
        });

        decrementButton.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                productQuantity.setText(String.valueOf(quantity));
            }
        });

        addToCartButton.setOnClickListener(v -> {
            ShoppingBagProduct shoppingBagProduct = new ShoppingBagProduct(quantity, product.getPrice(), product.getImage1(), product.getIdCategory(), product.getName());
            viewModel.addProductToCart(shoppingBagProduct);
            Toast.makeText(this, "Product added to cart", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadImage(String imageString) {
        if (imageString != null) {
            try {
                // Try to interpret the string as a drawable resource ID
                int imageResId = Integer.parseInt(imageString);
                productImage.setImageResource(imageResId);
            } catch (NumberFormatException e) {
                // If it's not a number, try to load it as a URI from the gallery
                Uri imageUri = Uri.parse(imageString);
                if (imageUri != null && imageUri.getScheme() != null) {
                    ImagePicker.with(this)
                            .galleryOnly()         // Only allow picking images from the gallery
                            .createIntent(intentPicker -> {
                                productImage.setImageURI(imageUri);
                                return null;  // Returning null to avoid launching the intent immediately
                            });
                } else {
                    productImage.setImageResource(R.drawable.error_image);
                }
            }
        } else {
            productImage.setImageResource(R.drawable.error_image); // Default image if there's no image
        }
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_MEDIA_IMAGES};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImage(product.getImage1()); // Reload the image if permission is granted
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}