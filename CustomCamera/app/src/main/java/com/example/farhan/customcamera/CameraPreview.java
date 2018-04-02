package com.example.farhan.customcamera;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.IOException;
import java.util.List;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private Camera camera;
    private SurfaceHolder mSurfaceHolder;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (camera == null) {
            return;
        }

        try {
            camera.setPreviewDisplay(holder);
            params(camera);
            camera.startPreview();
        } catch (IOException e) {
            Log.e("tag", "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (holder.getSurface() == null) {
            return;
        }

        try {
            camera.stopPreview();
        } catch (Exception e) {
            Log.e("tag", "Error setting camera stop: " + e.getMessage());
        }

        try {
            camera.setPreviewDisplay(holder);
            params(camera);
            camera.startPreview();
        } catch (IOException e) {
            Log.e("tag", "Error setting camera preview: " + e.getMessage());
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void params(Camera camera) {

        Camera.Parameters parameters = camera.getParameters();

        parameters.setFlashMode(parameters.FLASH_MODE_AUTO);
        parameters.setJpegQuality(100);
        parameters.setRotation(90);
        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (display.getRotation() == Surface.ROTATION_0) {
            camera.setDisplayOrientation(90);
        } else if (display.getRotation() == Surface.ROTATION_270) {
            camera.setDisplayOrientation(180);
        }
        List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
        Camera.Size size = sizes.get(0);
        for (int i = 0; i < sizes.size(); i++) {
            if (sizes.get(i).width > size.width)
                size = sizes.get(i);
        }
        parameters.setPictureSize(size.width, size.height);
        camera.setParameters(parameters);

    }

}

