package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

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
    private ImageButton atrasButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_product_list);

        // Inicialización del botón "Atrás"
        atrasButton = findViewById(R.id.atras_button_admin_product_list);

        // Configuración del clic del botón "Atrás"
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProductListActivity.this, CategoryListActivity.class);
                startActivity(intent);
                finish(); // Finaliza esta actividad si se desea que el usuario vuelva a ella
            }
        });


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
}