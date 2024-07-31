package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;
import com.atenea.unaltodosalau.crudsqlite.domain.model.ShoppingBagProduct;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.ClientProductDetailViewModel;
import com.bumptech.glide.Glide;

public class ClientProductDetailActivity extends AppCompatActivity {
    private ClientProductDetailViewModel viewModel;
    private TextView productName, productDescription, productPrice, productQuantity;
    private ImageView productImage;
    private Button addToCartButton;
    private ImageButton incrementButton, decrementButton;
    private Product product;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_product_detail);

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

        int imageResId = Integer.parseInt(product.getImage1());
        Glide.with(this).load(imageResId).into(productImage);

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
}