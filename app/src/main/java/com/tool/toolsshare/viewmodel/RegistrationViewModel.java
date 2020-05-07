package com.tool.toolsshare.viewmodel;

import android.app.Application;
import android.util.Log;

import com.tool.toolsshare.repositories.RegistrationRepository;

import org.json.JSONObject;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RegistrationViewModel extends AndroidViewModel {

    private RegistrationRepository registrationRepository;
    public RegistrationViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<HashMap<String, String>> getLength(){
        return registrationRepository.getRegistrationStatus();
    }

    public void initialize(Application application, JSONObject reqJsonObj){
        Log.e("init--->", "initialize: "+reqJsonObj.toString() );
        registrationRepository = new RegistrationRepository(application,reqJsonObj);

    }
}
