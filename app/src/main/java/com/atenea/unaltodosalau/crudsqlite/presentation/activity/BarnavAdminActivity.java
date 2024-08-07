package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BarnavAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.barnavadm);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                handleNavigationItem(item.getItemId());
                return true;
            }
        });
    }

    protected void handleNavigationItem(int itemId) {

        Intent intent = null;

        if (itemId == R.id.nav_homeadm) {
            Toast.makeText(BarnavAdminActivity.this, "Inicio", Toast.LENGTH_LONG).show();
            intent = new Intent(BarnavAdminActivity.this, CategoryListActivity.class);
        } else if (itemId == R.id.nav_ordersadm) {
            Toast.makeText(BarnavAdminActivity.this, "Pedidos", Toast.LENGTH_LONG).show();
            intent = new Intent(BarnavAdminActivity.this, AdminOrdersList.class);
        } else if (itemId == R.id.nav_estadisticas) {
            Toast.makeText(BarnavAdminActivity.this, "Estadisticas", Toast.LENGTH_LONG).show();
            intent = new Intent(BarnavAdminActivity.this, ChartsNotFound.class);
        } else if (itemId == R.id.nav_profileadm) {
            Toast.makeText(BarnavAdminActivity.this, "Perfil", Toast.LENGTH_LONG).show();
            intent = new Intent(BarnavAdminActivity.this, ProfileDetailActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
