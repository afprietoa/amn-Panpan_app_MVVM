package com.atenea.unaltodosalau.crudsqlite.presentation.viewHolder;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.ClientProductListAdapter;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.ProductListAdapter;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class ClientProductViewHolder extends RecyclerView.ViewHolder {
    private TextView txtName, txtDescription, txtPrice;
    private ImageView imgProduct;

    public ClientProductViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.product_item_client_name1);
        txtDescription = itemView.findViewById(R.id.product_item_client_description1);
        txtPrice = itemView.findViewById(R.id.product_item_client_price1);
        imgProduct = itemView.findViewById(R.id.product_item_client_image1);
    }

    public void bind(Product product, ClientProductListAdapter.OnItemClickListener listener) {
        txtName.setText(product.getName());
        txtDescription.setText(product.getDescription());
        txtPrice.setText(String.valueOf(product.getPrice()));
        String imageString = product.getImage1();

        if (imageString != null) {
            try {
                // Try interpreting the string as a drawable resource ID
                int imageResId = Integer.parseInt(imageString);
                imgProduct.setImageResource(imageResId);
            } catch (NumberFormatException e) {
                // If not a number, try to load it as a URI from the gallery
                Uri imageUri = Uri.parse(imageString);
                if (imageUri != null && imageUri.getScheme() != null) {
                    // Use ImagePicker to handle loading from the gallery
                    ImagePicker.with((Activity) itemView.getContext())
                            .galleryOnly()         // Only allow picking images from the gallery
                            .createIntent(intent -> {
                                imgProduct.setImageURI(imageUri);
                                return null; // Return null to avoid launching the intent immediately
                            });
                } else {
                    imgProduct.setImageResource(R.drawable.error_image); // Set error image if URI is invalid
                }
            }
        } else {
            imgProduct.setImageResource(R.drawable.error_image); // Default image if none is provided
        }

        itemView.setOnClickListener(v -> listener.onItemClick(product));
    }
}