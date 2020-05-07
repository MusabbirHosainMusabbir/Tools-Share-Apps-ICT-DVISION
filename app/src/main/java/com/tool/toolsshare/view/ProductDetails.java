package com.tool.toolsshare.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.tool.Utils.Helper;
import com.tool.Utils.Utils;
import com.tool.adapter.IntroViewPagerAdapter;
import com.tool.adapter.ProductViewPagerAdapter;
import com.tool.toolsshare.R;
import com.tool.toolsshare.model.ProducDetailsResponse;
import com.tool.toolsshare.model.ProductScreenItem;
import com.tool.toolsshare.model.ScreenItem;
import com.tool.toolsshare.viewmodel.ProductDetailsViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductDetails extends AppCompatActivity {
    private ViewPager screenPager;
    ProductViewPagerAdapter productViewPagerAdapter ;
    TabLayout tabIndicator;
    ImageView search;
    String productId;
    TextView titleTv, decriptionTv, priceTv, locationTv, countTv;
    Button plusBtn,minusBtn, rentBtn;

    int count = 1, price, netPrice;

    ProductDetailsViewModel productDetailsViewModel;
    ImageView backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        search = findViewById(R.id.search_img);
        titleTv = findViewById(R.id.product_title);
        decriptionTv = findViewById(R.id.description_et);
        locationTv = findViewById(R.id.location_et);
        priceTv = findViewById(R.id.price_tv);
        countTv = findViewById(R.id.count_tv);
        plusBtn = findViewById(R.id.plus_btn);
        minusBtn = findViewById(R.id.minus_btn);
        rentBtn = findViewById(R.id.rent_btn);
        backImg = findViewById(R.id.back_img);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(ProductDetails.this,HomeActivity.class);
                 startActivity(intent);
                 finish();
            }
        });

        countTv.setText(String.valueOf(count)+" days");


        Utils.fullScreenView(this,false);

        if (!Utils.hasNavBar(this)){
            Log.e("hasNav--->", "onCreate: YES---> " );
            Utils.adjustBottomNav(this,R.id.soft_key_layout,R.id.main_layout);
            //navLayout.setVisibility(View.GONE);

        }

        Intent intent = getIntent();
        productId = intent.getStringExtra("product_id");

        if (productId!= null){
            Helper.showLoader(ProductDetails.this,"");
            JSONObject reqJsonObj = new JSONObject();
            try {
                reqJsonObj.put("post_id",productId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            productDetailsViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ProductDetailsViewModel.class);
            productDetailsViewModel.initialize(getApplication(),reqJsonObj);

            productDetailsViewModel.getProductDetails().observe(ProductDetails.this, new Observer<ProducDetailsResponse>() {
                @Override
                public void onChanged(ProducDetailsResponse producDetailsModel) {
                    Helper.cancelLoader();

                    titleTv.setText(producDetailsModel.getName());
                    price = producDetailsModel.getPrice();
                    netPrice = price;
                    priceTv.setText("Per day: BDT-"+price);
                    decriptionTv.setText(producDetailsModel.getDescription());
                    locationTv.setText(producDetailsModel.getLocation());

                    rentBtn.setText("Rent: BDT "+count*price);

                    final List<ProductScreenItem> mList = new ArrayList<>();

                    for (int i = 0; i < producDetailsModel.getImages().size();i++){

                        mList.add(new ProductScreenItem("", producDetailsModel.getImages().get(i)));

                    }

                    // setup viewpager
                    screenPager =findViewById(R.id.screen_viewpager);
                    productViewPagerAdapter = new ProductViewPagerAdapter(ProductDetails.this,mList);
                    screenPager.setAdapter(productViewPagerAdapter);

                    // setup tablayout with viewpager

                    tabIndicator.setupWithViewPager(screenPager);

                }
            });
        }


        tabIndicator = findViewById(R.id.tab_indicator);



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetails.this,Search.class));
            }
        });
        rentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetails.this,Payment.class);
                intent.putExtra("product_id",productId);
                intent.putExtra("rent",String.valueOf(netPrice));
                startActivity(intent);
                finish();
            }
        });

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            startActivity(new Intent(ProductDetails.this,HomeActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void plusMinusListener(View view) {
        switch (view.getId()){
            case R.id.plus_btn:
                count++;
                break;
            case R.id.minus_btn:
                if (count>1){
                    count--;
                }

                break;

        }
        countTv.setText(String.valueOf(count)+" days");
        netPrice = count*price;
        rentBtn.setText("Rent: BDT "+netPrice);
    }
}
