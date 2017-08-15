package com.xwizard.magicpic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Random;


class MySwView extends SurfaceView implements SurfaceHolder.Callback {

    // TutorialThread thread = null;
    SurfaceHolder surfaceHolder;
    volatile boolean running = false;

    private Canvas canvas_white = new Canvas();
    private Bitmap bmp;
    Path path = new Path();
    private Bitmap tempBitmap;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean isInitiated = false;

    Random random;

    public MySwView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        //paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setStyle(Paint.Style.STROKE);
        // paint.setStrokeJoin(Paint.Join.ROUND);
        // paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAlpha(100);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(40);
        //paint.setColor(Color.TRANSPARENT);
        paint.setColor(Color.RED);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        setFocusable(true);
        // thread = new TutorialThread(getHolder(), this);
    }

    // We need to define this method to properly inherit ImageView class:
    public MySwView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // We need to define this method to properly inherit ImageView class:
    public MySwView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initPic(Canvas canvas) {
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.white_fon);

        tempBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.RGB_565);
        canvas = new Canvas(tempBitmap);
        canvas.drawBitmap(bmp, 0, 0, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Canvas canvas = surfaceHolder.lockCanvas();

        if (isInitiated == false) {
            initPic(canvas);
            isInitiated = true;

            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.white_fon);
            // canvas.drawColor(Color.GREEN, PorterDuff.Mode.ADD);
            canvas.drawBitmap(bmp, 0, 0, paint);
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            path.moveTo(event.getX(), event.getY());
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            path.lineTo(event.getX(), event.getY());
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            path.lineTo(event.getX(), event.getY());
        }

        Log.d("AAA", "aaaaaa");

        if (path != null) {
            //Canvas canvas = surfaceHolder.lockCanvas();
            // canvas.drawColor(Color.WHITE, PorterDuff.Mode.XOR);
            canvas.drawBitmap(bmp, 0, 0, paint);
            canvas.drawPath(path, paint);

            Log.d("AAA", "fff");
            //surfaceHolder.unlockCanvasAndPost(canvas);
        }

        surfaceHolder.unlockCanvasAndPost(canvas);

        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

/*
    class TutorialThread extends Thread {
        private SurfaceHolder _surfaceHolder;
        private MySwView _panel;
        private boolean _run = false;

        public TutorialThread(SurfaceHolder surfaceHolder, MySwView panel) {
            _surfaceHolder = surfaceHolder;
            _panel = panel;
        }

        public void setRunning(boolean run) {
            _run = run;
        }

        @Override
        public void run() {
            Canvas c;
            while (_run) {
                c = null;
                try {
                    c = _surfaceHolder.lockCanvas(null);
                    synchronized (_surfaceHolder) {
                        _panel.initPic(c);
                    }
                } finally {
                    if (c != null) {
                        _surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
    }
    */
}