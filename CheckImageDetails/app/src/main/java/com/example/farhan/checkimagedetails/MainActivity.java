package com.example.farhan.checkimagedetails;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.imageView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imgView.setImageURI(data.getData());
            Log.e("TAG", "onActivityResult: " + data.getData());

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                InputStream fileInputStream = getApplicationContext().getContentResolver().openInputStream(data.getData());

                int w = bitmap.getWidth();
                int h = bitmap.getHeight();
                int dataSize = fileInputStream.available();
                long size = dataSize / 1024;

                Log.e("TAG", "Width: " + w + " Height: " + h + " Size: " + size);

                Toast.makeText(this, w + "x" + h, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Size: " + size + "kb", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
