package com.tool.toolsshare.repositories;

import android.app.Application;
import android.util.Log;

import com.tool.Utils.RetrofitInstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequesterRepository {

    JSONObject reqJsonObj;
    public RequesterRepository(Application application, JSONObject reqJsonObj) {
        this.reqJsonObj = reqJsonObj;
        Log.e("json req--->", "RegistrationRepository: "+reqJsonObj.length() );
    }

    MutableLiveData<HashMap<String,String>> success;

    public LiveData<HashMap<String,String>> getRequesterDetails(){

        Log.e("RESPO____>","BAL ");
        if (success == null) {
            success = new MutableLiveData<>();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),reqJsonObj.toString());

        Call<ResponseBody> call = RetrofitInstance.getInstance().getApi().getRequester(requestBody);
        //Helper.showLoader(context,"");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("respo-->", "onResponse: "+response.body().toString().length() );

                // length.setValue(response.body().length());
                try {
                    JSONObject jsonObject=new JSONObject(response.body().string());
                    Log.e("RESPO____>",""+jsonObject.getJSONArray("response").length());
                    HashMap<String,String> responseMap = new HashMap<>();

                    for (int i = 0; i< jsonObject.getJSONArray("response").length(); i++){


                        responseMap.put("id",jsonObject.getJSONArray("response").getJSONObject(i).getString("id"));
                        responseMap.put("email",jsonObject.getJSONArray("response").getJSONObject(i).getString("email"));
                        responseMap.put("mobile",jsonObject.getJSONArray("response").getJSONObject(i).getString("mobile"));
                        responseMap.put("name",jsonObject.getJSONArray("response").getJSONObject(i).getString("name"));
                        responseMap.put("location",jsonObject.getJSONArray("response").getJSONObject(i).getString("location"));
                        responseMap.put("image",jsonObject.getJSONArray("response").getJSONObject(i).getString("image"));

                        //Log.e("IMAGE--->", "onResponse: "+jsonObject.getJSONArray("response").getJSONObject(i).getJSONArray("images").getString(0) );

                    }


                    success.setValue(responseMap);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("RESPO____>","BAL "+e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        return success;



    }


}
