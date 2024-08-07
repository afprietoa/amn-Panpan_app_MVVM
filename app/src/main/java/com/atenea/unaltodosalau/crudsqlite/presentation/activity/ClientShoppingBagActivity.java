package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button confirmOrderButton;
    private ImageButton atrasButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_shopping_bag);

        // Inicialización del botón "Atrás"
        atrasButton = findViewById(R.id.imageButton_client_shopping);

        // Configuración del clic del botón "Atrás"
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ClientShoppingBagActivity.this, ClientProductDetailActivity.class);
                startActivity(intent);
                finish(); // Finaliza esta actividad si se desea que el usuario vuelva a ella
            }
        });

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
}