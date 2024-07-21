package com.atenea.unaltodosalau.crudsqlite.data.datasource;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MemoryDataSource {

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Backpack", "Variedad de mochilas", String.valueOf(R.drawable.product1)));
        categories.add(new Category("Clothing", "Ropa de calidad", String.valueOf(R.drawable.product2)));
        categories.add(new Category("Shoes", "Calzado cómodo y elegante", String.valueOf(R.drawable.product3)));
        categories.add(new Category("Handbag", "Bolsos de moda", String.valueOf(R.drawable.product7)));
        categories.add(new Category("Wristwatch", "Relojes de lujo", String.valueOf(R.drawable.product11)));
        return categories;
    }

    public List<Product> getProducts(int categoryId) {
        List<Product> products = new ArrayList<>();
        switch (categoryId) {
            case 1:
                products.add(new Product("Robust Backpack", "Mochila robusta", String.valueOf(R.drawable.product1), String.valueOf(R.drawable.product1), categoryId, 500));
                products.add(new Product("Lightweight Backpack", "Mochila ligera", String.valueOf(R.drawable.product4), String.valueOf(R.drawable.product4), categoryId, 350));
                break;
            case 2:
                products.add(new Product("Elegant Shirt", "Camisa elegante", String.valueOf(R.drawable.product2), String.valueOf(R.drawable.product2), categoryId, 750));
                products.add(new Product("Casual Shirt", "Camisa casual", String.valueOf(R.drawable.product5), String.valueOf(R.drawable.product5), categoryId, 225));
                products.add(new Product("Comfortable Pants", "Pantalones cómodos", String.valueOf(R.drawable.product6), String.valueOf(R.drawable.product6), categoryId, 150));
                break;
            case 3:
                products.add(new Product("Sports Shoes", "Zapatos deportivos", String.valueOf(R.drawable.product3), String.valueOf(R.drawable.product3), categoryId, 400));
                products.add(new Product("Casual Shoes", "Zapatos casuales", String.valueOf(R.drawable.product8), String.valueOf(R.drawable.product8), categoryId, 525));
                products.add(new Product("Formal Shoes", "Zapatos formales", String.valueOf(R.drawable.product9), String.valueOf(R.drawable.product9), categoryId, 425));
                products.add(new Product("Elegant Shoes", "Zapatos elegantes", String.valueOf(R.drawable.product10), String.valueOf(R.drawable.product10), categoryId, 475));
                products.add(new Product("Leather Shoes", "Zapatos de cuero", String.valueOf(R.drawable.product14), String.valueOf(R.drawable.product14), categoryId, 320));
                break;
            case 4:
                products.add(new Product("Leather Handbag", "Bolso de cuero", String.valueOf(R.drawable.product7), String.valueOf(R.drawable.product7), categoryId, 300));
                products.add(new Product("Elegant Handbag", "Bolso elegante", String.valueOf(R.drawable.product13), String.valueOf(R.drawable.product13), categoryId, 105));
                break;
            case 5:
                products.add(new Product("Digital Watch", "Reloj digital", String.valueOf(R.drawable.product11), String.valueOf(R.drawable.product11), categoryId, 345));
                products.add(new Product("Analog Watch", "Reloj analógico", String.valueOf(R.drawable.product12), String.valueOf(R.drawable.product12), categoryId, 205));
                break;
        }
        return products;
    }
}