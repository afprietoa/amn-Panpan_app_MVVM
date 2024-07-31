package com.atenea.unaltodosalau.crudsqlite.data.datasource;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MemoryDataSource {

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Panaderia", "Variedad de panes frescos y artesanales para cada comida", String.valueOf(R.drawable.pan)));
        categories.add(new Category("Pateleria", "Clásicos y nuevos sabores, preparados con ingredientes de calidad.", String.valueOf(R.drawable.pasteleria)));
        categories.add(new Category("Postres", "Delicias dulces y adornadas para cualquier ocasión.", String.valueOf(R.drawable.postres)));
        categories.add(new Category("Bebidas", "Café, té y jugos naturales para todos los gustos.", String.valueOf(R.drawable.bebidas)));
        return categories;
    }

    public List<Product> getProducts(int categoryId) {
        List<Product> products = new ArrayList<>();
        switch (categoryId) {
            case 1:
                products.add(new Product("Baggett", "Pan francés crujiente y suave.", String.valueOf(R.drawable.baggett1), String.valueOf(R.drawable.baggett2), categoryId, 500));
                products.add(new Product("Croissant", "Hoja de masa fina, dorada y mantequillosa", String.valueOf(R.drawable.croissant1), String.valueOf(R.drawable.croissant2), categoryId, 350));
                products.add(new Product("Pan Arabe", "Pan plano y esponjoso, del Medio Oriente", String.valueOf(R.drawable.pan_arabe1), String.valueOf(R.drawable.pan_arabe2), categoryId, 250));
                products.add(new Product("Pan Frances", "Pan crujiente por fuera y suave por dentro, ligeramente dulce", String.valueOf(R.drawable.pan_frances1), String.valueOf(R.drawable.pan_frances2), categoryId, 150));
                products.add(new Product("Pan integral", "Pan elaborado con harina integral, lleno de fibra y nutrientes", String.valueOf(R.drawable.pan_integral1), String.valueOf(R.drawable.pan_integral2), categoryId, 200));
                break;
            case 2:
                products.add(new Product("Cupcakes", "Pequeños pasteles individuales, decorados con crema .", String.valueOf(R.drawable.cupcake1), String.valueOf(R.drawable.cupcake2), categoryId, 750));
                products.add(new Product("Milhojas", "Postre francés hecho con capas de hojaldre crujiente ", String.valueOf(R.drawable.milhojas1), String.valueOf(R.drawable.milhojas2), categoryId, 225));
                products.add(new Product("Pastel de Chocolate", "Exquisito pastel con múltiples capas de bizcocho húmedo", String.valueOf(R.drawable.pastel_chocolate1), String.valueOf(R.drawable.pastel_chocolate2), categoryId, 150));
                products.add(new Product("Pastel de Fresas", "Pastel de vainilla cubierto con una capa generosa de fresas", String.valueOf(R.drawable.pastel_fresas1), String.valueOf(R.drawable.pastel_fresas2), categoryId, 150));
                products.add(new Product("Pastel de Zanahoria", "Bizcocho húmedo y especiado, con trozos de zanahoria ", String.valueOf(R.drawable.pastel_zanahoria1), String.valueOf(R.drawable.pastel_zanahoria2), categoryId, 150));
                break;
            case 3:
                products.add(new Product("Brownie de Chocolate", "Delicioso pastel de chocolate, denso y húmedo", String.valueOf(R.drawable.brownie1), String.valueOf(R.drawable.brownie2), categoryId, 400));
                products.add(new Product("Cheesecake", "Pastel cremoso de queso, con una base de galleta ", String.valueOf(R.drawable.cheesecake1), String.valueOf(R.drawable.cheesecake2), categoryId, 525));
                products.add(new Product("Crème Brûlée", "Elegante postre francés, compuesto por una crema de vainilla ", String.valueOf(R.drawable.creme_brulee1), String.valueOf(R.drawable.creme_brulee2), categoryId, 425));
                products.add(new Product("Macarons", "Finos y coloridos pastelitos franceses, hechos de merengue y almendra", String.valueOf(R.drawable.macaros1), String.valueOf(R.drawable.macaros2), categoryId, 475));
                products.add(new Product("Tiramisú", "Postre italiano compuesto por capas de bizcocho empapado en café ", String.valueOf(R.drawable.tiramisu1), String.valueOf(R.drawable.tiramisu2), categoryId, 320));
                break;
            case 4:
                products.add(new Product("Café Espresso", "Fuerte y concentrado, perfecto para los amantes del café puro.", String.valueOf(R.drawable.cafe_expreso1), String.valueOf(R.drawable.cafe_expreso2), categoryId, 300));
                products.add(new Product("Capuccino", "Bebida italiana que combina espresso fuerte con leche vaporizada", String.valueOf(R.drawable.capuccino1), String.valueOf(R.drawable.capuccino2), categoryId, 105));
                products.add(new Product("Chocolate Caliente", "Bebida caliente y reconfortante, hecha con chocolate y leche", String.valueOf(R.drawable.chocolate1), String.valueOf(R.drawable.chocolate2), categoryId, 105));
                products.add(new Product("Smoothie de Frutas", "Bebida refrescante hecha con una mezcla de frutas frescas", String.valueOf(R.drawable.smoothie1), String.valueOf(R.drawable.smoothie2), categoryId, 105));
                products.add(new Product("Té Caliente", "Infusión reconfortante hecha con hojas de té de alta calidad", String.valueOf(R.drawable.te1), String.valueOf(R.drawable.te2), categoryId, 105));
                break;
        }
        return products;
    }
}