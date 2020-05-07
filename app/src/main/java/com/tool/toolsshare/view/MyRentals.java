package com.tool.toolsshare.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tool.Utils.Helper;
import com.tool.Utils.PreferenceMangement;
import com.tool.Utils.Utils;
import com.tool.adapter.BrowingReqListAdapter;
import com.tool.adapter.PostWiseRequestCountAdapter;
import com.tool.adapter.RentalAdapter;
import com.tool.toolsshare.R;
import com.tool.toolsshare.model.ProducDetailsResponse;
import com.tool.toolsshare.viewmodel.LoginViewModel;
import com.tool.toolsshare.viewmodel.PostListViewModel;
import com.tool.toolsshare.viewmodel.ProductDetailsViewModel;
import com.tool.toolsshare.viewmodel.ReqCountViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyRentals extends AppCompatActivity {
    RecyclerView recyclerView;
    PostWiseRequestCountAdapter adapter;
    PostListViewModel postListViewModel;
    ProductDetailsViewModel productDetailsViewModel;

    BrowingReqListAdapter browingReqListAdapter;

    ImageView post;

    ReqCountViewModel reqCountViewModel;
    ArrayList<HashMap<String,String>>dataList;

    BottomNavigationView bottomNavigationView;

    Boolean isLending = true;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rentals);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        Utils.fullScreenView(this,false);

        if (!Utils.hasNavBar(this)){
            Log.e("hasNav--->", "onCreate: YES---> " );
            Utils.adjustBottomNav(this,R.id.soft_key_layout,R.id.main_layout);
            //navLayout.setVisibility(View.GONE);

        }

        back = findViewById(R.id.back_img);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyRentals.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        post = findViewById(R.id.post_img);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyRentals.this, PublishPost.class));
                finish();
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
// on favorites clicked
                    Intent intent = new Intent(MyRentals.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }


                else if (item.getItemId() == R.id.profile){
                    Intent intent = new Intent(MyRentals.this,Profile.class);
                    startActivity(intent);
                    finish();
                    return true;

                }
                return false;
            }
        });

        inflateLendings();






    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            startActivity(new Intent(MyRentals.this,HomeActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void inflateLendings(){
        dataList = new ArrayList<>();

        Helper.showLoader(MyRentals.this,"");
        JSONObject reqJsonObj = new JSONObject();

        try {
            reqJsonObj.put("user_id", PreferenceMangement.getPreference(MyRentals.this,"user_id"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        postListViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PostListViewModel.class);
        postListViewModel.initialize(getApplication(),reqJsonObj);
        postListViewModel.getPosts().observe(MyRentals.this, new Observer<ArrayList<HashMap<String, String>>>() {
            @Override
            public void onChanged(ArrayList<HashMap<String, String>> hashMaps) {

                if (hashMaps.size() == 0 || hashMaps == null){
                    Helper.cancelLoader();

                }

                recyclerView = findViewById(R.id.recycler_view);
                recyclerView.setAdapter(null);

                for (int i = 0; i <hashMaps.size();i++){



                    JSONObject reqJson = new JSONObject();
                    try {
                        reqJson.put("post_id",hashMaps.get(i).get("id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    reqCountViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ReqCountViewModel.class);
                    reqCountViewModel.initialize(getApplication(),reqJson);
                    int finalI = i;
                    reqCountViewModel.getCounts().observe(MyRentals.this, new Observer<HashMap<String, String>>() {
                        @Override
                        public void onChanged(HashMap<String, String> stringStringHashMap) {


                            HashMap<String,String> dataMap = new HashMap<>();

                            dataMap.put("count",stringStringHashMap.get("count"));


                            dataMap.put("name",hashMaps.get(finalI).get("name"));
                            dataMap.put("post_id",hashMaps.get(finalI).get("id"));
                            dataMap.put("price",hashMaps.get(finalI).get("price"));
                            dataMap.put("image",hashMaps.get(finalI).get("image"));

                            Log.e("Name-->", "onChanged: "+dataMap.get("name") );

                            dataList.add(dataMap);

                            if (finalI== hashMaps.size()-1){

                                Helper.cancelLoader();
                                recyclerView = findViewById(R.id.recycler_view);
                                recyclerView.setLayoutManager(new LinearLayoutManager(MyRentals.this));
                                adapter = new PostWiseRequestCountAdapter(MyRentals.this,dataList);
                                adapter.setOnItemClickListener(new PostWiseRequestCountAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {

                                        Intent intent = new Intent(MyRentals.this,DataViewActivity.class);
                                        intent.putExtra("post_id",dataList.get(position).get("post_id"));
                                        intent.putExtra("name",dataList.get(position).get("name"));
                                        intent.putExtra("image",dataList.get(position).get("image"));
                                        intent.putExtra("price",dataList.get(position).get("price"));
                                        intent.putExtra("count",dataList.get(position).get("count"));

                                        startActivity(intent);
                                        Toast.makeText(MyRentals.this,""+dataList.get(position).get("post_id"),Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onEditClick(int position) {

                                    }

                                    @Override
                                    public void onViewClick(int position) {

                                    }
                                });
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    });






                }



            }
        });
    }
    public void inflateBrowings() {
        dataList = new ArrayList<>();

        //Helper.showLoader(MyRentals.this, "");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", PreferenceMangement.getPreference(MyRentals.this,"user_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Helper.showLoader(MyRentals.this, "");

        reqCountViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ReqCountViewModel.class);
        reqCountViewModel.initialize(getApplication(), jsonObject);
        reqCountViewModel.getMyBrowingList().observe(MyRentals.this, new Observer<ArrayList<HashMap<String, String>>>() {
            @Override
            public void onChanged(ArrayList<HashMap<String, String>> hashMaps) {

                if (hashMaps.size() == 0 || hashMaps == null){
                    Helper.cancelLoader();

                }

                recyclerView = findViewById(R.id.recycler_view);
                recyclerView.setAdapter(null);

                for (int i = 0; i<hashMaps.size();i++){
                    JSONObject reqJsonObj = new JSONObject();
                    try {
                        reqJsonObj.put("post_id",hashMaps.get(i).get("post_id"));
                        Log.e("postid",hashMaps.get(i).get("post_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    productDetailsViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ProductDetailsViewModel.class);
                    productDetailsViewModel.initialize(getApplication(),reqJsonObj);
                    int finalI = i;
                    productDetailsViewModel.getProductDetails().observe(MyRentals.this, new Observer<ProducDetailsResponse>() {
                        @Override
                        public void onChanged(ProducDetailsResponse producDetailsResponse) {

                            HashMap<String,String>dataMap = new HashMap<>();
                            dataMap.put("post_id",String.valueOf(producDetailsResponse.getId()));
                            dataMap.put("name",String.valueOf(producDetailsResponse.getName()));

                            if(producDetailsResponse.getImages().size() != 0){
                                dataMap.put("image",String.valueOf(producDetailsResponse.getImages().get(0)));
                            }
                            //Log.e("image",producDetailsResponse.getImages().get(0).toString());


                            dataMap.put("price",String.valueOf(producDetailsResponse.getPrice()));
                            dataMap.put("location",String.valueOf(producDetailsResponse.getLocation()));
                            dataMap.put("status",hashMaps.get(finalI).get("status"));

                            dataList.add(dataMap);

                            if (finalI == hashMaps.size()-1){
                                Helper.cancelLoader();

                                recyclerView = findViewById(R.id.recycler_view);
                                recyclerView.setLayoutManager(new LinearLayoutManager(MyRentals.this));
                                browingReqListAdapter = new BrowingReqListAdapter(MyRentals.this,dataList);
                                recyclerView.setAdapter(browingReqListAdapter);

                                browingReqListAdapter.setOnItemClickListener(new BrowingReqListAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        Intent intent = new Intent(MyRentals.this,ProductDetails.class);
                                        intent.putExtra("product_id",String.valueOf(dataList.get(position).get("post_id")));
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onEditClick(int position) {

                                    }

                                    @Override
                                    public void onViewClick(int position) {

                                    }
                                });


                            }

                        }
                    });
                }



               // Log.e("id--->", "onChanged: "+hashMaps.get(0).get("id") );


            }
        });
    }

    public void lendBrow(View view) {
        switch (view.getId()){
            case R.id.lending:
                //inflate my posts to the recyclerview
                if (isLending == false){

                    inflateLendings();
                    isLending = true;
                    findViewById(view.getId()).setBackgroundResource(R.drawable.button_bg);
                    findViewById(R.id.browing).setBackgroundResource(R.drawable.selected_capsule);
                }

                break;
            case R.id.browing:
                // inflatte my requests to the recyclerview
                if (isLending == true){

                    inflateBrowings();
                    isLending = false;
                    findViewById(view.getId()).setBackgroundResource(R.drawable.button_bg);
                    findViewById(R.id.lending).setBackgroundResource(R.drawable.selected_capsule);

                }


                break;
        }
    }
}
