package com.atenea.unaltodosalau.crudsqlite.domain.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.atenea.unaltodosalau.crudsqlite.data.dao.CategoriesDao;
import com.atenea.unaltodosalau.crudsqlite.data.database.AppDatabase;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryRepository {
    private final CategoriesDao categoriesDao;
    private final ExecutorService executorService;

    public CategoryRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        categoriesDao = db.categoriesDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Category>> getAllCategories() {
        return categoriesDao.getAllCategories();
    }

    public void insert(Category category) {
        executorService.execute(() -> categoriesDao.insert(category));
    }

    public void update(Category category) {
        executorService.execute(() -> categoriesDao.update(category));
    }

    public void delete(Category category) {
        executorService.execute(() -> categoriesDao.delete(category));
    }
}