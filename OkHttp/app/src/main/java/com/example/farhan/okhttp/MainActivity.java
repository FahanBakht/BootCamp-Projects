package com.example.farhan.okhttp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView showJsonResponse;
    Button callHttpRequestGet;
    Button callHttpRequestPost;
    OkHttpClient client;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler(Looper.getMainLooper());
        client = new OkHttpClient();

        showJsonResponse = findViewById(R.id.tvShowJsonResponse);

        callHttpRequestGet = findViewById(R.id.httpRequestGet);
        callHttpRequestGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                httpRequestGet();
            }
        });

        callHttpRequestPost = findViewById(R.id.httpRequestPost);
        callHttpRequestPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                httpRequestPost();
            }
        });
    }

    private void httpRequestGet() {

        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts/1")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    updateUI(response.body().string());
                }
            }
        });
    }

    private void httpRequestPost() {

        RequestBody formBody = new FormBody.Builder()
                .add("userId", "9")
                .add("title", "Demo OkHttp")
                .add("body", "Practice of OkHttp")
                .build();

        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .post(formBody) // PUT here.
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                updateUI(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    updateUI(response.body().string());
                }
            }
        });
    }

    void updateUI(final String string) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                showJsonResponse.setText(string);
            }
        });
    }

}
