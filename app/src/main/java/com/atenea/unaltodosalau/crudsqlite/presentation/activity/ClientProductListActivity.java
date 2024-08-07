package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

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
    private ImageButton atrasButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_product_list);

        // Inicialización del botón "Atrás"
        atrasButton = findViewById(R.id.imageBtn_productClient);

        // Configuración del clic del botón "Atrás"
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ClientProductListActivity.this, ClientCategoryListActivity.class);
                startActivity(intent);
                finish(); // Finaliza esta actividad si se desea que el usuario vuelva a ella
            }
        });

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
                viewModel.getProductsByByName(s.toString()).observe(ClientProductListActivity.this, products -> {
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

        // Configurar el click listener para el icono del carrito
        shoppingCartIcon.setOnClickListener(v -> {
            Intent cartIntent = new Intent(ClientProductListActivity.this, ClientShoppingBagActivity.class);
            startActivity(cartIntent);
        });

    }
}