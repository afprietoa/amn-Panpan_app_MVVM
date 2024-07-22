package com.atenea.unaltodosalau.crudsqlite.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Product;
import com.atenea.unaltodosalau.crudsqlite.presentation.viewHolder.ClientProductViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ClientProductListAdapter extends RecyclerView.Adapter<ClientProductViewHolder> {
    private List<Product> products = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClientProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_product_list_item, parent, false);
        return new ClientProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientProductViewHolder holder, int position) {
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
}
