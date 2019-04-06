package com.example.introsliderapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView imageViewSplash  ;
    TextView textViewSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageViewSplash = findViewById(R.id.image_splash);
        textViewSplash = findViewById(R.id.text_view_splash);
        Animation myAnim = AnimationUtils.loadAnimation(this,R.anim.myanim);

        imageViewSplash.startAnimation(myAnim);
        textViewSplash.startAnimation(myAnim);

        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(10000);
                    Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        myThread.start();
    }
}
