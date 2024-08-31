package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.atenea.unaltodosalau.crudsqlite.R;

public class AdminOrdersList extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_order_list);
        bottomNavigationView = findViewById(R.id.barnavadm);

        // Configuración de BottomNavigationView
        bottomNavigationView.setSelectedItemId(R.id.nav_ordersadm);  // Ajusta según la actividad actual
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Intent intent;
            if (itemId == R.id.nav_homeadm) {
                if (!getClass().getSimpleName().equals("CategoryListActivity")) {
                    intent = new Intent(AdminOrdersList.this, CategoryListActivity.class);
                    startActivity(intent);
                }
                return true;
            } else if (itemId == R.id.nav_ordersadm) {
                return true;  // Ya estamos en esta actividad
            } else if (itemId == R.id.nav_estadisticas) {
                if (!getClass().getSimpleName().equals("ChartsNotFound")) {
                    intent = new Intent(AdminOrdersList.this, ChartsNotFound.class);
                    startActivity(intent);
                }
                return true;
            } else if (itemId == R.id.nav_profileadm) {
                if (!getClass().getSimpleName().equals("ProfileDetailActivity")) {
                    intent = new Intent(AdminOrdersList.this, ProfileDetailActivity.class);
                    startActivity(intent);
                }
                return true;
            } else {
                return false;
            }
        });
    }
}
