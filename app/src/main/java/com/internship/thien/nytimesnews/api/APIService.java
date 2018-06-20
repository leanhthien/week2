package com.internship.thien.nytimesnews.api;

import com.internship.thien.nytimesnews.data.model.Result;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIService {

    @GET("/svc/search/v2/articlesearch.json")
    Call<Result> searchByHand(@QueryMap Map<String, String> options,
                              @Query("api-key") String api_key);

    @GET("/svc/search/v2/articlesearch.json")
    Call<Result> searchFull(@Query("begin_date") String begin_date,
                            @Query("end_date") String end_date,
                            @Query("sort") String sort,
                            @Query("fq") String new_desk,
                            @Query("api-key") String api_key);

}

