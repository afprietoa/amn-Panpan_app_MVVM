package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.ShoppingBagProduct;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.ShoppingBagListAdapter;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.ClientShoppingBagViewModel;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.ShoppingBagViewModel;

public class ClientShoppingBagActivity extends AppCompatActivity {
    private ClientShoppingBagViewModel viewModel;
    private RecyclerView recyclerView;
    private ShoppingBagListAdapter adapter;
    private TextView totalPrice;
    private int categoryId;

    private Button confirmOrderButton;
    private ImageButton atrasButton;

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_shopping_bag);

        // Inicialización del botón "Atrás"
        atrasButton = findViewById(R.id.imageButton_client_shopping);
        Intent intent = getIntent();
        categoryId = intent.getIntExtra("categoryId", -1);
        // Configuración del clic del botón "Atrás"
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ClientShoppingBagActivity.this, ClientProductListActivity.class);
                intent.putExtra("categoryId", categoryId);
                startActivity(intent);
                finish(); // Finaliza esta actividad si se desea que el usuario vuelva a ella
            }
        });

        // Request storage permission for handling gallery images
        requestStoragePermission();

        recyclerView = findViewById(R.id.shopping_cart_list);
        totalPrice = findViewById(R.id.total_price);
        confirmOrderButton = findViewById(R.id.confirm_order_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShoppingBagListAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ClientShoppingBagViewModel.class);
        viewModel.getAllProducts().observe(this, products -> {
            adapter.setProducts(products);
            viewModel.getTotalPrice().observe(this, total -> totalPrice.setText("TOTAL: " + total));
        });

        adapter.setOnItemClickListener(new ShoppingBagListAdapter.OnItemClickListener() {
            @Override
            public void onIncrementClick(ShoppingBagProduct product) {
                product.setQuantity(product.getQuantity() + 1);
                viewModel.updateProduct(product);
            }

            @Override
            public void onDecrementClick(ShoppingBagProduct product) {
                if (product.getQuantity() > 1) {
                    product.setQuantity(product.getQuantity() - 1);
                    viewModel.updateProduct(product);
                }
            }

            @Override
            public void onDeleteClick(ShoppingBagProduct product) {
                viewModel.deleteProduct(product);
            }
        });

        confirmOrderButton.setOnClickListener(v -> {
            // Implement order confirmation logic
            Toast.makeText(this, "Order confirmed", Toast.LENGTH_SHORT).show();
        });
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
                adapter.notifyDataSetChanged(); // Refresh the adapter to load images
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}