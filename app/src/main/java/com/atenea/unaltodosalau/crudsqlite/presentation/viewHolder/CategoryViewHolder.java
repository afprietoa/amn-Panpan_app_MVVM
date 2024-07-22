package com.atenea.unaltodosalau.crudsqlite.presentation.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.core.BaseViewHolder;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.CategoryListAdapter;
import com.bumptech.glide.Glide;

public class CategoryViewHolder extends BaseViewHolder {
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

    public void bind(Category category, CategoryListAdapter.OnItemClickListener listener) {
        txtName.setText(category.getName());
        txtDescription.setText(category.getDescription());
        int imageResId = Integer.parseInt(category.getImage());
        Glide.with(itemView.getContext()).load(imageResId).into(imgCategory);

        btnEdit.setOnClickListener(v -> listener.onEditClick(category));
        btnDelete.setOnClickListener(v -> listener.onDeleteClick(category));
        itemView.setOnClickListener(v -> listener.onItemClick(category));
    }
}