package com.tool.toolsshare.viewmodel;

import android.app.Application;
import android.util.Log;

import com.tool.toolsshare.repositories.LoginRepository;
import com.tool.toolsshare.repositories.ProfileUpdateRepository;

import org.json.JSONObject;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ProfileUpdateViewModel extends AndroidViewModel {

    private ProfileUpdateRepository profileUpdateRepository;
    public ProfileUpdateViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<HashMap<String,String>> getStatus(){
        return profileUpdateRepository.getUpdateStatus();
    }

    public void initialize(Application application, JSONObject reqJsonObj){
        Log.e("init--->", "initialize: "+reqJsonObj.toString() );
        profileUpdateRepository = new ProfileUpdateRepository(application,reqJsonObj);

    }
}
