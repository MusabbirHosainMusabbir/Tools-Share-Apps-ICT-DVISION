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

public class PostListRepository {

    JSONObject reqJsonObj;
    public PostListRepository(Application application, JSONObject reqJsonObj) {
        this.reqJsonObj = reqJsonObj;
        Log.e("json req--->", "RegistrationRepository: "+reqJsonObj.length() );
    }

    MutableLiveData<ArrayList<HashMap<String,String>>> success;

    public LiveData<ArrayList<HashMap<String,String>>> getAllList(){

        Log.e("RESPO____>","BAL ");
        if (success == null) {
            success = new MutableLiveData<>();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),reqJsonObj.toString());

        Call<ResponseBody> call = RetrofitInstance.getInstance().getApi().getPostList(requestBody);
        //Helper.showLoader(context,"");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("respo-->", "onResponse: "+response.body().toString().length() );

                // length.setValue(response.body().length());
                try {
                    JSONObject jsonObject=new JSONObject(response.body().string());
                    //Log.e("RESPO____>",""+jsonObject.getJSONArray("response").length());

                    ArrayList<HashMap<String,String>> dataList = new ArrayList<>();

                    for (int i = 0; i< jsonObject.getJSONArray("response").length(); i++){

                        HashMap<String,String> responseMap = new HashMap<>();
                        responseMap.put("id",jsonObject.getJSONArray("response").getJSONObject(i).getString("id"));
                        responseMap.put("name",jsonObject.getJSONArray("response").getJSONObject(i).getString("name"));
                        responseMap.put("price",jsonObject.getJSONArray("response").getJSONObject(i).getString("price"));
                        responseMap.put("location",jsonObject.getJSONArray("response").getJSONObject(i).getString("location"));

                        if(jsonObject.getJSONArray("response").getJSONObject(i).getJSONArray("images").length() != 0){
                            //Log.e("positions", String.valueOf(i));
                            responseMap.put("image",jsonObject.getJSONArray("response").getJSONObject(i).getJSONArray("images").getString(0));
                        }


                        //Log.e("IMAGE--->", "onResponse: "+jsonObject.getJSONArray("response").getJSONObject(i).getJSONArray("images").getString(0) );

                        dataList.add(responseMap);

                    }


                    success.setValue(dataList);
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

