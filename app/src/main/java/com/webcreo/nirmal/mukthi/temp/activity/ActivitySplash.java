package com.webcreo.nirmal.mukthi.temp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;

public class ActivitySplash extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        Tools.applyFontStyle(this, findViewById(android.R.id.content), Tools.FontTypeFace.font_Quattrocento, Tools.FontStyle.normal);

        Tools.applyFontStyle(this, findViewById(R.id.title), Tools.FontTypeFace.font_Quattrocento, Tools.FontStyle.bold);

        Tools.loadImageDrawable(this,(ImageView)findViewById(R.id.background),R.drawable.splash_background);
        /* New Handler to start the Menu-Activity
         * and close this ActivitySplash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(ActivitySplash.this,ActivityMain.class);
                ActivitySplash.this.startActivity(mainIntent);
                ActivitySplash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}