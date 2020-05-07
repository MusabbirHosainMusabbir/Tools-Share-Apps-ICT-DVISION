package com.tool.toolsshare.viewmodel;

import android.app.Application;
import android.util.Log;

import com.tool.toolsshare.repositories.LoginRepository;
import com.tool.toolsshare.repositories.ReqCountRepositry;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ReqCountViewModel extends AndroidViewModel {

    private ReqCountRepositry reqCountRepositry;
    public ReqCountViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<HashMap<String,String>> getCounts(){
        return reqCountRepositry.getRequestCounts();
    }

    public LiveData<ArrayList<HashMap<String,String>>> getUserList(){
        return reqCountRepositry.getUserIdList();
    }
    public LiveData<ArrayList<HashMap<String,String >>> getMyBrowingList(){
        return reqCountRepositry.myBrowRequestList();
    }

    public void initialize(Application application, JSONObject reqJsonObj){
        Log.e("init--->", "initialize: "+reqJsonObj.toString() );
        reqCountRepositry = new ReqCountRepositry(application,reqJsonObj);

    }
}
