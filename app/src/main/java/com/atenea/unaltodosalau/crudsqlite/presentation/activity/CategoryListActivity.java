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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.data.database.AppDatabase;
import com.atenea.unaltodosalau.crudsqlite.data.datasource.MemoryDataSource;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.CategoryListAdapter;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.CategoryViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CategoryListActivity extends AppCompatActivity {
    private CategoryViewModel categoryViewModel;
    private RecyclerView recyclerView;
    private CategoryListAdapter adapter;
    private FloatingActionButton fabAddCategory;
    private BottomNavigationView bottomNavigationView;

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_category_list);
        Log.d("CategoryListActivity", "Activity created");
        requestStoragePermission();
        recyclerView = findViewById(R.id.category_list_admin);
        fabAddCategory = findViewById(R.id.add_category_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryListAdapter();
        recyclerView.setAdapter(adapter);

        // Configuración de la barra de navegación
        bottomNavigationView = findViewById(R.id.barnavadm);
        Log.d("reference barnav menu adm",bottomNavigationView.toString());
        bottomNavigationView.setSelectedItemId(R.id.nav_homeadm);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Log.d("id del item", String.valueOf(itemId));
            if (itemId == R.id.nav_homeadm) {
                return true;  // Ya estamos en esta actividad
            } else if (itemId == R.id.nav_ordersadm) {
                if (!getClass().equals(AdminOrdersList.class)) {
                    startActivity(new Intent(this, AdminOrdersList.class));
                }
                return true;
            } else if (itemId == R.id.nav_estadisticas) {
                if (!getClass().equals(ChartsNotFound.class)) {
                    startActivity(new Intent(this, ChartsNotFound.class));
                }
                return true;
            } else if (itemId == R.id.nav_profileadm) {
                if (!getClass().equals(ProfileDetailActivity.class)) {
                    startActivity(new Intent(this, ProfileDetailActivity.class));
                }
                return true;
            } else {
                return false;
            }
        });

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

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_MEDIA_IMAGES};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("Permissions", "Request Code: " + requestCode);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
