package com.atenea.unaltodosalau.crudsqlite.presentation.viewHolder;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.core.BaseViewHolder;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.CategoryListAdapter;
import com.bumptech.glide.Glide;

import java.io.File;

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

        Log.d("CategoryViewHolder", "Image String: " + imageString); // Agrega este log para verificar el valor

        if (imageString != null) {
            if (imageString.startsWith("file://")) {
                // Es una ruta de archivo local
                Uri imageUri = Uri.parse(imageString);
                File file = new File(imageUri.getPath());
                if (file.exists()) {
                    Glide.with(itemView.getContext())
                            .load(imageUri)
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .into(imgCategory);
                } else {
                    imgCategory.setImageResource(R.drawable.error_image); // Imagen por defecto si el archivo no existe
                }
            } else {
                // Podría ser una URL o un recurso drawable
                try {
                    int imageResId = Integer.parseInt(imageString);
                    Glide.with(itemView.getContext())
                            .load(imageResId)
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .into(imgCategory);
                } catch (NumberFormatException e) {
                    Uri imageUri = Uri.parse(imageString);
                    Glide.with(itemView.getContext())
                            .load(imageUri)
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .into(imgCategory);
                }
            }
        } else {
            imgCategory.setImageResource(R.drawable.error_image);
        }

        btnEdit.setOnClickListener(v -> listener.onEditClick(category));
        btnDelete.setOnClickListener(v -> listener.onDeleteClick(category));
        itemView.setOnClickListener(v -> listener.onItemClick(category));
    }
}