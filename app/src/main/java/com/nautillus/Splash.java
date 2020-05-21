package com.nautillus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    ImageView eng;
    TextView logo, slogan;
    LinearLayout splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        splash  = findViewById(R.id.splash);
        eng     = findViewById(R.id.eng);
        logo    = findViewById(R.id.logo);
        slogan  = findViewById(R.id.slogan);

        logo.setAlpha(0);
        slogan.setAlpha(0);
        slogan.setTranslationX(-225);

        eng.animate().rotation(-360).setDuration(2800).setStartDelay(0).start();
        logo.animate().alpha(1).setDuration(2800).setStartDelay(200).start();
        slogan.animate().alpha(1).setDuration(2800).setStartDelay(200).start();
        slogan.animate().translationX(0).setDuration(2500).setStartDelay(200).start();

        splash.animate().alpha(0).setDuration(400).setStartDelay(4800).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent start = new Intent(getApplicationContext(), ActivityHome.class);
                startActivity(start);
                finish();
            }
        }, 5000);
    }
}
