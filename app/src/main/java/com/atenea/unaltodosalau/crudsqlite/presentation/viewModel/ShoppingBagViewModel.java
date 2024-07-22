package com.atenea.unaltodosalau.crudsqlite.presentation.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.atenea.unaltodosalau.crudsqlite.domain.model.ShoppingBagProduct;
import com.atenea.unaltodosalau.crudsqlite.domain.repository.ShoppingBagRepository;

import java.util.List;

public class ShoppingBagViewModel extends AndroidViewModel {
    private final ShoppingBagRepository repository;

    public ShoppingBagViewModel(@NonNull Application application) {
        super(application);
        repository = new ShoppingBagRepository(application);
    }

    public LiveData<List<ShoppingBagProduct>> getAllProducts() {
        return repository.getAllProducts();
    }

    public LiveData<Double> getTotalPrice() {
        return repository.getTotalPrice();
    }

    public void insert(ShoppingBagProduct product) {
        repository.insert(product);
    }

    public void update(ShoppingBagProduct product) {
        repository.update(product);
    }

    public void deleteById(String productId) {
        repository.deleteById(productId);
    }

//    public ShoppingBagProduct findById(String id) {
//        return repository.findById(id);
//    }
}