package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.data.database.AppDatabase;
import com.atenea.unaltodosalau.crudsqlite.data.datasource.MemoryDataSource;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.ClientProductListAdapter;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.ProductListAdapter;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.ClientProductListViewModel;
import android.widget.ImageButton;

import java.util.List;


public class ClientProductListActivity extends AppCompatActivity {
    private ClientProductListViewModel viewModel;
    private RecyclerView recyclerView;
    private ClientProductListAdapter adapter;
    private EditText searchBar;
    private ImageButton shoppingCartIcon;
    private int categoryId;
    private ImageButton atrasButton;
    private MemoryDataSource memoryDataSource = new MemoryDataSource(); // Agrega MemoryDataSource
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_product_list);

        // Inicialización del botón "Atrás"
        atrasButton = findViewById(R.id.imageBtn_productClient);

        // Configuración del clic del botón "Atrás"
        atrasButton.setOnClickListener(v -> {
            Intent intent = new Intent(ClientProductListActivity.this, ClientCategoryListActivity.class);
            startActivity(intent);
            finish(); // Finaliza esta actividad si se desea que el usuario vuelva a ella
        });

        // Solicita permiso de almacenamiento para manejar imágenes de la galería
        requestStoragePermission();

        recyclerView = findViewById(R.id.product_client_list);
        searchBar = findViewById(R.id.search_bar);
        shoppingCartIcon = findViewById(R.id.shopping_cart_icon);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ClientProductListAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ClientProductListViewModel.class);

        Intent intent = getIntent();
        categoryId = intent.getIntExtra("categoryId", -1);

        // Precarga de datos de MemoryDataSource
        AppDatabase db = AppDatabase.getInstance(this);
        viewModel.getProductsByCategory(categoryId).observe(this, products -> {
            if (products == null || products.isEmpty()) {
                new Thread(() -> {
                    List<Product> initialProducts = memoryDataSource.getProducts(categoryId);
                    for (Product product : initialProducts) {
                        db.productsDao().insert(product);
                    }
                    runOnUiThread(() -> viewModel.getProductsByCategory(categoryId).observe(this, adapter::setProducts));
                }).start();
            } else {
                adapter.setProducts(products);
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getProductsByName(s.toString()).observe(ClientProductListActivity.this, products -> {
                    adapter.setProducts(products);
                });
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        adapter.setOnItemClickListener(product -> {
            Intent detailIntent = new Intent(ClientProductListActivity.this, ClientProductDetailActivity.class);
            detailIntent.putExtra("product", product);
            startActivity(detailIntent);
        });

        shoppingCartIcon.setOnClickListener(v -> {
            Intent cartIntent = new Intent(ClientProductListActivity.this, ClientShoppingBagActivity.class);
            cartIntent.putExtra("categoryId", categoryId);
            startActivity(cartIntent);
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
                adapter.notifyDataSetChanged(); // Refresca el adaptador para cargar imágenes
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
