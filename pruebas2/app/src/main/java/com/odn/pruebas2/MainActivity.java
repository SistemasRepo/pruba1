package com.odn.pruebas2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.security.Policy;

public class MainActivity extends AppCompatActivity {


    Button button,btn_tabhost;
    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash;
    Camera.Parameters params;
    private Lampara lamp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.lampara);
        btn_tabhost=(Button)findViewById(R.id.but_tabhost);


            hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
            if (!hasFlash) {
                Toast.makeText(this, "no acepta lampara", Toast.LENGTH_LONG);
                return;
            }
            getCamera();




            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFlashOn) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {encenderAndroid6(0);}
                        else{turnOffFlash();}
                        button.setText("ON");
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {encenderAndroid6(1);}
                        else {turnOnFlash();}
                        button.setText("OFF");
                    }
                }
            });



        btn_tabhost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),Main2Activity.class);
                startActivity(i);
            }
        });




      /*  final ImageView ball = (ImageView) findViewById(R.id.ball);
        ball.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    AnimationDrawable animation = (AnimationDrawable) ball.getDrawable();
                    animation.stop();
                    animation.selectDrawable(0);
                    animation.start();
                    return true;
                }
                return false;
            }
        });


        ImageView img = (ImageView)findViewById(R.id.ball);
        img.setBackgroundResource(R.drawable.anim_load);

        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

        // Start the animation (looped playback by default).
        frameAnimation.start();


*/





      ///codigo necesario para android 7


        final ImageView ball = (ImageView) findViewById(R.id.zoom);
        ball.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    AnimationDrawable animation = (AnimationDrawable) ball.getDrawable();
                    animation.stop();
                    animation.selectDrawable(0);
                    animation.start();
                    return true;
                }
                return false;
            }
        });



    }


public void encenderAndroid6(int onOff){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = null; // Usually back camera is at 0 position.
        try {
            cameraId = camManager.getCameraIdList()[0];

            if(onOff==1){ camManager.setTorchMode(cameraId, true);isFlashOn = true;}   //Turn ON
            if(onOff==0){camManager.setTorchMode(cameraId, false);isFlashOn = false;}
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }
}



    private void getCamera() {

        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            }catch (Exception e) {

            }
        }

    }

    private void turnOnFlash() {

        if(!isFlashOn) {
            if(camera == null || params == null) {
                return;
            }

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }

    }

    private void turnOffFlash() {

        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // on pause turn off the flash
        turnOffFlash();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // on resume turn on the flash
  //      if(hasFlash)
    //        turnOnFlash();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // on starting the app get the camera params
        getCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // on stop release the camera
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }







}
