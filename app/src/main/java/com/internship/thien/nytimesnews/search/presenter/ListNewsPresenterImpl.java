package com.internship.thien.nytimesnews.search.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.internship.thien.nytimesnews.data.model.News;
import com.internship.thien.nytimesnews.helper.DBHelper;
import com.internship.thien.nytimesnews.search.model.DataListener;
import com.internship.thien.nytimesnews.search.model.DataRepository;
import com.internship.thien.nytimesnews.search.view.ListNewsView;
import com.internship.thien.nytimesnews.util.NetworkUtils;

import java.util.List;
import java.util.Map;

public class ListNewsPresenterImpl implements ListNewsPresenter, DataListener {

    private ListNewsView mView;
    private DataRepository newsRepository;
    private DBHelper dbHelper;

    public ListNewsPresenterImpl(ListNewsView mView, DataRepository movieRepository) {
        this.mView = mView;
        this.newsRepository = movieRepository;
    }

    @Override
    public void getNews(Context context, Map<String, String> query) {

        if (NetworkUtils.isOnline(context))
            newsRepository.getDataFromNetWork(this, query);
        else
            mView.showError("No network connection. Please try again!");

    }

    public Map setupQuery(Context context, String query) {

        //DBHelper db  = new DBHelper();

        Map<String, String> data = DBHelper.newInstance().getDB(context);

        if(data.get("fq") != null)
            query = query + " " + data.get("fq");

        data.put("fq", query);

        Log.d("Query setup",data.toString());
        return data;
    }

    @Override
    public void onResponse(List<News> news) {
        mView.showListNews(news);
    }

    @Override
    public void onError(String error) {
        mView.showError(error);
    }

    @Override
    public void getSettings(Context context, View view) {
        newsRepository.getDataFromSettings(context, view);

    }

    @Override
    public void resetSettings(Context context) {

        DBHelper.newInstance().resetDB(context);
    }

    @Override
    public String setupDate(int day, int month, int year) {
       return newsRepository.setupDateString(day, month, year);
    }


}
