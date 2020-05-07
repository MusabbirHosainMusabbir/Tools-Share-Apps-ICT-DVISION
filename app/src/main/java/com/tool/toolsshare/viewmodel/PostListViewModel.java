package com.tool.toolsshare.viewmodel;

import android.app.Application;
import android.util.Log;

import com.tool.toolsshare.repositories.CategoryListRepository;
import com.tool.toolsshare.repositories.PostListRepository;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PostListViewModel extends AndroidViewModel {

    private PostListRepository postListRepository;
    public PostListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<HashMap<String,String>>> getPosts(){
        return postListRepository.getAllList();
    }

    public void initialize(Application application, JSONObject reqJsonObj){
        Log.e("init--->", "initialize: "+reqJsonObj.toString() );
        postListRepository = new PostListRepository(application,reqJsonObj);

    }
}
