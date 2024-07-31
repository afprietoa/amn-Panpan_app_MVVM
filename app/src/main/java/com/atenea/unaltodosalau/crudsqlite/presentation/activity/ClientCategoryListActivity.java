package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.CategoryListAdapter;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.ClientCategoryListAdapter;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.ClientCategoryListViewModel;

public class ClientCategoryListActivity extends AppCompatActivity {
    private ClientCategoryListViewModel viewModel;
    private RecyclerView recyclerView;
    private ClientCategoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_category_list);

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
}