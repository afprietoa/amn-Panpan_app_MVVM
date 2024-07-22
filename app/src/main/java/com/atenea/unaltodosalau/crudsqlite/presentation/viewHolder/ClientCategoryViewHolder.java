package com.atenea.unaltodosalau.crudsqlite.presentation.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atenea.unaltodosalau.crudsqlite.R;
import com.atenea.unaltodosalau.crudsqlite.domain.model.Category;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.CategoryListAdapter;
import com.atenea.unaltodosalau.crudsqlite.presentation.adapter.ClientCategoryListAdapter;
import com.bumptech.glide.Glide;

public class ClientCategoryViewHolder extends RecyclerView.ViewHolder {
    private TextView txtName, txtDescription;
    private ImageView imgCategory;

    public ClientCategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.category_client_name);
        txtDescription = itemView.findViewById(R.id.category_client_description);
        imgCategory = itemView.findViewById(R.id.category_client_image);
    }

    public void bind(Category category, ClientCategoryListAdapter.OnItemClickListener listener) {
        txtName.setText(category.getName());
        txtDescription.setText(category.getDescription());
        int imageResId = Integer.parseInt(category.getImage());
        Glide.with(itemView.getContext()).load(imageResId).into(imgCategory);

        itemView.setOnClickListener(v -> listener.onItemClick(category));
    }
}