package com.internship.thien.nytimesnews.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    static Context context;

    public static boolean isOnline(Context context) {
        NetworkUtils.context = context;
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
            return  netInfo!= null && netInfo.isConnectedOrConnecting();
        }
        return false;
    }
}
