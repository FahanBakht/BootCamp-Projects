package com.example.farhan.volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView showJsonResponse;
    Button callHttpRequestGet;
    Button callHttpRequestPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        String url = "https://jsonplaceholder.typicode.com/posts/1";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                showJsonResponse.setText("Response: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }

    private void httpRequestPost() {

        JSONObject obj = new JSONObject();
        try {
            obj.put("userId", "9");
            obj.put("title", "Sample");
            obj.put("body", "Practice of Volley");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url = "https://jsonplaceholder.typicode.com/posts";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                showJsonResponse.setText("Response: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.e("TAG", "getHeaders: Header Runs");
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/json; charset=UTF-8");
                return params;
            }
        };

        queue.add(jsonObjectRequest);

    }

}
