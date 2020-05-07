package com.tool.toolsshare.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tool.toolsshare.repositories.ForgetPasswordRepository;
import com.tool.toolsshare.repositories.LoginRepository;

import org.json.JSONObject;

import java.util.HashMap;

public class ForgetPasswordViewModel extends AndroidViewModel {

    private ForgetPasswordRepository forgetPasswordRepository;
    public ForgetPasswordViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<HashMap<String,String>> getStatus(){
        return forgetPasswordRepository.getLoginStatus();
    }

    public void initialize(Application application, JSONObject reqJsonObj){
        Log.e("init--->", "initialize: "+reqJsonObj.toString() );
        forgetPasswordRepository = new ForgetPasswordRepository(application,reqJsonObj);

    }
}
