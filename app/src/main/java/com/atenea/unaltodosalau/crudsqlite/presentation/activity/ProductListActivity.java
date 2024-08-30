package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.data.database.AppDatabase;
import com.atenea.unaltodosalau.crudsqlite.data.datasource.MemoryDataSource;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.ProductListAdapter;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.ProductViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;
    private RecyclerView recyclerView;
    private ProductListAdapter adapter;
    private FloatingActionButton fabAddProduct;
    private int categoryId;
    private MemoryDataSource memoryDataSource = new MemoryDataSource();
    private static final int PERMISSION_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_product_list);
        requestStoragePermission();
        recyclerView = findViewById(R.id.product_list_admin);
        fabAddProduct = findViewById(R.id.add_product_button_admin);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductListAdapter();
        recyclerView.setAdapter(adapter);

        Intent receivedIntent = getIntent();
        categoryId = receivedIntent.getIntExtra("categoryId", -1);

        // Precarga de datos de MemoryDataSource
        AppDatabase db = AppDatabase.getInstance(this);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getProductsByCategory(categoryId).observe(this, products -> {
            if (products == null || products.isEmpty()) {
                new Thread(() -> {
                    List<Product> initialProducts = memoryDataSource.getProducts(categoryId);
                    for (Product product : initialProducts) {
                        db.productsDao().insert(product);
                    }
                    runOnUiThread(() -> productViewModel.getProductsByCategory(categoryId).observe(this, adapter::setProducts));
                }).start();
            } else {
                adapter.setProducts(products);
                Log.d("ProductListActivity", "Products loaded for category " + categoryId + ": " + products.size());
            }
        });

        adapter.setOnItemClickListener(new ProductListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                // Handle product click
            }

            @Override
            public void onEditClick(Product product) {
                Intent editIntent = new Intent(ProductListActivity.this, ProductUpdateActivity.class);
                editIntent.putExtra("product", product);
                startActivity(editIntent);
            }

            @Override
            public void onDeleteClick(Product product) {
                productViewModel.delete(product);
            }
        });

        fabAddProduct.setOnClickListener(v -> {
            Intent addIntent = new Intent(ProductListActivity.this, ProductCreateActivity.class);
            addIntent.putExtra("categoryId", categoryId);
            startActivity(addIntent);
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