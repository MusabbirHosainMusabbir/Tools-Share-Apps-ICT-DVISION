package com.tool.toolsshare.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tool.preference.PrefManager;
import com.tool.toolsshare.R;

public class Intro extends AppCompatActivity {

    private PrefManager prefManager;
    private ViewPager viewPager;
    private ImageView btnNext;
    private LinearLayout dotsLayout,linearLayout;
    private int[] layouts;
    private MyViewPagerAdapter myViewPagerAdapter;
    private int dotsCount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        prefManager = new PrefManager(this);
        /*if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }*/

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_intro);

        viewPager = findViewById(R.id.view_pager);
        linearLayout = findViewById(R.id.linear_layout);
        linearLayout.setBackgroundResource(R.drawable.welcome_bg_1);
        dotsLayout = findViewById(R.id.layoutDots);
        btnNext = findViewById(R.id.btn_next);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide_1,
                R.layout.welcome_slide_2,
                R.layout.welcome_slide_3};

        // adding bottom dots
        // addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setUiPageViewController();


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }

    private void setUiPageViewController() {
        dotsCount = myViewPagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.non_select));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(8, 0, 8, 0);
            dotsLayout.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.select));
    }

    private void changeStatusBarColor() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(Intro.this, UserLogin.class));
        finish();
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            // addBottomDots(position);
            //setUiPageViewController();

            Log.e("pos", String.valueOf(position));
            if(position == 0){
                linearLayout.setBackgroundResource(R.drawable.welcome_bg_1);
            } else if(position == 1){
                linearLayout.setBackgroundResource(R.drawable.welcome_bg_2);
            }else if(position == 2){
                linearLayout.setBackgroundResource(R.drawable.welcome_bg_3);
            }
            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {

                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.non_select));
                }

                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.select));
                // last page. make button text to GOT IT

            } else {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.non_select));
                }

                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.select));
                // still pages are left
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}
