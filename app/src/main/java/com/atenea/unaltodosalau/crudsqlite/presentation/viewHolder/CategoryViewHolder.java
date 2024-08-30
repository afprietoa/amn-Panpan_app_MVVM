package com.atenea.unaltodosalau.crudsqlite.presentation.viewHolder;

import android.app.Activity;
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
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.FileNotFoundException;
import java.io.InputStream;

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
                // Intentar interpretar el string como un recurso drawable
                int imageResId = Integer.parseInt(imageString);
                imgCategory.setImageResource(imageResId);
            } catch (NumberFormatException e) {
                // Si no es un número, intentar cargarlo como URI desde la galería
                Uri imageUri = Uri.parse(imageString);
                if (imageUri != null && imageUri.getScheme() != null) {
                    // Usar ImagePicker para cargar la imagen desde la galería
                    ImagePicker.with((Activity) itemView.getContext())
                            .crop()                // (Optional) Crop the image (you can customize the crop properties)
                            .galleryOnly()         // (Optional) Only allow picking images from the gallery
                            .createIntent(intent -> {
                                imgCategory.setImageURI(imageUri);
                                return null; // Return null to avoid launching the intent immediately
                            });
                } else {
                    imgCategory.setImageResource(R.drawable.error_image);
                }
            }
        } else {
            imgCategory.setImageResource(R.drawable.error_image); // Imagen por defecto si no hay imagen
        }

        btnEdit.setOnClickListener(v -> listener.onEditClick(category));
        btnDelete.setOnClickListener(v -> listener.onDeleteClick(category));
        itemView.setOnClickListener(v -> listener.onItemClick(category));
    }
}