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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tool.Utils.Helper;
import com.tool.Utils.PreferenceMangement;
import com.tool.Utils.Utils;
import com.tool.adapter.RequestersAdapter;
import com.tool.toolsshare.R;
import com.tool.toolsshare.viewmodel.AcceptOrRejectViewModel;
import com.tool.toolsshare.viewmodel.LoginViewModel;
import com.tool.toolsshare.viewmodel.ReqCountViewModel;
import com.tool.toolsshare.viewmodel.RequesterViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DataViewActivity extends AppCompatActivity {

    String postImage, postName, postPrice, postCount, postId;

    TextView name, price;
    Button count;
    ImageView imageView, back;

    ReqCountViewModel reqCountViewModel;
    RequesterViewModel requesterViewModel;
    AcceptOrRejectViewModel acceptOrRejectViewModel;

    RequestersAdapter requestersAdapter;

    BottomNavigationView bottomNavigationView;

    ArrayList<HashMap<String,String>> recyclerData;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view);

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
                finish();
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        name= findViewById(R.id.name_tv);
        price = findViewById(R.id.price_tv);
        count = findViewById(R.id.count);
        imageView = findViewById(R.id.product_image);
        recyclerView = findViewById(R.id.recycler_view);

        recyclerData = new ArrayList<>();

        Intent intent = getIntent();
        postName = intent.getStringExtra("name");
        postId = intent.getStringExtra("post_id");
        postPrice = intent.getStringExtra("price");
        postImage = intent.getStringExtra("image");
        postCount = intent.getStringExtra("count");

        Log.e("image--->", "onCreate: "+postImage );

        name.setText(postName);
        price.setText(postPrice);
        count.setText(postCount);

        Glide.with(DataViewActivity.this)
                .load(postImage)
                .into(imageView);



        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("post_id",postId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Helper.showLoader(DataViewActivity.this,"");

        reqCountViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ReqCountViewModel.class);
        reqCountViewModel.initialize(getApplication(),jsonObject);
        reqCountViewModel.getUserList().observe(DataViewActivity.this, new Observer<ArrayList<HashMap<String, String>>>() {
            @Override
            public void onChanged(ArrayList<HashMap<String, String>> hashMaps) {

                if (hashMaps.size() == 0 || hashMaps == null){
                    Helper.cancelLoader();

                }
                for (int i = 0; i<hashMaps.size(); i ++){
                    Log.e("user id--->", "onChanged: "+hashMaps.size()+"  "+i+"-->"+hashMaps.get(i).get("request_id") );
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("user_id",hashMaps.get(i).get("user_id"));
                        Log.e("status of user", "onChanged: "+hashMaps.get(i).get("status") );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    requesterViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RequesterViewModel.class);
                    requesterViewModel.initialize(getApplication(),jsonObject);
                    int finalI = i;
                    requesterViewModel.getUser().observe(DataViewActivity.this, new Observer<HashMap<String, String>>() {
                        @Override
                        public void onChanged(HashMap<String, String> stringStringHashMap) {
                            stringStringHashMap.put("status",hashMaps.get(finalI).get("status"));
                            recyclerData.add(stringStringHashMap);

                            if (finalI == hashMaps.size()-1){

                                Helper.cancelLoader();

                                requestersAdapter = new RequestersAdapter(DataViewActivity.this,recyclerData);
                                requestersAdapter.setOnItemClickListener(new RequestersAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {

                                    }

                                    @Override
                                    public void onAcceptClick(int position, View view) {

                                        Toast.makeText(DataViewActivity.this,"Accept",Toast.LENGTH_SHORT).show();

                                        Helper.showLoader(DataViewActivity.this,"");
                                        JSONObject reqJsonObj = new JSONObject();

                                        try {
                                            reqJsonObj.put("id",hashMaps.get(position).get("request_id"));
                                            reqJsonObj.put("user_id", PreferenceMangement.getPreference(DataViewActivity.this,"user_id"));
                                            reqJsonObj.put("post_id", postId);
                                            reqJsonObj.put("status", "1");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        acceptOrRejectViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(AcceptOrRejectViewModel.class);
                                        acceptOrRejectViewModel.initialize(getApplication(),reqJsonObj);
                                        acceptOrRejectViewModel.getStatus().observe(DataViewActivity.this, new Observer<HashMap<String, String>>() {
                                            @Override
                                            public void onChanged(HashMap<String, String> stringStringHashMap) {
                                                Helper.cancelLoader();
                                                if (stringStringHashMap.get("status").equals("1")){

                                                    view.setEnabled(false);
                                                    view.setBackgroundResource(R.drawable.blurred_accept);



                                                    Toast.makeText(DataViewActivity.this,"Accepted",Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });


                                    }

                                    @Override
                                    public void onRejectClick(int position, View view) {

                                        Toast.makeText(DataViewActivity.this,"Reject",Toast.LENGTH_SHORT).show();

                                        Helper.showLoader(DataViewActivity.this,"");
                                        JSONObject reqJsonObj = new JSONObject();

                                        try {
                                            reqJsonObj.put("id",hashMaps.get(position).get("request_id"));
                                            reqJsonObj.put("user_id", PreferenceMangement.getPreference(DataViewActivity.this,"user_id"));
                                            reqJsonObj.put("post_id", postId);
                                            reqJsonObj.put("status", "2");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        acceptOrRejectViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(AcceptOrRejectViewModel.class);
                                        acceptOrRejectViewModel.initialize(getApplication(),reqJsonObj);
                                        acceptOrRejectViewModel.getStatus().observe(DataViewActivity.this, new Observer<HashMap<String, String>>() {
                                            @Override
                                            public void onChanged(HashMap<String, String> stringStringHashMap) {
                                                Helper.cancelLoader();
                                                if (stringStringHashMap.get("status").equals("1")){

                                                    view.setEnabled(false);
                                                    view.setBackgroundResource(R.drawable.blurred_reject_capsule);
                                                    Toast.makeText(DataViewActivity.this,"Rejected",Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });

                                    }
                                });
                                recyclerView.setLayoutManager(new LinearLayoutManager(DataViewActivity.this));
                                recyclerView.setAdapter(requestersAdapter);



                            }
                        }


                    });
                }
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
// on favorites clicked
                    Intent intent = new Intent(DataViewActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }

                else if (item.getItemId() == R.id.profile){
                    Intent intent = new Intent(DataViewActivity.this,Profile.class);
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
}
