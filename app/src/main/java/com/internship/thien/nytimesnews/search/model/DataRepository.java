package com.internship.thien.nytimesnews.search.model;

import android.content.Context;
import android.view.View;

import com.internship.thien.nytimesnews.data.model.Meta;

import java.util.Map;

public interface DataRepository {

    void getDataFromNetWork(DataListener listener, Map<String, String> query, int type);

    void getDataFromSettings(Context context, View view);

    String setupDateString(int day, int month, int year);

    int setupOffset(Meta meta);

    Map<String, String> addQuery(Context context, String query);
}
