package com.example.delfiasale.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delfiasale.R;
import com.example.delfiasale.ui.model.ReceiptLine;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {

    private final List<ReceiptLine> lines;
    private final NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("bg", "BG"));

    public ReceiptAdapter(List<ReceiptLine> lines) {
        this.lines = lines;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receipt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReceiptLine line = lines.get(position);
        holder.name.setText(line.getName());
        holder.quantity.setText(String.valueOf(line.getQuantity()));
        holder.total.setText(formatter.format(line.getTotal()));
    }

    @Override
    public int getItemCount() {
        return lines.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView quantity;
        final TextView total;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.receiptItemName);
            quantity = itemView.findViewById(R.id.receiptItemQuantity);
            total = itemView.findViewById(R.id.receiptItemTotal);
        }
    }
}
