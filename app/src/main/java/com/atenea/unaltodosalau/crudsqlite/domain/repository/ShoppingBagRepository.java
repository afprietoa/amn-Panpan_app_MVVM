package com.atenea.unaltodosalau.crudsqlite.domain.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.atenea.unaltodosalau.crudsqlite.data.dao.ShoppingBagDao;
import com.atenea.unaltodosalau.crudsqlite.data.database.AppDatabase;
import com.atenea.unaltodosalau.crudsqlite.domain.model.ShoppingBagProduct;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoppingBagRepository {
    private final ShoppingBagDao shoppingBagDao;
    private final ExecutorService executorService;

    public ShoppingBagRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        shoppingBagDao = db.shoppingBagDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<ShoppingBagProduct>> getAllProducts() {
        return shoppingBagDao.getAllProducts();
    }

    public void insert(ShoppingBagProduct product) {
        executorService.execute(() -> shoppingBagDao.insert(product));
    }

    public void deleteById(String productId) {
        executorService.execute(() -> shoppingBagDao.deleteById(productId));
    }

    public LiveData<Double> getTotalPrice() {
        return shoppingBagDao.getTotalPrice();
    }
}