package com.internship.thien.nytimesnews.detail.View;

import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.internship.thien.nytimesnews.R;

public class DetailNewsActivity extends AppCompatActivity implements DetailNewsView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);


    }

    public void setupView() {
        String url = "https://www.codepath.com/";
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
// set toolbar color and/or setting custom actions before invoking build()
// Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
        CustomTabsIntent customTabsIntent = builder.build();
// and launch the desired Url with CustomTabsIntent.launchUrl()
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}
