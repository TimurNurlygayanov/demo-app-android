package xwizard.funnypuzzles;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.my.target.ads.InterstitialAd;

public class Level1_Step1 extends Level1 {

    private com.google.android.gms.ads.InterstitialAd mInterstitialAd;

    private InterstitialAd ad_fullscreen_mytarget;
    private boolean ad_fullscreen_mytarget_loaded = false;

    private boolean ad_showed = false;

    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_level1__step1);

        // Force horizontal orientation for the screen:
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Keep screen active forever:
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        initAD();

        count = getIntent().getIntExtra("count", 1);
    }

    @Override
    public void onBackPressed() {
        showAD(null);
        super.onBackPressed();
    }

    public void gotoMenu(View view) {
        Intent intent = new Intent(this, MainMenu.class);

        showAD(intent);
    }

    private void initAD() {

        // MyTarget:
        InterstitialAd.setDebugMode(true);
        ad_fullscreen_mytarget = new InterstitialAd(144012, this);

        ad_fullscreen_mytarget.setListener(new InterstitialAd.InterstitialAdListener()
        {
            @Override
            public void onLoad(InterstitialAd ad_fullscreen_mytarget)
            {
                ad_fullscreen_mytarget_loaded = true;
            }

            @Override
            public void onNoAd(String reason, InterstitialAd ad)
            {
                Log.e("fffffff2", "onNoAd() called with: reason = [" + reason + "], ad = [" + ad + "]");
            }

            @Override
            public void onClick(InterstitialAd ad)
            {
                ad_showed = true;
            }
            @Override
            public void onDisplay(InterstitialAd ad)
            {
            }

            @Override
            public void onDismiss(InterstitialAd ad)
            {
            }
            @Override
            public void onVideoCompleted(InterstitialAd ad)
            {
                ad_showed = true;
            }
        });

        // Запускаем загрузку данных
        //ad_revarded_mytarget.load();
        ad_fullscreen_mytarget.load();
    }

    private void showAD(Intent intent_togo) {

        // Highest priority for myTarget, if it is failed, load AD from Heyzap:
        if (ad_fullscreen_mytarget_loaded) {
            ad_fullscreen_mytarget.show();
        } else {
            // show full screen bunner from Heyzap:
            com.heyzap.sdk.ads.InterstitialAd.display(this);
            ad_showed = true;
        }

        if (intent_togo != null) {

            final Intent intent_tmp = intent_togo;

            new CountDownTimer(200000, 100) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (ad_showed) {
                        Level1_Step1.this.finish();
                        count += 1;
                        intent_tmp.putExtra("count", count);

                        startActivity(intent_tmp);

                        this.cancel();
                    }
                }

                @Override
                public void onFinish() {
                    Level1_Step1.this.finish();
                    count += 1;
                    intent_tmp.putExtra("count", count);

                    startActivity(intent_tmp);
                }
            }.start();
        }
    }

    public void gotoNextLevel() {
        Intent intent = new Intent(this, Level2_Step1.class);

        // Show AD full screen blocks each 4 levels:
        if (4 * (count / 4) == count) {
            showAD(intent);
        }
    }

}

