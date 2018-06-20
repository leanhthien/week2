package com.internship.thien.nytimesnews.search.model;

import android.support.annotation.Nullable;
import android.util.Log;

import com.internship.thien.nytimesnews.BuildConfig;
import com.internship.thien.nytimesnews.api.APIService;
import com.internship.thien.nytimesnews.data.model.News;
import com.internship.thien.nytimesnews.data.model.Responses;
import com.internship.thien.nytimesnews.data.model.Result;
import com.internship.thien.nytimesnews.util.APIUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepositoryImpl implements DataRepository {

    private APIService mService;

    public DataRepositoryImpl() {
        mService = APIUtils.getAPIService();
    }

    @Override
    public void getDataFromNetWork(final DataListener listener, Map query) {

        Map<String, String> data = new HashMap<>();
        data.put("begin_date", "20160112");

        String date = "20160112";
        String sort = "oldest";
        String desk = "Human";

        //Call<Result> call = mService.searchFull(date, null, null, desk, BuildConfig.API_KEY);

        Call<Result> call = mService.searchByHand(data, BuildConfig.API_KEY);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@Nullable Call<Result> call,@Nullable Response<Result> response) {

                assert response != null;
                if (response.isSuccessful()) {
                    Log.d("Response",response.toString());
                    List<News> news = Objects.requireNonNull(response.body()).getResponse().getNews();
                    if (news != null)
                        listener.onResponse(news);
                    else {
                        listener.onError("Nothing return!");
                    }
                }
                else {
                    Log.d("Response","Fail");
                    listener.onError("Response fail!");
                }

            }

            @Override
            public void onFailure(@Nullable Call<Result> call,@Nullable Throwable t) {
                assert t != null;
                listener.onError(t.getMessage());
            }
        });

    }
}
