package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.data.database.AppDatabase;
import com.atenea.unaltodosalau.crudsqlite.data.datasource.MemoryDataSource;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.CategoryListAdapter;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.CategoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CategoryListActivity extends BarnavAdminActivity {
    private CategoryViewModel categoryViewModel;
    private RecyclerView recyclerView;
    private CategoryListAdapter adapter;
    private FloatingActionButton fabAddCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_category_list);
        setupBottomNavigation();
        Log.d("CategoryListActivity", "Activity created");

        recyclerView = findViewById(R.id.category_list_admin);
        fabAddCategory = findViewById(R.id.add_category_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryListAdapter();
        recyclerView.setAdapter(adapter);

        // Precarga de datos de MemoryDataSource
        AppDatabase db = AppDatabase.getInstance(this);
        MemoryDataSource memoryDataSource = new MemoryDataSource();
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategories().observe(this, categories -> {
            if (categories == null || categories.isEmpty()) {
                new Thread(() -> {
                    List<Category> initialCategories = memoryDataSource.getCategories();
                    for (Category category : initialCategories) {
                        db.categoriesDao().insert(category);
                    }
                    runOnUiThread(() -> categoryViewModel.getAllCategories().observe(this, adapter::setCategories));
                }).start();
            } else {
                adapter.setCategories(categories);
                Log.d("CategoryListActivity", "Categories loaded: " + categories.size());
            }
        });


        adapter.setOnItemClickListener(new CategoryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                Intent intent = new Intent(CategoryListActivity.this, ProductListActivity.class);
                intent.putExtra("categoryId", category.getId());
                startActivity(intent);
            }

            @Override
            public void onEditClick(Category category) {
                Intent intent = new Intent(CategoryListActivity.this, CategoryUpdateActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Category category) {
                categoryViewModel.delete(category);
            }
        });

        fabAddCategory.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryListActivity.this, CategoryCreateActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void handleNavigationItem(int itemId) {

        Intent intent = null;

        if (itemId == R.id.nav_homeadm) {
            Toast.makeText(CategoryListActivity.this, "Inicio", Toast.LENGTH_LONG).show();
            intent = new Intent(CategoryListActivity.this, CategoryListActivity.class);
        } else if (itemId == R.id.nav_ordersadm) {
            Toast.makeText(CategoryListActivity.this, "Pedidos", Toast.LENGTH_LONG).show();
            intent = new Intent(CategoryListActivity.this, AdminOrdersList.class);
        } else if (itemId == R.id.nav_estadisticas) {
            Toast.makeText(CategoryListActivity.this, "Estadisticas", Toast.LENGTH_LONG).show();
            intent = new Intent(CategoryListActivity.this, ChartsNotFound.class);
        } else if (itemId == R.id.nav_profileadm) {
            Toast.makeText(CategoryListActivity.this, "Perfil", Toast.LENGTH_LONG).show();
            intent = new Intent(CategoryListActivity.this, ProfileDetailActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}

