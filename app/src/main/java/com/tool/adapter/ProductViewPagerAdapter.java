package com.tool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tool.toolsshare.R;
import com.tool.toolsshare.model.ProductScreenItem;
import com.tool.toolsshare.model.ScreenItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ProductViewPagerAdapter extends PagerAdapter {

    Context mContext ;
    List<ProductScreenItem> mListScreen;

    public ProductViewPagerAdapter(Context mContext, List<ProductScreenItem> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.layout_screen,null);

        ImageView imgSlide = layoutScreen.findViewById(R.id.intro_img);
        //TextView title = layoutScreen.findViewById(R.id.intro_title);
        //TextView description = layoutScreen.findViewById(R.id.intro_description);

        //title.setText(mListScreen.get(position).getTitle());
        //description.setText(mListScreen.get(position).getDescription());

        Glide.with(mContext)
                .load(mListScreen.get(position).getScreenImg())
                .into(imgSlide);

        container.addView(layoutScreen);

        return layoutScreen;





    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);

    }
}
