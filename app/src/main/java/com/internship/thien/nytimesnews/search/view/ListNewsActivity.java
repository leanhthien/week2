package com.internship.thien.nytimesnews.search.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;


import com.afollestad.materialdialogs.MaterialDialog;
import com.internship.thien.nytimesnews.R;
import com.internship.thien.nytimesnews.adapter.NewsAdapter;
import com.internship.thien.nytimesnews.data.model.News;
import com.internship.thien.nytimesnews.detail.View.DetailNewsActivity;
import com.internship.thien.nytimesnews.fragment.DatePickerFragment;
import com.internship.thien.nytimesnews.adapter.EndlessRecyclerViewScrollListener;
import com.internship.thien.nytimesnews.search.model.DataRepository;
import com.internship.thien.nytimesnews.search.model.DataRepositoryImpl;
import com.internship.thien.nytimesnews.search.presenter.ListNewsPresenter;
import com.internship.thien.nytimesnews.search.presenter.ListNewsPresenterImpl;

import org.parceler.Parcels;

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
    private EndlessRecyclerViewScrollListener scrollListener;
    String input= "";

    @BindView(R.id.rv_news)
    RecyclerView rvNews;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);
        ButterKnife.bind(this);

        setupView();
        DataRepository repository = new DataRepositoryImpl();
        presenter = new ListNewsPresenterImpl(this, repository);

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
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showListNews(List<News> news) {
        newsAdapter.setData(news);
        newsAdapter.setListener(new NewsAdapter.NewsItemListener() {
            @Override
            public void onNewsClick(News news) {
                Intent intent = new Intent(ListNewsActivity.this, DetailNewsActivity.class);
                intent.putExtra("news", Parcels.wrap(news));
                startActivity(intent);
            }
        });
    }

    @Override
    public void showError(String error) {

        hideLoading();

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
    public void searchBarListener(MenuItem searchItem) {

        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                Map<String, String> data = presenter.setupQuery(getApplicationContext(), query);
                input = query;
                presenter.getNews(getApplicationContext(), data);
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
                .customView(R.layout.modal_settings, wrapInScrollView)
                .positiveText(R.string.positive_button)
                .negativeText(R.string.negative_button)
                .onPositive((dialog, which) -> {
                    // TODO
                    Log.d("Action","Saved");
                    presenter.getSettings(this,Objects.requireNonNull(dialog).getCustomView());
                })
                .onNegative((dialog, which) -> {
                    // TODO
                    Log.d("Action","Cancel");
                    presenter.resetSettings(this);

                });

        MaterialDialog dialog = builder.build();
        View view = dialog.getCustomView();
        dialog.show();
        setupSettings(view);

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
    public void setupView() {
        newsAdapter = new NewsAdapter(this);

        //StaggeredGridLayout
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvNews.setLayoutManager(gridLayoutManager);

        rvNews.setAdapter(newsAdapter);

        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvNews.addOnScrollListener(scrollListener);
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {

        if (!input.equals("")) {
            Map<String, String> data = presenter.setupQuery(getApplicationContext(), input);
            data.put("page",String.valueOf(offset));
            presenter.getNews(getApplicationContext(), data);

        }
        else {
            showError("No query was found!");
        }

    }
}
