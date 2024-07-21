package com.atenea.unaltodosalau.crudsqlite.data.database;

import android.util.Log;

import com.atenea.unaltodosalau.crudsqlite.data.datasource.MemoryDataSource;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;

import java.util.List;

public class DatabaseInitializer {

    public static void populateAsync(final AppDatabase db) {
        new Thread(() -> {
            populateWithTestData(db);
            Log.d("DatabaseInitializer", "Data populated");
        }).start();
    }

    private static void populateWithTestData(AppDatabase db) {
        MemoryDataSource dataSource = new MemoryDataSource();

        List<Category> categories = dataSource.getCategories();
        for (Category category : categories) {
            db.categoriesDao().insert(category);
            Log.d("DatabaseInitializer", "Inserted category: " + category.getName());
        }

        for (Category category : categories) {
            List<Product> products = dataSource.getProducts(category.getId());
            for (Product product : products) {
                db.productsDao().insert(product);
                Log.d("DatabaseInitializer", "Inserted product: " + product.getName());
            }
        }
    }
}