package com.atenea.unaltodosalau.crudsqlite.presentation.viewHolder;

import android.view.View;
import android.widget.Button;
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
    private ImageView productImage, deleteButton;
    private Button incrementButton, decrementButton;

    public ShoppingBagViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.product_name);
        productPrice = itemView.findViewById(R.id.product_price_bagett);
        productQuantity = itemView.findViewById(R.id.quantity_shopclitem);
        productImage = itemView.findViewById(R.id.product_baggett_detail);
        deleteButton = itemView.findViewById(R.id.delete_button);
        incrementButton = itemView.findViewById(R.id.increment_button);
        decrementButton = itemView.findViewById(R.id.decrement_button);
    }

    public void bind(ShoppingBagProduct product, ShoppingBagListAdapter.OnItemClickListener listener) {
        productName.setText(product.getName());
        productPrice.setText(String.valueOf(product.getPrice()));
        productQuantity.setText(String.valueOf(product.getQuantity()));
        Glide.with(itemView.getContext()).load(product.getImage1()).into(productImage);

        incrementButton.setOnClickListener(v -> listener.onIncrementClick(product));
        decrementButton.setOnClickListener(v -> listener.onDecrementClick(product));
        deleteButton.setOnClickListener(v -> listener.onDeleteClick(product));
    }
}
