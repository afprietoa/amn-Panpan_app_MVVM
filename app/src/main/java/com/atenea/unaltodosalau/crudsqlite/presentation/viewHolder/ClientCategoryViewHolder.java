package com.atenea.unaltodosalau.crudsqlite.presentation.viewHolder;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.CategoryListAdapter;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.ClientCategoryListAdapter;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class ClientCategoryViewHolder extends RecyclerView.ViewHolder {
    private TextView txtName, txtDescription;
    private ImageView imgCategory;

    public ClientCategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.category_client_name);
        txtDescription = itemView.findViewById(R.id.category_client_description);
        imgCategory = itemView.findViewById(R.id.category_client_image);
    }

    public void bind(Category category, ClientCategoryListAdapter.OnItemClickListener listener) {
        txtName.setText(category.getName());
        txtDescription.setText(category.getDescription());
        String imageString = category.getImage();

        if (imageString != null) {
            try {
                // Attempt to interpret the string as a drawable resource ID
                int imageResId = Integer.parseInt(imageString);
                imgCategory.setImageResource(imageResId);
            } catch (NumberFormatException e) {
                // If not a number, try to load it as a URI from the gallery
                Uri imageUri = Uri.parse(imageString);
                if (imageUri != null && imageUri.getScheme() != null) {
                    // Use ImagePicker to handle loading from the gallery
                    ImagePicker.with((Activity) itemView.getContext())
                            .galleryOnly()         // Only allow picking images from the gallery
                            .createIntent(intent -> {
                                imgCategory.setImageURI(imageUri);
                                return null; // Return null to avoid launching the intent immediately
                            });
                } else {
                    imgCategory.setImageResource(R.drawable.error_image);
                }
            }
        } else {
            imgCategory.setImageResource(R.drawable.error_image); // Default image if none is provided
        }

        itemView.setOnClickListener(v -> listener.onItemClick(category));
    }
}