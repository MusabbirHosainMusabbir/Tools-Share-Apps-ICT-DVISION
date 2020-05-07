package com.tool.toolsshare.viewmodel;

import android.app.Application;
import android.util.Log;

import com.tool.toolsshare.repositories.CategoryListRepository;
import com.tool.toolsshare.repositories.LoginRepository;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CategoryListViewModel extends AndroidViewModel {

    private CategoryListRepository categoryListRepository;
    public CategoryListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<HashMap<String,String>>> getCategories(){
        return categoryListRepository.getCategoryList();
    }

    public void initialize(Application application, JSONObject reqJsonObj){
        Log.e("init--->", "initialize: "+reqJsonObj.toString() );
        categoryListRepository = new CategoryListRepository(application,reqJsonObj);

    }
}
