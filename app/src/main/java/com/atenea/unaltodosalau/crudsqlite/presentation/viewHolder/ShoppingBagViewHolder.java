package com.atenea.unaltodosalau.crudsqlite.presentation.viewHolder;

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

public class ShoppingBagViewHolder extends BaseViewHolder {
    private TextView productName, productPrice, productQuantity;
    private ImageView productImage;
    private ImageButton deleteButton, incrementButton, decrementButton;

    public ShoppingBagViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.product_name_shopping_bag);
        productPrice = itemView.findViewById(R.id.product_price_shopping_bag);
        productQuantity = itemView.findViewById(R.id.product_price_shopping_bag);
        productImage = itemView.findViewById(R.id.img_product_shopping_bag);
        deleteButton = itemView.findViewById(R.id.delete_button_shopping_bag);
        incrementButton = itemView.findViewById(R.id.increment_button_shopping_bag);
        decrementButton = itemView.findViewById(R.id.decrement_button_shooping_bag);
    }

    public void bind(ShoppingBagProduct product, ShoppingBagListAdapter.OnItemClickListener listener) {
        productName.setText(product.getName());
        productPrice.setText(String.valueOf(product.getPrice()));
        productQuantity.setText(String.valueOf(product.getQuantity()));
        int imageResId = Integer.parseInt(product.getImage1());
        Glide.with(itemView.getContext()).load(imageResId).into(productImage);

        incrementButton.setOnClickListener(v -> listener.onIncrementClick(product));
        decrementButton.setOnClickListener(v -> listener.onDecrementClick(product));
        deleteButton.setOnClickListener(v -> listener.onDeleteClick(product));
    }
}
