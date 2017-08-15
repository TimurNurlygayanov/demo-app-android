package com.xwizard.magicpic;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Force horizontal orientation for the screen:
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Keep screen active forever:
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.first_screen);
        final ClearWhite drawImage = (ClearWhite) findViewById(R.id.white_screen);
        drawImage.setOnTouchListener(drawImage);
        drawImage.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        drawImage.preparePic();

        // MySwView sw = new MySwView(this);
        // setContentView(sw);
        // MySwView sw = (MySwView) findViewById(R.id.white_experimental);
        // sw.setZOrderOnTop(true);
        // sw.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        //setContentView(new MySwView(this));

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);


    }

}
