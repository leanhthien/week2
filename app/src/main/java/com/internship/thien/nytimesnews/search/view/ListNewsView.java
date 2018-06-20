package com.internship.thien.nytimesnews.search.view;

import com.internship.thien.nytimesnews.data.model.News;

import java.util.List;

public interface ListNewsView {

    void showLoading();

    void hideLoading();

    void showListNews(List<News> news);

    void showError(String error);
}
