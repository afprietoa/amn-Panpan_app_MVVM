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
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.CategoryListAdapter;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.ClientCategoryListAdapter;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewModel.ClientCategoryListViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClientCategoryListActivity extends AppCompatActivity {
    private ClientCategoryListViewModel viewModel;
    private RecyclerView recyclerView;
    private ClientCategoryListAdapter adapter;
    private BottomNavigationView bottomNavigationView;

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_category_list);

        // Configuración de la barra de navegación
        bottomNavigationView = findViewById(R.id.barnavcliente);
        Log.d("reference barnav menu cliente",bottomNavigationView.toString());
        bottomNavigationView.setSelectedItemId(R.id.navcliente_inicio);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Log.d("id del item", String.valueOf(itemId));
            if (itemId == R.id.navcliente_inicio) {
                return true;  // Ya estamos en esta actividad
            } else if (itemId == R.id.navcliente_pedidos) {
                if (!getClass().equals(ClientOrderList.class)) {
                    startActivity(new Intent(this, ClientOrderList.class));
                }
                return true;
            } else if (itemId == R.id.navcliente_ubicacion) {
                if (!getClass().equals(LocationClientActivity.class)) {
                    startActivity(new Intent(this, LocationClientActivity.class));
                }
                return true;
            } else if (itemId == R.id.navcliente_perfil) {
                if (!getClass().equals(ProfileInfoClientActivity.class)) {
                    startActivity(new Intent(this, ProfileInfoClientActivity.class));
                }
                return true;
            } else {
                return false;
            }
        });

        requestStoragePermission();

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