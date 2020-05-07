package com.tool.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tool.toolsshare.R;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RequestersAdapter extends RecyclerView.Adapter<RequestersAdapter.ViewHolder> {

    ArrayList<HashMap<String,String>> dataList;
    Context context;
    private OnItemClickListener mlistener;

    public RequestersAdapter(Context context,ArrayList<HashMap<String, String>> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_user_item, parent, false);

        return new RequestersAdapter.ViewHolder(itemView,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nameTv.setText(dataList.get(position).get("name"));
        holder.address.setText(dataList.get(position).get("location"));
        Glide.with(context)
                .load(dataList.get(position).get("image"))
                .into(holder.userImage);

        if (dataList.get(position).get("status").equals("1")){
            holder.accept.setEnabled(false);
            holder.accept.setBackgroundResource(R.drawable.blurred_accept);
        }
        else if (dataList.get(position).get("status").equals("0")){

            holder.reject.setEnabled(false);
            holder.reject.setBackgroundResource(R.drawable.blurred_reject_capsule);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onAcceptClick(int position, View view);
        void onRejectClick(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
        Log.e("m listener set", "setOnItemClickListener: "+"listener setted" );
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv, address;
        Button accept, reject;
        ImageView userImage;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.user_name);
            address = itemView.findViewById(R.id.user_location);
            accept =itemView.findViewById(R.id.accept);
            reject =itemView.findViewById(R.id.reject);
            userImage = itemView.findViewById(R.id.user);

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAcceptClick(position, v);
                        }
                    }
                }
            });
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onRejectClick(position, v);
                        }
                    }
                }
            });
        }
    }
}
