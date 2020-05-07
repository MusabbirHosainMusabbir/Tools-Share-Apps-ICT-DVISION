package com.tool.toolsshare.viewmodel;

import android.app.Application;
import android.util.Log;

import com.tool.toolsshare.repositories.AcceptOrRejectRepository;
import com.tool.toolsshare.repositories.LoginRepository;

import org.json.JSONObject;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AcceptOrRejectViewModel extends AndroidViewModel {

    private AcceptOrRejectRepository acceptOrRejectRepository;

    public AcceptOrRejectViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<HashMap<String,String>> getStatus(){
        return acceptOrRejectRepository.getAcceptOrRejectStatus();
    }

    public void initialize(Application application, JSONObject reqJsonObj){
        Log.e("init--->", "initialize: "+reqJsonObj.toString() );
        acceptOrRejectRepository = new AcceptOrRejectRepository(application,reqJsonObj);

    }
}
