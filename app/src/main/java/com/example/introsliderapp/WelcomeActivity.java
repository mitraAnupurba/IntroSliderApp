package com.example.introsliderapp;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

//this project has 2 activities, where the default launcher is main activity.
//we have to change the launcher activity to welcome activity, in manifests.xml file
//for that we have to remove the intent filter portion of main activity and put it in welcome activity

//Erlier this code was crasing because, we didnot add the drawable images in all the folders of drawable
//that is: drawable hdpi, mdpi, xhdpi, xxhdpi, xxxhdpi.
//the introductiion slider wil only be shown when the app is opened for the first time.
//thus to do that we use shared preferences.
public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mPager;
    private int[] layouts = {R.layout.first_slide, R.layout.second_slide, R.layout.third_slide, R.layout.fourth_slide};

    private MpagerAdapter mpagerAdapter ;

    //variables created for using the image view for dots:
    private LinearLayout dotsLayout;
    private ImageView[] dots;

    //variables for using the buttons:
    private Button skipButton, nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //condition to check whether the app was opened erlier using shared preference:
        if(new PreferenceManager(this).checkPreference()){
            loadDashBoard();
        }

        //code to make the status bar transparent
        if(Build.VERSION.SDK_INT >= 19){
            //that if the API version is greater than or equal to 19
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_welcome);

        //initialising the mpager- viewpager variable:
        mPager =  findViewById(R.id.viewPager);
        mpagerAdapter = new MpagerAdapter(layouts,this);
        mPager.setAdapter(mpagerAdapter);

        //initialising the Linear Layout:
        dotsLayout = (LinearLayout)findViewById(R.id.dots_layout);

        //initialising the buttons:
        skipButton = (Button)findViewById(R.id.button_skip);
        skipButton.setOnClickListener(this);

        nextButton = (Button)findViewById(R.id.button_next);
        nextButton.setOnClickListener(this);

        this.createDots(0);

        //in order to make the dots slide with the layouts, there should be listeners for the view pager
        //create addOnPageChangeListener for the view pager object - mpager:

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            //If the last slide is reached, then the next button should be set to start button
            public void onPageSelected(int i) {
                    createDots(i);
                    if(i == (layouts.length -1)){
                        nextButton.setText("START");
                        skipButton.setVisibility(View.INVISIBLE);
                    }
                    else{
                        nextButton.setText("NEXT");
                        skipButton.setVisibility(View.VISIBLE);
                    }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });



    }
    private void createDots(int current_position){
        if(dotsLayout != null){
            dotsLayout.removeAllViews();
        }
        //creating a new array for storing the dots. Initialising the array with the number of elements
        dots = new ImageView[layouts.length];

        //adding the dots to linear layout:
        for(int i=0;i<layouts.length ;i++){
            dots[i] = new ImageView(this);
            if(i == current_position){
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots));
            }
            else{
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.inactive_dots));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);
            dotsLayout.addView(dots[i],params);
        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_next:
                loadNextSlide();
                break;
            case R.id.button_skip:
                loadDashBoard();
                //when the user clicks the skip button, we have to store the Shared preference
                new PreferenceManager(this).writePreference();
                break;
        }
    }

    //if the user clicks the skip button, the app will load the login activity:
    //method for SKIP button click:
    private void loadDashBoard(){
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    //if the user creates  the next button, the next slide will open :
    //method for next button click:
    private void loadNextSlide(){
        int nextSlide = mPager.getCurrentItem()+1;
        if(nextSlide< layouts.length){
            mPager.setCurrentItem(nextSlide);
        }
        else{
            loadDashBoard();
            new PreferenceManager(this).writePreference();
        }
    }
}
