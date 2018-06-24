package com.internship.thien.nytimesnews.search.presenter;

import android.content.Context;
import android.view.View;


import com.internship.thien.nytimesnews.data.model.Meta;

import java.util.Map;

public interface ListNewsPresenter {

    void getNews(Context context, Map<String, String> query, int type);

    Map<String,String> setupQuery(Context context, String query);

    void getSettings(Context context, View view);

    void resetSettings(Context context);

    String setupDate(int day, int month, int year);

    int setupPage(Meta meta);
}
