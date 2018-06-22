package com.internship.thien.nytimesnews.search.view;

import android.view.MenuItem;
import android.view.View;

import com.internship.thien.nytimesnews.data.model.News;

import java.util.List;

public interface ListNewsView {

    void showLoading();

    void hideLoading();

    void setupView();

    void showListNews(List<News> news);

    void showError(String error);

    void searchBarListener(MenuItem searchItem);

    void settingsBarListener();

    void setupSettings(View view);

}
