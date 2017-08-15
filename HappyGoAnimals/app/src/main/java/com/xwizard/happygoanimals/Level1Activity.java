package com.xwizard.happygoanimals;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

/**
 * Created by qawizard on 21.05.17.
 */

public class Level1Activity extends Activity implements Animal.OnTouchListener {

    private Animal firstAnimal;
    private Animal secondAnimal;
    private ImageView firstAnimalWhite;
    private ImageView secondAnimalWhite;

    private Animal activeImage;
    private ImageView background;
    private ViewGroup mMoveLayout;
    private int readyAnimals = 0;

    private float desiredX = 0;
    private float desiredY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.level1);

        // Force horizontal orientation for the screen:
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Keep screen active forever:
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mMoveLayout = (ViewGroup) findViewById(R.id.main_view);
        background = (ImageView) mMoveLayout.findViewById(R.id.imageView);

        firstAnimal = (Animal) mMoveLayout.findViewById(R.id.firstAnimal);
        secondAnimal = (Animal) mMoveLayout.findViewById(R.id.secondAnimal);

        firstAnimalWhite = (ImageView) mMoveLayout.findViewById(R.id.firstAnimalWhite);
        secondAnimalWhite = (ImageView) mMoveLayout.findViewById(R.id.secondAnimalWhite);

        firstAnimal.setOnTouchListener(this);
        secondAnimal.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        // Get position of the user's finger:
        final float X = event.getRawX();
        final float Y = event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                // If user clicked on the animal, mark this animal as active and remember it's
                // original position to move it back if user won't move this animal to the right
                // position with his first attempt:
                if (firstAnimal.isClicked(X, Y) && firstAnimal.isReady == false) {
                    activeImage = firstAnimal;
                    activeImage.activate();
                    desiredX = firstAnimalWhite.getX();
                    desiredY = firstAnimalWhite.getY();
                }
                if (secondAnimal.isClicked(X, Y) && secondAnimal.isReady == false) {
                    activeImage = secondAnimal;
                    activeImage.activate();
                    desiredX = secondAnimalWhite.getX();
                    desiredY = secondAnimalWhite.getY();
                }

                break;
            case MotionEvent.ACTION_MOVE:

                // If animal is not on the right position move the animal to the same point where
                // the user finger is located:
                if (activeImage.isReady == false) {
                    activeImage.moveAnimal(X, Y);
                }

                // If animal is near to the righ position move this animal to the
                // right position and deny any action with this animal for user:
                if (Math.abs(X - activeImage.relativeX - desiredX) < activeImage.getWidth()/5 &&
                        Math.abs(Y - activeImage.relativeY - desiredY) < activeImage.getHeight()/5) {
                    activeImage.isReady = true;
                    activeImage.setX(desiredX);
                    activeImage.setY(desiredY);
                }

                break;

            case MotionEvent.ACTION_UP:

                if (activeImage.isReady == false) {

                    // Move animal back to the initial position if it wasn't moved
                    // to the right position by user:
                    view.animate().x(activeImage.initialX).y(activeImage.initialY).setDuration(1000).start();

                } else {

                    // Increase the number of animals which locate on the right positions:
                    this.readyAnimals += 1;

                    // If all animals are ready it means that level is completed:
                    if (this.readyAnimals >= 2) {

                        // Create window with congratulations:
                        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupView = layoutInflater.inflate(R.layout.finish_level, null);
                        final PopupWindow popupWindow = new PopupWindow(popupView,
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);

                        // Show window with congratulations in the center of the screen:
                        popupWindow.showAtLocation(this.findViewById(R.id.main_view), Gravity.CENTER, 0, 0);
                    }
                }

                break;
        }

        return true;
    }
}
