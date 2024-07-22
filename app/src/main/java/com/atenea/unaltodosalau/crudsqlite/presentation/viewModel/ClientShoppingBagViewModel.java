package com.atenea.unaltodosalau.crudsqlite.presentation.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.atenea.unaltodosalau.crudsqlite.domain.model.ShoppingBagProduct;
import com.atenea.unaltodosalau.crudsqlite.domain.repository.ShoppingBagRepository;

import java.util.List;

public class ClientShoppingBagViewModel extends AndroidViewModel {
    private ShoppingBagRepository repository;
    private LiveData<List<ShoppingBagProduct>> allProducts;
    private LiveData<Double> totalPrice;

    public ClientShoppingBagViewModel(@NonNull Application application) {
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

    public void addProduct(ShoppingBagProduct product) {
        repository.insert(product);
    }

    public void updateProduct(ShoppingBagProduct product) {
        repository.update(product);
    }

    public void deleteProduct(ShoppingBagProduct product) {
        repository.delete(product);
    }

    public void deleteProductById(String productId) {
        repository.deleteById(productId);
    }
}