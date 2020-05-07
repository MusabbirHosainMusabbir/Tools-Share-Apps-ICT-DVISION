package com.tool.toolsshare.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tool.Utils.Helper;
import com.tool.Utils.PreferenceMangement;
import com.tool.Utils.Utils;
import com.tool.adapter.CustomViewAdapter;
import com.tool.adapter.RecyclerViewAdapter;
import com.tool.toolsshare.R;
import com.tool.toolsshare.model.CustomRecyclerViewItem;
import com.tool.toolsshare.model.Items;
import com.tool.toolsshare.viewmodel.CategoryListViewModel;
import com.tool.toolsshare.viewmodel.LoginViewModel;
import com.tool.toolsshare.viewmodel.PostListViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String LOG_TAG = CustomViewAdapter.CustomRecyclerViewHolder.class.getSimpleName();
    private RecyclerView recyclerView = null;
    private List<CustomRecyclerViewItem> itemList = null;
    private List<Items> trendingList = new ArrayList<>();
    private CustomViewAdapter customRecyclerViewDataAdapter = null;
    private RecyclerViewAdapter recycleviewAdapter;
    private Handler uiHandler = null;
    private int MESSAGE_UPDATE_RECYCLER_VIEW = 1;
    private String MESSAGE_KEY_NEW_ITEM_INDEX = "MESSAGE_KEY_NEW_ITEM_INDEX";
    private RecyclerView recyclerViewTrendinnow,recyclerViewPopular,recyclerViewRecentltyViewed,recyclerViewFavouriteTools;
    FloatingActionButton floatingActionButton;
    FlexboxLayout flexboxLayout;

    BottomNavigationView bottomNavigationView;
    int height,width,heightForPostLayout, widthForPostLayout;

    PostListViewModel postListViewModel;

    View.OnClickListener productClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Toast.makeText(HomeActivity.this,"Product",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this,ProductDetails.class);
            intent.putExtra("product_id",String.valueOf(view.getTag()));
            startActivity(intent);
            finish();
        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        //categoryLinearLayout = findViewById(R.id.category_layout);

        initControls();
        Utils.fullScreenView(this,false);

        if (!Utils.hasNavBar(this)){
            Log.e("hasNav--->", "onCreate: YES---> " );
            Utils.adjustBottomNav(this,R.id.soft_key_layout,R.id.main_layout);
            //navLayout.setVisibility(View.GONE);

        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        heightForPostLayout = (int) Math.ceil(height * 0.35);
        widthForPostLayout = (int) Math.ceil(width * 0.45);







        Log.e("user---->", "onCreate: "+ PreferenceMangement.getPreference(this,"user_id"));

        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        customRecyclerViewDataAdapter = new CustomViewAdapter(itemList);
        recyclerView.setAdapter(customRecyclerViewDataAdapter);
        recyclerView.scrollToPosition(1);

        flexboxLayout = findViewById(R.id.flex_layout);

        GridLayoutManager layoutManagerTrending = new GridLayoutManager(this,1);
        layoutManagerTrending.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewTrendinnow.setLayoutManager(layoutManagerTrending);
        recycleviewAdapter = new RecyclerViewAdapter(HomeActivity.this,trendingList);
        recyclerViewTrendinnow.setAdapter(recycleviewAdapter);

        /*GridLayoutManager layoutManagerPopularFoods = new GridLayoutManager(this,1);
        layoutManagerPopularFoods.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewPopular.setLayoutManager(layoutManagerPopularFoods);
        recycleviewAdapter = new RecyclerViewAdapter(HomeActivity.this,trendingList);
        recyclerViewPopular.setAdapter(recycleviewAdapter);

        GridLayoutManager layoutManagerRecentltyViewed = new GridLayoutManager(this,1);
        layoutManagerRecentltyViewed.setOrientation(LinearLayoutManager.HORIZONTAL);
        //recyclerViewRecentltyViewed.setLayoutManager(layoutManagerRecentltyViewed);
        recycleviewAdapter = new RecyclerViewAdapter(HomeActivity.this,trendingList);
        //recyclerViewRecentltyViewed.setAdapter(recycleviewAdapter);

        GridLayoutManager layoutManagerFavouriteTools = new GridLayoutManager(this,1);
        layoutManagerFavouriteTools.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewFavouriteTools.setLayoutManager(layoutManagerFavouriteTools);
        recycleviewAdapter = new RecyclerViewAdapter(HomeActivity.this,trendingList);
        recyclerViewFavouriteTools.setAdapter(recycleviewAdapter);
*/
        floatingActionButton = findViewById(R.id.add_post);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,PublishPost.class));
                finish();
            }
        });

        JSONObject jsonObject = new JSONObject();


        postListViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PostListViewModel.class);
        postListViewModel.initialize(getApplication(),jsonObject);
        postListViewModel.getPosts().observe(HomeActivity.this, new Observer<ArrayList<HashMap<String, String>>>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onChanged(ArrayList<HashMap<String, String>> hashMaps) {
                for (int x= 0; x<hashMaps.size();x++){
                    LinearLayout postItemLayout = new LinearLayout(HomeActivity.this);
                    postItemLayout.setOrientation(LinearLayout.VERTICAL);
                    postItemLayout.setGravity(Gravity.CENTER);
                    postItemLayout.setWeightSum(2f);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        postItemLayout.setElevation(5);
                    }

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthForPostLayout,heightForPostLayout);
                    params.setMargins(8,8,8,8);
                    postItemLayout.setLayoutParams(params);

                    ImageView productImage = new ImageView(HomeActivity.this);
                    productImage.setBackgroundResource(R.drawable.post_card);
                    productImage.setOnClickListener(productClickListener);
                    productImage.setImageResource(R.drawable.login_cover);
                    productImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        productImage.setClipToOutline(true);
                    }
                    productImage.setTag(hashMaps.get(x).get("id"));

                    LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1.2f);
                    productImage.setLayoutParams(imageParams);

                    LinearLayout holderLayout = new LinearLayout(HomeActivity.this);
                    holderLayout.setOrientation(LinearLayout.VERTICAL);
                    holderLayout.setGravity(Gravity.CENTER|Gravity.LEFT);
                    holderLayout.setWeightSum(5f);

                    LinearLayout.LayoutParams holderParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,0.8f);
                    holderLayout.setLayoutParams(holderParams);
                    // title
                    TextView titleTv = new TextView(HomeActivity.this);
                    titleTv.setGravity(Gravity.LEFT|Gravity.CENTER);
                    titleTv.setText(hashMaps.get(x).get("name"));
                    titleTv.setTypeface(null, Typeface.BOLD);

                    TextView locationTv = new TextView(HomeActivity.this);
                    locationTv.setGravity(Gravity.LEFT|Gravity.CENTER);
                    locationTv.setText(hashMaps.get(x).get("location"));
                    //locationTv.setTypeface(null, Typeface.BOLD);

                    TextView priceTv = new TextView(HomeActivity.this);
                    priceTv.setGravity(Gravity.LEFT|Gravity.CENTER);
                    priceTv.setText("৳"+hashMaps.get(x).get("price")+" Per Day");
                    priceTv.setTextColor(Color.parseColor("#FEFEFE"));
                    priceTv.setBackgroundResource(R.drawable.bg_price);
                    //locationTv.setTypeface(null, Typeface.BOLD);

                    LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0,1.2f);
                    titleTv.setLayoutParams(tvParams);
                    locationTv.setLayoutParams(tvParams);
                    priceTv.setLayoutParams(tvParams);

                    holderLayout.addView(titleTv);
                    holderLayout.addView(locationTv);
                    holderLayout.addView(priceTv);

                    postItemLayout.addView(productImage);
                    postItemLayout.addView(holderLayout);

                    Glide.with(HomeActivity.this)
                            .load(hashMaps.get(x).get("image"))
                            .into(productImage);



                    flexboxLayout.addView(postItemLayout);

                }
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_rental) {
// on favorites clicked
                    Intent intent = new Intent(HomeActivity.this,MyRentals.class);
                    startActivity(intent);
                    finish();
                    return true;
                }

                else if (item.getItemId() == R.id.navigation_profile){
                    Intent intent = new Intent(HomeActivity.this,Profile.class);
                    startActivity(intent);
                    finish();
                    return true;

                }
                return false;
            }
        });





    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initControls() {
        if(recyclerView == null)
        {
            recyclerView = findViewById(R.id.custom_refresh_recycler_view);
            recyclerViewTrendinnow = findViewById(R.id.trendingnowRecylceview);
            recyclerViewPopular = findViewById(R.id.popularfoodsRecylceview);
            //recyclerViewRecentltyViewed = findViewById(R.id.recentltyviewedRecylceview);
            recyclerViewFavouriteTools = findViewById(R.id.favouriteToolsRecylceview);


//            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//
//                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//
//                    int firstCompleteVisibleItemPosition = -1;
//                    int lastCompleteVisibleItemPosition = -1;
//                    int visibleItemCount = layoutManager.getChildCount();
//                    int totalItemCount = layoutManager.getItemCount();
//
//                    if(layoutManager instanceof GridLayoutManager)
//                    {
//                        GridLayoutManager gridLayoutManager = (GridLayoutManager)layoutManager;
//                        firstCompleteVisibleItemPosition = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
//                        lastCompleteVisibleItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
//                    }else if(layoutManager instanceof  LinearLayoutManager)
//                    {
//                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager)layoutManager;
//                        firstCompleteVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
//                        lastCompleteVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
//                    }
//
//                    String message = "";
//
//                    // Means scroll at beginning ( top to bottom or left to right).
//                    if(firstCompleteVisibleItemPosition == 0)
//                    {
//                        // dy < 0 means scroll to bottom, dx < 0 means scroll to right at beginning.
//                        if(dy < 0 || dx < 0)
//                        {
//                            // Means scroll to bottom.
//                            if(dy < 0)
//                            {
//                                loadData(true);
//                            }
//
//                            // Means scroll to right.
//                            if(dx < 0 )
//                            {
//                                loadData(true);
//                            }
//                        }
//                    }
//                    // Means scroll at ending ( bottom to top or right to left )
//                    else if(lastCompleteVisibleItemPosition == (totalItemCount - 1))
//                    {
//                        // dy > 0 means scroll to up, dx > 0 means scroll to left at ending.
//                        if(dy > 0 || dx > 0)
//                        {
//                            // Scroll to top
//                            if(dy > 0)
//                            {
//                                loadData(false);
//                            }
//
//                            // Scroll to left
//                            if(dx > 0 )
//                            {
//                                loadData(false);
//                            }
//                        }
//                    }
//
//                    if(message.length() > 0) {
//                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                      }
//                }
//            });
        }

        trendingList.add(new Items("Gary Rance","Baridhara,Dhaka,","4.5","$2 per day",R.drawable.rectangle_1,false));
        trendingList.add(new Items("Gary Rance","Baridhara,Dhaka,","4.5","$2 per day",R.drawable.rectangle_1,false));
        trendingList.add(new Items("Gary Rance","Baridhara,Dhaka,","4.5","$2 per day",R.drawable.rectangle_1,false));
        trendingList.add(new Items("Gary Rance","Baridhara,Dhaka,","4.5","$2 per day",R.drawable.rectangle_1,false));

        if(itemList == null)
        {
            itemList = new ArrayList<CustomRecyclerViewItem>();
            for(int i=0;i<6;i++)
            {
                CustomRecyclerViewItem item = new CustomRecyclerViewItem();
                item.setText("Card " + (i + 1));
                itemList.add(item);
            }
        }
    }
}