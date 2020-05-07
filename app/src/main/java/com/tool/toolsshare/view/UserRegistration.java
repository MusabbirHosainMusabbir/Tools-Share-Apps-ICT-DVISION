package com.tool.toolsshare.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.tool.Utils.Helper;
import com.tool.toolsshare.R;
import com.tool.toolsshare.viewmodel.RegistrationViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class UserRegistration extends AppCompatActivity {

    ImageButton showPassword;
    EditText passwordEdt, emailEdt, nameEdt, locationEdt;
    private boolean isShowPassword = false;
    Button createAccBtn;
    RegistrationViewModel registrationViewModel;
    JSONObject reqJsonObj;
    ImageView backimg;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        createAccBtn = findViewById(R.id.create_account_btn);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        showPassword = findViewById(R.id.showpassword);
        passwordEdt = findViewById(R.id.passwordEdt);
        emailEdt = findViewById(R.id.emailEdt);
        nameEdt = findViewById(R.id.nameEdt);
        locationEdt = findViewById(R.id.locationEdt);
        backimg = findViewById(R.id.back_img);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(UserRegistration.this,UserLogin.class);
                startActivity(intent);
                finish();
            }
        });


        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Helper.showLoader(UserRegistration.this,"");


                reqJsonObj = new JSONObject();

                try {
                    reqJsonObj.put("name",nameEdt.getText().toString().trim());
                    reqJsonObj.put("email",emailEdt.getText().toString().trim());
                    reqJsonObj.put("password",passwordEdt.getText().toString().trim());
                    reqJsonObj.put("location",locationEdt.getText().toString().trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                registrationViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegistrationViewModel.class);
                registrationViewModel.initialize(getApplication(),reqJsonObj);
                registrationViewModel.getLength().observe(UserRegistration.this, new Observer<HashMap<String, String>>() {
                    @Override
                    public void onChanged(HashMap<String, String> stringStringHashMap) {
                        Helper.cancelLoader();
                            if(stringStringHashMap.get("status").equalsIgnoreCase("1")){
                                Toast.makeText(UserRegistration.this,""+stringStringHashMap.get("message"), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(UserRegistration.this,UserLogin.class));
                                finish();
                            }else{
                                Toast.makeText(UserRegistration.this,""+stringStringHashMap.get("message"), Toast.LENGTH_LONG).show();
                            }
                    }
                });
//                registrationViewModel.getLength().observe(UserRegistration.this, new Observer<Integer>() {
//                    @Override
//                    public void onChanged(Integer integer) {
//                        Helper.cancelLoader();
//                        //Toast.makeText(UserRegistration.this,"Length "+integer, Toast.LENGTH_LONG).show();
//                        if (integer == 1){
//                            Toast.makeText(UserRegistration.this,"Registration Successfull..", Toast.LENGTH_LONG).show();
//                            startActivity(new Intent(UserRegistration.this,UserLogin.class));
//                        }
//                        else {
//                            Toast.makeText(UserRegistration.this,"Sorry "+integer, Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });

            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(UserRegistration.this,UserLogin.class);
            startActivity(intent);
            finish();

            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
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
