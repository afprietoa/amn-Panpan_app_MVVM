package com.atenea.unaltodosalau.crudsqlite.presentation.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.core.BaseViewHolder;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.ProductListAdapter;
import com.bumptech.glide.Glide;

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
        int imageResId = Integer.parseInt(product.getImage1());
        Glide.with(itemView.getContext()).load(imageResId).into(imgProduct);

        btnEdit.setOnClickListener(v -> listener.onEditClick(product));
        btnDelete.setOnClickListener(v -> listener.onDeleteClick(product));
        itemView.setOnClickListener(v -> listener.onItemClick(product));
    }
}