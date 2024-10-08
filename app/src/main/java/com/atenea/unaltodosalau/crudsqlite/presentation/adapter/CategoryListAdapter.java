package com.atenea.unaltodosalau.crudsqlite.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewHolder.CategoryViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    private List<Category> categories = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Category category);
        void onEditClick(Category category);
        void onDeleteClick(Category category);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_category_list_item, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category currentCategory = categories.get(position);
        holder.bind(currentCategory, listener);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }
}