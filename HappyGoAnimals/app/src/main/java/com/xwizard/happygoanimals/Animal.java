package com.xwizard.happygoanimals;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by qawizard on 21.05.17.
 */


public class Animal extends android.support.v7.widget.AppCompatImageView {

    public float relativeX;
    public float relativeY;
    public float initialX;
    public float initialY;

    public boolean isReady;

    // We need to define this method to properly inherit ImageView class:
    public Animal(Context context) {
        super(context);

        this.isReady = false;
    }

    // We need to define this method to properly inherit ImageView class:
    public Animal(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // We need to define this method to properly inherit ImageView class:
    public Animal(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isClicked(float X, float Y) {

        if (X > this.getX() && X < this.getX() + this.getWidth() &&
                Y > this.getY() && Y < this.getY() + this.getHeight()) {
            this.relativeX = X - this.getX();
            this.relativeY = Y - this.getY();
            return true;
        }

        return false;
    }

    public void moveAnimal(float X, float Y) {
        this.setX(X - this.relativeX);
        this.setY(Y - this.relativeY);
    }

    public void activate() {
        this.initialX = this.getX();
        this.initialY = this.getY();
    }
}

