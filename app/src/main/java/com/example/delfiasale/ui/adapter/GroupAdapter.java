package com.example.delfiasale.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delfiasale.R;
import com.example.delfiasale.data.model.GroupDto;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    public interface OnGroupClickListener {
        void onGroupSelected(GroupDto group);
    }

    private final OnGroupClickListener listener;
    private List<GroupDto> groups;

    public GroupAdapter(List<GroupDto> groups, OnGroupClickListener listener) {
        this.groups = groups;
        this.listener = listener;
    }

    public void updateData(List<GroupDto> newGroups) {
        this.groups = newGroups;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GroupDto group = groups.get(position);
        holder.name.setText(group.getName());
        holder.itemView.setOnClickListener(v -> listener.onGroupSelected(group));
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView name;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.groupName);
        }
    }
}
