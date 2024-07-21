package com.atenea.unaltodosalau.crudsqlite.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.core.BaseViewHolder;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {
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

    static class CategoryViewHolder extends BaseViewHolder {
        private TextView txtName, txtDescription;
        private ImageView imgCategory, btnEdit, btnDelete;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.category_name);
            txtDescription = itemView.findViewById(R.id.category_description);
            imgCategory = itemView.findViewById(R.id.category_image);
            btnEdit = itemView.findViewById(R.id.edit_category_button);
            btnDelete = itemView.findViewById(R.id.delete_category_button);
        }

        public void bind(Category category, OnItemClickListener listener) {
            txtName.setText(category.getName());
            txtDescription.setText(category.getDescription());

            // Convierte la cadena a un entero y usa Glide para cargar la imagen
            int imageResId = Integer.parseInt(category.getImage());
            Glide.with(itemView.getContext()).load(imageResId).into(imgCategory);

            btnEdit.setOnClickListener(v -> listener.onEditClick(category));
            btnDelete.setOnClickListener(v -> listener.onDeleteClick(category));
            itemView.setOnClickListener(v -> listener.onItemClick(category));
        }
    }
}