package com.internship.thien.nytimesnews.search.presenter;

import android.content.Context;
import android.view.View;

import java.util.Map;

public interface ListNewsPresenter {

    void getNews(Context context, Map<String, String> query);

    Map<String,String> setupQuery(Context context, String query);

    void getSettings(Context context, View view);

    void resetSettings(Context context);

    String setupDate(int day, int month, int year);

    //boolean checkInternet(Context context);
}
