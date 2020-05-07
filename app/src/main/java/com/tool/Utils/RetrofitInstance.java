package com.tool.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private  Retrofit retrofit = null;
    private static RetrofitInstance minstance = null;
    private static final String BASE_URL = "http://202.126.123.157/tool_share/recipes/";

    private  RetrofitInstance(){

            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

    }

    public static synchronized RetrofitInstance getInstance(){

        if (minstance == null){
            minstance = new RetrofitInstance();
        }
        return minstance;

    }

    public ApiInterface getApi(){
        return retrofit.create(ApiInterface.class);
    }

}
