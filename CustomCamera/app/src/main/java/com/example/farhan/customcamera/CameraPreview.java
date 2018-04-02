package com.example.farhan.customcamera;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

import static android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private Camera camera;
    private SurfaceHolder mSurfaceHolder;
    private Context context;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.context = context;
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
            setCameraDisplayOrientation((Activity) context, camera);
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
            setCameraDisplayOrientation((Activity) context, camera);
            camera.startPreview();
        } catch (IOException e) {
            Log.e("tag", "Error setting camera preview: " + e.getMessage());
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            camera.stopPreview();
            Log.e("TAG", "surfaceDestroyed: SurfaceDestroyed ");
        }
    }

    private void params(Camera camera) {

        Camera.Parameters parameters = camera.getParameters();

        parameters.setFlashMode(parameters.FLASH_MODE_AUTO);
        parameters.setJpegQuality(100);
        parameters.setRotation(90);
        List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
        Camera.Size size = sizes.get(0);
        for (int i = 0; i < sizes.size(); i++) {
            if (sizes.get(i).width > size.width)
                size = sizes.get(i);
        }
        parameters.setPictureSize(size.width, size.height);
        camera.setParameters(parameters);

    }

    public static void setCameraDisplayOrientation(Activity activity, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(CAMERA_FACING_BACK, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        result = (info.orientation - degrees + 360) % 360;
        camera.setDisplayOrientation(result);
    }
}

