package com.tool.toolsshare.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tool.Utils.Utils;
import com.tool.adapter.ImageAdapter;
import com.tool.toolsshare.R;

public class SelectCategory extends AppCompatActivity {

    LinearLayout scrollLinearLayout;
    ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
        scrollLinearLayout = findViewById(R.id.main_scroll_layout);

        search = findViewById(R.id.search_img);

        Utils.fullScreenView(this,false);

        if (!Utils.hasNavBar(this)){
            Log.e("hasNav--->", "onCreate: YES---> " );
            Utils.adjustBottomNav(this,R.id.soft_key_layout,R.id.main_layout);
            //navLayout.setVisibility(View.GONE);

        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int heightForCategory = (int) Math.ceil(width * 0.28);

        int remainingLevelNum = 20;


        int rowNum = (int) Math.ceil((double) remainingLevelNum /3);

        Log.e("----->", "onCreate: "+rowNum );
        Log.e("----->", "onCreate: remaining "+remainingLevelNum );

        for (int i = 0; i< rowNum; i++){
            LinearLayout rowLinearLayout = new LinearLayout(this);
            rowLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLinearLayout.setGravity(Gravity.CENTER);
            rowLinearLayout.setWeightSum(3f);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,heightForCategory);
            rowLinearLayout.setLayoutParams(params);
            //attach to scroll layout
            scrollLinearLayout.addView(rowLinearLayout);
            // here j= 3 because only max 3 columns are in each row--------->
            for (int j= 0 ; j<3; j++){
                // for responsiv
                //parent level layout


                LinearLayout levelLayout = new LinearLayout(this);
                levelLayout.setOrientation(LinearLayout.VERTICAL);
                levelLayout.setGravity(Gravity.CENTER);
                levelLayout.setBackgroundResource(R.drawable.rounded_card_bg);
                levelLayout.setWeightSum(3f);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
                //LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(120, 165,1);
                params1.setMargins(25,5,25,10);
                levelLayout.setLayoutParams(params1);

                rowLinearLayout.addView(levelLayout);
                TextView levelTxt = new TextView(this);
                levelTxt.setGravity(Gravity.CENTER);
                levelTxt.setText(String.valueOf((i*3)+j+1));
                //levelTxt.setTextColor(Color.WHITE);
                levelTxt.setTextSize(20);
                levelTxt.setId(i*3+j+1);

                // here previous levels were situated



                //levelTxt.setOnClickListener(levelSelectListener);

                LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,2f);
                levelTxt.setLayoutParams(txtParams);

                levelLayout.addView(levelTxt);

                LinearLayout starLinear = new LinearLayout(this);
                starLinear.setWeightSum(3f);
                starLinear.setOrientation(LinearLayout.HORIZONTAL);
                starLinear.setGravity(Gravity.CENTER);

                LinearLayout.LayoutParams starParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,1f);
                starParams.setMargins(0,0,5,0);
                starLinear.setLayoutParams(starParams);

                levelLayout.addView(starLinear);

                // rateing bar using image views

                RatingBar ratingBar = new RatingBar(this,null, android.R.attr.ratingBarStyleSmall);
                ratingBar.setNumStars(3);
                ratingBar.setStepSize(0.01f);
                ratingBar.setId(i*3+j+1+4000);

                LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ratingBar.setLayoutParams(imgParams);

                starLinear.addView(ratingBar);

                // new situated--->


                remainingLevelNum --;
                Log.e("----->", "onCreate: remaining "+remainingLevelNum );

                if (remainingLevelNum == 0){
                    Log.e("----->", "onCreate: BROKEN---> ");

                    break;
                }

            }
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectCategory.this,ProductDetails.class));
                finish();
            }
        });
    }
}
