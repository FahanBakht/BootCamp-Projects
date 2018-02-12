package com.example.farhan.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    APIService apiService;

    TextView showJsonResponse;
    Button callHttpRequestGet;
    Button callHttpRequestPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = ApiUtils.getApiService();

        showJsonResponse = findViewById(R.id.showJsonResponse);

        callHttpRequestGet = findViewById(R.id.httpRequestGet);
        callHttpRequestGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallHttpRequestGet();
            }
        });

        callHttpRequestPost = findViewById(R.id.httpRequestPost);
        callHttpRequestPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallHttpRequestPost();
            }
        });

    }

    private void CallHttpRequestPost() {
        User userObj = new User(9,"abc","xyz");

        apiService.postUser(userObj).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                showJsonResponse.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CallHttpRequestGet() {
        apiService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                showJsonResponse.setText(response.body().toString());

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Get User by giving id..
        /*apiService.getUsers("2").enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                showJsonResponse.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
