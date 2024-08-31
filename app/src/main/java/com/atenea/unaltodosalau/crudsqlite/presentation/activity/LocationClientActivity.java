package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.atenea.unaltodosalau.crudsqlite.R;

public class LocationClientActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_not_found);

        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        ViewCompat.setOnApplyWindowInsetsListener(constraintLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configuración de la barra de navegación
        bottomNavigationView = findViewById(R.id.barnavcliente);
        bottomNavigationView.setSelectedItemId(R.id.navcliente_ubicacion);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navcliente_inicio) {
                if (!getClass().equals(ClientCategoryListActivity.class)) {
                    startActivity(new Intent(this, ClientCategoryListActivity.class));
                }
                return true;
            } else if (itemId == R.id.navcliente_pedidos) {
                if (!getClass().equals(ClientOrderList.class)) {
                    startActivity(new Intent(this, ClientOrderList.class));
                }
                return true;
            } else if (itemId == R.id.navcliente_ubicacion) {
                return true;  // Ya estamos en esta actividad
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
