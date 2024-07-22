package com.atenea.unaltodosalau.crudsqlite.presentation.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.atenea.unaltodosalau.crudsqlite.domain.model.ShoppingBagProduct;
import com.atenea.unaltodosalau.crudsqlite.domain.repository.ShoppingBagRepository;

public class ClientProductDetailViewModel extends AndroidViewModel {
    private ShoppingBagRepository shoppingBagRepository;

    public ClientProductDetailViewModel(@NonNull Application application) {
        super(application);
        shoppingBagRepository = new ShoppingBagRepository(application);
    }

    public void addProductToCart(ShoppingBagProduct product) {
        shoppingBagRepository.insert(product);
    }
}