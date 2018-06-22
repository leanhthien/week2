package com.internship.thien.nytimesnews.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;

public class DBHelper {

    private String SHARED_PREFERENCES_NAME = "settings";

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private static DBHelper mInstance;

    public static DBHelper newInstance() {
        if (mInstance == null) {
            mInstance = new DBHelper();
        }
        return mInstance;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setDB(Context context, Map<String, String> data) {
        settings =  context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        data.forEach((k, v) -> editor.putString(k,v));
        editor.apply();
    }

    public Map<String, String> getDB(Context context) {
        Map<String, String> data = new HashMap<>();
        String date;
        String sort;
        String new_desk;

        settings = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        date = settings.getString("date",null);
        sort = settings.getString("sort",null);
        new_desk = settings.getString("new_desk",null);

        if (date != null)
            data.put("begin_date", date);
        if (sort != null)
            data.put("sort", sort);
        if (new_desk != null)
            data.put("fq", new_desk);

        return data;
    }

    public void resetDB(Context context) {

        settings = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.apply();

    }



}
