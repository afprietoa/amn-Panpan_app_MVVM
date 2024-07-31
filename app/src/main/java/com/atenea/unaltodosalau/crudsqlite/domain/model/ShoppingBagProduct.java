package com.atenea.unaltodosalau.crudsqlite.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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
    public int idCategory;

    @ColumnInfo(name = "image1")
    public String image1;

    @ColumnInfo(name = "price")
    public double price;

    @ColumnInfo(name = "quantity")
    public int quantity;

    public ShoppingBagProduct(int quantity, double price, String image1, int idCategory, String name) {
        this.quantity = quantity;
        this.price = price;
        this.image1 = image1;
        this.idCategory = idCategory;
        this.name = name;
    }

    @Ignore
    public ShoppingBagProduct() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
