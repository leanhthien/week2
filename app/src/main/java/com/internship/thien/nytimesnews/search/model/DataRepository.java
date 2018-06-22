package com.internship.thien.nytimesnews.search.model;

import android.content.Context;
import android.view.View;

import java.util.Map;

public interface DataRepository {

    void getDataFromNetWork(DataListener listener, Map<String, String> query);

    void getDataFromSettings(Context context, View view);

    String setupDateString(int day, int month, int year);
}
