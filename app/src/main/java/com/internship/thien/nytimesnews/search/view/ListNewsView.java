package com.internship.thien.nytimesnews.search.view;

import android.view.MenuItem;
import android.view.View;

import com.internship.thien.nytimesnews.data.model.Meta;
import com.internship.thien.nytimesnews.data.model.News;

import java.util.List;

public interface ListNewsView {

    void setupView();

    void showListNews(List<News> news, int type, Meta meta);

    void searchBarListener(MenuItem searchItem);

    void settingsBarListener();

    void setupSettings(View view);

    void openWebsite(News news);

    void showLoading();

    void hideLoading();

    void showError(String error);

}
