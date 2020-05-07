package com.tool.toolsshare.view;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tool.Utils.Helper;
import com.tool.Utils.PreferenceMangement;
import com.tool.toolsshare.R;
import com.tool.toolsshare.repositories.ForgetPasswordRepository;
import com.tool.toolsshare.viewmodel.ForgetPasswordViewModel;
import com.tool.toolsshare.viewmodel.LoginViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText registeredEdt;
    TextView submit,back;
    String user_id;
    Intent intent;
    String activity;
    ForgetPasswordViewModel forgetPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        intent = getIntent();
        activity = intent.getStringExtra("activity");


        registeredEdt = findViewById(R.id.registered_emailid);
        submit = findViewById(R.id.forgot_button);
        back = findViewById(R.id.backToLoginBtn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 setPassword();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(ForgetPasswordActivity.this,UserLogin.class);
                    startActivity(intent);
                    finish();
            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent(ForgetPasswordActivity.this,UserLogin.class);
        startActivity(intent);
        finish();

        return super.onKeyDown(keyCode, event);

    }

    private void setPassword() {
        Helper.showLoader(ForgetPasswordActivity.this,"");


        JSONObject reqJsonObj = new JSONObject();

        try {
            reqJsonObj.put("email",registeredEdt.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        forgetPasswordViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ForgetPasswordViewModel.class);
        forgetPasswordViewModel.initialize(getApplication(),reqJsonObj);
        forgetPasswordViewModel.getStatus().observe(ForgetPasswordActivity.this, new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(HashMap<String, String> stringStringHashMap) {
                Helper.cancelLoader();
                if (stringStringHashMap.get("status").equals("1")){
                    String message = stringStringHashMap.get("message");
                    Toast.makeText(ForgetPasswordActivity.this, ""+message, Toast.LENGTH_LONG).show();
                }
                else {
                    String message = stringStringHashMap.get("message");
                    Toast.makeText(ForgetPasswordActivity.this, ""+message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
