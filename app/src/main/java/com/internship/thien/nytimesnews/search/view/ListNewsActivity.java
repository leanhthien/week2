package com.internship.thien.nytimesnews.search.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.internship.thien.nytimesnews.fragment.DatePickerFragment;
import com.internship.thien.nytimesnews.search.model.DataRepository;
import com.internship.thien.nytimesnews.search.model.DataRepositoryImpl;
import com.internship.thien.nytimesnews.search.presenter.ListNewsPresenter;
import com.internship.thien.nytimesnews.search.presenter.ListNewsPresenterImpl;

import java.text.DateFormat;
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
    }

    @Override
    public void showError(String error) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Something went wrong!\n" + error);

        alert.setPositiveButton("Ok", (dialog, whichButton) -> {

        });

        alert.setNegativeButton("Cancel", (dialog, whichButton) -> {

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

    void setupView() {
        newsAdapter = new NewsAdapter(this);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNews.setLayoutManager(layoutManager);
        rvNews.setAdapter(newsAdapter);
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }


}
