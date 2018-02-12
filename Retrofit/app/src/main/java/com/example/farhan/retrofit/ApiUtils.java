package com.example.farhan.retrofit;

/**
 * Created by Farhan on 2/12/2018.
 */

public class ApiUtils {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public static APIService getApiService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
