package com.atenea.unaltodosalau.crudsqlite.domain.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.atenea.unaltodosalau.crudsqlite.data.dao.ProductsDao;
import com.atenea.unaltodosalau.crudsqlite.data.database.AppDatabase;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {
    private final ProductsDao productsDao;
    private final ExecutorService executorService;
    private LiveData<List<Product>> productsByCategory, productsByName;

    public ProductRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        productsDao = db.productsDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Product>> getAllProducts() {
        return productsDao.getAllProducts();
    }

    public LiveData<List<Product>> getProductsByCategory(int categoryId) {
        productsByCategory = productsDao.getProductsByCategory(categoryId);
        return productsByCategory;
    }

    public LiveData<List<Product>> getProductsByByName(String name) {
        productsByName = productsDao.getProductsByByName(name);;
        return productsByName;
    }

    public void insert(Product product) {
        executorService.execute(() -> productsDao.insert(product));
    }

    public void update(Product product) {
        executorService.execute(() -> productsDao.update(product));
    }

    public void delete(Product product) {
        executorService.execute(() -> productsDao.delete(product));
    }
}