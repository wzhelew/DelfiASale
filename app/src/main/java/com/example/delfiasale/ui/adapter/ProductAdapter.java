package com.example.delfiasale.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delfiasale.R;
import com.example.delfiasale.data.model.ItemDto;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public interface OnProductClickListener {
        void onProductSelected(ItemDto item);
    }

    private final OnProductClickListener listener;
    private List<ItemDto> products;
    private final NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("bg", "BG"));

    public ProductAdapter(List<ItemDto> products, OnProductClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    public void updateData(List<ItemDto> items) {
        this.products = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemDto item = products.get(position);
        holder.name.setText(item.getName());
        holder.price.setText(formatter.format(item.getPrice()));
        holder.itemView.setOnClickListener(v -> listener.onProductSelected(item));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView name;
        final TextView price;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
        }
    }
}
