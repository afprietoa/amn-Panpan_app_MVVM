
package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.atenea.unaltodosalau.crudsqlite.R;

public class AdminOrderDetailActivity extends AppCompatActivity {

    private ImageButton atrasButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_order_detail_product_list);

        // Inicialización del botón "Atrás"
        atrasButton = findViewById(R.id.atras_button_admin_order_detail);

        // Configuración del clic del botón "Atrás"
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent para iniciar AdminOrderListActivity
                Intent intent = new Intent(AdminOrderDetailActivity.this, AdminOrdersList.class);
                startActivity(intent);
                finish(); // Finaliza esta actividad si se desea que el usuario vuelva a ella
            }
        });
    }
}
