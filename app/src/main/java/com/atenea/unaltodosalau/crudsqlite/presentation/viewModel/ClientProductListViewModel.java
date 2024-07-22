package com.atenea.unaltodosalau.crudsqlite.presentation.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;
import com.atenea.unaltodosalau.crudsqlite.domain.repository.ProductRepository;

import java.util.List;

public class ClientProductListViewModel extends AndroidViewModel {
    private ProductRepository repository;
    private LiveData<List<Product>> allProducts;

    public ClientProductListViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepository(application);
        allProducts = repository.getAllProducts();
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public LiveData<List<Product>> getProductsByCategory(int categoryId) {
        return repository.getProductsByCategory(categoryId);
    }

    public LiveData<List<Product>> getProductsByByName(String name) {
        return repository.getProductsByByName(name);
    }
}
