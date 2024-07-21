package com.atenea.unaltodosalau.crudsqlite.domain.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.atenea.unaltodosalau.crudsqlite.data.dao.AddressDao;
import com.atenea.unaltodosalau.crudsqlite.data.database.AppDatabase;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Address;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddressRepository {
    private final AddressDao addressDao;
    private final ExecutorService executorService;

    public AddressRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        addressDao = db.addressDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Address>> getAddressesByUser(String userId) {
        return addressDao.getAddressesByUser(userId);
    }

    public void insert(Address address) {
        executorService.execute(() -> addressDao.insert(address));
    }

    public void update(Address address) {
        executorService.execute(() -> addressDao.update(address));
    }

    public void delete(Address address) {
        executorService.execute(() -> addressDao.delete(address));
    }
}
