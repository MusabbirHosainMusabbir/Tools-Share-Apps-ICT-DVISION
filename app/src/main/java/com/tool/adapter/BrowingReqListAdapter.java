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

public class BrowingReqListAdapter extends RecyclerView.Adapter<BrowingReqListAdapter.ViewHolder> {

    ArrayList<HashMap<String,String>> dataList;
    Context context;
    OnItemClickListener mlistener;

    public BrowingReqListAdapter(Context context,ArrayList<HashMap<String, String>> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.browing_req_item, parent, false);

        return new ViewHolder(itemView,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .load(dataList.get(position).get("image"))
                .into(holder.productImage);

        Log.e("in viehholder-->", "onBindViewHolder: "+dataList.get(position).get("id")+" "+dataList.get(position).get("status") );

        holder.titleTv.setText(dataList.get(position).get("name"));
        holder.addressTv.setText(dataList.get(position).get("location"));
        holder.priceTv.setText(dataList.get(position).get("price"));

        if (dataList.get(position).get("status").equals("1")){
            Log.e("rej", "onBindViewHolder: PENDING" );
            holder.statusBtn.setText("Accepted");
            holder.statusBtn.setBackgroundResource(R.drawable.accept_capsule);
        }
        else if (dataList.get(position).get("status").equals("0")){
            holder.statusBtn.setText("Pending");

            Log.e("rej", "onBindViewHolder: REJECTED" );
        }
        else {
            holder.statusBtn.setText("Rejected");
            holder.statusBtn.setBackgroundResource(R.drawable.regect_capsule);

        }


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
        TextView titleTv, priceTv, addressTv;
        TextView statusBtn;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.name_tv);
            priceTv = itemView.findViewById(R.id.price_tv);
            addressTv = itemView.findViewById(R.id.address_tv);
            statusBtn =itemView.findViewById(R.id.status_tv);
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
