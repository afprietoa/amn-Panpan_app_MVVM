package com.atenea.unaltodosalau.crudsqlite.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "shopping_bag")
public class ShoppingBagProduct implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_shopping_bag")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "id_category")
    public String idCategory;

    @ColumnInfo(name = "image1")
    public String image1;

    @ColumnInfo(name = "price")
    public double price;

    @ColumnInfo(name = "quantity")
    public int quantity;


}
