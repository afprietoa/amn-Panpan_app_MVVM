package com.atenea.unaltodosalau.crudsqlite.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.core.BaseViewHolder;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    private List<Product> products = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
        void onEditClick(Product product);
        void onDeleteClick(Product product);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_product_list_item, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product currentProduct = products.get(position);
        holder.bind(currentProduct, listener);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    static class ProductViewHolder extends BaseViewHolder {
        private TextView txtName, txtDescription, txtPrice;
        private ImageView imgProduct, btnEdit, btnDelete;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.product_name);
            txtDescription = itemView.findViewById(R.id.product_description);
            txtPrice = itemView.findViewById(R.id.product_price);
            imgProduct = itemView.findViewById(R.id.product_image);
            btnEdit = itemView.findViewById(R.id.edit_product_button);
            btnDelete = itemView.findViewById(R.id.delete_product_button);
        }

        public void bind(Product product, OnItemClickListener listener) {
            txtName.setText(product.getName());
            txtDescription.setText(product.getDescription());
            txtPrice.setText(String.valueOf(product.getPrice()));

            // Convierte la cadena a un entero y usa Glide para cargar la imagen
            int imageResId = Integer.parseInt(product.getImage1());
            Glide.with(itemView.getContext()).load(imageResId).into(imgProduct);

            btnEdit.setOnClickListener(v -> listener.onEditClick(product));
            btnDelete.setOnClickListener(v -> listener.onDeleteClick(product));
            itemView.setOnClickListener(v -> listener.onItemClick(product));
        }
    }
}