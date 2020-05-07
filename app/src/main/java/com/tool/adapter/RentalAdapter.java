package com.tool.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tool.toolsshare.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RentalAdapter extends RecyclerView.Adapter<RentalAdapter.ViewHolder>{

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rental_item, parent, false);

        return new RentalAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 12;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
