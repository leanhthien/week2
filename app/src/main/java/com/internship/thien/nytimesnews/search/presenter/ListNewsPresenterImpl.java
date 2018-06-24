package com.internship.thien.nytimesnews.search.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.internship.thien.nytimesnews.data.model.Meta;
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

    public ListNewsPresenterImpl(ListNewsView mView, DataRepository movieRepository) {
        this.mView = mView;
        this.newsRepository = movieRepository;
    }

    @Override
    public void getNews(Context context, Map<String, String> query, int type) {

        mView.showLoading();
        if (NetworkUtils.isOnline(context))
             newsRepository.getDataFromNetWork(this, query, type);
        else
            mView.showError("No network connection. Please try again!");

    }

    public Map<String, String> setupQuery(Context context, String query) {

        return newsRepository.addQuery(context, query);
    }

    @Override
    public void onResponse(List<News> news, int type, Meta meta) {

        mView.hideLoading();
        mView.showListNews(news, type, meta);

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

    @Override
    public int setupPage(Meta meta) {
        return newsRepository.setupOffset(meta);
    }


}
