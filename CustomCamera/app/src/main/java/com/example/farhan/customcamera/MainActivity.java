package com.example.farhan.customcamera;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK;
import static android.os.Environment.DIRECTORY_PICTURES;

public class MainActivity extends AppCompatActivity {

    private FrameLayout camera_Preview_Frame;
    private Camera camera;
    private CameraPreview cameraPreview;
    private static boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera_Preview_Frame = findViewById(R.id.camera_preview);
        FloatingActionButton fab_Capture_Img = findViewById(R.id.fab_Capture);

        initCamera();

        fab_Capture_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check) {
                    camera.autoFocus(new Camera.AutoFocusCallback() {
                        @Override
                        public void onAutoFocus(boolean success, Camera camera) {
                            Log.e("TAG", "onAutoFocus: Auto Focus CallBAck Runs" );
                            camera.takePicture(myShutterCallback, null, pictureCallback);
                        }
                    });
                    check = false;
                }
            }
        });

    }

    private void initCamera() {
        if (checkCameraHardware()) {
            camera = getCameraInstance();

            cameraPreview = new CameraPreview(this, camera);
            camera_Preview_Frame.addView(cameraPreview);
            setFocus();
            check = true;

            camera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
                    Log.e("TAG", "onPreviewFrame: onPreview Runs");
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Device not support camera feature", Toast.LENGTH_SHORT).show();
        }
    }

    public void setFocus() {
        Camera.Parameters params = camera.getParameters();
        if (params.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            Log.e("TAG", "setFocus: Continuous Focus Mode True");
        } else {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            Log.e("TAG", "setFocus: Continuous Focus Mode False,,,, Running Auto Focus");
        }
        camera.setParameters(params);
    }

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES) + "/" + System.currentTimeMillis() + ".jpg");
            if (file == null) {
                Log.e("tag", "Error creating media file, check storage permissions: ");
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.startPreview();
            check = true;
        }
    };

    Camera.ShutterCallback myShutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {

        }
    };

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(CAMERA_FACING_BACK);
        } catch (Exception e) {
            Log.e("TAG", "getCameraInstance: No Camera Found");
        }
        return c;
    }

    private boolean checkCameraHardware() {

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("TAG", "onRestart: onRestart");
        initCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null) {
            camera_Preview_Frame.removeAllViews();
            camera.release();
            camera = null;
            Log.e("TAG", "onPause: onPause");
        }
    }
}
