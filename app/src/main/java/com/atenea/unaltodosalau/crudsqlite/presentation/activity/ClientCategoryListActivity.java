package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.ClientCategoryListAdapter;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.ClientCategoryListViewModel;

public class ClientCategoryListActivity extends BarnavClientActivity {
    private ClientCategoryListViewModel viewModel;
    private RecyclerView recyclerView;
    private ClientCategoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_category_list);
        setupBottomNavigation();

        recyclerView = findViewById(R.id.category_list_client);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClientCategoryListAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ClientCategoryListViewModel.class);
        viewModel.getAllCategories().observe(this, categories -> {
            adapter.setCategories(categories);
        });


        adapter.setOnItemClickListener(category -> {
            Intent intent = new Intent(ClientCategoryListActivity.this, ClientProductListActivity.class);
            intent.putExtra("categoryId", category.getId());
            startActivity(intent);
        });
    }
    @Override
    protected void handleNavigationItem(int itemId) {

        Intent intent = null;

        if (itemId == R.id.navcliente_inicio) {
            Toast.makeText(ClientCategoryListActivity.this, "Inicio", Toast.LENGTH_LONG).show();
            intent = new Intent(ClientCategoryListActivity.this, ClientCategoryListActivity.class);
        } else if (itemId == R.id.navcliente_pedidos) {
            Toast.makeText(ClientCategoryListActivity.this, "Pedidos", Toast.LENGTH_LONG).show();
            intent = new Intent(ClientCategoryListActivity.this, ClientOrderList.class);
        } else if (itemId == R.id.navcliente_ubicacion) {
            Toast.makeText(ClientCategoryListActivity.this, "Ubicación", Toast.LENGTH_LONG).show();
            intent = new Intent(ClientCategoryListActivity.this, LocationClientActivity.class);
        } else if (itemId == R.id.navcliente_perfil) {
            Toast.makeText(ClientCategoryListActivity.this, "Perfil", Toast.LENGTH_LONG).show();
            intent = new Intent(ClientCategoryListActivity.this, ProfileInfoClientActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}

