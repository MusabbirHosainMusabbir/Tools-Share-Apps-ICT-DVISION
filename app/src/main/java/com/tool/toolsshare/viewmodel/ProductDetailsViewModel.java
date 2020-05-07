package com.tool.toolsshare.viewmodel;

import android.app.Application;
import android.util.Log;

import com.tool.toolsshare.model.ProducDetailsResponse;
import com.tool.toolsshare.repositories.ProductDetailsRepository;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ProductDetailsViewModel extends AndroidViewModel {

    private ProductDetailsRepository productDetailsRepository;
    public ProductDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ProducDetailsResponse> getProductDetails(){
        return productDetailsRepository.getProductDetails();
    }

    public void initialize(Application application, JSONObject reqJsonObj){
        Log.e("init--->", "initialize: "+reqJsonObj.toString() );
        productDetailsRepository = new ProductDetailsRepository(application,reqJsonObj);

    }
}
