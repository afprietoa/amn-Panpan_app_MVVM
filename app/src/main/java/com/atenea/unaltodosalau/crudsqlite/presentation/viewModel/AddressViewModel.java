package com.atenea.unaltodosalau.crudsqlite.presentation.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.atenea.unaltodosalau.crudsqlite.domain.model.Address;
import com.atenea.unaltodosalau.crudsqlite.domain.repository.AddressRepository;

import java.util.List;

public class AddressViewModel extends AndroidViewModel {
    private final AddressRepository repository;
    private final LiveData<List<Address>> allAddresses;

    public AddressViewModel(Application application) {
        super(application);
        repository = new AddressRepository(application);
        allAddresses = repository.getAddressesByUser("1");
    }

    public LiveData<List<Address>> getAllAddresses() {
        return allAddresses;
    }

    public void insert(Address address) {
        repository.insert(address);
    }

    public void update(Address address) {
        repository.update(address);
    }

    public void delete(Address address) {
        repository.delete(address);
    }
}