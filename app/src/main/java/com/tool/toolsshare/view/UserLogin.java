package com.tool.toolsshare.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tool.Utils.Helper;
import com.tool.Utils.PreferenceMangement;
import com.tool.Utils.Utils;
import com.tool.customfonts.MyTextView_SF_Pro_Display_Medium;
import com.tool.toolsshare.R;
import com.tool.toolsshare.viewmodel.LoginViewModel;
import com.tool.toolsshare.viewmodel.RegistrationViewModel;
import com.tool.toolsshare.viewmodel.ReqCountViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UserLogin extends AppCompatActivity {

    ImageButton showPassword;
    EditText passwordEdt,mailEdittext;
    Button loginBtn;
    private boolean isShowPassword = false;
    TextView signUp;

    LinearLayout forg;

    LoginViewModel loginViewModel;
    CheckBox checkBox;
    ReqCountViewModel reqCountViewModel;
    MyTextView_SF_Pro_Display_Medium forgetpassword;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        Utils.fullScreenView(this,false);

        if (!Utils.hasNavBar(this)){
            Log.e("hasNav--->", "onCreate: YES---> " );
            Utils.adjustBottomNav(this,R.id.soft_key_layout,R.id.main_layout);
            //navLayout.setVisibility(View.GONE);

        }

        forg = findViewById(R.id.forg);

        checkBox = findViewById(R.id.checkbox);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    checkLocationPermission();
                    //Toast.makeText(UserLogin.this, "checked", Toast.LENGTH_SHORT).show();
                }else{
                   // Toast.makeText(UserLogin.this, "not checked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        showPassword = findViewById(R.id.showpassword);
        passwordEdt = findViewById(R.id.passwordEdt);
        mailEdittext = findViewById(R.id.mail_et);
        loginBtn = findViewById(R.id.login);
        signUp = findViewById(R.id.sign_up);
        forgetpassword = findViewById(R.id.forget_password);


        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLogin.this,ForgetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserLogin.this,UserRegistration.class));
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.showLoader(UserLogin.this,"");


                JSONObject reqJsonObj = new JSONObject();

                try {
                    reqJsonObj.put("email",mailEdittext.getText().toString().trim());
                    reqJsonObj.put("password",passwordEdt.getText().toString().trim());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loginViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
                loginViewModel.initialize(getApplication(),reqJsonObj);
                loginViewModel.getStatus().observe(UserLogin.this, new Observer<HashMap<String, String>>() {
                    @Override
                    public void onChanged(HashMap<String, String> stringStringHashMap) {
                        Helper.cancelLoader();
                        if (stringStringHashMap.get("status").equals("1")){

                            Toast.makeText(UserLogin.this, ""+stringStringHashMap.get("message"), Toast.LENGTH_SHORT).show();
                            PreferenceMangement.savePreference(UserLogin.this,"user_id",stringStringHashMap.get("user_id"));

                            startActivity(new Intent(UserLogin.this,HomeActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(UserLogin.this, ""+stringStringHashMap.get("message"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        forg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("post_id","5");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                reqCountViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ReqCountViewModel.class);
                reqCountViewModel.initialize(getApplication(),jsonObject);
                reqCountViewModel.getCounts().observe(UserLogin.this, new Observer<HashMap<String, String>>() {
                    @Override
                    public void onChanged(HashMap<String, String> stringStringHashMap) {
                        //dataMap.put("count",stringStringHashMap.get("count"));
                    }
                });

            }
        });
    }

    private void   checkLocationPermission() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    public void ShowHidePass(View view) {

        if (isShowPassword) {
            passwordEdt.setTransformationMethod(new PasswordTransformationMethod());
            showPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_hide_password));
            isShowPassword = false;
        }else{
            passwordEdt.setTransformationMethod(null);
            showPassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_show_password));
            isShowPassword = true;
        }
    }
}
