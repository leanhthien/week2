package com.internship.thien.nytimesnews.search.model;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.internship.thien.nytimesnews.BuildConfig;
import com.internship.thien.nytimesnews.R;
import com.internship.thien.nytimesnews.api.APIService;
import com.internship.thien.nytimesnews.data.model.News;
import com.internship.thien.nytimesnews.data.model.Result;
import com.internship.thien.nytimesnews.helper.DBHelper;
import com.internship.thien.nytimesnews.util.APIUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepositoryImpl implements DataRepository {

    private APIService mService;

    public DataRepositoryImpl() {
        mService = APIUtils.getAPIService();
    }

    @Override
    public void getDataFromNetWork(final DataListener listener, Map<String, String> query) {

        Call<Result> call = mService.searchByHand(query, BuildConfig.API_KEY);

        Log.d("Query call API", call.request().toString());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@Nullable Call<Result> call,@Nullable Response<Result> response) {

                assert response != null;
                if (response.isSuccessful()) {
                    Log.d("Response",response.toString());
                    List<News> news = Objects.requireNonNull(response.body()).getResponse().getNews();
                    if (news != null)
                        listener.onResponse(news);
                    else {
                        listener.onError("Nothing return!");
                    }
                }
                else {
                    Log.d("Response","Fail");
                    listener.onError("Response fail!");
                }

            }

            @Override
            public void onFailure(@Nullable Call<Result> call,@Nullable Throwable t) {
                assert t != null;
                listener.onError(t.getMessage());
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void getDataFromSettings(Context context, View view) {
        String sort;
        String new_desk = "new_desk:(";
        String empty_desk = "new_desk:()";
        String date;
        String date_formatted;

        //DBHelper db = new DBHelper();

        EditText ed_date = view.findViewById(R.id.edit_date);
        Spinner spinner = view.findViewById(R.id.spn_sort);
        CheckBox check_0 = view.findViewById(R.id.cb_arts);
        CheckBox check_1 = view.findViewById(R.id.cb_fashion);
        CheckBox check_2 = view.findViewById(R.id.cb_sports);

        date = ed_date.getText().toString();
        String[] separated = date.split("/");

        date_formatted = separated[2] + separated[1] + separated[0];
        Log.d("Date", date_formatted);

        sort = upperFirstLetter(spinner.getSelectedItem().toString());
        Log.d("Spinner", sort);

        if (check_0.isChecked())
            new_desk += "\"Arts\" ";
        if (check_1.isChecked())
            new_desk += "\"Fashion & Style\" ";
        if (check_2.isChecked())
            new_desk += "\"Sports\" ";
        new_desk += ")";
        Log.d("Check box", new_desk);

        Map<String, String> data = new HashMap<>();
        data.put("date", date_formatted);
        data.put("sort", sort);
        if(!new_desk.equals(empty_desk))
            data.put("new_desk", new_desk);

        DBHelper.newInstance().setDB(context, data);

    }

    @Override
    public String setupDateString(int day, int month, int year) {
        String mDay;
        String mMonth;
        if (day < 10)
            mDay =  "0" + String.valueOf(day);
        else
            mDay =  String.valueOf(day);
        if (month < 10)
            mMonth =  "0" + String.valueOf(month);
        else
            mMonth =  String.valueOf(month);

        return mDay + "/" + mMonth + "/" + String.valueOf(year);
    }

    static private String upperFirstLetter (String data) {
        String firstLetter = data.substring(0,1).toLowerCase();
        String restLetters = data.substring(1);
        return firstLetter + restLetters;
    }
}
