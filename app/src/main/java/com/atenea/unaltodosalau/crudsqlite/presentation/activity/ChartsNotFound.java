package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.atenea.unaltodosalau.crudsqlite.R;

public class ChartsNotFound extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charts_not_found);

        bottomNavigationView = findViewById(R.id.barnavadm);

        // Configuración de BottomNavigationView
        bottomNavigationView.setSelectedItemId(R.id.nav_estadisticas);  // Ajusta según la actividad actual
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Intent intent;
            if (itemId == R.id.nav_homeadm) {
                if (!getClass().getSimpleName().equals("CategoryListActivity")) {
                    intent = new Intent(ChartsNotFound.this, CategoryListActivity.class);
                    startActivity(intent);
                }
                return true;
            } else if (itemId == R.id.nav_ordersadm) {
                if (!getClass().getSimpleName().equals("AdminOrdersList")) {
                    intent = new Intent(ChartsNotFound.this, AdminOrdersList.class);
                    startActivity(intent);
                }
                return true;
            } else if (itemId == R.id.nav_estadisticas) {
                return true;  // Ya estamos en esta actividad
            } else if (itemId == R.id.nav_profileadm) {
                if (!getClass().getSimpleName().equals("ProfileDetailActivity")) {
                    intent = new Intent(ChartsNotFound.this, ProfileDetailActivity.class);
                    startActivity(intent);
                }
                return true;
            } else {
                return false;
            }
        });
    }
}
