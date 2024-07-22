package com.atenea.unaltodosalau.crudsqlite.presentation.viewHolder;

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

public class ClientProductViewHolder extends RecyclerView.ViewHolder {
    private TextView txtName, txtDescription, txtPrice;
    private ImageView imgProduct;

    public ClientProductViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.product_client_name);
        txtDescription = itemView.findViewById(R.id.product_client_description);
        txtPrice = itemView.findViewById(R.id.product_client_price);
        imgProduct = itemView.findViewById(R.id.product_client_image);
    }

    public void bind(Product product, ClientProductListAdapter.OnItemClickListener listener) {
        txtName.setText(product.getName());
        txtDescription.setText(product.getDescription());
        txtPrice.setText(String.valueOf(product.getPrice()));
        int imageResId = Integer.parseInt(product.getImage1());
        Glide.with(itemView.getContext()).load(imageResId).into(imgProduct);

        itemView.setOnClickListener(v -> listener.onItemClick(product));
    }
}