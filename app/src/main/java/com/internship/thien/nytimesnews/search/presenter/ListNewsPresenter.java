package com.internship.thien.nytimesnews.search.presenter;

import android.content.Context;

import java.util.Map;

public interface ListNewsPresenter {

    void getNews(Context context, Map query);

    //boolean checkInternet(Context context);
}
