package com.atenea.unaltodosalau.crudsqlite.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.atenea.unaltodosalau.crudsqlite.R;

public class ClientPaymentForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_payment_form);

        // botón de navegación en el Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_mi_pago);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent para iniciar ProfileDetailActivity
                Intent intent = new Intent(ClientPaymentForm.this, ClientAddressList.class);
                startActivity(intent);
                finish(); //  finaliza la actividad actual si no secquiere que el usuario vuelva a ella
            }
        });
    }
}


