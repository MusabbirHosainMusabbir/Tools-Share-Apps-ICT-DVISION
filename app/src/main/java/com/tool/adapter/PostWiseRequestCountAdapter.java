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

public class PostWiseRequestCountAdapter extends RecyclerView.Adapter<PostWiseRequestCountAdapter.ViewHolder> {

    ArrayList<HashMap<String,String>> dataList;
    Context context;
    private OnItemClickListener mlistener;

    public PostWiseRequestCountAdapter(Context context,ArrayList<HashMap<String, String>> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.req_list_item, parent, false);

        return new PostWiseRequestCountAdapter.ViewHolder(itemView,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("name test-->", "onBindViewHolder: "+dataList.get(position).get("name") );
        holder.titleTv.setText(dataList.get(position).get("name"));
        holder.priceTv.setText(dataList.get(position).get("price"));
        holder.countBtn.setText(dataList.get(position).get("count"));
        Glide.with(context)
                .load(dataList.get(position).get("image"))
                .into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
        Log.e("m listener set", "setOnItemClickListener: "+"listener setted" );
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onEditClick(int position);
        void onViewClick(int position);
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTv, priceTv;
        Button countBtn;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.name_tv);
            priceTv = itemView.findViewById(R.id.price_tv);
            countBtn =itemView.findViewById(R.id.req_count_tv);
            productImage = itemView.findViewById(R.id.image_product);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
