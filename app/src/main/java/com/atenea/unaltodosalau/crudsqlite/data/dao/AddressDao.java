package com.atenea.unaltodosalau.crudsqlite.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.atenea.unaltodosalau.crudsqlite.domain.model.Address;

import java.util.List;

@Dao
public interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Address address);

    @Query("SELECT * FROM Address WHERE id_user = :userId")
    LiveData<List<Address>> getAddressesByUser(String userId);

    @Update
    void update(Address address);

    @Delete
    void delete(Address address);
}