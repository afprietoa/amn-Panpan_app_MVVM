package com.atenea.unaltodosalau.crudsqlite.presentation.viewHolder;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.core.BaseViewHolder;
import com.atenea.unaltodosalau.crudsqlite.domain.model.ShoppingBagProduct;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.ShoppingBagListAdapter;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class ShoppingBagViewHolder extends BaseViewHolder {
    private TextView productName, productPrice, productQuantity;
    private ImageView productImage;
    private ImageButton deleteButton, incrementButton, decrementButton;

    public ShoppingBagViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.product_name_shopping_bag);
        productPrice = itemView.findViewById(R.id.product_price_shopping_bag);
        productQuantity = itemView.findViewById(R.id.product_quantity_shopping_bag);
        productImage = itemView.findViewById(R.id.img_product_shopping_bag);
        deleteButton = itemView.findViewById(R.id.delete_button_shopping_bag);
        incrementButton = itemView.findViewById(R.id.increment_button_shopping_bag);
        decrementButton = itemView.findViewById(R.id.decrement_button_shopping_bag);
    }

    public void bind(ShoppingBagProduct product, ShoppingBagListAdapter.OnItemClickListener listener) {
        productName.setText(product.getName());
        productPrice.setText(String.valueOf(product.getPrice()));
        productQuantity.setText(String.valueOf(product.getQuantity()));
        String imageString = product.getImage1();

        if (imageString != null) {
            try {
                // Try interpreting the string as a drawable resource ID
                int imageResId = Integer.parseInt(imageString);
                productImage.setImageResource(imageResId);
            } catch (NumberFormatException e) {
                // If not a number, try to load it as a URI from the gallery
                Uri imageUri = Uri.parse(imageString);
                if (imageUri != null && imageUri.getScheme() != null) {
                    // Use ImagePicker to handle loading from the gallery
                    ImagePicker.with((Activity) itemView.getContext())
                            .galleryOnly()         // Only allow picking images from the gallery
                            .createIntent(intent -> {
                                productImage.setImageURI(imageUri);
                                return null; // Return null to avoid launching the intent immediately
                            });
                } else {
                    productImage.setImageResource(R.drawable.error_image); // Set error image if URI is invalid
                }
            }
        } else {
            productImage.setImageResource(R.drawable.error_image); // Default image if none is provided
        }

        incrementButton.setOnClickListener(v -> listener.onIncrementClick(product));
        decrementButton.setOnClickListener(v -> listener.onDecrementClick(product));
        deleteButton.setOnClickListener(v -> listener.onDeleteClick(product));
    }
}
