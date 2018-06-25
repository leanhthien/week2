package com.internship.thien.nytimesnews.search.view;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.afollestad.materialdialogs.MaterialDialog;
import com.internship.thien.nytimesnews.R;
import com.internship.thien.nytimesnews.adapter.NewsAdapter;
import com.internship.thien.nytimesnews.data.model.Meta;
import com.internship.thien.nytimesnews.data.model.News;
import com.internship.thien.nytimesnews.fragment.DatePickerFragment;
import com.internship.thien.nytimesnews.adapter.EndlessRecyclerViewScrollListener;
import com.internship.thien.nytimesnews.search.model.DataRepository;
import com.internship.thien.nytimesnews.search.model.DataRepositoryImpl;
import com.internship.thien.nytimesnews.search.presenter.ListNewsPresenter;
import com.internship.thien.nytimesnews.search.presenter.ListNewsPresenterImpl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListNewsActivity extends AppCompatActivity implements ListNewsView, DatePickerDialog.OnDateSetListener {

    NewsAdapter newsAdapter;
    ListNewsPresenter presenter;
    EditText editDate;
    Meta meta;
    private EndlessRecyclerViewScrollListener scrollListener;
    String input= "";

    @BindView(R.id.rv_news)
    @Nullable
    RecyclerView rvNews;
    @BindView(R.id.progressBar)
    @Nullable
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome_layout);

        //Display the logo during 5 seconds,
        new CountDownTimer(5000,1000){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTick(long millisUntilFinished){}

            @Override
            public void onFinish(){
                //Set the Content of Main activity
                ListNewsActivity.this.setContentView(R.layout.activity_list_news);

                ButterKnife.bind( ListNewsActivity.this);

                setupView();
                DataRepository repository = new DataRepositoryImpl();
                presenter = new ListNewsPresenterImpl( ListNewsActivity.this, repository);
                defaultQuery();
            }
        }.start();

    }

    @Override
    public void setupView() {
        newsAdapter = new NewsAdapter(this);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        Objects.requireNonNull(rvNews).setLayoutManager(gridLayoutManager);

        rvNews.setAdapter(newsAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                int currentPage = presenter.setupPage(meta);
                if (currentPage != -1)
                    loadNextDataFromApi(currentPage);

            }
        };
        rvNews.addOnScrollListener(scrollListener);
    }

    @Override
    public void defaultQuery() {

        String defaultQuery = "fashion";
        Map<String, String> data = presenter.setupQuery(getApplicationContext(), defaultQuery);
        input = defaultQuery;

        scrollListener.resetState();
        presenter.getNews(getApplicationContext(), data, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                searchBarListener(item);
                return true;
            case R.id.action_settings:
                settingsBarListener();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showListNews(List<News> news, int type, Meta meta) {

        newsAdapter.setData(news, type);
        newsAdapter.setListener(this::openWebsite);
        this.meta = meta;
    }

    @Override
    public void searchBarListener(MenuItem searchItem) {

        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Map<String, String> data = presenter.setupQuery(getApplicationContext(), query);

                input = query;

                scrollListener.resetState();
                presenter.getNews(getApplicationContext(), data, 0);
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void settingsBarListener() {

        final boolean wrapInScrollView = true;

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("Settings")
                .customView(R.layout.modal_settings, wrapInScrollView)
                .positiveText(R.string.positive_button)
                .negativeText(R.string.negative_button)
                .onPositive((dialog, which) -> {
                    Log.d("Action","Saved");
                    presenter.getSettings(this,Objects.requireNonNull(dialog).getCustomView());
                })
                .onNegative((dialog, which) -> {
                    Log.d("Action","Cancel");
                    presenter.resetSettings(this);
                });

        MaterialDialog dialog = builder.build();
        View view = dialog.getCustomView();
        dialog.show();
        setupSettings(view);

    }

    @Override
    public void setupSettings(View view) {

        final Calendar c = Calendar.getInstance();

        String date = presenter.setupDate(  c.get(Calendar.DAY_OF_MONTH),
                                            c.get(Calendar.MONTH),
                                            c.get(Calendar.YEAR));

        editDate = view.findViewById(R.id.edit_date);
        editDate.setText(date);
        editDate.setInputType(0);
        editDate.setOnClickListener(this::showDatePickerDialog);

    }

    @Override
    public void openWebsite(News news) {

        String url;
        CustomTabsIntent.Builder  builder = new CustomTabsIntent.Builder();

        builder.setToolbarColor(ContextCompat.getColor(this, R.color.dark_black));
        builder.addDefaultShareMenuItem();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_name);

        if (news != null && news.getWebUrl() != null)
            url = news.getWebUrl();
        else
            url = "https://www.nytimes.com/";

        int requestCode = 100;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, url);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setActionButton(bitmap, "Share Link", pendingIntent, true);

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    @Override
    public void showLoading() {
        Objects.requireNonNull(progressBar).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (Objects.requireNonNull(progressBar).isShown()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String error) {

        hideLoading();

        Log.e("Error detail", error);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Something went wrong!\n" + error);

        alert.setPositiveButton("Ok", (dialog, whichButton) -> {
            //TO DO
        });

        alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
            //TO DO
        });

        alert.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        editDate.setText(presenter.setupDate(dayOfMonth, monthOfYear, year));

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Map<String, String> data;
        if (!input.equals("")) {

            setupView();
            data = presenter.setupQuery(getApplicationContext(), input);

            scrollListener.resetState();
            presenter.getNews(getApplicationContext(), data, 0);
        }

    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    public void loadNextDataFromApi(int offset) {

        Log.d("Next page", String.valueOf(offset));

        Map<String, String> data = presenter.setupQuery(getApplicationContext(), input);
        data.put("page", String.valueOf(offset));
        presenter.getNews(getApplicationContext(), data, 1);

    }

}
