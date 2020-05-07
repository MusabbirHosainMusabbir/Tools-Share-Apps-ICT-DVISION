package com.tool.toolsshare.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.tool.Utils.Helper;
import com.tool.Utils.PreferenceMangement;
import com.tool.Utils.Utils;
import com.tool.toolsshare.R;
import com.tool.toolsshare.viewmodel.LoginViewModel;
import com.tool.toolsshare.viewmodel.SendRequestViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Payment extends AppCompatActivity {
    TextView toolTitle, mainTitle, paymentTitle, amountTv;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    String productId, netPrice;
    private int paymentType;
    LinearLayout payBtn, paymentOptionLayout;
    AlertDialog dialog;
    SendRequestViewModel sendRequestViewModel;
    ImageView backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        toolTitle = findViewById(R.id.tool_title);
        mainTitle = findViewById(R.id.main_title);
        toolbar = findViewById(R.id.tool_bar);
        paymentTitle = findViewById(R.id.pay_title);
        amountTv = findViewById(R.id.pay_amount);
        payBtn = findViewById(R.id.pay_btn_layout);
        payBtn.setEnabled(false);
        paymentOptionLayout = findViewById(R.id.payment_layout);
        backImg = findViewById(R.id.back_img);


        Intent intent = getIntent();
        productId = intent.getStringExtra("product_id");
        netPrice = intent.getStringExtra("rent");

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Payment.this,ProductDetails.class);
                intent.putExtra("product_id",productId);
                startActivity(intent);
                finish();
            }
        });

        amountTv.setText("BDT "+netPrice);

        appBarLayout = findViewById(R.id.appBarLayout);

        Utils.fullScreenView(this,false);

        if (!Utils.hasNavBar(this)){
            Log.e("hasNav--->", "onCreate: YES---> " );
            Utils.adjustBottomNav(this,R.id.soft_key_layout,R.id.main_layout);
            //navLayout.setVisibility(View.GONE);

        }

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

                Log.e("----->", "onOffsetChanged: "+i+" "+toolbar.getHeight() );

                if (i < -toolbar.getHeight()) {
                    //toolbar is collapsed here
                    //write your code here
                    toolTitle.setVisibility(View.VISIBLE);
                    mainTitle.setVisibility(View.INVISIBLE);
                    paymentOptionLayout.setPadding(0,20,0,0);
                }
                else {
                    toolTitle.setVisibility(View.GONE);
                    mainTitle.setVisibility(View.VISIBLE);
                    paymentOptionLayout.setPadding(0,0,0,0);
                }


            }
        });

        mainTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Payment.this,MyRentals.class));
                finish();
            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop(Payment.this,R.layout.surancce_pop_up);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(Payment.this,ProductDetails.class);
            intent.putExtra("product_id",productId);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void payment(View view) {
        switch (view.getId()){
            case R.id.cash_on_delivery:
                paymentType=1;
                payBtn.setEnabled(true);
            break;
            case R.id.bkash:
                payBtn.setEnabled(true);
                paymentType=2;
                break;
            case R.id.credit_card:
                paymentType=3;
                payBtn.setEnabled(true);
                break;
        }

        payBtn.setBackgroundResource(R.drawable.button_bg);


    }

    public void sendRequest(){

        // call api to send reqquest
        Helper.showLoader(Payment.this,"");


        JSONObject reqJsonObj = new JSONObject();

        try {
            reqJsonObj.put("post_id",productId);
            reqJsonObj.put("user_id", PreferenceMangement.getPreference(Payment.this,"user_id"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        sendRequestViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(SendRequestViewModel.class);
        sendRequestViewModel.initialize(getApplication(),reqJsonObj);
        sendRequestViewModel.getReqStatus().observe(Payment.this, new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(HashMap<String, String> stringStringHashMap) {
                Helper.cancelLoader();
                if (stringStringHashMap.get("status").equals("0")){
                    dialog.dismiss();
                    showPop(Payment.this,R.layout.popup);
                }
                else {
                    Toast.makeText(Payment.this,"Request Not Placed",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void showPop(Context context,int layoutId) {

        //bgMusicPlayer.release();
        AlertDialog.Builder builder = new AlertDialog.Builder(Payment.this);
        View view = getLayoutInflater().inflate(layoutId, null);
        final Activity activity = (Activity)context;


        if (layoutId == R.layout.surancce_pop_up) {


            final Button yes = view.findViewById(R.id.yes);
            final Button no = view.findViewById(R.id.no);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendRequest();
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
        else if (layoutId == R.layout.popup){
            final TextView browse = view.findViewById(R.id.browse_txt);

            browse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Payment.this,HomeActivity.class));
                    finish();
                }
            });


        }


        builder.setView(view);

        dialog = builder.create();
        dialog.requestWindowFeature(DialogFragment.STYLE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // dialog.getWindow().setLayout(100,100);
        dialog.setCancelable(true);
        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // The absolute width of the available display size in pixels.
        int displayWidth = displayMetrics.widthPixels;
        // The absolute height of the available display size in pixels.
        int displayHeight = displayMetrics.heightPixels;

        // Initialize a new window manager layout parameters
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        // Copy the alert dialog window attributes to new layout parameter instance
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        // Set alert dialog width equal to screen width 70%
        int dialogWindowWidth = (int) (displayWidth * 0.75f);
        // Set alert dialog height equal to screen height 70%
        int dialogWindowHeight = (int) (displayHeight * 0.6f);

        // Set the width and height for the layout parameters
        // This will bet the width and height of alert dialog
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        // Apply the newly created layout parameters to the alert dialog window
        dialog.getWindow().setAttributes(layoutParams);




    }
}
