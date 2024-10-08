/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad Nacional de Colombia (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 * Licenciado bajo el esquema Academic Free License version 2.1
 *
 * Proyecto Todos a la U (http://unaltodosalau.com)
 * Ejercicio: Crud de Usuarios
 * Autor: afprietoa
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.atenea.unaltodosalau.crudsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.atenea.unaltodosalau.crudsqlite.data.dao.CategoriesDao;
import com.atenea.unaltodosalau.crudsqlite.data.database.AppDatabase;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.atenea.unaltodosalau.crudsqlite.presentation.activity.CategoryListActivity;
import com.atenea.unaltodosalau.crudsqlite.presentation.activity.ClientCategoryListActivity;
import com.atenea.unaltodosalau.crudsqlite.presentation.activity.ProductListActivity;

import java.util.List;


/**
 * <h1>MainActivity<h1>
 * Actividad principal de la aplicación.
 * @author afprietoa
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        //setContentView(R.layout.roles_content);



//        ImageButton adminRole = findViewById(R.id.admin_role);
//        ImageButton clientRole = findViewById(R.id.client_role);
//
//        adminRole.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, CategoryListActivity.class);
//            startActivity(intent);
//        });
//
//        clientRole.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, ClientCategoryListActivity.class);
//            startActivity(intent);
//        });
    }
}