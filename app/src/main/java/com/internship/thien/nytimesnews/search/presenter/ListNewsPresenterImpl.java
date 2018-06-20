package com.internship.thien.nytimesnews.search.presenter;

import android.content.Context;

import com.internship.thien.nytimesnews.data.model.News;
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
    public void getNews(Context context, Map query) {

        if (NetworkUtils.isOnline(context))
            newsRepository.getDataFromNetWork(this, query);
        else
            mView.showError("There're no network connection. Please try again!");

    }

    @Override
    public void onResponse(List<News> news) {
        mView.showListNews(news);
    }

    @Override
    public void onError(String error) {
        mView.showError(error);
    }
}
