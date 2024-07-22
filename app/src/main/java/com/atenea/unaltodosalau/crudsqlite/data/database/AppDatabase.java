/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad Nacional de Colombia (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 * Licenciado bajo el esquema Academic Free License version 2.1
 *
 * Proyecto Todos a la U (http://unaltodosalau.com)
 * Ejercicio: Crud de Usuarios
 * Autor: afprietoa
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.atenea.unaltodosalau.crudsqlite.data.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;

import com.atenea.unaltodosalau.crudsqlite.data.dao.AddressDao;
import com.atenea.unaltodosalau.crudsqlite.data.dao.CategoriesDao;
import com.atenea.unaltodosalau.crudsqlite.data.dao.ProductsDao;
import com.atenea.unaltodosalau.crudsqlite.data.dao.ShoppingBagDao;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Address;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Order;
import com.atenea.unaltodosalau.crudsqlite.domain.model.OrderHasProducts;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Role;
import com.atenea.unaltodosalau.crudsqlite.domain.model.ShoppingBagProduct;
import com.atenea.unaltodosalau.crudsqlite.domain.model.User;
import com.atenea.unaltodosalau.crudsqlite.domain.model.UserRole;

@Database(entities = {OrderHasProducts.class, Order.class, Role.class, UserRole.class, User.class, Address.class, Category.class, Product.class, ShoppingBagProduct.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract AddressDao addressDao();
    public abstract CategoriesDao categoriesDao();
    public abstract ProductsDao productsDao();
    public abstract ShoppingBagDao shoppingBagDao();

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "ecommerce_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}