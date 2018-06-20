package com.internship.thien.nytimesnews.util;

import com.internship.thien.nytimesnews.api.APIService;

public class APIUtils {

    public static final String BASE_URL = "https://api.nytimes.com";

    private APIUtils() {
    }

    public static APIService getAPIService() {

        return RetrofitUtils.getClient(BASE_URL).create(APIService.class);
    }
}