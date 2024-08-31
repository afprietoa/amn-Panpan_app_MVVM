package com.atenea.unaltodosalau.crudsqlite.presentation.viewHolder;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.core.BaseViewHolder;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.ProductListAdapter;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class ProductViewHolder extends BaseViewHolder {
    private TextView txtName, txtDescription, txtPrice;
    private ImageView imgProduct, btnEdit, btnDelete;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.text_product_list_item_admin);
        txtDescription = itemView.findViewById(R.id.product_description_list_item_admin);
        txtPrice = itemView.findViewById(R.id.product_price_list_item_admin);
        imgProduct = itemView.findViewById(R.id.image_product_list_item_admin);
        btnEdit = itemView.findViewById(R.id.edit_product_button_list_item_admin);
        btnDelete = itemView.findViewById(R.id.delete_product_button_list_item_admin);
    }

    public void bind(Product product, ProductListAdapter.OnItemClickListener listener) {
        txtName.setText(product.getName());
        txtDescription.setText(product.getDescription());
        txtPrice.setText(String.valueOf(product.getPrice()));
        String imageString = product.getImage1();

        if (imageString != null) {
            try {
                // Attempt to interpret the string as a drawable resource ID
                int imageResId = Integer.parseInt(imageString);
                imgProduct.setImageResource(imageResId);
            } catch (NumberFormatException e) {
                // If not a number, try to load it as a URI from the gallery
                Uri imageUri = Uri.parse(imageString);
                if (imageUri != null && imageUri.getScheme() != null) {
                    // Use ImagePicker to handle loading from the gallery
                    ImagePicker.with((Activity) itemView.getContext())
                            .crop()                // (Optional) Crop the image (you can customize the crop properties)
                            .galleryOnly()         // (Optional) Only allow picking images from the gallery
                            .createIntent(intent -> {
                                imgProduct.setImageURI(imageUri);
                                return null; // Return null to avoid launching the intent immediately
                            });
                } else {
                    imgProduct.setImageResource(R.drawable.error_image);
                }
            }
        } else {
            imgProduct.setImageResource(R.drawable.error_image); // Default image if none is provided
        }

        btnEdit.setOnClickListener(v -> listener.onEditClick(product));
        btnDelete.setOnClickListener(v -> listener.onDeleteClick(product));
        itemView.setOnClickListener(v -> listener.onItemClick(product));
    }
}