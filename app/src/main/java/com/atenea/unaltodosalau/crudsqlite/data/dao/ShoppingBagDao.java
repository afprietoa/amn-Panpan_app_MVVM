package com.atenea.unaltodosalau.crudsqlite.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.atenea.unaltodosalau.crudsqlite.domain.model.ShoppingBagProduct;

import java.util.List;

@Dao
public interface ShoppingBagDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ShoppingBagProduct product);

    @Update
    void update(ShoppingBagProduct product);

    @Delete
    void delete(ShoppingBagProduct product);

    @Query("SELECT * FROM shopping_bag")
    LiveData<List<ShoppingBagProduct>> getAllProducts();

    @Query("DELETE FROM shopping_bag WHERE id_shopping_bag = :productId")
    void deleteById(String productId);

    @Query("SELECT SUM(price * quantity) FROM shopping_bag")
    LiveData<Double> getTotalPrice();
}