package com.example.pagination;

import com.example.pagination.Pojo.Pages;
import com.example.pagination.Pojo.PagesData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitApi {

    @GET("users")
    Call<Pages> getPageDate(
            @Query("page") Integer pages,
            @Query("per_page") Integer pages_count
    );


}
