package com.internship.thien.nytimesnews.search.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.internship.thien.nytimesnews.R;
import com.internship.thien.nytimesnews.adapter.NewsAdapter;
import com.internship.thien.nytimesnews.data.model.News;
import com.internship.thien.nytimesnews.search.model.DataRepository;
import com.internship.thien.nytimesnews.search.model.DataRepositoryImpl;
import com.internship.thien.nytimesnews.search.presenter.ListNewsPresenter;
import com.internship.thien.nytimesnews.search.presenter.ListNewsPresenterImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListNewsActivity extends AppCompatActivity implements ListNewsView {

    NewsAdapter newsAdapter;
    ListNewsPresenter presenter;

    @BindView(R.id.rv_news)
    RecyclerView rvNews;
    @BindView(R.id.action_search)
    MenuItem searchItem;
    @BindView(R.id.action_more)
    MenuItem moreItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);
        ButterKnife.bind(this);

        Map<String, String> data = new HashMap<>();
        data.put("begin_date", "20160112");

        setupView();
        DataRepository repository = new DataRepositoryImpl();
        presenter = new ListNewsPresenterImpl(this, repository);
        presenter.getNews(this, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Query here

                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showListNews(List<News> news) {
        newsAdapter.setData(news);
    }

    @Override
    public void showError(String error) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("There's something went wrong!\n" + error);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }

        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }

        });

        alert.show();

    }

    void setupView() {
        newsAdapter = new NewsAdapter(this);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNews.setLayoutManager(layoutManager);
        rvNews.setAdapter(newsAdapter);
    }
}
