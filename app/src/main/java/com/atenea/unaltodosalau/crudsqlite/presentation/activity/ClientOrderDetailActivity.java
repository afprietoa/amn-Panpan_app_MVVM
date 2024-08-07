package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.atenea.unaltodosalau.crudsqlite.R;

public class ClientOrderDetailActivity extends AppCompatActivity {

    private ImageButton atrasButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_order_detail_product_list);

        // Inicialización del botón "Atrás"
        atrasButton = findViewById(R.id.atras_button_client_order_detail_product);

        // Configuración del clic del botón "Atrás"
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ClientOrderDetailActivity.this, ClientOrderList.class);
                startActivity(intent);
                finish(); // Finaliza esta actividad si se desea que el usuario vuelva a ella
            }
        });
    }
}
