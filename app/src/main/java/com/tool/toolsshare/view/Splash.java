package com.tool.toolsshare.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.tool.Utils.PreferenceMangement;
import com.tool.Utils.Utils;
import com.tool.toolsshare.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.e("user---->", "run: "+PreferenceMangement.getPreference(Splash.this,"user_id"));

                if (!PreferenceMangement.getPreference(Splash.this,"user_id").equals("0")){
                    startActivity(new Intent(Splash.this,HomeActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(Splash.this,Intro.class));
                    finish();
                }



            }
        },4000);

    }
}
