package com.example.pagination.Retrofit;

import com.example.pagination.RetrofitApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConstant {

    private static final String BASE_URL = "https://reqres.in/api/";
    private static RetrofitConstant Instance;
    private Retrofit retrofit;


    private RetrofitConstant() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static RetrofitConstant getInstance()
    {
        if (Instance == null)
        {
            Instance = new RetrofitConstant();
        }

        return Instance;
    }

    public RetrofitApi getApi()
    {
        return retrofit.create(RetrofitApi.class);
    }



}
