package com.tool.toolsshare.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tool.Utils.Utils;
import com.tool.toolsshare.R;

public class InviteFriends extends AppCompatActivity {
    Button shareBtn;
    ImageView copyImg, discountImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
        shareBtn = findViewById(R.id.share_btn);
        copyImg = findViewById(R.id.copy_img);
        discountImg = findViewById(R.id.discount_img);

        Utils.fullScreenView(this,false);

        if (!Utils.hasNavBar(this)){
            Log.e("hasNav--->", "onCreate: YES---> " );
            Utils.adjustBottomNav(this,R.id.soft_key_layout,R.id.main_layout);
            //navLayout.setVisibility(View.GONE);

        }

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InviteFriends.this,AddressDetails.class));
                finish();
            }
        });

        copyImg.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(InviteFriends.this);
                bottomSheetDialog.setContentView(R.layout.share);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.show();
            }
        });

        discountImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InviteFriends.this,Payment.class));
                finish();
            }
        });
    }
}
