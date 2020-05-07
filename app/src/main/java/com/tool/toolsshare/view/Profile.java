package com.tool.toolsshare.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tool.Utils.Helper;
import com.tool.Utils.PreferenceMangement;
import com.tool.Utils.Utils;
import com.tool.toolsshare.R;
import com.tool.toolsshare.viewmodel.RequesterViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Profile extends AppCompatActivity {
    ImageView search, profileImage, post, back;
    TextView titleTv, nameTv, emailTv, addressTv, mobileTv, editProfile;
    RequesterViewModel requesterViewModel;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        search = findViewById(R.id.search_img);

        titleTv = findViewById(R.id.title_tv);
        nameTv = findViewById(R.id.name_tv);
        addressTv = findViewById(R.id.address_tv);
        emailTv = findViewById(R.id.mail_tv);
        profileImage = findViewById(R.id.image);
        mobileTv = findViewById(R.id.mobile_tv);
        editProfile = findViewById(R.id.edit_profile);
        post = findViewById(R.id.post_img);

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
                Intent intent = new Intent(Profile.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", PreferenceMangement.getPreference(Profile.this,"user_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Helper.showLoader(Profile.this,"");

        requesterViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RequesterViewModel.class);
        requesterViewModel.initialize(getApplication(),jsonObject);

        requesterViewModel.getUser().observe(Profile.this, new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(HashMap<String, String> stringStringHashMap) {
                Helper.cancelLoader();
                titleTv.setText(stringStringHashMap.get("name"));
                nameTv.setText(stringStringHashMap.get("name"));
                emailTv.setText(stringStringHashMap.get("email"));
                addressTv.setText(stringStringHashMap.get("location"));
                mobileTv.setText(stringStringHashMap.get("mobile"));

                Log.e("image",stringStringHashMap.get("image"));

                Glide.with(Profile.this)
                        .load(stringStringHashMap.get("image"))
                        .into(profileImage);

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this,Settings.class));
                finish();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, PublishPost.class));
                finish();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this,Settings.class));
                finish();
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
// on favorites clicked
                    Intent intent = new Intent(Profile.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }

                else if (item.getItemId() == R.id.rental){
                    Intent intent = new Intent(Profile.this,MyRentals.class);
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
            Intent intent = new Intent(Profile.this,HomeActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
