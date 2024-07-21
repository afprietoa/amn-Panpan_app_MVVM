package com.atenea.unaltodosalau.crudsqlite.presentation.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.atenea.unaltodosalau.crudsqlite.domain.model.ShoppingBagProduct;
import com.atenea.unaltodosalau.crudsqlite.domain.repository.ShoppingBagRepository;

import java.util.List;

public class ShoppingBagViewModel extends AndroidViewModel {
    private final ShoppingBagRepository repository;
    private final LiveData<List<ShoppingBagProduct>> allProducts;
    private final LiveData<Double> totalPrice;

    public ShoppingBagViewModel(Application application) {
        super(application);
        repository = new ShoppingBagRepository(application);
        allProducts = repository.getAllProducts();
        totalPrice = repository.getTotalPrice();
    }

    public LiveData<List<ShoppingBagProduct>> getAllProducts() {
        return allProducts;
    }

    public LiveData<Double> getTotalPrice() {
        return totalPrice;
    }

    public void insert(ShoppingBagProduct product) {
        repository.insert(product);
    }

    public void deleteById(String productId) {
        repository.deleteById(productId);
    }
}