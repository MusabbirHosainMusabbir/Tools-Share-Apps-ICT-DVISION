package com.tool.toolsshare.viewmodel;

import android.app.Application;
import android.util.Log;

import com.tool.toolsshare.repositories.RequesterRepository;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RequesterViewModel extends AndroidViewModel {

    private RequesterRepository requesterRepository;
    public RequesterViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<HashMap<String,String>> getUser(){
        return requesterRepository.getRequesterDetails();
    }



    public void initialize(Application application, JSONObject reqJsonObj){
        Log.e("init--->", "initialize: "+reqJsonObj.toString() );
        requesterRepository = new RequesterRepository(application,reqJsonObj);

    }
}
