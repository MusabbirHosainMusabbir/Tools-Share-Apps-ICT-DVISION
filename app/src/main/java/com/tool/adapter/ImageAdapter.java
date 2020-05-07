package com.tool.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.tool.toolsshare.R;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.e("-----------", "getView: "+position );
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(60, 50));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.cameraicon, R.drawable.cameraicon,
            R.drawable.cameraicon, R.drawable.cameraicon,
            R.drawable.cameraicon, R.drawable.cameraicon,
            R.drawable.cameraicon, R.drawable.cameraicon,
            R.drawable.cameraicon, R.drawable.cameraicon,
            R.drawable.cameraicon, R.drawable.cameraicon,
            R.drawable.cameraicon, R.drawable.cameraicon,
            R.drawable.cameraicon, R.drawable.cameraicon,
            R.drawable.cameraicon, R.drawable.cameraicon,
            R.drawable.cameraicon, R.drawable.cameraicon,
            R.drawable.cameraicon, R.drawable.cameraicon
    };
}