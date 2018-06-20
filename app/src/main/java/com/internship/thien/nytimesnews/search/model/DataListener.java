package com.internship.thien.nytimesnews.search.model;

import com.internship.thien.nytimesnews.data.model.News;

import java.util.List;

public interface DataListener {

    void onResponse(List<News> news);

    void onError(String error);
}
