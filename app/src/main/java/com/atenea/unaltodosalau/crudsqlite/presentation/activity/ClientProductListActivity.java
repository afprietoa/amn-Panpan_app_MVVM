package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.ClientProductListAdapter;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.ProductListAdapter;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.ClientProductListViewModel;


public class ClientProductListActivity extends AppCompatActivity {
    private ClientProductListViewModel viewModel;
    private RecyclerView recyclerView;
    private ClientProductListAdapter adapter;
    private EditText searchBar;
    private ImageButton shoppingCartIcon;
    private int categoryId;

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_product_list);

        // Request storage permission for handling gallery images
        requestStoragePermission();

        recyclerView = findViewById(R.id.product_client_list);
        searchBar = findViewById(R.id.search_bar);
        shoppingCartIcon = findViewById(R.id.shopping_cart_icon);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClientProductListAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ClientProductListViewModel.class);

        Intent intent = getIntent();
        categoryId = intent.getIntExtra("categoryId", -1);

        viewModel.getProductsByCategory(categoryId).observe(this, products -> {
            adapter.setProducts(products);
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
                adapter.notifyDataSetChanged(); // Refresh the adapter to load images
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}