package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BarnavClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

//    protected void setupBottomNavigation() {
//        BottomNavigationView bottomNavigationView = findViewById(R.id.barnavcliente);
//
//        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                handleNavigationItem(item.getItemId());
//                return true;
//            }
//        });
//    }
//
//    protected void handleNavigationItem(int itemId) {
//
//        Intent intent = null;
//
//        if (itemId == R.id.navcliente_inicio) {
//            Toast.makeText(BarnavClientActivity.this, "Inicio", Toast.LENGTH_LONG).show();
//            intent = new Intent(BarnavClientActivity.this, ClientCategoryListActivity.class);
//        } else if (itemId == R.id.navcliente_pedidos) {
//            Toast.makeText(BarnavClientActivity.this, "Pedidos", Toast.LENGTH_LONG).show();
//            intent = new Intent(BarnavClientActivity.this, ClientOrderList.class);
//        } else if (itemId == R.id.navcliente_ubicacion) {
//            Toast.makeText(BarnavClientActivity.this, "Ubicación", Toast.LENGTH_LONG).show();
//            intent = new Intent(BarnavClientActivity.this, LocationClientActivity.class);
//        } else if (itemId == R.id.navcliente_perfil) {
//            Toast.makeText(BarnavClientActivity.this, "Perfil", Toast.LENGTH_LONG).show();
//            intent = new Intent(BarnavClientActivity.this, ProfileInfoClientActivity.class);
//        }
//
//        if (intent != null) {
//            startActivity(intent);
//        }
//    }
}
