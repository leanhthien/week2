package com.internship.thien.nytimesnews.detail.View;

import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.internship.thien.nytimesnews.R;
import com.internship.thien.nytimesnews.data.model.News;

import org.parceler.Parcels;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailNewsActivity extends AppCompatActivity implements DetailNewsView {

    String mUrl;

    @BindColor(R.color.dark_black)
    int dark_black;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        ButterKnife.bind(this);

        setupView();

    }

    @Override
    public void setupView() {
        String url;
        News news = Parcels.unwrap(getIntent().getParcelableExtra("news"));

        if (news != null) url = news.getWebUrl();
        else url = "https://www.nytimes.com/";

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

        builder.setToolbarColor(ContextCompat.getColor(this, R.color.dark_black));
        CustomTabsIntent customTabsIntent = builder.build();

        customTabsIntent.launchUrl(this, Uri.parse(url));
    }


}
