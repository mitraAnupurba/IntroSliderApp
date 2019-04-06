package com.example.introsliderapp;

import android.content.Context;
import android.content.SharedPreferences;

//class to handle the shared preferene:
public class PreferenceManager {

    private Context context;
    private SharedPreferences sharedPreferences;

    //constructor:
    public PreferenceManager(Context context){
        this.context = context;
        getSharedPreference();
    }

    private void getSharedPreference(){
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.my_preference),Context.MODE_PRIVATE);

    }

    //method for writing shared preferences:
    public void writePreference(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.my_preference_key),"INIT_OK");
        editor.commit();
    }

    //method for reading shared preferences:
    public boolean checkPreference(){
        boolean status = false;

        //condition to check whether the user opens the app for the first time:
        if(sharedPreferences.getString(context.getString(R.string.my_preference_key),"null").equals("null")){
            status = false;

        }
        else{
            status = true;
        }
        return status;
    }

    // method for clearing the shared preference:
    public void clearPreference(){
        sharedPreferences.edit().clear().commit();
    }

}
