package com.tool.toolsshare.repositories;

import android.app.Application;
import android.util.Log;

import com.tool.Utils.RetrofitInstance;
import com.tool.toolsshare.model.ProducDetailsResponse;
import com.tool.toolsshare.model.ProductDetailsModel;

import org.json.JSONObject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsRepository {

    JSONObject reqJsonObj;
    public ProductDetailsRepository(Application application, JSONObject reqJsonObj) {
        this.reqJsonObj = reqJsonObj;
        Log.e("json req--->", "RegistrationRepository: "+reqJsonObj.length() );
    }

    MutableLiveData<ProducDetailsResponse> success;

    public LiveData<ProducDetailsResponse> getProductDetails(){

        Log.e("RESPO____>","BAL ");
        if (success == null) {
            success = new MutableLiveData<>();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),reqJsonObj.toString());

        Call<ProductDetailsModel> call = RetrofitInstance.getInstance().getApi().getPostDetails(requestBody);
        call.enqueue(new Callback<ProductDetailsModel>() {
            @Override
            public void onResponse(Call<ProductDetailsModel> call, Response<ProductDetailsModel> response) {
                success.setValue(response.body().getResponse().get(0));
                Log.e("RESPO____>","PRoduct details  "+response.body().getResponse().get(0).getName());
            }

            @Override
            public void onFailure(Call<ProductDetailsModel> call, Throwable t) {

            }
        });

        return success;



    }

}
