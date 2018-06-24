package com.internship.thien.nytimesnews.search.model;

import com.internship.thien.nytimesnews.data.model.Meta;
import com.internship.thien.nytimesnews.data.model.News;

import java.util.List;

public interface DataListener {

    void onResponse(List<News> news, int type, Meta meta );

    void onError(String error);
}
