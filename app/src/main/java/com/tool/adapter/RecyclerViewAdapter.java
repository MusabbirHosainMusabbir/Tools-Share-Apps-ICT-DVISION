package com.tool.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.tool.customfonts.MyTextView_SF_Pro_Display_Bold;
import com.tool.toolsshare.R;
import com.tool.toolsshare.model.Items;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Items> itemsList = new ArrayList<>();

    private Context mContext;

    public RecyclerViewAdapter(Context context, List<Items> items) {
        itemsList = items;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

//        Glide.with(mContext)
//                .asBitmap()
//                .load(mImageUrls.get(position))
//                .into(holder.image);
        //holder.image.setBackgroundResource(R.drawable.rectangular_1);
        holder.name.setText(itemsList.get(position).getName());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG, "onClick: clicked on an image: " + mNames.get(position));
                //Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout image;
        MyTextView_SF_Pro_Display_Bold name;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.name);
        }
    }
}