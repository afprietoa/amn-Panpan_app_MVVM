package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.atenea.unaltodosalau.crudsqlite.R;

public class ClientOrderList extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_order_list);

        // Configuración de la barra de navegación
        bottomNavigationView = findViewById(R.id.barnavcliente);
        bottomNavigationView.setSelectedItemId(R.id.navcliente_pedidos);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navcliente_inicio) {
                if (!getClass().equals(ClientCategoryListActivity.class)) {
                    startActivity(new Intent(this, ClientCategoryListActivity.class));
                }
                return true;
            } else if (itemId == R.id.navcliente_pedidos) {
                return true;  // Ya estamos en esta actividad
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
    }
}
