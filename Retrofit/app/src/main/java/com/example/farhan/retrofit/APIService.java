package com.example.farhan.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface APIService {

    @GET("posts")
    Call<List<User>> getUsers();

    @GET("posts/")
    Call<List<User>> getUsers(@Query("id") String userId);

    @POST("posts/")
    Call<User> postUser(@Body User user);
}
