package com.xwizard.magicpic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by qawizard on 30.05.17.
 */

public class ClearWhite extends android.support.v7.widget.AppCompatImageView implements View.OnTouchListener {

    private Canvas canvas_white = new Canvas();
    private Bitmap bmp;
    private Bitmap tempBitmap;
    private Paint paint = new Paint();
    private boolean isInitiated = false;

    float x, y;

    public ClearWhite(Context context) {
        super(context);
    }

    // We need to define this method to properly inherit ImageView class:
    public ClearWhite(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // We need to define this method to properly inherit ImageView class:
    public ClearWhite(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initPic() {
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        //paint.setAlpha(100);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(40);
        paint.setColor(Color.TRANSPARENT);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.white_fon);

        tempBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_4444);
        canvas_white = new Canvas(tempBitmap);
        canvas_white.drawBitmap(bmp, 0, 0, null);
    }

    public void preparePic() {
        Bitmap b = Bitmap.createBitmap(1280, 720, Bitmap.Config.ARGB_4444);
        Canvas c = new Canvas(b);
        c.drawColor(Color.WHITE);
        this.setImageBitmap(b);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float x_pos = event.getX();
        float y_pos = event.getY();

        if (isInitiated == false) {
            initPic();
            isInitiated = true;

            canvas_white.drawColor(Color.WHITE, PorterDuff.Mode.XOR);
        }

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                canvas_white.drawPoint(event.getX(), event.getY(), paint);

                x = x_pos;
                y = y_pos;

                //canvas_white.drawCircle(event.getX(), event.getY(), 20, paint);
                this.setImageBitmap(tempBitmap);

                break;
            case MotionEvent.ACTION_MOVE:

                canvas_white.drawLine(x, y, x_pos, y_pos, paint);

                canvas_white.drawPoint(event.getX(), event.getY(), paint);

                x = x_pos;
                y = y_pos;

                this.setImageBitmap(tempBitmap);

                break;
            default:
                return false;
        }
        return true;
    }
}
