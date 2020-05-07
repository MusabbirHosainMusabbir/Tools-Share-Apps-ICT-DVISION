package com.tool.toolsshare.viewmodel;

import android.app.Application;
import android.util.Log;

import com.tool.toolsshare.repositories.LoginRepository;
import com.tool.toolsshare.repositories.PublishPostRepository;

import org.json.JSONObject;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PublishPostViewModel extends AndroidViewModel {
    private PublishPostRepository publishPostRepository;
    public PublishPostViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<HashMap<String,String>> getPublishStatus(){
        return publishPostRepository.publishStatus();
    }

    public void initialize(Application application, JSONObject reqJsonObj){
        Log.e("init--->", "initialize: "+reqJsonObj.toString() );
        publishPostRepository = new PublishPostRepository(application,reqJsonObj);

    }
}
