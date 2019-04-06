package com.example.introsliderapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


//error: - the app was crashing on clicking the back button
//how to fix : after starting the actiivity, the app should not finish. that is, we cant call this.finish()


//in order to add action bar only to specific items, apply theme to indivisual activities in the manifests.xml file

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadSlides(View view) {
        new PreferenceManager(this).clearPreference();
        startActivity(new Intent(this,WelcomeActivity.class));

    }

    public void loadInstitutes(View view) {
        startActivity(new Intent(this,FindInstituteActivity.class));

    }

    public void loadInstituteLogin(View view) {

        startActivity(new Intent(this,InstituteLoginActivity.class));

    }

    public void loadStudentLogin(View view) {

        startActivity(new Intent(this,StudentLoginActivity.class));

    }

    public void loadParentLogin(View view) {

        startActivity(new Intent(this,ParentLoginActivity.class));

    }

    public void loadAdminLogin(View view) {

        startActivity(new Intent(this,AdminLoginActivity.class));

    }

    public void loadExamPortal(View view) {

        startActivity(new Intent(this,ExamPortalActivity.class));

    }


    public void loadAppSettings(View view) {

        startActivity(new Intent(this,AppSettings.class));

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //the set Cancellable method doesnt allow us to cancel the dialog box without choosing yes or no
        builder.setMessage("Are you sure you want to exit the app?").setCancelable(false)
        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                MainActivity.super.onBackPressed();
            }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }) ;
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //if we use this, then the app crashes on clicking the exit button:
        //super.onBackPressed();
    }
}
