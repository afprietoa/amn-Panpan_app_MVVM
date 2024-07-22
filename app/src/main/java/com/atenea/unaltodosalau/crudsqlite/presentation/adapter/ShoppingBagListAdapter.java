package com.atenea.unaltodosalau.crudsqlite.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.ShoppingBagProduct;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewHolder.ShoppingBagViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBagListAdapter extends RecyclerView.Adapter<ShoppingBagViewHolder> {
    private List<ShoppingBagProduct> products = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onIncrementClick(ShoppingBagProduct product);
        void onDecrementClick(ShoppingBagProduct product);
        void onDeleteClick(ShoppingBagProduct product);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShoppingBagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_shopping_bag_item, parent, false);
        return new ShoppingBagViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingBagViewHolder holder, int position) {
        ShoppingBagProduct currentProduct = products.get(position);
        holder.bind(currentProduct, listener);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<ShoppingBagProduct> products) {
        this.products = products;
        notifyDataSetChanged();
    }
}
