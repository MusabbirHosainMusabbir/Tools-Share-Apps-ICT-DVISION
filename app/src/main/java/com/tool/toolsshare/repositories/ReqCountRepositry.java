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

public class ReqCountRepositry {

    JSONObject reqJsonObj;

    public ReqCountRepositry(Application application, JSONObject reqJsonObj) {
        this.reqJsonObj = reqJsonObj;

    }

    MutableLiveData<HashMap<String,String>> success;
    MutableLiveData<ArrayList<HashMap<String,String>>> userList;
    MutableLiveData<ArrayList<HashMap<String,String>>> myBrowings;

    public LiveData<HashMap<String,String>> getRequestCounts(){

        Log.e("RESPO____>","BAL ");
        if (success == null) {
            success = new MutableLiveData<>();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),reqJsonObj.toString());

        Call<ResponseBody> call = RetrofitInstance.getInstance().getApi().getCount(requestBody);
        //Helper.showLoader(context,"");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("respo-->", "onResponse: isEmpty "+response.body().toString().isEmpty() );

                // length.setValue(response.body().length());
                try {
                    JSONObject jsonObject=new JSONObject(response.body().string());
                    Log.e("RESPO____ReqCount>",""+jsonObject.getJSONArray("response").length());

                    HashMap<String,String> responseMap = new HashMap<>();
                    responseMap.put("count",String.valueOf(jsonObject.getJSONArray("response").length()));

                    success.setValue(responseMap);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("RESPO____>","BAL Excep "+e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("RESPO____>","SAL Excep"+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        return success;



    }
    public LiveData<ArrayList<HashMap<String,String>>> getUserIdList(){

        Log.e("RESPO____>","BAL ");
        if (userList == null) {
            userList = new MutableLiveData<>();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),reqJsonObj.toString());

        Call<ResponseBody> call = RetrofitInstance.getInstance().getApi().getCount(requestBody);
        //Helper.showLoader(context,"");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("respo-->", "onResponse: isEmpty "+response.body().toString().isEmpty() );

                // length.setValue(response.body().length());
                try {
                    JSONObject jsonObject=new JSONObject(response.body().string());
                    Log.e("RESPO____ReqCount>","respo-- Num of Requests> "+jsonObject.getJSONArray("response").length());

                    ArrayList<HashMap<String,String>> dataList = new ArrayList<>();


                    for (int i = 0; i<jsonObject.getJSONArray("response").length(); i++){

                        HashMap<String,String> dataMap = new HashMap<>();
                        dataMap.put("user_id",jsonObject.getJSONArray("response").getJSONObject(i).getString("user_id"));
                        dataMap.put("request_id",jsonObject.getJSONArray("response").getJSONObject(i).getString("id"));
                        dataMap.put("status",jsonObject.getJSONArray("response").getJSONObject(i).getString("status"));

                        dataList.add(dataMap);
                    }



                    userList.setValue(dataList);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("RESPO____>","BAL Excep "+e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("RESPO____>","SAL Excep"+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        return userList;



    }
    public LiveData<ArrayList<HashMap<String,String>>> myBrowRequestList(){

        Log.e("RESPO____>","BAL ");
        if (myBrowings == null) {
            myBrowings = new MutableLiveData<>();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),reqJsonObj.toString());

        Call<ResponseBody> call = RetrofitInstance.getInstance().getApi().getCount(requestBody);
        //Helper.showLoader(context,"");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("respo-->", "onResponse: isEmpty "+response.body().toString().isEmpty() );

                // length.setValue(response.body().length());
                try {
                    JSONObject jsonObject=new JSONObject(response.body().string());
                    Log.e("RESPO____>",""+jsonObject.getJSONArray("response").length());

                    ArrayList<HashMap<String,String>> dataList = new ArrayList<>();

                    for (int i = 0; i< jsonObject.getJSONArray("response").length(); i++){

                        HashMap<String,String> responseMap = new HashMap<>();
                        responseMap.put("id",jsonObject.getJSONArray("response").getJSONObject(i).getString("id"));
                        responseMap.put("name",jsonObject.getJSONArray("response").getJSONObject(i).getString("name"));
                        responseMap.put("post_id",jsonObject.getJSONArray("response").getJSONObject(i).getString("post_id"));
                        //responseMap.put("price",jsonObject.getJSONArray("response").getJSONObject(i).getString("price"));
                        //responseMap.put("location",jsonObject.getJSONArray("response").getJSONObject(i).getString("location"));
                        responseMap.put("status",jsonObject.getJSONArray("response").getJSONObject(i).getString("status"));
                        //responseMap.put("image",jsonObject.getJSONArray("response").getJSONObject(i).getJSONArray("images").getString(0));



                        dataList.add(responseMap);

                    }


                    myBrowings.setValue(dataList);
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

        return myBrowings;



    }

}
