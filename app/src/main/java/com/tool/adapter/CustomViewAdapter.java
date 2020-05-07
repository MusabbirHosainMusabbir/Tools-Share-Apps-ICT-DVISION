package com.tool.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tool.Utils.CommonDrawable;
import com.tool.toolsshare.R;
import com.tool.toolsshare.model.CustomRecyclerViewItem;

import java.util.List;

public class CustomViewAdapter extends RecyclerView.Adapter<CustomViewAdapter.CustomRecyclerViewHolder> {

    private List<CustomRecyclerViewItem> viewItemList;
    CommonDrawable commonDrawable;

    public CustomViewAdapter(List<CustomRecyclerViewItem> viewItemList) {
        this.viewItemList = viewItemList;
    }

    @Override
    public CustomRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View itemView = layoutInflater.inflate(R.layout.activity_custom_refresh_recycler_view_item, parent, false);

        // Create and return our customRecycler View Holder object.
        CustomRecyclerViewHolder ret = new CustomRecyclerViewHolder(itemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(CustomRecyclerViewHolder holder, int position) {
        if(viewItemList!=null) {
            // Get car item dto in list.
            String buttoncolor = String.valueOf(Color.parseColor("#1AFB6D3A"));
            CustomRecyclerViewItem viewItem = viewItemList.get(position);
            commonDrawable = new CommonDrawable(Integer.parseInt(buttoncolor));

            if(viewItem != null) {
                // Set car item title.
                holder.getTextView().setText(viewItem.getText());
                holder.linearLayout.setBackground(commonDrawable);

            }
        }
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if(viewItemList!=null)
        {
            ret = viewItemList.size();
        }
        return ret;
    }

    public class CustomRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView textView = null;
        private LinearLayout linearLayout;

        public CustomRecyclerViewHolder(View itemView) {
            super(itemView);

            if(itemView != null)
            {
                textView = (TextView)itemView.findViewById(R.id.custom_refresh_recycler_view_text_view);
                linearLayout = itemView.findViewById(R.id.linear_layout);
            }
        }

        public TextView getTextView() {
            return textView;
        }
    }
}