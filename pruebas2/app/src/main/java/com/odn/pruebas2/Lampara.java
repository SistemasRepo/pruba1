package com.odn.pruebas2;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.Log;

/**
 * Created by EDGAR ARANA on 15/02/2018.
 */

public class Lampara {
        private static final String TAG = Lampara.class.getSimpleName();
        private Camera mCamera;
        private Camera.Parameters parameters;

        private CameraManager camManager;
        private Context context;

        public Lampara(Context context) {
            this.context = context;
        }

        public void encender() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                    String cameraId = null; // Usually front camera is at 0 position.
                    if (camManager != null) {
                        cameraId = camManager.getCameraIdList()[0];
                        camManager.setTorchMode(cameraId, true);
                    }
                } catch (CameraAccessException e) {
                    Log.e(TAG, e.toString());
                }
            } else {
                mCamera = Camera.open();
                parameters = mCamera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
                mCamera.startPreview();
            }
        }

        public void apagar() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    String cameraId;
                    camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                    if (camManager != null) {
                        cameraId = camManager.getCameraIdList()[0]; // Usually front camera is at 0 position.
                        camManager.setTorchMode(cameraId, false);
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            } else {
                mCamera = Camera.open();
                parameters = mCamera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
                mCamera.stopPreview();
            }
        }







    }





