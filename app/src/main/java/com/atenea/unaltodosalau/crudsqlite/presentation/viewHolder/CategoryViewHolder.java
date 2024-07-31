package com.atenea.unaltodosalau.crudsqlite.presentation.viewHolder;

import android.net.Uri;
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
        txtName = itemView.findViewById(R.id.text_category_list_admin);
        txtDescription = itemView.findViewById(R.id.category_description_list_admin);
        imgCategory = itemView.findViewById(R.id.category_image_list_admin);
        btnEdit = itemView.findViewById(R.id.edit_category_button_list_admin);
        btnDelete = itemView.findViewById(R.id.delete_category_button_list_admin);
    }

    public void bind(Category category, CategoryListAdapter.OnItemClickListener listener) {
        txtName.setText(category.getName());
        txtDescription.setText(category.getDescription());
        String imageString = category.getImage();
        if (imageString != null) {
            try {
                // If the imageString is an integer (resource ID), load it as a resource
                int imageResId = Integer.parseInt(imageString);
                Glide.with(itemView.getContext()).load(imageResId).into(imgCategory);
            } catch (NumberFormatException e) {
                // If it's not an integer, it should be a URI, extract the number at the end of the URI and load it
                Uri imageUri = Uri.parse(imageString);
                String lastSegment = imageUri.getLastPathSegment();
                if (lastSegment != null) {
                    try {
                        int imageId = Integer.parseInt(lastSegment);
                        Glide.with(itemView.getContext()).load(imageId).into(imgCategory);
                    } catch (NumberFormatException ex) {
                        // Log or handle the error when the last segment is not a number
                        ex.printStackTrace();
                    }
                }
            }

            btnEdit.setOnClickListener(v -> listener.onEditClick(category));
            btnDelete.setOnClickListener(v -> listener.onDeleteClick(category));
            itemView.setOnClickListener(v -> listener.onItemClick(category));
        }
    }
}