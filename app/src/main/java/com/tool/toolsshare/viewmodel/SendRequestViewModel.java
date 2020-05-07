package com.tool.toolsshare.viewmodel;

import android.app.Application;
import android.util.Log;

import com.tool.toolsshare.repositories.LoginRepository;
import com.tool.toolsshare.repositories.SendRequestRepository;

import org.json.JSONObject;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SendRequestViewModel extends AndroidViewModel {

    private SendRequestRepository sendRequestRepository;
    public SendRequestViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<HashMap<String,String>> getReqStatus(){
        return sendRequestRepository.getLoginStatus();
    }

    public void initialize(Application application, JSONObject reqJsonObj){
        Log.e("init--->", "initialize: "+reqJsonObj.toString() );
        sendRequestRepository = new SendRequestRepository(application,reqJsonObj);

    }
}
